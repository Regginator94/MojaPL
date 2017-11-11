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

exports.authenticate = =function(req,res){
	var email=req.body.email;
    var password=req.body.password;
    connection.query('SELECT * FROM users WHERE email = ?',[email], function (error, results, fields) {
      if (error) {
          res.json({
            status:false,
            message:'there are some error with query'
            })
      }else{
        if(results.length >0){
            if(password==results[0].password){
                var token=jwt.sign(results[0],secretKey,{
                    expiresIn:5000
                });
                res.json({
                    status:true,
                    token:token
                })
            }else{
                res.json({
                  status:false,                  
                  message:"Email and password does not match"
                 });
            }
         
        }
        else{
          res.json({
              status:false,
            message:"Email does not exits"
          });
        }
      }
    });
}

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
	UserLoginMethods.userLoginToken(connection, response, token);
}