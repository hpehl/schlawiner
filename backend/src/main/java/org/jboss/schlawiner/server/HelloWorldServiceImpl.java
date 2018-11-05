package org.jboss.schlawiner.server;

import io.vertx.core.Future;
import io.vertx.grpc.GrpcWriteStream;
import org.jboss.schlawiner.server.hello.GreeterGrpc;
import org.jboss.schlawiner.server.hello.HelloReply;
import org.jboss.schlawiner.server.hello.HelloRequest;
import org.jboss.schlawiner.server.hello.RepeatHelloRequest;

public class HelloWorldServiceImpl extends GreeterGrpc.GreeterVertxImplBase {

    @Override
    public void sayHello(HelloRequest request, Future<HelloReply> response) {
        response.complete(HelloReply.newBuilder().setMessage("Hello " + request.getName()).build());
    }

    @Override
    public void sayRepeatHello(RepeatHelloRequest request, GrpcWriteStream<HelloReply> response) {
        for (int i = 0; i < request.getCount(); i++) {
            response.write(HelloReply.newBuilder().setMessage("Hello #" + i + " " + request.getName()).build());
        }
        response.end();
    }

    @Override
    public void sayHelloAfterDelay(HelloRequest request, Future<HelloReply> response) {
        response.complete(HelloReply.newBuilder().setMessage("Hello " + request.getName()).build());
    }
}
