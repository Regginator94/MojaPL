var jwt = require('jsonwebtoken');
var secretKey = 'adssad1';
const UserModel = require('./../objects/UserModel');

exports.userLogin = function(connection, response, request) {
    var email = request.body.email;
    var password = request.body.password;
	var query = 'SELECT * FROM users WHERE U_EMAIL = ? AND U_PASSWORD = ?'; 
	connection.query(query, [email, password],function (err, result, fields){
        var user = result[0];
       if(result.length > 0){
            if (err){
                throw err;
            } 
            else{
                if(userLoginDataCorrect(result)){
                    var token = jwt.sign(JSON.parse(JSON.stringify(result[0])),secretKey,{
                        expiresIn:604800
                    });
                    userCorrectTokenLoginResponse(response, token, user);
                } 
                else {
                    userIncorrectLoginResponse(response, email)
                }
            } 
    } else {
            userIncorrectLoginResponse(response, email)
        }
   	});
}

exports.userLoginToken = function(connection, response, request) {
    var token = request.headers.token;  
    var email = request.body.email;
    var password = request.body.password;
	if(token) {
        jwt.verify(token,secretKey,function(err,ress){
            if(err){
                if(email != null && password != null){
                    exports.userLogin(connection, response, request);
                } else {
                    userIncorrectLoginResponse(response, email)
                }
            } else{        
                userCorrectTokenLoginResponse(response, request.headers.token);
            }
        })
    } else{
        userIncorrectLoginResponse(response, email);
    }
}

function userLoginDataCorrect(result){
	var queryResult = result[0];
	var queryKey = Object.keys(queryResult)[0];
	return queryResult[queryKey];
}

function decodeUserToken(token){
    decoded = jwt.verify(token, secretKey);
    user = new UserModel(decoded.U_ID, decoded.U_EMAIL, decoded.U_PASSWORD, decoded.U_CREATE_DATE, decoded.LAST_LOGIN_DATE);
    return user;
}

function userIncorrectLoginResponse(response, email){
    console.log('User incorrect login, email :'+email);
    response.status(403);
    response.json({
        status:false,
        token:''
    })
}

function userCorrectLoginResponse(response, user, token){
    response.status(200);
    response.json({
        status:true,
        userId: user.U_ID,  
        email: user.U_EMAIL,
        token:token
    })  
    console.log('User correct login, email :'+user.U_EMAIL);
}

function userCorrectTokenLoginResponse(response, token, user){
    var user = decodeUserToken(token);
    console.log('User correct login, email :'+user.email);
    response.status(200);
    response.json({
        status:true,
        userId:user.id,
        email:user.email,
        token:token
    })  
}