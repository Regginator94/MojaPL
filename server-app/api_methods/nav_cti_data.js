const request = require('request');

exports.getData = function(response) {
    var content = '';
    var urlPath = "https://nav.cti.p.lodz.pl:5000/events"
    const { headers, method, url } = request;
    request.get({url: urlPath, "rejectUnauthorized": false})
       .on('response', function(res) {
        let body = [];
       if (res.statusCode === 200) {
         res.on('data', (chunk) => {
            body.push(chunk);
        });
         res.on('end', () => {
            body = Buffer.concat(body).toString();   
            const responseBody = { headers, method, url, body };
            response.statusCode = 200;
            response.setHeader('Content-Type', 'application/json');
            response.write(JSON.stringify(responseBody));
            response.end();
        });
       } else {
            response.statusCode = res.statusCode;
            response.setHeader('Content-Type', 'application/json');
            response.write(res.statusCode);
            response.end();          
       }
    });   
}