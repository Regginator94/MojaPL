var express = require('express'),
	app = express();

var bodyParser = require('body-parser');
app.use(bodyParser.json()); // support json encoded bodies
app.use(bodyParser.urlencoded({ extended: true })); // support encoded bodies

const FBDownloader = require('./downloaders_module/FBDownloader');
const WEEIADownloader = require('./downloaders_module/WEEIADownloader');
const PLDownloader = require('./downloaders_module/PLDownloader');
const DBConnection = require('./database_module/DBConnection');

app.set('port', (process.env.PORT || 5000));

app.get('/', function(request, response) {
  response.end('Welcome API page - to configure\n');
});

app.get('/api/events', function(request, response){
	response.end('API /api/events removed\n');
});

//wymagany parametr date=2012-12-25 00:00:00
app.get('/data', function(request, response) {
		console.log(JSON.stringify(request.headers));
	DBConnection.getNews(response, request.headers.token);
});

app.post('/createUser', function(request, response){
 	DBConnection.addUser(response, request.body.email, request.body.password);
});

app.post('/login', function(request, response){
 	DBConnection.userLogin(response, request.body.email, request.body.password);
});

app.post('/tokenLogin', function(request, response) {
	DBConnection.userLoginToken(response, request.body.token);
	//
});

app.listen(app.get('port'), function() {
  console.log('Serwer został uruchomiony na porcie', app.get('port'));
  
  FBDownloader.getData('Politechnika.Lodzka');
  PLDownloader.getData();
});
