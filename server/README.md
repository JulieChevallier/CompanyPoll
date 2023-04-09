# Company poll - Server
## Installation Docker
### Requirements
- Docker
- Maven (version 3.6.3) + java openjdk17 minimum
- Git
### Guide
Toutes les commandes doivent être exécutées à la racine du projet GIT à l'exception de `git clone` qui permet de générer ce projet et de `cd ./server` qui permet de s'y rendre.
#### 0. Récupération du code source
```bash
git clone git@github.com:CompanyPoll/server.git
```
#### 1. Compilation du projet
```bash
cd ./server
mvn clean compile assembly:single
```
Le .jar généré par la commande ci-dessus se trouve dans le dossier `./target`.
#### 2. Dockerization du server
/!\ avant de lancer la conteneurisation du server il faut s'assurer que le scrutateur a bien généré une paire de clés et a envoyé chacun de ses secrets à chaque `keyholder`. Une fois ce protocole validé on pourra conteneuriser le server. Cette contrainte est causée par le fait que le server doivent récupérer la clé publique auprès du server (on peut éventuellement refacto le code pour mieux gérer la récupération de la clé publique).

```
docker compose create
```
va créer le conteneur et il faudra le lancer depuis l'interface graphique de docker. Pour exécuter directement le conteneur après le `compose`, utilisez la commande ci-dessous  :
```
docker compose up
```

*Et voilà c'est bon*
