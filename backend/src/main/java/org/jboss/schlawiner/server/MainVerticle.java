package org.jboss.schlawiner.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

import static java.lang.String.format;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() {
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        // TODO add handler for REST endpoints, SockJS

        // static resources (GWT frontend)
        router.get("/schlawiner/*")
            .handler(event -> {
                event.response().end("Hello Schlawiner");
            })
            .handler(StaticHandler.create()
                .setWebRoot("")
                .setIndexPage("index.html"))
            .failureHandler(fh -> {
                String message;
                String uri = fh.request().uri();
                int statusCode = fh.statusCode();
                switch (statusCode) {
                    case 404:
                        message = format("404: '%s' not found!", uri);
                        break;
                    case 500:
                        message = format("500: Internal server error for '%s'!", uri);
                        break;
                    default:
                        message = format("Unknown error for '%s'!", uri);
                        break;
                }
                fh.response().setStatusCode(statusCode).end(message);
            });

        // fire up the server
        server.requestHandler(router::accept).listen(8080);
    }
}
