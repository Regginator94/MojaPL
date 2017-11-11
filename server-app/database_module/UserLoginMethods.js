exports.userLogin = function(connection, response, email, password) {
	var query = "SELECT EXISTS(SELECT * FROM users WHERE U_EMAIL LIKE '"+email+"' AND U_PASSWORD LIKE '"+password+"')";
	connection.query(query, function (err, result, fields) {
	    if (err){
	    	throw err;
	    } 
	    else{
	    	if(userLoginDataCorrect(result)){
	    		response.setHeader('Content-Type', 'application/json');
	   	 		response.write(JSON.stringify("Logged"));
		    	response.end();
	    	} 
	    	else {
	    		response.setHeader('Content-Type', 'application/json');
	   	 		response.write(JSON.stringify("Incorrect login data"));
		    	response.end();
	    	}
	    }
   	});
}

exports.userLoginToken = function(connection, response, token) {
	var query = "SELECT EXISTS(SELECT * FROM users WHERE U_TOKEN LIKE '"+token+"')";
	connection.query(query, function (err, result, fields) {
	    if (err){
	    	throw err;
	    } 
	    else{
	    	if(userLoginDataCorrect(result)){
	    		response.setHeader('Content-Type', 'application/json');
	   	 		response.write(JSON.stringify("Logged"));
		    	response.end();
	    	} 
	    	else {
	    		response.setHeader('Content-Type', 'application/json');
	   	 		response.write(JSON.stringify("Incorrect login data"));
		    	response.end();
	    	}
	    }
   	});
}

function userLoginDataCorrect(result){
	var queryResult = result[0];
	var queryKey = Object.keys(queryResult)[0];
	return queryResult[queryKey];
}