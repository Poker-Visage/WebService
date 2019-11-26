function checkUser(){
	var code=document.getElementById("user").value;
	var span=document.getElementById("msg_user");
	var reg=/^\w{5,10}$/;
	console.log(code);
	if(reg.test(code)){
		span.className="ok";
		return true;
	}else{
		span.className="error";
		return false;
	}
}

function checkPwd(){
	var code=document.getElementById("pwd").value;
	var span=document.getElementById("msg_pwd");
	var reg=/^\w{5,10}$/;
	if(reg.test(code)){
		span.className="ok";
		return true;
	}else{
		span.className="error";
		return false;
	}
}