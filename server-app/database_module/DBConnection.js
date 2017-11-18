var mysql      = require('mysql');
var jwt = require('jsonwebtoken');
const NewsInserts = require('./NewsInserts');
const NewsGetters = require('./NewsGetters');
const UserLoginMethods = require('./UserLoginMethods');
const UserRegistration = require('./UserRegistration');
const UserModel = require('./../objects/UserModel');

var secretKey = 'adssad1';

// var connection = mysql.createConnection({
//   host     : 'localhost',
//   user     : 'root',
//   password : 'root',
//   database : 'mojapl_db',
// });

var connection = mysql.createConnection({
  host     : 'us-cdbr-iron-east-05.cleardb.net',
  user     : 'bf038d7d725ad7',
  password : '0dfef14d',
  database : 'heroku_cd344eaef5f5056',
});

setInterval(function () {
    connection.query('SELECT 1');
}, 12000);

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

exports.insertFBPosts = function(postsList, organisationId, categoryId) {
	NewsInserts.insertFBPosts(connection, postsList, organisationId, categoryId);
}

exports.insertPLNews = function(postsList) {
	NewsInserts.insertPLNews(connection, postsList);
}

exports.getNews = function(response, token, filters) {  
  //decodeUserFromToken(token);
  if(authenticateUser(response, token)){
    NewsGetters.getNewsByOrganisationFilter(connection, response, filters);
  } else{
       response.status(511);
        response.json({
            status:false,
            message:'Token is required'
        })  
        //console.log('User incorrect token login, email :'+email);
    }
}

exports.getNewsFiltered = function(response, token, categoryId) {  
  //decodeUserFromToken(token);
  if(authenticateUser(response, token)){
     var user = decodeUserToken(token);
    NewsGetters.getNewsFiltered(connection, response, categoryId, user.id);
  } else{
       response.status(511);
        response.json({
            status:false,
            message:'Token is required'
        })  
        //console.log('User incorrect token login, email :'+email);
    }
}

exports.getNewsByOrganisationFilter = function(response, token) {  
  if(authenticateUser(response, token)){
   
    var user = decodeUserToken(token);
    NewsGetters.getNewsByOrgs(connection, response, user.id);
    //NewsGetters.getNewsByOrganisationFilter(connection, response, organisations);
  } else{
       response.status(511);
        response.json({
            status:false,
            message:'Token is required'
        })  
    }
}

exports.getNewsByCategoryFilter = function(response, token, filters) {	
  //decodeUserFromToken(token);
	if(authenticateUser(response, token)){
		NewsGetters.getNewsByCategoryFilter(connection, response, filters);
	} else{
       response.status(511);
        response.json({
            status:false,
            message:'Token is required'
        })  
        //console.log('User incorrect token login, email :'+email);
    }
}

exports.addUser = function(response, request){
	UserRegistration.addUser(connection, response, request);
}

exports.userLogin = function(response, request) {
	UserLoginMethods.userLogin(connection, response, request);
}

exports.userLoginToken = function(response, request) {
	UserLoginMethods.userLoginToken(connection, response, request);
}

function authenticateUser(response, token){
	if(token){
        return jwt.verify(token,secretKey,function(err,ress){
            if(err){
				return false;
            } else{
            	return true;
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

function decodeUserToken(token){
    decoded = jwt.verify(token, secretKey);
    user = new UserModel(decoded.U_ID, decoded.U_EMAIL, decoded.U_PASSWORD, decoded.U_CREATE_DATE, decoded.LAST_LOGIN_DATE);
    return user;
}