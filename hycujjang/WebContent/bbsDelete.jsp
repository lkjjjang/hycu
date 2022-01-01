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
	<input type="button" value="Ȯ��" onclick="contentDelete()">

	<script src="https://code.jquery.com/jquery-3.6.0.js"
  			integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
  			crossorigin="anonymous">
	</script>
	<script type="text/javascript">
		function contentDelete(){
			console.log(111111111111);
			var param = getParam('id').split('_');
			var bbsID = param[1];
			var password = $('#delPassword').val();
			deleteAction(bbsID, password);
	    }
		
		function deleteAction(bbsID, password) {
			console.log('remove inside');	
			var data = {bbsID: bbsID, password: password}				
			$.ajax({
				type: "post",
				url: "freeBoardDeleteController",
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				dataType: "json",				
				success: function(json) {
					var resultCode = json[0].resultCode;
					if (resultCode == 'ok') {
						opener.passwordError("�����Ǿ����ϴ�.");		
					} else if (resultCode == 'wrongPass') {
						opener.passwordError("�߸��� ��й�ȣ �Դϴ�.");
					} else if (resultCode == 'error') {
						opener.passwordError("������ ���̽� ���� �Դϴ�.");
					}
					window.close();
				},
				error: function(json) {
					opener.passwordError("�����ͺ��̽� ���� �Դϴ�.");
				}
			});
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