//Object represent content item from http://www.weeia.p.lodz.pl/
class WeeiaItem {
	constructor(_title, _preview, _date, _href) {
		this.title = _title;
		this.preview = _preview;
		this.date = _date
		this.image = ''
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

module.exports = WeeiaItem;