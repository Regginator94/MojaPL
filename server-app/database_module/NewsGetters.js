var EventDBItem = require('./../objects/EventDBItem');

exports.getNews = function(connection, response, filters){	
	lastLoginDate = '2012-12-25 00:00:00';
	var organizationsId = filters; 
	var eventDBList = [];
	var query = "SELECT E_ID, E_O_ID, E_C_ID, E_TEXT, E_TITLE, E_IMAGE_URL, E_CREATE_DATE, E_URL, E_FB_POST,"+
		"organizations.O_NAME FROM events join organizations on events.E_O_ID = organizations.O_ID "+
		"WHERE E_O_ID in ("+filters+") AND E_CREATE_DATE >='"+lastLoginDate+"'";

	connection.query(query, function (err, result, fields) {
	    if (err){
	    	response.status(503);
	    	response.json({
                    status:503,
                    message:'Data base error'
                });
	    	console.log(err);
	    	throw err;
	    } 
	    else{
	    	if(result.length > 0){
	    		for(var i = 0; i < result.length; i++){
		    		var row = result[i];
		    		var eventDB = new EventDBItem(row.E_ID, row.E_O_ID, row.E_C_ID, 
		    			row.E_TEXT, row.E_TITLE, row.E_IMAGE_URL, row.E_CREATE_DATE, row.E_URL, row.E_FB_POST, row.O_NAME);
		    		eventDBList.push(eventDB);
		    	}
		    	response.status(200);
		    	response.json(eventDBList)	
	    	} else {
	    		response.status(503);
		    	response.json({
	                    status:503,
	                    message:'No data for those filters.'
	                });
	    	}
	    }
	  });
}

exports.getNewsByOrgs = function(connection, response, userId){
	var eventDBList = [];
	var query = "SELECT E_ID, E_O_ID, E_C_ID, E_TEXT, E_TITLE, E_IMAGE_URL, E_CREATE_DATE, E_URL, E_FB_POST,"+
		"organizations.O_NAME FROM events JOIN organizations ON events.E_O_ID = organizations.O_ID "+
		"JOIN categories ON events.E_C_ID = categories.C_ID where O_ID IN (SELECT UFBO_ORGS FROM users_filters_by_org WHERE UFBO_U_ID="+userId+")";

	connection.query(query, function (err, result, fields) {
	    if (err){
	    	response.status(503);
	    	response.json({
                    status:503,
                    message:'Data base error'
                });
	    	console.log(err);
	    	throw err;
	    } 
	    else{
	    	if(result.length > 0){
	    		for(var i = 0; i < result.length; i++){
		    		var row = result[i];
		    		var eventDB = new EventDBItem(row.E_ID, row.E_O_ID, row.E_C_ID, 
		    			row.E_TEXT, row.E_TITLE, row.E_IMAGE_URL, row.E_CREATE_DATE, row.E_URL, row.E_FB_POST, row.O_NAME);
		    		eventDBList.push(eventDB);
		    	}
		    	response.status(200);
		    	response.json(eventDBList)	
	    	} else {
	    		response.status(503);
		    	response.json({
	                    status:503,
	                    message:'No data for those filters.'
	                });
	    	}
	    }
	  });
}

