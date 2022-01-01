<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>��ۻ���</title>
</head>
<body>
	��й�ȣ
	<input type="password" id="delPassword">
	<input type="button" value="����" onclick="commentRemove()">

	<script src="https://code.jquery.com/jquery-3.6.0.js"
  			integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
  			crossorigin="anonymous">
	</script>
	<script type="text/javascript">
		function commentRemove(){
			// ���޵Ǵ� �Ķ���Ͱ� ����
			// delID_ comment or reply or _ bbsContent _ id
			var param = getParam('id').split('_');
			var divide = param[1];
			var id = param[2];
			var password = $('#delPassword').val();
			console.log('divide : ' + divide);	
			console.log('id : ' + id);	
			console.log('password : ' + $('#delPassword').val());	
			removeAction(divide, id, password);
	    }
		
		function removeAction(divide, id, password) {
			var data = {
					divide: divide,
					id: id, 
					password: password						
			}	
			
			var url = getUrl(divide);
			
			$.ajax({
				type: "post",
				url: url,
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				dataType: "json",				
				success: function(json) {
					var resultCode = json[0].resultCode;
										
					if (divide == 'bbsContent') {
						if (resultCode == 'OK') {
							opener.bbsDelMsg("���� �Ǿ����ϴ�.", "OK");
						} else if (resultCode == 'WRONG_PASS') {
							opener.bbsDelMsg("�߸��� ��й�ȣ �Դϴ�.");
						} else if (resultCode == 'ERROR') {
							opener.bbsDelMsg("�����ͺ��̽� ���� �Դϴ�.", "OK");					
						}
						window.close();
					}
					
					var commentCount = json[1].commentCount;
					if (resultCode == 'OK') {
						opener.delMsg("���� �Ǿ����ϴ�.", resultCode, commentCount);
					} else if (resultCode == 'WRONG_PASS') {
						opener.delMsg("�߸��� ��й�ȣ �Դϴ�.", resultCode, commentCount);
					} else if (resultCode == 'ERROR') {
						opener.delMsg("�����ͺ��̽� ���� �Դϴ�.", resultCode, commentCount);					
					}
					window.close();
				},
				error: function(json) {
					console.log(json[0]);
					opener.delMsg("�ý��� ���� �Դϴ�.");
				}
			});
		}
		
		function getUrl(divide) {
			let url;
			if (divide == 'bbsContent') {
				url = 'freeBoardDeleteController';
			} else if (divide == 'comment') {
				url = 'commentDeleteController';
			} else {
				url = 'replyDeleteController';
			}
			return url;
		}
		
		// �Ķ���� id������ �з� ���ó���� getParam(���ϴ� id);
		function getParam(sname) {
		    var params = location.search.substr(location.search.indexOf("?") + 1);
		    var sval = "";
		    params = params.split("&");
		    for (var i = 0; i < params.length; i++) {
		        temp = params[i].split("=");
		        if ([temp[0]] == sname) { sval = temp[1]; }
		    }
		    return sval;
		}
	</script>
</body>
</html>