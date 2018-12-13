FROM jboss/base-jdk
VOLUME /tmp
COPY target/sparklearning-0.0.1-SNAPSHOT.jar.original /opt/app/sparklearning.jar
ENTRYPOINT java -jar /opt/app/sparklearning.jar