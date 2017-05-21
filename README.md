# UserTracking

Partie tracking du projet P08 pour Axians: "Mise en place d'un POV CMX".

## Descriptif du projet

### Objectif
Preuve de faisabilité d'une application de géolocalisation pour les Hospices Civils de Lyon. Conception en deux partie:
- Mise en place du réseau (CMX, Access Points)
- Réalisation de l'application mobile (sous Android)

### Fonctionnalités
2 types d'utilisateurs: guests et "clients". Les guests ont simplement un accès au Wi-Fi de l'établissement et ne nous serviront qu'à des fins statistiques:
Les "clients" se connectent via un portail avec leurs identifiants (voire, si besoin est, via les réseaux sociaux) et ont accès à l'application qui leur permettra:
+ De se repérer dans l'établissement
+ De se déplacer dans l'établissement 

## Explication des packages

#### Package usefulmethods

Contient l'ensemble des classes/méthodes qui vont être utilisées au cours du projet et communes aux classes existantes et futures, à savoir:
+ BaseClass
+ ConfigReader
+ HTTPMethods
+ Point

#### Package usertracking

Contient les classes permettant de récupérer les différentes informations sur l'utilisateur (position "absolue", lieu, username...)
