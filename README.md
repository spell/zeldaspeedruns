# zeldaspeedruns

This is the next-generation backend API server for the [ZeldaSpeedRuns](https://www.zeldaspeedruns.com)
website, currently in active development and not yet deployed to production. While this software is
specifically written for use by ZSR, it is released as free, open-source software licensed under the GPLv3
and may be freely used by anyone who might find use for it.

## Quick Start Guide

ZeldaSpeedRuns is written in Java 15, to run and compile the project, you must have at least JDK v15 or 
higher installed. 

It is recommended that you use Docker to set up the environment, see the section about Docker below.

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
   
### Docker

To get started and the API server on your own local machine, it is recommended that you use Docker for
the easiest set up. It is highly recommended you start the environment using `docker-compose up --build`,
which will automatically do the following:

- Set up Keycloak with a realm and clients already preconfigured.
- Set up Mailhog as a lightweight SMTP server for testing.
- Set up Redis as caching storage.
- Set up a Postgres database.

### Notes

#### Docker environment provided services

The Docker environment provides a lot of services that the ZSR server relies on. Some of these services are
exposed on the following ports. To change these ports see *Changing the Docker environment* below.

- **Keycloak** exposes an administrative panel and all of its REST APIs at http://localhost:8080.
- A **PostgreSQL** server listens on `localhost:5432`.
- A **Redis** server listens on `localhost:6379`.
- **Mailhog** is used a lightweight development SMTP server. Any emails sent by Keycloak or the ZSR
  server are intercepted by this SMTP server. You can read the emails using Mailhog's web interface
  at http://localhost:8025.

#### Keycloak client secret

If you use the recommended Docker development environment, most of the settings should already be correct,
however, you may still have to update the Keycloak client secret in your `application.properties` file.

To do so, do the following after starting the Docker development environment:

1. Navigate to `http://localhost:8080/auth` and sign in with the username `admin` and password `zsr`.
1. Select the `zeldaspeedruns` realm in the dropdown menu at the top left.
1. Open the `Clients` tab in the navigation menu on the left.
1. Select the `zeldaspeedruns` client from the list and open the `Credentials` tab above.
1. Copy the Client Secret or click `Regenerate Client Secret` if the field is empty.
1. Update the `keycloak.credentials.secret` value in `application.properties` with the new secret.
1. Restart the ZSR API Server if it is running.

#### Changing the Docker environment

If you want to change the ports that are used by the Docker environment, you can create an override
file. Simply create a new file called `docker-compose.override.yml`. To change the port that Keycloak
is exposed on, for example, you would have the following contents.

```yaml
version: "3.8"

services:
  keycloak:
    ports:
      - "LOCAL_PORT:8080"
```

Where `LOCAL_PORT` is the port number you want to expose the Keycloak services on.

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
