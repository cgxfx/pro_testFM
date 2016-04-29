angular.module('FM.i18n.zh', ['pascalprecht.translate']).config(function($translateProvider) {
	$translateProvider.translations('zh',{
		HOME : '土豆',
		LOGIN : '登录',
		SEARCH : '搜索',
		HEART_LIST : '红心兆赫',
		PERSONAL_LIST : '私人兆赫',
		FILTRATE_LIST : '淘歌',

		REGISTER : "注册",
		USEREMAIL : "账号",
		USERPWD : "密码",
		PLACEHOLDER_USEREMAIL : "请输入邮箱",
		PLACEHOLDER_USERPWD : "请输入密码",
		VERIFICATION_ID : "获取验证码",
		VERIFICATION_CODE : "验证码",
		PLACEHOLDER_VERIFICATION_CODE : "区分大小写",

		NICKNAME : '昵称',
		UNNECESSARY : '可不填',
		USERPWD2 : '确认密码',
		PLACEHOLDER_USERPWD2 : '请重复上一栏密码',
		ERROR_DOUBLE_USERPWD : '两次密码填写不一致',



		SEARCH_SONG : '歌曲',
		SEARCH_SINGER : '歌手',
		SEARCH_ALBUM : '专辑',
		PLACEHOLDER_SEARCH : '搜索-歌曲|歌手|专辑',

		PLAYALLSONGS : '播放全部',
		SONG : '歌名',
		SINGER : '歌手',
		ALBUM : '所在专辑',
		ALUM_INFORMATION : '专辑简介:',
		SINGER_INFORMATION : '歌手简介',

		TOTAL : '共',
		NUMBER : '首',
		DEFAULT_MENU_FAVOR_NAMEs : '红心兆赫',
		WITHOUT_COPYRIGHT : '因合作方要求,该资源暂时下架>_<',
		SONGS_NUMBER : '单曲数',
		ALBUMS_NUMBER : '专辑数',
		MENU_CREATED : '创建',
		ALBUM_PUBLISHED : '发布',

		SINGLEMODE : '单曲循环',
		CYCLEMODE : '列表循环',
		RANDOMMODE : '随机播放',
		BACKSONG : '上一首',
		FORSONG : '下一首',

		CREATED_MENUS : '创建的歌单',
		CREATE_MENU : '新建歌单',
		DEL_MENU : '删除歌单',
		CONFIRM_DEL_MENU : '确认删除该歌单列表?',
		ALREADY_BEEN_SHARE : '已分享',
		SHARE_MENU : '分享歌单',
		EDIT_MENU : '编辑歌单',

		MENU_NAME : '歌单名',
		MENU_SHARE_TITLE : '分享标题',
		MENU_SHARE_TYPE : '歌单类型',
		MENU_IS_SHARE : '分享',

		ALL : '全部',
		SADNESS : '伤感',
		QUIETNESS : '安静',
		HAPPINESS : '幸福',
		HEARTBROKEN : '心痛',
		MISSING : '想念',
		SWEETIE : '甜蜜',
		INSPIRATION : '励志',
		CATHARSIS : '宣泄',
		WARMING : '治愈',
		LONELINESS : '孤单',
		REBELLIOUS : '叛逆',
		EXCEPTATION : '期待',

		TOTAL_CONTAIN : '共收录',

		DEL_MENU_SONG : '从列表中删除',
		APPEND_MENU_SONG : '收藏至歌单: ',
	});
});