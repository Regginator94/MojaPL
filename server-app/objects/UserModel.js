
class UserModel {
	constructor(_id, _email, _password, _createDate, _lastLoginDate) {
		this.id = _id;
		this.email = _password;
		this.createDate = _createDate
		this.lastLoginDate = _lastLoginDate;
	}
}

module.exports = UserModel;