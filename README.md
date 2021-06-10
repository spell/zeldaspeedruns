# zeldaspeedruns

This is the next-generation backend API server for the [ZeldaSpeedRuns](https://www.zeldaspeedruns.com)
website, currently in active development and not yet deployed to production. While this software is
specifically written for use by ZSR, it is released as free, open-source software licensed under the GPLv3
and may be freely used by anyone who might find use for it.

## Quick Start Guide

ZeldaSpeedRuns is written in Java 15, to run and compile the project, you must have at least JDK v15 or 
higher installed. 

### Instructions

1. Clone this repository.
1. Copy the example properties at `src/main/resources/application.properties.example` to
   `src/main/resources/application.properties`.
1. Replace the values in the `application.properties` file with the values of your configuration. 
   See the section *Notes* below. 
1. Test that everything is working correctly by issuing `mvnw test` from the command line.
1. You are now ready to run the project. To do so, either compile the project using the included Maven
   wrapper or run it from your favourite IDE using the `main` method of the `ZeldaSpeedRunsApplication`
   class.

**NOTE:** The server will automatically run the `schema.sql` file, you do not need to run this manually.

**RECOMMENDED:** JetBrains IntelliJ IDEA Community Edition is suggested for the best developer experience.

### Compile instructions

If you wish optionally compile the project, simply run `mvnw package` from the command line to build
the project. This requires you to have the JDK v15 or higher installed on your machine and locatable by
the Maven wrapper.

### Notes

#### Keycloak

The ZeldaSpeedRuns API Server relies on Keycloak for authentication and user management. You can set up Keycloak in
whatever way is convenient for you. The simplest way to do it would be to create a file named
`docker-compose.override.yml` with the following contents:

```yaml
version: "3.8"

services:
  keycloak:
    image: zeldaspeedruns/keycloak
    restart: always
    depends_on:
      - keycloak-db
    ports:
      - "8080:8080"
    environment:
      KEYCLOAK_USER: admin
      KEYCLOAK_PASSWORD: zsr
      KEYCLOAK_IMPORT: /tmp/realm-zeldaspeedruns.json
      DB_VENDOR: postgres
      DB_ADDR: keycloak-db
      DB_DATABASE: postgres
      DB_USER: postgres
      DB_PASSWORD: postgres
      JAVA_OPTS_APPEND: "-Dkeycloak.profile.feature.upload_scripts=enabled"
    volumes:
      - ./realm-zeldaspeedruns.json:/tmp/realm-zeldaspeedruns.json
  keycloak-db:
    image: postgres
    restart: always
    environment:
      POSTGRES_PASSWORD: postgres
```

If you do not want to go this route, you can set up Keycloak whatever way is convenient to you. Inside of Keycloak,
you can import the `realm-zeldaspeedruns.json` file to set up a realm, clients, groups and more. This will configure
Keycloak correctly. Lastly, you need to edit `application.properties` to match your Keycloak settings.

#### Changing the Docker environment

If you want to change the ports that are used by the Docker environment, you can create an override
file. Simply create a new file called `docker-compose.override.yml`. To change the port that PostgreSQL
is exposed on, for example, you would have the following contents.

```yaml
version: "3.8"

services:
  database:
    ports:
      - "LOCAL_PORT:5432"
```

Where `LOCAL_PORT` is the port number you want to expose the PostgreSQL service on.


#### localhost vs. 127.0.0.1

Always use `localhost` instead of `127.0.0.1` in configuration files, especially when the configuration
is related to Keycloak. Keycloak authentication will fail when the issuer does not match the expected
hostname, thus mixing the two can result in authentication breaking.

#### Windows

If you are developing on Windows, please make sure that you commit to this repository using UNIX line 
endings. Pull requests that do not use UNIX line endings will be rejected. If you installed Git for
Windows with the default settings, this will already be configured properly.

## Contributing

We welcome all contributions to help improve the ZeldaSpeedRuns API server. If you are unsure where to 
start it is recommended you have a look at the Issues page. New to Spring Boot or Java? Join the 
[ZSR Discord](https://discord.gg/bwwHyMxqyB) and contact Spell, who will be happy to mentor and guide 
you with some easy tasks to help you learn the technology stack used.

If you are not a programmer, but you have skills as a designer, translator, etc., you can may find it
more useful to contribute to the ZSR front-end client (the actual website!) You can find its repository
[here on GitHub](https://github.com/spell/zsr-client).
