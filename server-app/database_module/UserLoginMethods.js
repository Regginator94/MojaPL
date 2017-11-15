var jwt = require('jsonwebtoken');
var secretKey = 'adssad1';

exports.userLogin = function(connection, response, email, password) {
	var query = 'SELECT * FROM users WHERE U_EMAIL = ? AND U_PASSWORD = ?'; 
	connection.query(query, [email, password],function (err, result, fields){
       if(result.length > 0){
            if (err){
                throw err;
            } 
            else{
                if(userLoginDataCorrect(result)){
                 var token = jwt.sign(JSON.parse(JSON.stringify(result[0])),secretKey,{
                        expiresIn:604800
                    });
                    response.status(200);
                    response.json({
                        status:true,
                        token:token
                    })  
                    console.log('User correct login, email :'+email);
                } 
                else {
                    console.log('User incorrect login, email :'+email);
                    response.status(403);
                    response.json({
                        status:false,
                        message:'Incorrect login data'
                    })
                }
            } 
    } else {
            response.status(403);
                response.json({
                    status:false,
                    message:'Invalid user data.'
                })
        }

   	});
}

exports.userLoginToken = function(response, token) {
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

function authenticateUser(response, token){
	if(token){
        jwt.verify(token,secretKey,function(err,ress){
            if(err){
                response.status(403);
                response.json({
                    status:false,
                    message:'Token invalid'
                })
                //console.log('User token authentication invalid, email :'+email);
            }else{
            	response.status(200);
                response.json({
                    status:true,
                    message:'Token valid'
                })
                //console.log('User token authentication valid, email :'+email);
            }
        })
    }else{
    	response.status(511);
        response.json({
            status:false,
            message:'Token is required'
        })	
    }
}

function userLoginDataCorrect(result){
	var queryResult = result[0];
	var queryKey = Object.keys(queryResult)[0];
	return queryResult[queryKey];
}