# Uputstvo za pokretanje projekta

## Neophodni programi
Da bi se aplikacija pokrenula potrebno je instalirati sledeće programe:
* [Eclipse IDE for Enterprise Java and Web Developers](https://www.eclipse.org/downloads/packages/release/2021-03/r/eclipse-ide-enterprise-java-and-web-developers)
* [Node.js](https://nodejs.org/en/)
* [Apache TomEE plus 8](http://tomee.apache.org/download-ng.html)
* [Exist](https://github.com/eXist-db/exist/releases/tag/eXist-4.8.0)
* [Fuseki](https://archive.apache.org/dist/jena/binaries/apache-jena-fuseki-3.17.0.zip)

### Podesavanje Apache TomEE plus 8 servera

Kopirati [server.xml](./server.xml) u /conf direktorijum TomEE-a.

Preuzmi dva puta exist war fajl, prvi nazovite exist.war, a drugi exist2.war.
Potrebno je istu proceduru odraditi za fuseki, fajlove preimenujte u fuseki.war i fuseki2.war.
Sledece je potrebno premestiti sve prethodno navedene war fajlove u /webapps direktorijum TomEE-a.

Pokrenuti aplikativni server (/bin/startup.bat) i pristupiti exist i fuseki bazama na sledecim url-ovima: 
* http://localhost:8082/exist
* http://localhost:8082/exist2
* http://localhost:8082/fuseki
* http://localhost:8082/fuseki2

Na http://localhost:8082/fuseki kreirati dataset pod nazivom **Vakcinacija**, 
a na http://localhost:8082/fuseki2 dataset nazvati **Sluzbenik**.

### Pokretanje frontend aplikacije
```
cd vakcinacija-front 
npm install
npm start
```

```
cd sluzbenik-front
npm install
npm start
```

### Pokretanje backend aplikacije

Importovati projekte vakcinacija i sluzbenik u Eclipse IDE.

Odabrati opciju ```File -> Import -> Maven -> Existing Maven projects```.

