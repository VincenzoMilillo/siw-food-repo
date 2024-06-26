# SIW Food 00

Questo è un progetto assegnato dal docente del corso di Sistemi Informativi Su Web, sviluppato con Spring Boot.

## Descrizione

Il progetto "SIW-Food-00" è un'applicazione web basata su Java 17 e Spring Boot 3.2.5. Utilizza diverse dipendenze di Spring Boot per gestire la persistenza dei dati, la sicurezza, la validazione e la presentazione dei dati tramite Thymeleaf. Il progetto è configurato per utilizzare un database PostgreSQL.

## Struttura del Progetto e tecnologie utilizzate 

**PostgreSQL:** Database relazionale utilizzato per memorizzare i dati dell'applicazione.
- **Thymeleaf:** Motore di template utilizzato per creare le pagine web dinamiche.
- **Java 17:** Linguaggio di programmazione utilizzato per sviluppare l'applicazione.
- **Maven:** Strumento di gestione dei progetti utilizzato per gestire le dipendenze e il ciclo di vita del progetto.

## Prerequisiti

- Java 17
- Maven
- PostgreSQL

## Dipendenze

Le principali dipendenze utilizzate nel progetto sono:

- `spring-boot-starter-data-jpa`: per la persistenza dei dati.
- `spring-boot-starter-thymeleaf`: per il motore di template.
- `spring-boot-starter-validation`: per la validazione dei dati.
- `spring-boot-starter-web`: per creare applicazioni web.
- `spring-boot-starter-security`: per la sicurezza dell'applicazione.
- `postgresql`: driver JDBC per PostgreSQL.
- `spring-boot-starter-test`: per il testing.

## Configurazione del Database

Assicurati di avere PostgreSQL installato e in esecuzione. Crea un database per l'applicazione e aggiorna le credenziali di connessione nel file `application.properties`.

Esempio di configurazione in `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tuo_database
spring.datasource.username=tuo_username
spring.datasource.password=tuo_password
spring.jpa.hibernate.ddl-auto=update

