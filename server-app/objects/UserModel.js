
class UserModel {
	constructor(_id, _email, _password, _createDate, _lastLoginDate, _userFilters) {
		this.id = _id;
		this.email = _email;
		this.password = _password;
		this.createDate = _createDate
		this.lastLoginDate = _lastLoginDate;
		this.userFilters = _userFilters;
	}
}

module.exports = UserModel;