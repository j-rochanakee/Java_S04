'use strict';

var rootUrl = "/java_s04/api/v1.1/login";
$('#loginButton').click(login);


function login(){
	$('.error').children().remove();
	if ($('#username').val() === '') {
		$('.error').append('<div>usernameを入力してください。</div>');
	}
	if ($('#password').val() === '') {
		$('.error').append('<div>passwordを入力してください。</div>');
	}
	var id = $('#username').val();

	var fd = new FormData(document.getElementById("loginForm"));
	$.ajax({
		type: "POST",
		url: rootUrl,
		data: fd,
		contentType : false,
		processData : false,
		dataType: "json",
		success: function(json){
			if(json.isLogin == 1){
				sessionStorage.setItem("username",json.username);
				sessionStorage.setItem("type",json.type);
				alert("username = " + sessionStorage.getItem("username")+ "type =" + sessionStorage.getItem("type") );
				window.open(menu.html);
				$('#password').val('');
			}
			else{
				alert('ログインが失敗しました。');
				$('.error').text('ID又はパスワードが違っています。');
				$('#username').val('');
				$('#password').val('');
			}
		}
	});
}




