# comunio-stats-backend

Spring Boot backend for the comunio-stats project.

## Model

The four main entities that we have are: Club, ClubSnapshot, Player and PlayerSnapshot. All of them have a id which is uniquely assigned by the persistence backend. The club entity does have another unique attribute which is `name`, the player entity's unique attribute is `link`. These attributes are assumed to be unique, but they are not validated by the backend towards their uniqueness.

ClubSnapshot and Club do have a many-to-one relationship which means that a club entity is a parent to n clubSnapshot entities. The same relation for PlayerSnapshot and Player.

## Docker build

The spring-boot-maven-plugin is able to create a docker image for this spring boot application. However, it might be a security risk to let the plugin create docker images by the non root user. To still enable the current user to use docker, add the user to the docker group (`sudo groupadd docker` and `sudo usermod -aG docker $USER`, then log out and in again). See also: [Post-installation steps for Linux](https://docs.docker.com/engine/install/linux-postinstall/)

## Developer Mode

If you wish to start the spring application for development purposes, you can use the following maven command to do so with an temporary h2 database:
`./mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.config.location=classpath:/dev.properties"`

The `dev.properties` file defines the database connection with an in-memory h2 instance.
