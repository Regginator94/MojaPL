var jwt = require('jsonwebtoken');
const UserModel = require('./../objects/UserModel');
var secretKey = 'adssad1';

exports.userLogin = function(connection, response, email, password) {
	var query = 'SELECT * FROM users WHERE U_EMAIL = ? AND U_PASSWORD = ?'; //"SELECT EXISTS(SELECT * FROM users WHERE U_EMAIL LIKE '"+email+"' AND U_PASSWORD LIKE '"+password+"')";
	connection.query(query, [email, password],function (err, result, fields){
	    if (err){
	    	throw err;
	    } 
	    else{
	    	if(userLoginDataCorrect(result)){
	    	 var token = jwt.sign(JSON.parse(JSON.stringify(result[0])),secretKey,{
                    expiresIn:60
                });
                response.json({
                    status:true,
                    token:token
                })	
	    	} 
	    	else {
	    		response.setHeader('Content-Type', 'application/json');
	   	 		response.write(JSON.stringify("Incorrect login data"));
		    	response.end();
	    	}
	    }
   	});
}

exports.userLoginToken = function(response, token) {
	if(token){
        jwt.verify(token,secretKey,function(err,ress){
            if(err){
                response.status(500).send('Token Invalid');
            }else{
            	var decoded = jwt.verify(token, secretKey);
				response.status(500).send('Token Valid');
            }
        })
    }else{
        response.send('Please send a token')
    }
}

function authenticateUser(response, token){
	if(token){
        jwt.verify(token,secretKey,function(err,ress){
            if(err){
                response.status(500).send('Token Invalid');
            }else{
            	 response.status(500).send('Token Valid');
            }
        })
    }else{
        response.send('Please send a token')
    }
}

function userLoginDataCorrect(result){
	var queryResult = result[0];
	var queryKey = Object.keys(queryResult)[0];
	return queryResult[queryKey];
}