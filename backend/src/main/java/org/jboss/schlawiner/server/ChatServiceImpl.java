package org.jboss.schlawiner.server;

import com.google.protobuf.Timestamp;
import io.vertx.core.Future;
import io.vertx.grpc.GrpcBidiExchange;
import org.jboss.schlawiner.server.chat.ChatServiceGrpc;
import org.jboss.schlawiner.server.chat.ClientMessage;
import org.jboss.schlawiner.server.chat.ServerMessage;

class ChatServiceImpl extends ChatServiceGrpc.ChatServiceVertxImplBase {

    @Override
    public void simpleChat(ClientMessage request, Future<ServerMessage> response) {
        response.complete(ServerMessage.newBuilder()
            .setTimestamp(now())
            .setMessage(request)
            .build());
    }

    @Override
    public void chat(GrpcBidiExchange<ClientMessage, ServerMessage> exchange) {
        exchange.handler(clientMessage -> {
            ServerMessage serverMessage = ServerMessage.newBuilder()
                .setTimestamp(now())
                .setMessage(clientMessage)
                .build();
            exchange.write(serverMessage);
            exchange.endHandler(v -> exchange.end());
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
