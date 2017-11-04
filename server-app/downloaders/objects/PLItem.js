//Object represent content item from p.lodz.pl/aktualnosci

class PLItem {
	constructor(_title, _preview, _date,  _image, _href) {
		this.title = _title;
		this.preview = _preview;
		this.date = _date
		this.image = _image;
		this.href = _href;	
	}

	getTitle(){
		return this.title;
	}

	getPreview(){
		return this.preview;
	}

	getDate(){
		return this.date;
	}

	getImage(){
		return this.image;
	}

	getHref(){
		return this.href;
	}
}

module.exports = PLItem;