package org.jboss.schlawiner.server;

import com.google.protobuf.Timestamp;
import io.vertx.grpc.GrpcBidiExchange;
import org.jboss.schlawiner.server.chat.ChatServiceGrpc;
import org.jboss.schlawiner.server.chat.ClientMessage;
import org.jboss.schlawiner.server.chat.ServerMessage;

class ChatServiceImpl extends ChatServiceGrpc.ChatServiceVertxImplBase {

    @Override
    public void chat(GrpcBidiExchange<ClientMessage, ServerMessage> exchange) {
        exchange.handler(clientMessage -> {
            long millis = System.currentTimeMillis();
            Timestamp timestamp = Timestamp.newBuilder()
                .setSeconds(millis / 1000)
                .setNanos((int) ((millis % 1000) * 1000000))
                .build();
            ServerMessage serverMessage = ServerMessage.newBuilder()
                .setTimestamp(timestamp)
                .setMessage(clientMessage)
                .build();
            exchange.write(serverMessage);
            exchange.endHandler(v -> exchange.end());
        });
    }
}
