#!/usr/bin/env bash

SOURCE=././../backend/src/main/proto
TARGET=./src/main/web/grpc

rm -rf $TARGET
mkdir -p $TARGET
for filename in $SOURCE/*.proto; do
    echo -n "Processing $filename..."
    protoc --proto_path=$SOURCE \
           --js_out=import_style=commonjs:$TARGET \
           --grpc-web_out=import_style=commonjs,mode=grpcwebtext:$TARGET \
           $filename
    echo "DONE"
done
