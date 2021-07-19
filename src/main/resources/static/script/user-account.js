/**
 * 
 */
 
 if(document.getElementById("changePassMsg") != null){
	alert(document.getElementById("changePassMsg").value);
}
 
 function changePassword(){
	
	var newPwd = prompt(document.getElementById("newPwdMsg").value).trim();
	
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

function takeawayInstrument(id){
	
	var place = document.getElementById(id+"place").value;
	var comment = document.getElementById(id+"comment").value;
	document.getElementById("from-instrument-id").value = id;
	document.getElementById("from-location").value = place;
	document.getElementById("from-msg").value = comment;
	document.getElementById("takeaway-instrument-form").submit();
}

function takeawayAdditional(id){
	
	var instrumentid = document.getElementById(id+"instrument").value;
	var place = document.getElementById(id+"place").value;
	var comment = document.getElementById(id+"comment").value;
	if("-" === instrumentid){
		if(confirm("Biztos, hogy műszer nélkül veszed fel a kiegészítőt?")){
			sendDataForTakeAway(id, instrumentid, place, comment);
			return;
		}
		return;
	}
	sendDataForTakeAway(id, instrumentid, place, comment);
}
	
function sendDataForTakeAway(additionalId, instrumentId, place, comment){
	
	document.getElementById("from-additional-id").value = additionalId;
	document.getElementById("from-instrument-id").value = instrumentId;
	document.getElementById("from-location").value = place;
	document.getElementById("from-msg").value = comment;
	document.getElementById("takeaway-additional-form").submit();
	
}

function restoreTool(id, isInstrument){
	
	var place = document.getElementById(id+isInstrument+"place").value;
	var comment = document.getElementById(id+isInstrument+"comment").value;
	
	if("true" === isInstrument){
		
		if(confirm("Biztos, hogy leadod a műszert minden kiegészítőjével együtt?")){
			
			sendDataForRestore(id, isInstrument, place, comment);
			
		}
		
	}
	else if("false" === isInstrument){
		
		if(confirm("Ha a kiegészítő műszerrel lett felvéve, akkor biztos, hogy csak a kiegészítőt adod le?")){
			
			sendDataForRestore(id, isInstrument, place, comment);
		}
		
	}
	
}

function sendDataForRestore(id, isInstrument, place, comment){

	document.getElementById("back-tool-id").value = id;
	document.getElementById("is-instrument").value = isInstrument;
	document.getElementById("back-location").value = place;
	document.getElementById("back-msg").value = comment;
	document.getElementById("tool-restore-form").submit();
}
