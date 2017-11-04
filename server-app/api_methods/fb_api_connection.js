const request = require('request');
const fbToken = '141819799794306|ytHLoE5jcAAjF7Mgg3xUIKrvKk4';

var pageId = 'samorzadstudenckipl';
const https = require('https');

exports.getData = function() {
	https.get('https://graph.facebook.com/v2.9/KlinikaTestosteronu/posts?access_token=141819799794306|ytHLoE5jcAAjF7Mgg3xUIKrvKk4', (res) => {
	  
	  let JSONmessage = []
	  res.on('data', (d) => {
	  	JSONmessage.push(d);
	  });
	  res.on('end', () => {
 			JSONmessage = Buffer.concat(JSONmessage).toString();   
        });

	}).on('error', (e) => {
	  console.error(e);
	});

}
