var Twitter = require('twitter');
const TwitterItem = require('./../objects/TwitterItem');
 
var client = new Twitter({
  consumer_key: 'tU6v1pM2X4hL570KXZnF4r0gw',
  consumer_secret: 'QMH5Qmx1Ff04CVBCIDDoCgoCrvPzkO0z5Yhr2gpqSvVuePxSI9',
  access_token_key: '1396414418-T83Qcio9C59mtYlItpR4Lw5uxNBpJIeQeHX06ju',
  access_token_secret: 'hv0SDmylcLExXctnoOj5pbrl0E3S8U51ylLuaVkJQDMUr'
});
 

exports.getTweets = function(){
	let tweets = [];
	var screen_name = 'K_Stanowski';
	var params = {screen_name: screen_name};
	tweets = client.get('statuses/user_timeline', params, function(error, data, response) {
		if (!error) {
			tweets = data;
			// console.log(data[2]);
			// for(var i = 0; i < tweets.length; i++){
			// 		tweet = data[i];
			// 		console.log(tweet);
			// 		var tweetUrl = 'https://twitter.com/'+screen_name+'/status/'+tweet.id;
			// 		tweet = new TwitterItem(tweet.created_at, '', tweet.text, tweet.id, tweetUrl, screen_name);
			// 		tweets.push();
			// }		
			// console.log(tweets)
			return tweets;
		} else {
			console.log('ERROR');
		}

	});

	console.log(tweets);
	
}
