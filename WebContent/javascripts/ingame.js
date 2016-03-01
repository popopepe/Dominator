// 변수
var PLAYER_PLANT = null;
var effect = new Audio();

window.onload = function() {

	var bgm = new Audio();
	bgm.src = '/Dominator/sounds/bgm1.mp3';
	bgm.load();
	bgm.loop = true;
	bgm.play();

	effect.src = '/Dominator/sounds/effect.mp3';
	effect.load();
	effect.volume = 0.5;

	// 행성 클릭이벤트
	$('.plant').bind(
			'click',
			function(e) {
				setMiddle(parseInt(e.target.style.top),
						parseInt(e.target.style.left), 500);
			});

	// 미사일 발사 이벤트
	var mouse_x;
	var mouse_y;
	$("body")
			.bind(
					'keydown',
					function(e) {
						if (e.keyCode == 65 && PLAYER_PLANT != null) {
							var startX = parseInt(document
									.getElementById(PLAYER_PLANT).style.left);
							var startY = parseInt(document
									.getElementById(PLAYER_PLANT).style.top);
							var realX = mouse_x - $('#space').offset().left;
							var realY = mouse_y - $('#space').offset().top;
							$
									.ajax({
										type : 'post',
										data : {
											action : "missAdd",
											x1 : startX,
											y1 : startY,
											x2 : realX,
											y2 : realY
										},
										dataType : 'json',
										url : '/Dominator/ajaxdata',
										success : function(result) {
											if (result != null) {
												$(
														'<div id="miss'
																+ result.code
																+ '" class="missile" style="border-color:'
																+ result.color
																+ ';transform:rotate('
																+ (result.deg + 45)
																+ 'deg);"></div>')
														.appendTo($('#space'));
												var m = $('#miss' + result.code);
												m.css({
													'top' : startY,
													'left' : startX
												});
											} else {
												//console.log('잔탄부족');
											}
										},
										error : function(e) {
											console.log('error');
										}
									});
						} else if (e.keyCode == 27) {
							if (bgm.paused)
								bgm.play();
							else
								bgm.pause();
						}
					});
	$("#space").bind('mousemove', function(e) {
		// 현 마우스의 위치를 저장함
		mouse_x = e.pageX;
		mouse_y = e.pageY;
	});

	// 로그인 처리
	$('#loginBtn').bind('click', function() {
		var loginId = $('#login__username').val();
		if (loginId == null || loginId.length == 0)
			loginId = 'noName';
		if (loginId == 'destroyed')
			loginId = 'notdestroyed';
		$.ajax({
			type : 'post',
			data : {
				action : "login",
				id : loginId
			},
			dataType : 'json',
			url : '/Dominator/ajaxdata',
			success : function(result) {
				if (result != null) {
					PLAYER_PLANT = result.p.id;
					$('#login_background').hide();

					setPlayerList();
					$('#' + PLAYER_PLANT).trigger('click');
				} else {
					$('#warning').html("풀방입니다. 나중에 참가하세요.");
				}
			},
			error : function(e) {
				console.log('error');
			}
		});
	});

	// 배경 동적 조절
	$('#space').css({
		'width' : MAP_SIZE_X,
		'height' : MAP_SIZE_Y
	});
	setMiddle(MAP_SIZE_Y / 2, MAP_SIZE_X / 2, 0);

	setPlayerList();
	// ajax요청
	setInterval(setPlant, 200);
	setInterval(setMissile, 200);
	setInterval(setPlayerList, 5000);
	setInterval(getGameCode, 5000);
}
// 클릭시 행성위치로
function setMiddle(top, left, delay) {
	$('#space').animate({
		'top' : -top + window.innerHeight / 2,
		'left' : -left + window.innerWidth / 2
	}, delay);
}

