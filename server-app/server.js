var express = require('express'),
	app = express();
var http = require('http').createServer(app);

var bodyParser = require('body-parser');
app.use(bodyParser.json()); // support json encoded bodies
app.use(bodyParser.urlencoded({ extended: true })); // support encoded bodies

const FBDownloader = require('./downloaders_module/FBDownloader');
const WEEIADownloader = require('./downloaders_module/WEEIADownloader');
const PLDownloader = require('./downloaders_module/PLDownloader');
const DBConnection = require('./database_module/DBConnection');
const TwitterDownloader = require('./downloaders_module/TwitterDownloader');

app.set('port', (process.env.PORT || 5000));

app.get('/description', function(request, response) {
  response.sendFile(__dirname+'/index.html');
});

app.get('/api/events', function(request, response){
	response.end('API /api/events removed\n');
});

app.get('/dataByCategory', function(request, response) {
	DBConnection.getNewsFiltered(response, request.headers.token, request.query.category);
});

app.get('/dataByOrganisation', function(request, response) {
	DBConnection.getNewsByOrganisationFilter(response, request.headers.token);
});

app.post('/createUser', function(request, response){
 	DBConnection.addUser(response, request);
});

app.post('/login', function(request, response){
	if(request.headers.token != null){
		DBConnection.userLoginToken(response, request);
	} else {
		DBConnection.userLogin(response, request);
	}
});

app.put('/updateFilters', function(request, response){
	DBConnection.updateFilters(response, request);
})

app.put('/modifyUser', function(request, response){
	if(request.body.email != null){
		DBConnection.changeEmail(response, request);
	} else if(request.body.password != null) {
		DBConnection.changePassword(response, request);
	}
})

app.post('/passwordRepeater', function(request, response){
	DBConnection.passwordRepeater(response, request);
})

setInterval(function () {	  
	console.log("DATA UPDATE.");
	FBDownloader.getData('Politechnika.Lodzka',1,1);
	FBDownloader.getData('weeia',2,1);
	FBDownloader.getData('klubfuturysta',201,2);
	FBDownloader.getData('studentradiozak',301,3);
	FBDownloader.getData('finestrapizzeria',402,4);
	PLDownloader.getData();
 }, 3000000);

http.listen(app.get('port'), function() {
  console.log('Serwer został uruchomiony na porcie', app.get('port'));
  //Radio Zak Tweets dd
  TwitterDownloader.updateTweets('888mhz',301,3);
});
