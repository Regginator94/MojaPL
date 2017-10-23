var express = require('express'),
	app = express();

const Nav = require('./api_methods/nav_cti_data');

const PlNews = require('./api_methods/pl_news');

app.set('port', (process.env.PORT || 5000));

app.get('/', function(request, response) {
  response.end('Welcome API page - to configure\n');
});

app.get('/api/events', function(request, response){
	PlNews.getData(response);
});

app.listen(app.get('port'), function() {
  console.log('Serwer został uruchomiony na porcie', app.get('port'));
});
