var emojiStrip = require('emoji-strip')

exports.insertFBPosts = function(connection, postsList, organisationId, categoryId) {
		for(var i = 0; i < postsList.length; i++) {
			var post = postsList[i];
			if(post.message){
			  connection.query('INSERT INTO events (E_O_ID, E_C_ID, E_TEXT, E_START_DATE, E_CREATE_DATE, E_URL, E_FB_POST, E_FB_ID) '+
				 'VALUES ('+organisationId+','+categoryId+',"'+emojiStrip(post.message.replace(/\"/g, ""))+'","'+post.startDate+'", NOW(),"'+post.postUrl+'",'+true+',"'+post.id+'")', function(err){
			        if(err) {
			        	//console.log('Error during db insert query.On postId : '+post.id);
			        }
				});
		}
	}
	console.log('NEWS FOR ORGANISTATION (ID='+organisationId+') UPDATED');
}	


exports.insertPLNews = function(connection, postsList) {
	for(var i = 0; i < postsList.length; i++) {
		var post = postsList[i];
		  connection.query('INSERT INTO events (E_O_ID, E_C_ID, E_TEXT, E_TITLE, E_IMAGE_URL, E_START_DATE, E_CREATE_DATE, E_URL, E_FB_POST) '+
			 'VALUES ('+1+','+1+',"'+post.preview.replace(/\"/g, "")+'","'+post.title.replace(/\"/g, "")+'","'+post.image+'","'
			 +post.date+'",NOW(),"'+post.href+'",'+false+')', function(err){
		        if(err) {
		        	console.log('Error during db insert query. In insertPLNews');
		        }
		});
	}
	console.log('NEWS FOR ORGANISTATION pl.lodz.pl UPDATED');
}