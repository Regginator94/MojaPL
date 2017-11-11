var emojiStrip = require('emoji-strip')


exports.insertFBPosts = function(connection, postsList) {
	// connection.connect();
	for(var i = 0; i < postsList.length; i++) {
		var post = postsList[i];
		  connection.query('INSERT INTO events (E_O_ID, E_C_ID, E_TEXT, E_CREATE_DATE, E_URL, E_FB_POST, E_FB_ID) '+
			 'VALUES ('+1+','+1+',"'+emojiStrip(post.message.replace(/\"/g, ""))+'","'+post.createdTime+'","'+post.postUrl+'",'+true+',"'+post.id+'")');
	}
	console.log('POSTS ADDED TO DB');
	// connection.end();
}


exports.insertPLNews = function(connection, postsList) {
	// connection.connect();
	for(var i = 0; i < postsList.length; i++) {
		var post = postsList[i];
		  connection.query('INSERT INTO events (E_O_ID, E_C_ID, E_TEXT, E_TITLE, E_IMAGE_URL, E_CREATE_DATE, E_URL, E_FB_POST) '+
			 'VALUES ('+1+','+1+',"'+post.preview.replace(/\"/g, "")+'","'+post.title.replace(/\"/g, "")+'","'+post.image+'","'+post.date+'","'+post.href+'",'+false+')');
	}
	console.log('POSTS ADDED TO DB');
}