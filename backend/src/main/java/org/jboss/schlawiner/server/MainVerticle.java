package org.jboss.schlawiner.server;

import java.io.IOException;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.grpc.VertxServer;
import io.vertx.grpc.VertxServerBuilder;

public class MainVerticle extends AbstractVerticle {

    private VertxServer rpcServer;

    @Override
    public void start() throws IOException {
        // gRPC endpoint
        rpcServer = VertxServerBuilder
            .forPort(vertx, 9090)
            .addService(new ChatServiceImpl())
            .build();
        rpcServer.start();

        // static resources
        Router router = Router.router(vertx);
        router.get().handler(StaticHandler.create());
        vertx.createHttpServer()
            .requestHandler(router::accept)
            .listen(8081);
    }

    @Override
    public void stop() {
        if (rpcServer != null) {
            rpcServer.shutdown();
        }
    }
}
