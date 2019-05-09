'use strict';

var rootUrl = "/java_s04/api/v1.1/Permission";


initPage();

$('#savePost').click(function() {
	$('.error').children().remove();

	if ($('#title').val() === '') {
		$('.error').append('<div>タイトルは必須入力です。</div>');
	}
	if ($('#payAt').val() === '') {
		$('.error').append('<div>支払先は必須入力です。</div>');
	}
	if ($('#money').val() === '') {
		$('.error').append('<div>金額は必須入力です。</div>');
	}

	var id = $('#postId').val();
	if (id === '')
		addRequest();
	else{
		if($('#status').val() =='申請中'){
			updatePermission(id,1);
		}
		else if(($('#status').val() =='承認')){
			updatePermission(id,2);
		}
		else {
			updatePermission(id,3);
		}

	}
	return false;
})



$('#newPost').click(function() {
	renderDetails({});
});


function initPage() {
	findAll();
}

function findAll() {
	var type = sessionStorage.getItem("type");
	var username = sessionStorage.getItem("username");

	console.log('findAll start.')
	$.ajax({
		type : "GET",
		url : rootUrl + '?username=' + username + '&type=' + type,
		dataType : "json",
		success : function(data) {
			renderTable(data);
		}
	});
}

function findById(id) {
	console.log('findByID start - id:' + id);
	var type = sessionStorage.getItem("type");//my type

	$.ajax({
		type : "GET",
		url : rootUrl + '/' + id,
		dataType : "json",
		success : function(data) {
			console.log('findById success: ' + data.name);
			renderDetails(data)

			var buttonAdd = $('#AcceptOrDeny');
			if(data.reqPersonType < type && data.status ==1) {
				buttonAdd.append($('<button>').text("承認").attr("type","button").attr("onclick", "updatePermission("+data.id+',2)'));
				buttonAdd.append($('<button>').text("却下").attr("type","button").attr("onclick", "updatePermission("+data.id+',3)'));
			}
			else {

			}
		}
	});
}




function addRequest() {
	console.log('addRequest start');

	var fd = new FormData(document.getElementById("postForm"));
	fd.append('username', sessionStorage.getItem("username"));

	$.ajax({
		url : rootUrl,
		type : "POST",
		data : fd,
		contentType : false, //what does it mean
		processData : false, //what does it mean
		dataType : "json",
		success : function(data, textStatus, jqXHR) {
			alert('経費申請の追加に成功しました');
			findAll();
			renderDetails(data);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('経費申請の追加に失敗しました');
		}
	})
}

function updatePermission(id,status) {
	console.log('updatePermission start');

	var fd = new FormData(document.getElementById("postForm"));
	fd.append('username', sessionStorage.getItem("username"));
	fd.append('status',status);

	$.ajax({
		url : rootUrl + '/' + id,
		type : "PUT",
		data :  fd ,
		contentType : false,
		processData : false,
		dataType : "json",
		success : function(data, textStatus, jqXHR) {
			alert('経費申請の更新に成功しました');
			findAll();
			renderDetails(data);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('経費申請の更新に失敗しました');
		}
	})
}

function deleteById(id) {
	console.log('delete start - id:' + id);
	$.ajax({
		type : "DELETE",
		url : rootUrl + '/' + id,
		success : function() {
			alert('経費申請の削除に成功しました');
			findAll();
			renderDetails({});
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('経費申請の削除に失敗しました');
		}
	});
}

function renderTable(data) {
	var headerRow = '<tr><th>申請ID</th><th>申請日</th><th>更新日</th><th>申請者</th><th>タイトル</th><th>金額</th><th>ステータス</th><th></th></tr>';

	$('#posts').children().remove();

	if (data.length === 0) {
		$('#posts').append('<p>現在データが存在していません。</p>')
	} else {
		var table = $('<table>').attr('border', 1);
		table.append(headerRow);

		$.each(data, function(index,permission) {
			var row = $('<tr>');
			row.append($('<td>').text(permission.id));
			row.append($('<td>').text(permission.requestedDate));
			row.append($('<td>').text(permission.updatedDate));
			row.append($('<td>').text(permission.reqPersonId));
			row.append($('<td>').text(permission.title));
			row.append($('<td>').text(permission.money));
			if(permission.status == 1){
			row.append($('<td>').text("申請中"));
			}
			else if(permission.status == 2){
			row.append($('<td>').text("承認"));
			}
			else {
			row.append($('<td>').text("却下"));
			}

			row.append($('<td>').append(
					$('<button>').text("詳細").attr("type","button").attr("onclick", "findById("+permission.id+')')
				));

			table.append(row);
		});

		$('#posts').append(table);
	}
}

function renderDetails(permission) {
	$('.error').text('');
	$('#postId').val(permission.id);
	$('#created_date').val(permission.requestedDate);
	$('#updated_date').val(permission.updatedDate);
	$('#request_person').val(permission.reqPersonId);
	$('#title').val(permission.title);
	$('#payAt').val(permission.payAt);
	$('#money').val(permission.money);
	if(permission.status == 3){
		$('#status').val("却下");
	}
	else if(permission.status == 2){
		$('#status').val("承認");
	}
	else if (permission.status == 1) {
		$('#status').val("申請中");
	}
	else
		$('#status').val("");
	$('#update_person').val(permission.updatePersonId);
}


