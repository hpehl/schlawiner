#!/usr/bin/env bash

SOURCE=src/main/proto
TARGET=src/main/resources/webroot

for filename in $SOURCE/*.proto; do
    protoc --proto_path=$SOURCE --js_out=import_style=commonjs:$TARGET --grpc-web_out=import_style=commonjs,mode=grpcwebtext:$TARGET $filename
done
