//Object represents event from db

class FBItem {
	constructor(_id, _organisationId, _categoryId, _content, _title, _imageUrl, _startDate, _createDate, _url, _fbPost, _organisationName, _tweet) {
		this.id = _id;
		this.organisationId = _organisationId;
		this.categoryId = _categoryId;
		this.content = _content
		this.title = _title;
		this.imageUrl = _imageUrl;
		this.createDate = _createDate;
		this.startDate = _startDate;
		this.url = _url
		this.fbPost = (_fbPost == 1 ? "true" : "false");
		this.organisationName = _organisationName;
		this.tweet = (_tweet == 1 ? "true" : "false");
	}

	getId(){
		return this.id;
	}

	getOrganisationId() {
		return this.organisationId;
	}

	getCategoryId(){
		return this.categoryId;
	}

	getContent(){
		return this.content;
	}

	getTitle(){
		return this.title;
	}

	getImageUrl(){
		return this.imageUrl
	}

	getDate(){
		return this.date;
	}

	getUrl(){
		return this.url;
	}

	getFbPost(){
		return this.fbPost;
	}

	getOrganisationName(){
		return this.organisationName;
	}
}

module.exports = FBItem;