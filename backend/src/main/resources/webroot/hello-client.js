const {HelloRequest, HelloReply} = require("./hello_pb");
const {GreeterClient} = require("./hello_grpc_web_pb");

var client = new GreeterClient("http://localhost:8080");

/**
 * @param {function():?} callback
 */
function runSayHello(name, callback) {
    client.sayHello({name: name}, {}, (err, response) => {
        console.log(response.message);
        callback(response.message);
    });
}

/**
 * @param {function():?} callback
 */
function runSayRepeatHello(name, count, callback) {
    var stream = client.sayRepeatHello({name: name, count: count}, {});
    stream.on("data", (response) => {
        console.log(response.message);
    });
    stream.on("end", () => {
        callback();
    });
}

/**
 * @param {function():?} callback
 */
function runSayHelloAfterDelay(name, callback) {
    var deadline = new Date();
    deadline.setSeconds(deadline.getSeconds() + 1);

    client.sayHelloAfterDelay({name: name}, {deadline: deadline.getTime()},
        (err, response) => {
            console.log("Got error, code = " + err.code + ", message = " + err.message);
            callback();
        });
}


