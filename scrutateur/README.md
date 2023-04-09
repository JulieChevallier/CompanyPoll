# Company poll - Server
## Installation Docker
### Requirements
- Docker
- Maven (version 3.6.3) + java openjdk17 minimum
- Git
### Guide
Toutes les commandes doivent être exécutées à la racine du projet GIT à l'exception de `git clone` qui permet de générer ce projet et de `cd ./scrutateur` qui permet de s'y rendre.
#### 0. Récupération du code source
```bash
git clone git@github.com:CompanyPoll/scrutateur.git
```
#### 1. Compilation du projet
```bash
cd ./scrutateur
mvn clean compile assembly:single
```
Le .jar généré par la commande ci-dessus se trouve dans le dossier `./target`. Comme pour le serveur.
#### 2. Dockerization du server
/!\ Il faut lancer les conteneurs des keyholders avant le srcutateur car le scrutateur va devoir générer une clé puis envoyer les bouts de cette clé aux différents keyholder. Si ils ne sont pas atteignable **le scrutateur va planter.**
```
docker compose create
```
va créer le conteneur et il faudra le lancer depuis l'interface graphique de docker. Pour exécuter directement le conteneur après le `compose`, utilisez la commande ci-dessous  :
```
docker compose up
```

*Et voilà c'est bon*
