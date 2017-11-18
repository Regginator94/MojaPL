const request = require('request');
const https = require('https');
const fbUrl = 'https://graph.facebook.com/v2.9/';
const fbToken = '/posts?access_token=141819799794306|ytHLoE5jcAAjF7Mgg3xUIKrvKk4';
const fbHome = 'https://www.facebook.com/';
const FBItem = require('./../objects/FBItem');
const DBConnection = require('./../database_module/DBConnection');

exports.getData = function(pageId, organisationId, categoryId) {
	https.get(fbUrl+pageId+fbToken, (res) => {	  
	  let JSONResponse = [];
	  res.on('data', (d) => {
	  	JSONResponse.push(d);
	  });
	  res.on('end', () => {
 			var JSONMessageList = getDataFromResponse(JSONResponse);
 			var PostList = [];
 			for(var i = 0; i < JSONMessageList.length; i++) {
 				var post = JSONMessageList[i];
 				var postUrl = getPostUrl(post.id, pageId);
 				post = new FBItem('', post.created_time, post.message, post.id, postUrl, pageId);
 				PostList.push(post);
 			}
 			DBConnection.insertFBPosts(PostList, organisationId, categoryId);
        });
	}).on('error', (e) => {
	  console.error(e);
	});
}

function getPostUrl(postId, pageId){
	var indexOf_ = postId.indexOf("_");
 	var postUrl = fbHome+pageId+'/posts/'+ postId.substring(indexOf_+1);

	return postUrl;
}

function getDataFromResponse(JSONResponse) {
	JSONResponse = Buffer.concat(JSONResponse).toString();   
	JSONResponse = JSON.parse(JSONResponse);
	var JSONMessageList = JSONResponse.data

	return JSONMessageList;
}