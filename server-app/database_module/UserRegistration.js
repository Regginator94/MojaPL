const uuidV1 = require('uuid/v1');

exports.addUser = function(connection, response, email, password){
	var userToken = uuidV1();
	var query = "SELECT EXISTS(SELECT * FROM users WHERE U_EMAIL LIKE '"+email+"')";

	connection.query(query, function (err, result, fields) {
	    if (err){
	    	throw err;
	    } 
	    else{
	    	if(!userExists(result)) {
	    		query = 'INSERT INTO users (U_EMAIL, U_PASSWORD, U_CREATE_DATE, U_TOKEN)  VALUES ("'+email+'", "'+password+'", NOW() ,"'+userToken+'")';
	    		connection.query(query, function (err, result, fields) {
	    			if (err){
				    	throw err;
				    } 
				    else {
    		    		response.setHeader('Content-Type', 'application/json');
			   	 		response.write(JSON.stringify(userToken));
				    	response.end();
				    }
	    		});
	    	} else {
	    		response.setHeader('Content-Type', 'application/json');
	   	 		response.write(JSON.stringify("User already exists"));
		    	response.end();
	    	}
	    }
   	});
}

function userExists(result){
	var queryResult = result[0];
	var queryKey = Object.keys(queryResult)[0];
	return queryResult[queryKey];
}