var mysql      = require('mysql');
var connection = mysql.createConnection({
  host     : 'localhost',
  user     : 'root',
  password : 'root',
  database : 'mojapl_db'
});

exports.getData = function() {
	connection.connect();
	connection.query('SELECT * from organizations', function(err, rows, fields) {
	  if (!err)
	    console.log('The solution is: ', rows);
	  else
	    console.log('Error while performing Query.');
	});



	connection.end();
}


exports.insertFBPosts = function(postsList) {
	connection.connect();



	connection.end();
}
