var jwt = require('jsonwebtoken');
const generatePassword = require("password-generator");
var secretKey = 'adssad1';
const UserModel = require('./../objects/UserModel');
const NodeMail = require('./NodeMail');

exports.changeEmail = function(connection, response, email, userId){
    const updateQuery = 'UPDATE users SET U_EMAIL ="'+email+'" WHERE U_ID ='+userId;
    connection.query(updateQuery, function(err, result, fields){
		if(err){
			throw err;
            response.status(403);
            response.json({
                status:false,
                message:'Email change failed.'
            })
		} else {
            response.status(200);
            response.json({
                status:true,
                message:'Email change successed'
            });
		}
	})
}

exports.changePassword = function(connection, response, password, userId){
    const updateQuery = 'UPDATE users SET U_PASSWORD ="'+password+'" WHERE U_ID ='+userId;
    connection.query(updateQuery, function(err, result, fields){
		if(err){
			throw err;
            response.status(403);
            response.json({
                status:false,
                message:'Password change failed.'
            })
		} else {
            response.status(200);
            response.json({
                status:true,
                message:'Password change successed'
            });
		}
	})
}

exports.passwordRepeater = function(connection, response, request) {
    const email = request.body.email;
    const query = "SELECT EXISTS(SELECT * FROM users WHERE U_EMAIL LIKE '"+email+"')";
    const newPassword = generatePassword(8, false);
    connection.query(query, function (err, result, fields) {
	    if (err){
	    	throw err;
	    	response.status(403);
            response.json({
                status:false,
                message:'User not exists in data base.'
            })
        } else{
            const updateQuery = 'UPDATE users SET U_PASSWORD ="'+newPassword+'" WHERE U_EMAIL ="'+email+'"';
            connection.query(updateQuery, function(err, result, fields){
                if(err){
                    throw err;
                    response.status(403);
                    response.json({
                        status:false,
                        message:'Password change failed.'
                    })
                } else {
                    NodeMail.sendReapet(response, email, newPassword)
                }
            })           
        }
    });
}
