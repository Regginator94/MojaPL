var mysql      = require('mysql');
var emojiStrip = require('emoji-strip')
var EventDBItem = require('./objects/EventDBItem');
const uuidV1 = require('uuid/v1');
var dateNow = new Date();

var connection = mysql.createConnection({
  host     : 'us-cdbr-iron-east-05.cleardb.net',
  user     : 'bf038d7d725ad7',
  password : '0dfef14d',
  database : 'heroku_cd344eaef5f5056',
});

exports.getData = function() {
	// connection.connect();
	connection.query('SELECT * from organizations', function(err, rows, fields) {
	  if (!err)
	    console.log('The solution is: ', rows);
	  else
	    console.log('Error while performing Query.');
	});
	// connection.end();
}

exports.insertFBPosts = function(postsList) {
	// connection.connect();
	for(var i = 0; i < postsList.length; i++) {
		var post = postsList[i];
		  connection.query('INSERT INTO events (E_O_ID, E_C_ID, E_TEXT, E_CREATE_DATE, E_URL, E_FB_POST, E_FB_ID) '+
			 'VALUES ('+1+','+1+',"'+emojiStrip(post.message.replace(/\"/g, ""))+'","'+post.createdTime+'","'+post.postUrl+'",'+true+',"'+post.id+'")');
	}
	console.log('POSTS ADDED TO DB');
	// connection.end();
}

exports.insertPLNews = function(postsList) {
	// connection.connect();
	for(var i = 0; i < postsList.length; i++) {
		var post = postsList[i];
		  connection.query('INSERT INTO events (E_O_ID, E_C_ID, E_TEXT, E_TITLE, E_IMAGE_URL, E_CREATE_DATE, E_URL, E_FB_POST) '+
			 'VALUES ('+1+','+1+',"'+post.preview.replace(/\"/g, "")+'","'+post.title.replace(/\"/g, "")+'","'+post.image+'","'+post.date+'","'+post.href+'",'+false+')');
	}
	console.log('POSTS ADDED TO DB');
}

exports.getNews = function(response, lastLoginDate){	
	lastLoginDate = '2012-12-25 00:00:00';
	var eventDBList = [];
	var query ="SELECT E_ID, E_O_ID, E_C_ID, E_TEXT, E_TITLE, E_IMAGE_URL, E_CREATE_DATE, E_URL, E_FB_POST FROM events WHERE E_CREATE_DATE >='"+lastLoginDate+"'";
	connection.query(query, function (err, result, fields) {
	    if (err){
	    	throw err;
	    } 
	    else{
    		for(var i = 0; i < result.length; i++){
	    		var row = result[i];
	    		var eventDB = new EventDBItem(row.E_ID, row.E_O_ID, row.E_C_ID, row.E_TEXT, row.E_TITLE, row.E_IMAGE_URL, row.E_CREATE_DATE, row.E_URL, row.E_FB_POST);
	    		eventDBList.push(eventDB);
	    	}
	        response.setHeader('Content-Type', 'application/json');
	        response.write(JSON.stringify(eventDBList));
	        response.end(); 	
	    }
	  });
}

exports.addUser = function(response, email, password){
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

exports.userLogin = function(response, email, password) {
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

function userExists(result){
	var queryResult = result[0];
	var queryKey = Object.keys(queryResult)[0];
	return queryResult[queryKey];
}

function userLoginDataCorrect(result){
	var queryResult = result[0];
	var queryKey = Object.keys(queryResult)[0];
	return queryResult[queryKey];
}