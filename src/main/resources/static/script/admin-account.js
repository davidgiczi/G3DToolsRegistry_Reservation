/**
 * 
 */

if(document.getElementById("search-field") != null){	
document.getElementById("search-field").addEventListener("focus", setup);
document.getElementById("search-field").focus();
}
if(document.getElementById("instSaved") != null){	
alert(document.getElementById("instSaved").value);
}

if(document.getElementById("locSaved") != null){	
alert(document.getElementById("locSaved").value);
}

if(document.getElementById("alreadyUsed") != null){	
alert(document.getElementById("alreadyUsed").value);
}

if(document.getElementById("workerNotChosen") != null){	
alert(document.getElementById("workerNotChosen").value);
}

function setup(){
if(document.getElementById("search-value").value != null){
var searchValue = document.getElementById("search-value").value;
document.getElementById("search-field").value = searchValue;
}
} 
 
function enterWorkerAccount(id){
	location.href = location.origin + "/tools-registry/admin/enter?id=" + id;
}

function enabledUserAccount(id){
	location.href = location.origin + "/tools-registry/admin/enabled?id=" + id;
	}
	
function changeRole(id){
	location.href = location.origin + "/tools-registry/admin/change-role?id=" + id;
}

function searchWorker(){
	var str = document.getElementById("search-field").value;
	location.href = location.origin + "/tools-registry/admin/search-worker?text=" + str;
}

function searchInstrument(){
	var str = document.getElementById("search-field").value;
	location.href = location.origin + "/tools-registry/admin/search-instrument?text=" + str;
}

function cancelRestore(id){
	
	location.href = location.origin + "/tools-registry/admin/cancel-restore?id=" + id;
}

function takeawayInstrument(id){
	var workerid = document.getElementById(id+"worker").value;
	var place = document.getElementById(id+"place").value;
	var comment = document.getElementById(id+"comment").value;
	document.getElementById("from-instrument-id").value = id;
	document.getElementById("from-worker-id").value = workerid;
	document.getElementById("from-location").value = place;
	document.getElementById("from-msg").value = comment;
	document.getElementById("takeaway-form").submit();
}

function restoreInstrument(id){
	var place = document.getElementById(id+"place").value;
	var comment = document.getElementById(id+"comment").value;
	document.getElementById("back-instrument-id").value = id;
	document.getElementById("back-location").value = place;
	document.getElementById("back-msg").value = comment;
	document.getElementById("instrument-restore-form").submit();
}

 function addInstrument(){
	
	var newInst = prompt(document.getElementById("newInstMsg").value).trim();
	
	if(isValidInputText(newInst)){
			location.href = location.origin + "/tools-registry/admin/add-instrument?inst=" + newInst;
	}
	else{
			alert(document.getElementById("newInstError").value);
		}
		
	}
	
	function addLocation(){
	
	var newLocation = prompt(document.getElementById("newLocationMsg").value).trim();
	
	if(isValidInputText(newLocation)){
			location.href = location.origin + "/tools-registry/admin/add-location?location=" + newLocation;
	}
	else{
			alert(document.getElementById("newLocationError").value);
		}
		
	}
	
function isValidInputText(text){
		
	if(text.length < 3) {
			return false;
		}
		
	return true;
}

function getDate(){
	var from = document.getElementById("pickupdate").value;
	var to = document.getElementById("putdowndate").value;
	location.href = location.origin + "/tools-registry/admin/search-by-dates?from=" + from + "&to=" + to;
}


