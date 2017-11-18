const uuidV1 = require('uuid/v1');

const POLITECHNIKA_ORG_ID = 1;
exports.addUser = function(connection, response, request){
	var email = request.body.email;
	var password = request.body.password;
	var facultyId = request.body.facultyId;
	var userToken = uuidV1();
	var query = "SELECT EXISTS(SELECT * FROM users WHERE U_EMAIL LIKE '"+email+"')";

	connection.query(query, function (err, result, fields) {
	    if (err){
	    	throw err;
	    	console.log('Incorrect registration, email :'+email);
    		response.status(500);
            response.json({
                status:false,
                message:'Server error'
            })
	    } 
	    else{
	    	if(!userExists(result)) {
	    		query = 'INSERT INTO users (U_EMAIL, U_PASSWORD, U_CREATE_DATE)  VALUES ("'+email+'", "'+password+'", NOW())';
	    		connection.query(query, function (err, result, fields) {
		    			if (err){
					    	throw err;
					    	console.log('Incorrect registration, email :'+email);
				    		response.status(500);
				            response.json({
				                status:false,
				                message:'Server error'
				            })
					    } 
					    else {
					    	query = 'INSERT INTO users_filters_by_org (UFBO_U_ID, UFBO_ORGS) VALUES ('+result.insertId+',"'+
					    	POLITECHNIKA_ORG_ID+','+facultyId+'")';
					    	connection.query(query, function (err, result, fields) {
			    			if (err){
						    	throw err;
						    	console.log('Incorrect registration, email :'+email);
					    		response.status(500);
					            response.json({
					                status:false,
					                message:'Server error'
					            })
						    } else {
		    		    		response.status(200);
			                	response.json({
			                   	 	status:true,
			                    	message:'User created'
			                	})	
						    }
					    });
		    		}
		    	});
	    	} else {
		    	console.log('Incorrect registration, email :'+email);
	    		response.status(403);
                response.json({
                    status:false,
                    message:'User already exists'
                })
	    	}
	    }
   	});
}

function userExists(result){
	var queryResult = result[0];
	var queryKey = Object.keys(queryResult)[0];
	return queryResult[queryKey];
}