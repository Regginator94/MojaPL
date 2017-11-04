var express = require('express'),
	app = express();

const FBDownloader = require('./downloaders/FBDownloader');
const WEEIADownloader = require('./downloaders/WEEIADownloader');
const PLDownloader = require('./downloaders/PLDownloader');
const DBConnection = require('./downloaders/DBConnection');

app.set('port', (process.env.PORT || 5000));

app.get('/', function(request, response) {
  response.end('Welcome API page - to configure\n');
});

app.get('/api/events', function(request, response){
	//FBDownloader.getData('lodzpl');
	PLDownloader.getData();

});

app.listen(app.get('port'), function() {
  console.log('Serwer został uruchomiony na porcie', app.get('port'));
  DBConnection.getData();
});
