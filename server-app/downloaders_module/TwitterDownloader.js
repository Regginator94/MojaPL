
var Twitter = require('twitter');
const TwitterItem = require('./../objects/TwitterItem');
const DBConnection = require('./../database_module/DBConnection');

 
var client = new Twitter({
  consumer_key: 'tU6v1pM2X4hL570KXZnF4r0gw',
  consumer_secret: 'QMH5Qmx1Ff04CVBCIDDoCgoCrvPzkO0z5Yhr2gpqSvVuePxSI9',
  access_token_key: '1396414418-T83Qcio9C59mtYlItpR4Lw5uxNBpJIeQeHX06ju',
  access_token_secret: 'hv0SDmylcLExXctnoOj5pbrl0E3S8U51ylLuaVkJQDMUr'
});

exports.getTweets = function(pageID){
	return new Promise((resolve, reject)=>{
		let tweets = [];
		var screen_name = pageID;
		var params = {screen_name: screen_name};
		tweets = client.get('statuses/user_timeline', params, function(error, data, response) {
			if (!error) {
				var tweetsResult = [];
				tweets = data;
				for(var i = 0; i < tweets.length; i++){
						tweet = data[i];
						var tweetUrl = 'https://twitter.com/'+screen_name+'/status/'+tweet.id;
						tweet = new TwitterItem(tweet.created_at, '', tweet.text, tweet.id, tweetUrl, screen_name);
						tweetsResult.push(tweet);
				}		
				return resolve(tweetsResult);
			} else {
				return reject(error);
			}	
		});	
	})
}

exports.updateTweets = function(pageID, organisationId, categoryId){
	exports.getTweets(pageID).then(function(response){
		const tweets = response;
		DBConnection.insertTweets(tweets, organisationId, categoryId);
	});
}