function setPlant() {
	$.ajax({
		type : 'post',
		data : {
			action : "plant"
		},
		dataType : 'json',
		url : '/Dominator/ajaxdata',
		success : function(result) {
			for (i = 1; i < result.length; i++) {
				var p = $('#' + result[i].id);
				if (p.html() != result[i].own) {
					// 소유자가 바뀐경우 재지정
					p.html(result[i].own);
					p.css({
						'color' : result[i].ownColor
					});
					if (result[i].own == "destroyed") {
						// 파괴된 행성으로 바뀌는 경우
						p.css('background-image',
								'url(/Dominator/image/deadsatellite.png)');
						// 본인 행성이 파괴된 경우 제거 함수
						if (result[i].id == PLAYER_PLANT)
							deletePlayer();
					}
				}
				p.css({
					'top' : result[i].y - result[i].r,
					'left' : result[i].x - result[i].r
				});

				var o = $('#o' + result[i].id);
				o.css({
					'top' : result[i].parent.y - result[i].revR,
					'left' : result[i].parent.x - result[i].revR
				});
			}
		},
		error : function(e) {
			console.log('error');
		}
	});
}
function setMissile() {
	$.ajax({
		type : 'post',
		data : {
			action : "miss"
		},
		dataType : 'json',
		url : '/Dominator/ajaxdata',
		success : function(result) {
			for (i = 0; i < result.length; i++) {
				// 없는 미사일이면 생성
				if ($('#miss' + result[i].code).length == 0) {
					$(
							'<div id="miss' + result[i].code
									+ '" class="missile" style="border-color:'
									+ result[i].color + ';transform:rotate('
									+ (result[i].deg + 45) + 'deg);"></div>')
							.appendTo($('#space'));
				}
				var m = $('#miss' + result[i].code);
				m.css({
					'top' : result[i].y,
					'left' : result[i].x
				});
				if (result[i].range >= MIS_MAX_RANGE || result[i].crash) {
					m.remove();
					if (result[i].crash) {
						$('#bomb').css({
							'top' : result[i].y - 50,
							'left' : result[i].x - 50
						});
						$('#bomb').show();
						setTimeout(function() {
							$('#bomb').hide()
						}, 200);
						setTimeout(function() {
							effect.play();
						}, 0)
					}
				}
			}
			if(result.length == 0)
				$('.missile').remove();
		},
		error : function(e) {
			console.log('error');
		}
	});
}
function setPlayerList() {
	$.ajax({
		type : 'post',
		data : {
			action : "playerList"
		},
		dataType : 'json',
		url : '/Dominator/ajaxdata',
		success : function(result) {
			$('#playerList').html('');
			for (i = 0; i < result.length; i++) {
				var playerId = result[i].id + " [" + result[i].point + "p]";
				if (PLAYER_PLANT != null && PLAYER_PLANT == result[i].p.id)
					playerId += " @YOU";
				$(
						'<li data-plant="' + result[i].p.id + '" style="color:'
								+ result[i].color + '">' + playerId + '</li>')
						.appendTo($('#playerList'));
			}
			if (result.length == 0)
				$('<li>no Player...</li>').appendTo($('#playerList'));

			// 플레이어 리스트 클릭 이벤트 등록
			$('#playerList li').bind('click', function() {
				$('#' + $(this).data('plant')).trigger('click');
			});
		},
		error : function(e) {
			console.log('error');
		}
	});
}
function getGameCode() {
	$.ajax({
		type : 'post',
		data : {
			action : "gameCode"
		},
		dataType : 'json',
		url : '/Dominator/ajaxdata',
		success : function(result) {
			// 게임이 재시작되면 새로고침
			if (result != GAME_CODE)
				deletePlayer();
		},
		error : function(e) {
			console.log('error');
		}
	});
}
function deletePlayer() {
	$.ajax({
		type : 'post',
		data : {
			action : "playerDelete"
		},
		dataType : 'json',
		url : '/Dominator/ajaxdata',
		success : function(result) {
			location.reload();
		},
		error : function(e) {
			console.log('error');
		}
	});
}

// 이하 드래그 소스
// ## 익스플로어 넷츠게이프 체크 ##//
var nn6 = document.getElementById && !document.all;

// ## 기타 변수 ##//
var drag_isdrag = false;
var x, y;
var drag_dobj;

function movemouse(e) {
	if (drag_isdrag) {
		if ((tx + event.clientX - x) > 0) {
			drag_dobj.style.left = 0 + 'px';
		} else {
			drag_dobj.style.left = (tx + event.clientX - x) + 'px';
		}
		if ((ty + event.clientY - y) > 0) {
			drag_dobj.style.top = 0 + 'px';
		} else {

			drag_dobj.style.top = (ty + event.clientY - y) + 'px';
		}
		if ((window.innerWidth - MAP_SIZE_X) > (tx + event.clientX - x)) {
			drag_dobj.style.left = (window.innerWidth - MAP_SIZE_X) + 'px';
		}
		if ((screen.height - MAP_SIZE_Y) > (ty + event.clientY - y)) {
			drag_dobj.style.top = (screen.height - MAP_SIZE_Y) + 'px';
		}

		return false;
	}
}

function selectmouse(e) {
	var fobj = nn6 ? e.target : event.srcElement;
	var topelement = nn6 ? 'HTML' : 'BODY';
	while (fobj.tagName != topelement && fobj.className != 'dragme') {
		fobj = nn6 ? fobj.parentNode : fobj.parentElement;
	}
	if (fobj.className == 'dragme') {
		drag_isdrag = true;
		drag_dobj = fobj;
		tx = parseInt(drag_dobj.style.left + 0);
		ty = parseInt(drag_dobj.style.top + 0);
		x = nn6 ? e.clientX : event.clientX;
		y = nn6 ? e.clientY : event.clientY;
		document.onmousemove = movemouse;
		return false;
	}
}

document.onmousedown = selectmouse;
document.onmouseup = new Function('drag_isdrag=false');