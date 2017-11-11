var mysql      = require('mysql');
var jwt=require('jsonwebtoken');
const NewsInserts = require('./NewsInserts');
const NewsGetters = require('./NewsGetters');
const UserLoginMethods = require('./UserLoginMethods');
const UserRegistration = require('./UserRegistration');

var secretKey = '123123d1';

var connection = mysql.createConnection({
  host     : 'localhost',
  user     : 'root',
  password : 'root',
  database : 'mojapl_db',
});

connection.connect(function(err) {
  if(err)
 console.log(err);
});

exports.getData = function() {
	// connection.connect();
	connection.query('SELECT * from organizations', function(err, rows, fields) {
	  if (!err)
	    console.log('The solution is: ', rows);
	  else
	    console.log('Error while performing Query.');
	});
	// connection.end();
}

exports.insertFBPosts = function(postsList) {
	NewsInserts.insertFBPosts(connection, postsList);
}

exports.insertPLNews = function(postsList) {
	NewsInserts.insertPLNews(connection, postsList);
}

exports.getNews = function(response) {	
	NewsGetters.getNews(connection, response);
}

exports.addUser = function(response, email, password){
	UserRegistration.addUser(connection, response, email, password);
}

exports.userLogin = function(response, email, password) {
	UserLoginMethods.userLogin(connection, response, email, password);
}

exports.userLoginToken = function(response, token) {
	UserLoginMethods.userLoginToken(response, token);
}