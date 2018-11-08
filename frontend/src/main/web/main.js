// external dependencies
require('normalize-css/normalize.css')
require('@fortawesome/fontawesome-free/less/fontawesome.less')
require('@fortawesome/fontawesome-free/less/brands.less')
require('@fortawesome/fontawesome-free/less/regular.less')
require('@fortawesome/fontawesome-free/less/solid.less')
require('tingle.js/src/tingle.css')
require('tingle.js/src/tingle.js')
require('raphael/raphael.js')

// generated gRPC dependencies
const {ChatServiceClient} = require('./grpc/chat_grpc_web_pb.js');
const {ClientMessage, ServerMessage} = require('./grpc/chat_pb.js');
var client = new ChatServiceClient('http://localhost:8080', null, null);
var clientMessage = new ClientMessage();
clientMessage.setMessage("Message in a bottle");
client.simpleChat(clientMessage, {}, (error, serverMessage) => {
    if (error) {
        console.log("gRPC error: " + error.code + ": " + error.message);
    } else {
        console.log("Received message from server: " + serverMessage.getMessage().getMessage());
    }
});

// Schlawiner dependencies
require('./less/schlawiner.less')
require('./favicon.ico');
require('./countdown.js');
