const dateFormat = require('dateformat');
var now = new Date();

class TwitterItem {
	constructor(_startDate, _createdTime, _text, _id, _tweetUrl, _pageId) {
		this.id = _id;
		this.startDate = dateFormat(_startDate, "yyyy-mm-dd HH:MM:ss");   // czas tweta
		this.createdTime = _createdTime; // czas dodania do DB
		this.text = _text.replace(/['"]+/g, '');
		this.tweetUrl = _tweetUrl
		this.pageId = _pageId;
	}
}

module.exports = TwitterItem;