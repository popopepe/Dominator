<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="/Dominator/image/favicon.png">
<meta http-equiv='imagetoolbar' ConTENT='no'>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/Dominator/css/ingame.css" type="text/css" />
<link rel="stylesheet" href="/Dominator/css/login.css" type="text/css" />
<script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
var GAME_CODE = ${ gamecode };
var MAP_SIZE_X = ${ sizex };
var MAP_SIZE_Y = ${ sizey };
var MIS_MAX_RANGE = ${ mismaxrange };
</script>
<script src="/Dominator/javascripts/ingame.js" type="text/javascript"></script>
<title>DOMINATOR</title>
</head>
<body>

	<div id="login_background">
		<div class="site__container">
			<div class="grid__container">
				<div id="login_top">DOMINATOR</div>
				<div class="form form--login">
					<div class="form__winner">
						<label id="warning">지난회 우승자 : ${ winner }</label>
					</div>
					<div class="form__field">
						<label class="fontawesome-user" for="login__username"><span
							class="hidden">Username</span></label> <input id="login__username"
							type="text" class="form__input" placeholder="Username"
							value="${sessionScope.playerid}">
					</div>
					<div class="form__field">
						<c:if test="${ sessionScope.playerid != null }">
							<input id="loginBtn" type="button" value="REJOIN !">
						</c:if>
						<c:if test="${ sessionScope.playerid == null }">
							<input id="loginBtn" type="button" value="PLAY !">
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="player_board">
		<h2>PLAYER LIST</h2>
		<hr>
		<ul id="playerList">
			<li>no player...</li>
		</ul>
		<h2>HELP</h2>
		<hr>
		<label># <span class="mainColor">id</span>를 클릭하면 해당 행성으로 이동합니다.</label><br /> 
		<label># <span class="mainColor">'A'</span>로 미사일을 발사합니다.</label><br /> 
		<label># <span class="mainColor">'ESC'</span>로 BGM을 제어할 수 있습니다.</label>
	</div>

	<div id="game">
		<div id="space" class="dragme">
			<c:forEach items="${ plants }" var="p">
				<div class="plant ${ p.type }" id="${ p.id }"
					style="
				width : ${ p.r*2 }px;
				height : ${ p.r*2 }px;
				border-radius : ${ p.r }px;
				top : ${ p.y-p.r }px;
				left : ${ p.x-p.r }px">
					${p.own }</div>
				<div class="orbit" id="o${ p.id }"
					style="
				width : ${ p.revR*2 }px;
				height : ${ p.revR*2 }px;
				border-radius : ${ p.revR }px;
				top : ${ p.parent.y-p.revR }px;
				left : ${ p.parent.x-p.revR }px"></div>
			</c:forEach>
			<img id="bomb" src="/Dominator/image/bomb01.gif" />
		</div>
	</div>
</body>
</html>