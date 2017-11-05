var express = require('express'),
	app = express();

var bodyParser = require('body-parser');
app.use(bodyParser.json()); // support json encoded bodies
app.use(bodyParser.urlencoded({ extended: true })); // support encoded bodies

const FBDownloader = require('./downloaders/FBDownloader');
const WEEIADownloader = require('./downloaders/WEEIADownloader');
const PLDownloader = require('./downloaders/PLDownloader');
const DBConnection = require('./downloaders/DBConnection');

app.set('port', (process.env.PORT || 5000));

app.get('/', function(request, response) {
  response.end('Welcome API page - to configure\n');
});

app.get('/api/events', function(request, response){
	//FBDownloader.getData('Politechnika.Lodzka');
	PLDownloader.getData();
});

//wymagany parametr date=2012-12-25 00:00:00
app.post('/data', function(request, response) {
	DBConnection.getNews(response, request.body.date);
});

app.post('/createUser', function(request, response){
 	DBConnection.addUser(response, request.body.email, request.body.password);
});

app.post('/login', function(request, response){
 	DBConnection.userLogin(response, request.body.email, request.body.password);
});

app.listen(app.get('port'), function() {
  console.log('Serwer został uruchomiony na porcie', app.get('port'));
});
