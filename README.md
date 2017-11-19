![Image of Project](https://www.bleedingcool.com/wp-content/uploads/2017/04/Magic-The-Gathering-logo-800x279.png)

# Projet Teamrenaissance

> teamrenaissance.fr est un site web permettant aux participants de tournois Magic The Gathering d'organiser des échanges de cartes

![](http://www.unica-network.eu/sites/default/files/styles/university-logo-m/public/fr-paris-upmc.png)
*Projet scolaire réalisé pour la matière DAR (Développement d'Applications Réticulaires) pour le Master 2 de STL à l'UPMC*

#### Membres du projet
- Sarra HELLAL 
- Julien HENRY
- Vanessa SRIKANTHAN
- Jeanne GAMAIN

## Installation / Getting started

Comment rapidement installer le projet 

```bash
git clone https://github.com/jgamain/teamrenaissance.git
cd teamrenaissance
mvn package
```
Dans `teamrenaissance/target` se trouve le .WAR à placer dans votre tomcat

### Technologies utilisées
- **Serveur**:  Tomcat 8
- **Gestionnaire production**:  Maven
- **Langage côté serveur**:  Java
- **Langage côté client**:  Angularjs
- **Base de données**:  MySQL
- **ORM**:  Hibernate

### Mise à jour de la base de données
La base de données est mise à jour une fois par semaine. Une tâche crontab est planifiée.
Un script python a été créé dans ce but. Pour plus de détail voir le `README` qui se trouve dans `teamrenaissance/backend`

