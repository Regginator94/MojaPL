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

app.set('port', (process.env.PORT || 5000));

app.get('/', function(request, response) {
  response.sendFile(__dirname+'/index.html');
});

app.get('/api/events', function(request, response){
	response.end('API /api/events removed\n');
});

// app.get('/data', function(request, response) {
// 	DBConnection.getNews(response, request.headers.token, request.query.filters);
// });

app.get('/dataFiltered', function(request, response) {
	DBConnection.getNewsFiltered(response, request.headers.token, request.query.category, request.query.organisations);
});

//flitracja po organizacjach
app.get('/dataByOrganisation', function(request, response) {
	DBConnection.getNewsByOrganisationFilter(response, request.headers.token, request.query.organisations);
});

//flitracja po kategoriach
app.get('/dataByCategory', function(request, response) {
	DBConnection.getNewsByCategoryFilter(response, request.headers.token, request.query.category);
});

app.post('/createUser', function(request, response){
 	DBConnection.addUser(response, request.body.email, request.body.password);
});

app.post('/login', function(request, response){
 	DBConnection.userLogin(response, request.body.email, request.body.password);
});

app.post('/tokenLogin', function(request, response) {
	DBConnection.userLoginToken(response, request.headers.token);
	//
});

// setInterval(function () {	  
// 	 FBDownloader.getData('Politechnika.Lodzka');
// 	 PLDownloader.getData();
// }, 80000);

http.listen(app.get('port'), function() {
  console.log('Serwer został uruchomiony na porcie', app.get('port'));
  	 FBDownloader.getData('Politechnika.Lodzka',1,1);
	 FBDownloader.getData('weeia',2,1);
 	 FBDownloader.getData('klubfuturysta',201,2);
 	 FBDownloader.getData('studentradiozak',301,3);
 	// FBDownloader.getData('Cotton-Club-Łódź-193621223991274',401,4);
	 PLDownloader.getData();
});
