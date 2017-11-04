//Object represent post message from Facebook API

class FBItem {
	constructor(_createdTime, _message, _id, _postUrl, _pageId) {
		this.id = _id;
		this.createdTime = _createdTime;
		this.message = _message;
		this.postUrl = _postUrl
		this.pageId = _pageId;
	}

	getCreatedTime(){
		return this.createdTime;
	}

	getMessage(){
		return this.message;
	}

	getId(){
		return this.id;
	}

	getPostUrl(){
		return this.postUrl;
	}

	getPageId(){
		return this.pageId;
	}

}


module.exports = FBItem;