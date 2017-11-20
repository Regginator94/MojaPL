var mysql = require('mysql');
var jwt = require('jsonwebtoken');
const NewsInserts = require('./NewsInserts');
const NewsGetters = require('./NewsGetters');
const UserLoginMethods = require('./UserLoginMethods');
const UserRegistration = require('./UserRegistration');
const FiltersManagement = require('./FiltersManagement');
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
}, 18000);

connection.connect(function(err) {
  if(err)
 console.log(err);
});

exports.insertFBPosts = function(postsList, organisationId, categoryId) {
	NewsInserts.insertFBPosts(connection, postsList, organisationId, categoryId);
}

exports.insertPLNews = function(postsList) {
	NewsInserts.insertPLNews(connection, postsList);
}

exports.getNewsFiltered = function(response, token, categoryId) {  
  if(authenticateUser(response, token)){
    var user = decodeUserToken(token);
    NewsGetters.getNewsFiltered(connection, response, categoryId, user.id);
  } else{
    tokenIsRequiredResponse(response);
  } 
}

exports.getNewsByOrganisationFilter = function(response, token) {  
  if(authenticateUser(response, token)){ 
    var user = decodeUserToken(token);
    NewsGetters.getNewsByOrgs(connection, response, user.id);
  } else{
    tokenIsRequiredResponse(response);
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

exports.updateFilters = function(response, request){
    if(authenticateUser(response, request.headers.token)){
      var user = decodeUserToken(request.headers.token);
      FiltersManagement.updateFilters(connection, response, request);
  } else{
    tokenIsRequiredResponse(response);
  } 
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
      tokenIsRequiredResponse(response);
    }
}

function decodeUserToken(token){
  decoded = jwt.verify(token, secretKey);
  user = new UserModel(decoded.U_ID, decoded.U_EMAIL, decoded.U_PASSWORD, decoded.U_CREATE_DATE, decoded.LAST_LOGIN_DATE);
  return user;
}

function tokenIsRequiredResponse(response){
 response.status(511);
  response.json({
    status:false,
    message:'Token is required'
  })  
}