exports.getNewsFiltered = function(connection, response,  categoryId, userId){	
	var eventDBList = [];
	var query = "SELECT E_ID, E_O_ID, E_C_ID, E_TEXT, E_TITLE, E_IMAGE_URL, E_CREATE_DATE, E_URL, E_FB_POST,"+
		"organizations.O_NAME FROM events join organizations on events.E_O_ID = organizations.O_ID "+
		"join categories on events.E_C_ID = categories.C_ID where E_C_ID = "+categoryId+" and "+
		"O_ID IN (SELECT UFBO_ORGS FROM users_filters_by_org WHERE UFBO_U_ID="+userId+")";

	connection.query(query, function (err, result, fields) {
		console.log(result);
	    if (err){
	    	response.status(503);
	    	response.json({
                    status:503,
                    message:'Data base error'
                });
	    	console.log(err);
	    	throw err;
	    } 
	    else{
	    	if(result.length > 0){
	    		for(var i = 0; i < result.length; i++){
		    		var row = result[i];
		    		var eventDB = new EventDBItem(row.E_ID, row.E_O_ID, row.E_C_ID, 
		    			row.E_TEXT, row.E_TITLE, row.E_IMAGE_URL, row.E_CREATE_DATE, row.E_URL, row.E_FB_POST, row.O_NAME);
		    		eventDBList.push(eventDB);
		    	}
		    	response.status(200);
		    	response.json(eventDBList)	
	    	} else {
	    		response.status(503);
		    	response.json({
	                    status:503,
	                    message:'No data for those filters.'
	                });
	    	}
	    }
	  });
}

exports.getNewsByCategoryFilter = function(connection, response, filters){	
	lastLoginDate = '2012-12-25 00:00:00';
	var organizationsId = filters; 
	var eventDBList = [];
	var query = "SELECT E_ID, E_O_ID, E_C_ID, E_TEXT, E_TITLE, E_IMAGE_URL, E_CREATE_DATE, E_URL, E_FB_POST,"+
		"organizations.O_NAME FROM events join organizations on events.E_O_ID = organizations.O_ID "+
		"WHERE E_C_ID ="+filters+" AND E_CREATE_DATE >='"+lastLoginDate+"'";

	connection.query(query, function (err, result, fields) {
	    if (err){
	    	response.status(503);
	    	response.json({
                    status:503,
                    message:'Data base error'
                });
	    	console.log(err);
	    	throw err;
	    } 
	    else{
	    	if(result.length > 1){
	    		for(var i = 0; i < result.length; i++){
		    		var row = result[i];
		    		var eventDB = new EventDBItem(row.E_ID, row.E_O_ID, row.E_C_ID, 
		    			row.E_TEXT, row.E_TITLE, row.E_IMAGE_URL, row.E_CREATE_DATE, row.E_URL, row.E_FB_POST, row.O_NAME);
		    		eventDBList.push(eventDB);
		    	}
		    	response.status(200);
		    	response.json(eventDBList)	
	    	} else {
	    		response.status(503);
		    	response.json({
	                    status:503,
	                    message:'No data for those filters.'
	                });
	    	}
	    }
	  });
}

exports.getNewsByOrganisationFilter = function(connection, response, organisations){	
	lastLoginDate = '2012-12-25 00:00:00';
	var eventDBList = [];
	var query = "SELECT E_ID, E_O_ID, E_C_ID, E_TEXT, E_TITLE, E_IMAGE_URL, E_CREATE_DATE, E_URL, E_FB_POST,"+
		"organizations.O_NAME FROM events join organizations on events.E_O_ID = organizations.O_ID "+
		"WHERE E_O_ID in ("+organisations+") AND E_CREATE_DATE >='"+lastLoginDate+"'";

	connection.query(query, function (err, result, fields) {
	    if (err){
	    	response.status(503);
	    	response.json({
                    status:503,
                    message:'Data base error'
                });
	    	console.log(err);
	    	throw err;
	    } 
	    else{
	    	if(result.length > 0){
	    		for(var i = 0; i < result.length; i++){
		    		var row = result[i];
		    		var eventDB = new EventDBItem(row.E_ID, row.E_O_ID, row.E_C_ID, 
		    			row.E_TEXT, row.E_TITLE, row.E_IMAGE_URL, row.E_CREATE_DATE, row.E_URL, row.E_FB_POST, row.O_NAME);
		    		eventDBList.push(eventDB);
		    	}
		    	response.status(200);
		    	response.json(eventDBList)	
	    	} else {
	    		response.status(503);
		    	response.json({
	                    status:503,
	                    message:'No data for those filters.'
	                });
	    	}
	    }
	  });
}