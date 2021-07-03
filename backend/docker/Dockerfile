FROM adoptopenjdk/openjdk11:alpine
WORKDIR /home/app
COPY build/docker/layers/libs /home/app/libs
COPY build/docker/layers/resources /home/app/resources
COPY build/docker/layers/application.jar /home/app/application.jar
RUN addgroup app && adduser -G app app -D && chown -R app:app /home/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/home/app/application.jar"]
