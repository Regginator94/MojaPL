
class TwitterItem {
	constructor(_startDate, _createdTime, _text, _id, _tweetUrl, _pageId) {
		this.id = _id;
		this.startDate = _startDate; // czas tweta
		this.createdTime = _createdTime; // czas dodania do DB
		this.text = _text;
		this.tweetUrl = _tweetUrl
		this.pageId = _pageId;
	}
}


module.exports = TwitterItem;