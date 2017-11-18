const request = require('tinyreq');
const cheerio = require('cheerio');
const mainPageURL = 'https://www.p.lodz.pl';
const DBConnection = require('./../database_module/DBConnection');
var PLItem = require('./../objects/PLItem');

exports.getData = function() {
    var pageBody = '';
    request("https://www.p.lodz.pl/pl/lista/aktualnosci", function (err, body) {
        var plItems = getItems(body);
        DBConnection.insertPLNews(plItems);
    });
}

function getItems(body){
    $ = cheerio.load(body);
    var content = ($('.Content').html());
    $ = cheerio.load(content);
    var items = [];
    var plItems = [];
    $('.Item').each(function(i, elem) {
        items[i] = $(this).html();
        $ = cheerio.load(items[i]);
        var title = ($('h3').text());
        var preview = ($('.Preview').text());
        var date = ($('.Date').text());
        var image = ($('img').attr('src'));
        var link = ($('.MoreLink').html());   
        $ = cheerio.load(link);
        var href = mainPageURL + ($('a').attr('href'));             
        var p = new PLItem(title, preview, date, image, href);
        plItems.push(p);
    });
   return plItems;
}