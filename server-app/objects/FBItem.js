//Object represent post message from Facebook API

class FBItem {
	constructor(_startDate, _text, _message, _id, _postUrl, _pageId) {
		this.id = _id;
		this.startDate = _startDate.replace(/T/, ' ').replace(/\..+/, '').replace('+0000','');  //sparsowany czas
		//this.createdTime = _createdTime; // czas dodania do DB
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