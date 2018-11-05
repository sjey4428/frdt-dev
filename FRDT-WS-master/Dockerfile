FROM java:8

# Create app directory
RUN mkdir -p /usr/src/frdt-ws
WORKDIR /usr/src/frdt-ws

COPY gradle/wrapper ./gradle/wrapper/
COPY gradlew ./
COPY build.gradle ./

COPY src ./src/

EXPOSE 8080

RUN chmod +x gradlew

ENTRYPOINT ["./gradlew"]
CMD ["bootRun" ]