var EventDBItem = require('./../objects/EventDBItem');

exports.getNewsByOrgs = function(connection, response, userId){
	var eventDBList = [];
	var ufboQuery = "SELECT UFBO_ORGS FROM users_filters_by_org WHERE UFBO_U_ID="+userId;

	connection.query(ufboQuery, function (err, result, fields) {
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
	    	const query = "SELECT E_ID, E_O_ID, E_C_ID, E_TEXT, E_TITLE, E_IMAGE_URL, E_START_DATE, E_CREATE_DATE, E_URL, E_FB_POST, E_TWEET, "+
			"organizations.O_NAME FROM events JOIN organizations ON events.E_O_ID = organizations.O_ID "+
			"JOIN categories ON events.E_C_ID = categories.C_ID where O_ID IN ("+result[0].UFBO_ORGS+")";
	    	connection.query(query, function(err, result, fields){
	    		if (err){
			    	response.status(503);
			    	response.json({
		                    status:503,
		                    message:'Data base error'
		                });
			    	throw err;
			    } else {
	    		    	if(result.length > 0){
				    		for(var i = 0; i < result.length; i++){
					    		var row = result[i];
					    		var eventDB = new EventDBItem(row.E_ID, row.E_O_ID, row.E_C_ID, 
					    			row.E_TEXT, row.E_TITLE, row.E_IMAGE_URL, row.E_START_DATE, row.E_CREATE_DATE, row.E_URL, row.E_FB_POST, row.O_NAME, row.E_TWEET);
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
	    	})
	    }
	  });
}

exports.getNewsFiltered = function(connection, response,  categoryId, userId){	
	var eventDBList = [];
	var ufboQuery = "SELECT UFBO_ORGS FROM users_filters_by_org WHERE UFBO_U_ID="+userId;
	connection.query(ufboQuery, function (err, result, fields) {
	    if (err){
	    	response.status(503);
	    	response.json({
                    status:503,
                    message:'Data base error'
                });
	    	throw err;
	    } 
	    else{
	    	const query = "SELECT E_ID, E_O_ID, E_C_ID, E_TEXT, E_TITLE, E_IMAGE_URL, E_START_DATE, E_CREATE_DATE, E_URL, E_FB_POST, E_TWEET, "+
			"organizations.O_NAME FROM events JOIN organizations ON events.E_O_ID = organizations.O_ID "+
			"JOIN categories ON events.E_C_ID = categories.C_ID where E_C_ID = "+categoryId+" and "+
			"E_O_ID IN ("+result[0].UFBO_ORGS+")";
	    	connection.query(query, function(err, result, fields){
	    		if (err){
			    	response.status(503);
			    	response.json({
		                    status:503,
		                    message:'Data base error'
		                });
			    	throw err;
			    } else {
    		    	if(result.length > 0){
			    		for(var i = 0; i < result.length; i++){
				    		var row = result[i];
				    		var eventDB = new EventDBItem(row.E_ID, row.E_O_ID, row.E_C_ID, 
				    			row.E_TEXT, row.E_TITLE, row.E_IMAGE_URL, row.E_START_DATE, row.E_CREATE_DATE, row.E_URL, row.E_FB_POST, row.O_NAME, row.E_TWEET);
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
	    	})
	    }
	  });
}

exports.getNewsBySearch = function(connection, response, userId, regex){
	var eventDBList = [];
	var ufboQuery = "SELECT UFBO_ORGS FROM users_filters_by_org WHERE UFBO_U_ID="+userId;

	connection.query(ufboQuery, function (err, result, fields) {
	    if (err){
	    	response.status(503);
	    	response.json({
                    status:503,
                    message:'Data base error'
                });
	    	throw err;
	    } 
	    else{
	    	const query = 'SELECT E_ID, E_O_ID, E_C_ID, E_TEXT, E_TITLE, E_IMAGE_URL, E_START_DATE, E_CREATE_DATE, E_URL, E_FB_POST, E_TWEET, '+
			'organizations.O_NAME FROM events JOIN organizations ON events.E_O_ID = organizations.O_ID '+
			'JOIN categories ON events.E_C_ID = categories.C_ID where O_ID IN ('+result[0].UFBO_ORGS+') AND  E_TEXT LIKE "%'+regex+'%"';
	    	connection.query(query, function(err, result, fields){
	    		if (err){
			    	response.status(503);
			    	response.json({
		                    status:503,
		                    message:'Data base error'
		                });
			    	throw err;
			    } else {
	    		    	if(result.length > 0){
				    		for(var i = 0; i < result.length; i++){
					    		var row = result[i];
					    		var eventDB = new EventDBItem(row.E_ID, row.E_O_ID, row.E_C_ID, 
					    			row.E_TEXT, row.E_TITLE, row.E_IMAGE_URL, row.E_START_DATE, row.E_CREATE_DATE, row.E_URL, row.E_FB_POST, row.O_NAME, row.E_TWEET);
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
	    	})
	    }
	  });
}