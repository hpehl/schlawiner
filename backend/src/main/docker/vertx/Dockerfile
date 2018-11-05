FROM vertx/vertx3
LABEL maintainer="Harald Pehl <hpehl@redhat.com>"

ENV VERTICLE_NAME org.jboss.schlawiner.server.MainVerticle
ENV VERTICLE_FILE target/schlawiner-backend-0.5.jar

ENV VERTICLE_HOME /usr/verticles

EXPOSE 8081
EXPOSE 9090

COPY $VERTICLE_FILE $VERTICLE_HOME/

WORKDIR $VERTICLE_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec vertx run $VERTICLE_NAME -cp $VERTICLE_HOME/*"]
