<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="FMApp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>土豆FM</title>
<link rel="icon" href="img/icon.png">
<!-- css -->
<%@ include file="static/jsp/css-init.jspf"%>
<!-- js -->
<%@ include file="static/jsp/js-init.jspf"%>
</head>
<body ng-controller="appCtrl">
	<div class="container-fluid">
		<div class="row">
			<%@ include file="static/jsp/head.html"%>
		</div>
		<div class="row" style="background: #EFEEEB; margin-top: -2px;">
			<div class="container contain-clearfix"
				style="border-color: #FBFBFB; margin-top: 15px; margin-bottom: 15px">
				<div class="row contain-clearfix"
					style="background: #D8D8D8; border-color: #E8E8E8">
					<div class="contain-clearfix" style="background: #F9F8F2; border-color: #BBB">
						<div id="MainPage" main-page-width class="ui-view container" style="min-height:350px"></div>
					</div>
				</div>
			</div>
		</div>
		
		<div music-transmit song-list-model="songListModel"></div>
	</div>