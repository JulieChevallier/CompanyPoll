FROM openjdk:17
COPY target/urne-1-jar-with-dependencies.jar app.jar
COPY server.cer server.cer
COPY serverkeystore.jks serverkeystore.jks
COPY clienttruststore.jks clienttruststore.jks
ENTRYPOINT ["java","-jar","/app.jar"]