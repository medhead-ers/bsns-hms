<div align="center">
<img  width="75" src="project-icon.png" />
<br>
<br>
<h1>BSNS HMS - Hospital Management Service</h1>
</div>

<br>
<br>

Ce dépot contient le micro-service business de gestion des hopitaux utilisé dans le cadre du POC MedHead ERS.  

# Table of contents

- [Quick Start](#quick-start)
    - [Requirement :](#requirement-)
    - [Démarrage de l'application](#demarrage-de-lapplication)
    

### Requirement :

- Docker
- JDK 19

### Démarrage de l'application

Le lancement de l'application en local nécessite simplement la génération du package .jar et le lancement du docker compose : 

```shell
mvn clean package
docker compose up -d --build
```