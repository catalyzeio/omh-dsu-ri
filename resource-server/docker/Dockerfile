FROM java:openjdk-8-jre
MAINTAINER Emerson Farrugia <emerson@openmhealth.org>

ENV SERVER_PREFIX /opt/omh-dsu-ri/resource-server
ENV PORT 8083
ENV MONGO_HOST omh-mongo
ENV MONGO_PORT 27017
ENV POSTGRES_HOST omh-postgres
ENV POSTGRES_PORT 5432
ENV POSTGRES_DATABASE omh
ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD postgres

RUN mkdir -p $SERVER_PREFIX
ADD resource-server.jar $SERVER_PREFIX/
EXPOSE 8083

CMD /usr/bin/java -jar $SERVER_PREFIX/resource-server.jar --spring.config.location=file:$SERVER_PREFIX/
