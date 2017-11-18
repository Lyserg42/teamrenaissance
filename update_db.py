import mysql.connector
import json
import urllib2
import time
import requests
import unicodedata

config = {
  'user': 'teamrenaye',
  'password': '123Soleil',
  'host': 'teamrenaissance.fr',
  'database': 'teamrenaissance',
  'raise_on_warnings': False,
  'use_pure': False,
}

cnx = mysql.connector.connect(**config)

cursor = cnx.cursor()

query = ("SELECT name FROM Card ")

cursor.execute(query)
local_cards = list(cursor.fetchall())

local_cards = [val for sublist in local_cards for val in sublist]

remote_cards = json.load(urllib2.urlopen("https://api.scryfall.com/catalog/card-names"))['data']
time.sleep(1)

differences_list = list(set(remote_cards) - set(local_cards))

nb_insert = 0

for card_name in differences_list:
	try:
		time.sleep(1)
		api_request = """https://api.scryfall.com/cards/named?exact="{}" """.format(card_name).strip()
		print "Requesting... {}".format(api_request)
		json_object = requests.get(api_request).json()
		name = None
		image = None
		isStandard = json_object['legalities']['standard'] is 'legal'
		isLegacy = json_object['legalities']['legacy'] is 'legal'
		isModern = json_object['legalities']['modern'] is 'legal'
		if 'image_uris' not in json_object:
			if 'card_faces' not in json_object:
				continue
			else:
				name = json_object['name'].split("//")[0].strip()
				face_name = json_object['card_faces'][0]['name']
				if face_name is name:
					image = json_object['card_faces'][0]['image_uris']['normal']
				else:
					image = json_object['card_faces'][1]['image_uris']['normal']
		else:
			#traitement classique
			name = json_object['name']
			image = json_object['image_uris']['normal']
		#print "Card to be inserted => name={}, isStandard={}, isLegacy={}, isModern={}".format(name, isStandard, isLegacy, isModern)
		if name not in local_cards:
			query = """INSERT IGNORE INTO Card (name, picture, isStandardLegal, isModernLegal, isLegacyLegal) VALUES ("{}","{}",{},{},{})""".format(name, image, isStandard, isModern, isLegacy)
			print query
			cursor.execute(query)
			nb_insert = nb_insert + 1
	except UnicodeEncodeError:
		print "UNICODE ERROR"
        pass

cnx.commit()
print "{} cards inserted".format(nb_insert)

cursor.close()
cnx.close()

