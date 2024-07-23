FROM azul/zulu-openjdk:17
WORKDIR /spring
COPY ./build/libs/idontwantwalk-0.0.1-SNAPSHOT.jar ./server.jar
ENTRYPOINT ["java", "-jar", "server.jar","--debug"]
