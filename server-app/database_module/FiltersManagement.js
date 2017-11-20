const jwt = require('jsonwebtoken');
const secretKey = 'adssad1';
const UserModel = require('./../objects/UserModel');

exports.updateFilters = function(connection, response, request){
	const user = decodeUserToken(request.headers.token);
	const newFilters = request.query.filters;
	const updateQuery = 'UPDATE users_filters_by_org SET UFBO_ORGS ="'+newFilters+'" WHERE UFBO_U_ID ='+user.id;
	connection.query(updateQuery, function(err, result, fields){
		if(err){
			throw err;
            response.status(403);
            response.json({
                status:false,
                message:'Data base ERROR'
            })
		} else {
			console.log('UPDATED FITERS FOR '+user.email);
            response.status(200);
            response.json({
                status:true,
                message:'Filters updated'
            });
		}
	})
}

function decodeUserToken(token){
    decoded = jwt.verify(token, secretKey);
    user = new UserModel(decoded.U_ID, decoded.U_EMAIL, decoded.U_PASSWORD, decoded.U_CREATE_DATE, decoded.LAST_LOGIN_DATE);
    return user;
}