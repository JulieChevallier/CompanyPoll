# Company poll - Keyholder
## Installation Docker
### Requirements
- Docker
- Maven (version 3.6.3) + java openjdk17 minimum
- Git
### Guide
Toutes les commandes doivent être exécutées à la racine du projet GIT à l'exception de `git clone` qui permet de générer ce projet et de `cd ./scrutateur` qui permet de s'y rendre.
#### 0. Récupération du code source
```bash
git clone git@github.com:CompanyPoll/keyholder.git
```
#### 1. Compilation du projet
```bash
cd ./scrutateur
mvn clean compile assembly:single
```
Le .jar généré par la commande ci-dessus se trouve dans le dossier `./target`. Comme pour le serveur.
#### 2. Dockerization du server
Lancez en premier les keyholders !!!

```
docker compose create
```
va créer le conteneur et il faudra le lancer depuis l'interface graphique de docker. Pour exécuter directement le conteneur après le `compose`, utilisez la commande ci-dessous  :
```
docker compose up
```

*Et voilà c'est bon*
