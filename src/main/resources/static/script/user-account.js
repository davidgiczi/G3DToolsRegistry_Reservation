/**
 * 
 */
 
 if(document.getElementById("changePassMsg") != null){
	alert(document.getElementById("changePassMsg").value);
}
 
 function changePassword(){
	
	var newPwd = prompt(document.getElementById("newPwdMsg").value);
	
	if(newPwd != null){
		
		if(isValidPwd(newPwd)){
			
			document.getElementById("changePass").value = newPwd;
			document.getElementById("changePassForm").submit();
			
		}
		else{
			alert(document.getElementById("newPwdError").value);
		}
		
	}
	
}

function isValidPwd(pass){
	
	if(pass == null){
	 pass = document.getElementById("pass").value;
	}		
	if(pass.length < 3) {
			return false;
		}
		
	return true;
}