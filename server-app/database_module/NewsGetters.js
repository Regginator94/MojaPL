var EventDBItem = require('./../objects/EventDBItem');

exports.getNews = function(connection, response){	
	lastLoginDate = '2012-12-25 00:00:00';
	var organizationsId = '1,2'; 
	var eventDBList = [];
	var query = "SELECT E_ID, E_O_ID, E_C_ID, E_TEXT, E_TITLE, E_IMAGE_URL, E_CREATE_DATE, E_URL, E_FB_POST,"+
		"organizations.O_NAME FROM events join organizations on events.E_O_ID = organizations.O_ID "
		"where E_O_ID in ("+organizationsId+") AND E_CREATE_DATE >='"+lastLoginDate+"'";

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
    		for(var i = 0; i < result.length; i++){
	    		var row = result[i];
	    		var eventDB = new EventDBItem(row.E_ID, row.E_O_ID, row.E_C_ID, 
	    			row.E_TEXT, row.E_TITLE, row.E_IMAGE_URL, row.E_CREATE_DATE, row.E_URL, row.E_FB_POST, row.O_NAME);
	    		eventDBList.push(eventDB);
	    	}
	    	response.status(200);
	    	response.json(eventDBList)	
	    }
	  });
}