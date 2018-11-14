package org.jboss.schlawiner.server;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.grpc.GrpcBidiExchange;
import org.jboss.schlawiner.server.chat.ChatServiceGrpc;
import org.jboss.schlawiner.server.chat.ClientMessage;
import org.jboss.schlawiner.server.chat.ServerMessage;

class ChatServiceImpl extends ChatServiceGrpc.ChatServiceVertxImplBase {

    private static final Logger log = LoggerFactory.getLogger(ChatServiceImpl.class);
    private static Set<StreamObserver<ServerMessage>> observers = ConcurrentHashMap.newKeySet();

    @Override
    public void chat(GrpcBidiExchange<ClientMessage, ServerMessage> exchange) {
        observers.add(exchange.writeObserver());

        exchange
            .handler(clientMessage -> {
                log.info("Received chat message {0} from {1}",
                    clientMessage.getMessage(), clientMessage.getPlayer());
                ServerMessage serverMessage = ServerMessage.newBuilder()
                    .setTimestamp(now())
                    .setMessage(clientMessage)
                    .build();
                for (StreamObserver<ServerMessage> observer : observers) {
                    observer.onNext(serverMessage);
                }
            })
            .exceptionHandler(exception -> log.error("gRPC error", exception))
            .endHandler(v -> {
                log.info("gRPC end");
                exchange.end();
                observers.remove(exchange.writeObserver());
            });
    }

    private Timestamp now() {
        long millis = System.currentTimeMillis();
        return Timestamp.newBuilder()
            .setSeconds(millis / 1000)
            .setNanos((int) ((millis % 1000) * 1000000))
            .build();
    }
}
