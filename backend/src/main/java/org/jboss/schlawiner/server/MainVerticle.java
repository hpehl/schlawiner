package org.jboss.schlawiner.server;

import java.io.IOException;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.grpc.VertxServer;
import io.vertx.grpc.VertxServerBuilder;

public class MainVerticle extends AbstractVerticle {

    private VertxServer rpcServer;
    private HttpServer httpServer;

    @Override
    public void start() throws IOException {
        // gRPC endpoint
        rpcServer = VertxServerBuilder
            .forPort(vertx, 9090)
            .addService(new ChatServiceImpl())
            .addService(new HelloWorldServiceImpl())
            .build();
        rpcServer.start();

        // static resources
        Router router = Router.router(vertx);
        router.get().handler(StaticHandler.create());
        httpServer = vertx.createHttpServer()
            .requestHandler(router::accept)
            .listen(8081);
    }

    @Override
    public void stop() {
        if (httpServer != null) {
            httpServer.close();
        }
        if (rpcServer != null) {
            rpcServer.shutdown();
        }
    }
}
