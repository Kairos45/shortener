# URL Shortener

Programme permettant de réduire une URL donnée. Les schémas actuellement supportés sont http et https.
Utilise Spring Boot et PostgreSQL pour persister les données.

# Démarrage rapide

## Builder le projet

Pour builder le projet, suivre ces étapes:

```shell script
git clone https://github.com/Kairos45/shortener.git
cd shortener
mvn install
```

## Déploiement

Pour déployer le projet:

```shell script
docker-compose up --build
```
**Une fois déployé, un swagger-ui permet de tester les différents endpoints: http://localhost:8080/swagger-ui/index.html**

## API Endpoints

### POST `/shorten`
Prend un objet JSON en entrée contenant l'URL à réduire. 

```json
{
  "longUrl":"url-a-raccourcir"
}
```

Validation réalisée sur l'URL: 
* UrlValidator de la librairie : apache-commons-validator

Retourne un objet JSON contenant la portion réduite.

```json
{
  "shortUrl":"xxxxxxxx"
}
```

### GET `/{shortUrlPart}`

Prend la portion réduite d'une URL précédemment générée pour retourner l'URL complète d'origine.

Validations faites:
* la chaine doit être sur 8 caractères
* pas d'espace dans la chaine
* composé uniquement de caractères alphanumérique

# Fonctionnement de la réduction de l'URL

L'URL réduite doit respecter plusieurs critères :
* Sans compter l’URL du service (domaine), une URL raccourcie ne devrait pas compter plus de 10 caractères.
* Deux URL complètes identiques doivent donner la même URL raccourcie

J'ai fais le choix d'utiliser une fonction de hashage (implémentation murmur3_32 de la librairie Guava de Google) pour réduire l'URL complète et permettre ainsi dans le cas ou une même URL est passé 2 fois sans être persisté d'être équivalente.
La chaine de caractères en sortie du hashage est sur 8 caractères.

Les 2 autres options que j'avais envisagée :
1. Générer une chaine de caractères aléatoire sur 10 caractères et vérifier en base qu'elle n'existe pas. Je n'avais cependant pas la certitude d'avoir l'assurance que 2 URLs équivalentes donnent la même URL réduite.
2. Encoder en Base62 l'identifiant de l'entrée crée en base de données. Cette solution apporte le désavantage d'être prédictible car cyclique et lorsque l'identifiant de la base atteint une grande valeur, la chaine réduite peut dépasser les 10 caractères.

# Pour aller plus loin
* Sécuriser les différentes infos sensibles en clair dans le projet (password de la db...)
* Réfléchir à une solution de réduction plus pereine (car possibilité de collisions, et ce n'est pas géré dans le code).
* Améliorer la partie base de données (créer une base dédiée, un user spécifique...)

