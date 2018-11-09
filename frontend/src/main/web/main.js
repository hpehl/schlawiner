// external dependencies
require('normalize-css/normalize.css')
require('@fortawesome/fontawesome-free/less/fontawesome.less')
require('@fortawesome/fontawesome-free/less/brands.less')
require('@fortawesome/fontawesome-free/less/regular.less')
require('@fortawesome/fontawesome-free/less/solid.less')
require('tingle.js/src/tingle.css')
require('tingle.js/src/tingle.js')
require('raphael/raphael.js')

// gRPC dependencies
const {ChatServiceClient} = require('./grpc/chat_grpc_web_pb.js');
proto.chat.ChatServiceClient = ChatServiceClient; // re-export under namespace "proto.chat"

// Schlawiner dependencies
require('./less/schlawiner.less')
require('./favicon.ico');
require('./countdown.js');
