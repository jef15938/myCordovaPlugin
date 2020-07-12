var exec = require('cordova/exec');

exports.saySomething = function (arg0, success, error) {
  exec(success, error, 'HelloWorld', 'saySomething', [arg0]);
};

exports.getBattery = function (success, error) {
  exec(success, error, 'HelloWorld', 'getBattery');
};

exports.openCamera = function (success, error) {
  exec(success, error, 'HelloWorld', 'openCamera');
};

