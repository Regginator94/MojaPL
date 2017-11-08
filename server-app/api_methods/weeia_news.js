const request = require('tinyreq');
const cheerio = require('cheerio')
const mainPageURL = 'https://www.p.lodz.pl';

var WeeiaItem = require('./objects/WeeiaItem');

exports.getData = function(response) {
    var pageBody = '';
    request("https://www.p.lodz.pl/pl/lista/aktualnosci", function (err, body) {
        $ = cheerio.load(body);
        var content = ($('.Content').html());
        $ = cheerio.load(content);
        var items = [];
        var plItems = [];
        $('.Item Filterable FilterStudent FilterCandidate FilterPhD').each(function(i, elem) {
        	items[i] = $(this).html();
        	$ = cheerio.load(items[i]);
	        var title = ($('h3').text());
	        var preview = ($('.Preview').text());
	        var date = ($('.Date').text());
	        var link = ($('.MoreLink').html());   
            if(link != null) {
                 $ = cheerio.load(link);
                var href = mainPageURL + ($('a').attr('href'));             
            } else {
                var href = '';
            }	       
	        var p = new PLItem(title, preview, date, href);
	        plItems.push(p);
        });
    });
}