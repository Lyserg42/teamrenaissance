# Weekly DB upgrade
*Scripts used to update current DB by comparing local Cards DB with Scryfall api*

Actions:
1. Get our Cards already registered
2. Get remote Cards from Scryfall api
3. Compare and create a list containing new Cards name
4. Request new cards info to Scryfall api
5. Register new cards into our DB

## 1. Configure your environement
It will basically install all mandatory dependencies from Python pip repo
```
./install.sh
```

## 2. Script to add in a crontab
```
./launch.sh
```

OR

```
python update_db.py
```
