var mysql      = require('mysql');
var jwt=require('jsonwebtoken');
const NewsInserts = require('./NewsInserts');
const NewsGetters = require('./NewsGetters');
const UserLoginMethods = require('./UserLoginMethods');
const UserRegistration = require('./UserRegistration');

var secretKey = 'adssad1';

var connection = mysql.createConnection({
  host     : 'localhost',
  user     : 'root',
  password : 'root',
  database : 'mojapl_db',
});

setInterval(function () {
    connection.query('SELECT 1');
    console.log('MySQL Connection keeper RUN');
}, 80000);

connection.connect(function(err) {
  if(err)
 console.log(err);
});

exports.getData = function() {
	request.headers.token
	connection.query('SELECT * from organizations', function(err, rows, fields) {
	  if (!err)
	    console.log('The solution is: ', rows);
	  else
	    console.log('Error while performing Query.');
	});
}

exports.insertFBPosts = function(postsList) {
	NewsInserts.insertFBPosts(connection, postsList);
}

exports.insertPLNews = function(postsList) {
	NewsInserts.insertPLNews(connection, postsList);
}

exports.getNews = function(response, token) {	
	//if(authenticateUser(response, token)){
		NewsGetters.getNews(connection, response);
	//}
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


function authenticateUser(response, token){
		if(token){
        jwt.verify(token,secretKey,function(err,ress){
            if(err){
                response.status(403);
                response.json({
                    status:false,
                    message:'Token invalid'
                })
                //console.log('User incorrect token login, email :'+email);
            }else{
            	response.status(200);
                response.json({
                    status:true,
                    message:'Valid token. Logged'
                })	
                //console.log('User correct token login, email :'+email);
            }
        })
    }else{
       response.status(511);
        response.json({
            status:false,
            message:'Token is required'
        })	
        //console.log('User incorrect token login, email :'+email);
    }
}