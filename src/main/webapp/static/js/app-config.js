var prefix = '/pro_testFM';
var config = {
	'GET_OR_REGISTER_VISITOR' : prefix + '/user/visit',
	'LOGIN_AS_USER' : prefix + '/user/login',
	'REGISTER_AS_USER' : prefix + '/user/register',
	'VERIFY_IDENTITY' : prefix + '/user/verifyIdentity',
	'DO_REGISTER_AS_USER' : prefix + '/user/doRegister',

	'GET_VISITOR_SONGS' : prefix + '/getter/songs/visitor',
	'GET_USER_SONGS' : prefix + '/getter/songs/user',
	'GET_USER_MENUS' : prefix + '/getter/menus/user',
	'GET_DISCOVERY_SONGS' : prefix + '/getter/songs/discovery',
	'GET_SONGS_BY_SONG_NAME' : prefix + '/getter/songs/name',
	'GET_SINGERS_BY_SINGER_NAME' : prefix + '/getter/singers/name',
	'GET_SONGS_BY_SINGER_ID' : prefix + '/getter/songs/singerId',
	'GET_ALBUMS_BY_ALBUM_NAME' : prefix + '/getter/albums/name',
	'GET_SONGS_BY_ALBUM_ID' : prefix + '/getter/songs/albumId',
	
	'HANDLE_APPEND_FAVOR_SONG' : prefix + '/handle/append/song/favor',
	'HANDLE_DEL_FAVOR_SONG' : prefix + '/handle/del/song/favor',
	'HANDLE_APPEND_MENU_SONG' : prefix + '/handle/append/song/menu',
	'HANDLE_DEL_MENU_SONG' : prefix + '/handle/del/song/menu',
	'HANDLE_CREATE_MENU' : prefix + '/handle/create/menu',
	'HANDLE_DEL_MENU' : prefix + '/handle/del/menu',
	'HANDLE_SHARE_MENU' : prefix + '/handle/share/menu',
	'HANDLE_EDIT_MENU' : prefix + '/handle/edit/menu',
};
var configModel = angular.module('FM.config', []);
angular.forEach(config, function(value, key) {
	configModel.constant(key, value);
});