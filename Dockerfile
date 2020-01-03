FROM library/openjdk:11-slim
EXPOSE $PORT
COPY target/producer-1.0.0.jar app.jar
ENTRYPOINT exec java -Xms724m -Xmx724m -jar $JAVA_OPTS /app.jar
