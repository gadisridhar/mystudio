<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type='text/javascript' src='dwr/interface/Hello.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>

<script type="text/javascript">
function hello() {
    var user = dwr.util.getValue('user');
    Hello.getBean(user, callback);
}
    
function callback(msg) {
	alert(msg);
   dwr.util.setValue('result', msg.chineseName);
} 

</script>
</head>

<body>
	<input id="user" type="text" />
	<input type='button' value='Hello' onclick='hello();' />
	<div id="result"></div>
</body>
</html>
