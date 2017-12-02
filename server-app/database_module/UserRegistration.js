
const POLITECHNIKA_ORG_ID = 1;
exports.addUser = function(connection, response, request){
	var email = request.body.email;
	var password = request.body.password;
	var facultyId = request.body.facultyId;
	var query = "SELECT EXISTS(SELECT * FROM users WHERE U_EMAIL LIKE '"+email+"')";

	if(!validateEmail(email)){
		incorrectRegistrationEmail(response);
	}
	else if(!validatePassword(password)){
		incorrectRegistrationPassword(response);
	}
	else {
		connection.query(query, function (err, result, fields) {
			if (err){
				throw err;
				incorrectRegistrationResponse(response, email)
			} 
			else{
				if(!userExists(result)) {
					query = 'INSERT INTO users (U_EMAIL, U_PASSWORD, U_CREATE_DATE)  VALUES ("'+email+'", "'+password+'", NOW())';
					connection.query(query, function (err, result, fields) {
							if (err){
								throw err;
								incorrectRegistrationResponse(response, email)
							} 
							else {
								query = 'INSERT INTO users_filters_by_org (UFBO_U_ID, UFBO_ORGS) VALUES ('+result.insertId+',"'+
								POLITECHNIKA_ORG_ID+','+facultyId+'")';
								connection.query(query, function (err, result, fields) {
								if (err){
									throw err;
									incorrectRegistrationResponse(response, email);
								} else {
									userCreatedResponse(response)
								}
							});
						}
					});
				} else {
					userAlreadyExists(response, email);
				}
			}
		   });
	}
}

function validatePassword(password)
{
  // at least one number, one lowercase and one uppercase letter
  // at least six characters
  var re = /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}/;
  return re.test(password);
}


function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}

function incorrectRegistrationEmail(response){
	response.status(500);
    response.json({
        status:false,
        message:'Incorrect user email.'
    })
}

function incorrectRegistrationPassword(response){
	response.status(500);
    response.json({
        status:false,
        message:'Incorrect user password.'
    })
}

function userExists(result){
	var queryResult = result[0];
	var queryKey = Object.keys(queryResult)[0];
	return queryResult[queryKey];
}

function incorrectRegistrationResponse(response, email){
	console.log('Incorrect registration, email :'+email);
	response.status(500);
    response.json({
        status:false,
        message:'Server error'
    })
}

function userAlreadyExists(response, email){
	console.log('Incorrect registration, email :'+email);
	response.status(500);
    response.json({
        status:false,
        message:'User already exists error'
    })
}

function userCreatedResponse(response){
	response.status(200);
	response.json({
   	 	status:true,
    	message:'User created'
	})	
}