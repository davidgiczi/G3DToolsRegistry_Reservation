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

if(document.getElementById("additionalSaved") != null){	
alert(document.getElementById("additionalSaved").value);
}

if(document.getElementById("locSaved") != null){	
alert(document.getElementById("locSaved").value);
}

if(document.getElementById("alreadyUsed") != null){	
alert(document.getElementById("alreadyUsed").value);
}

if(document.getElementById("otherUser") != null){	
	if(confirm(document.getElementById("otherUser").value)){
	
	document.getElementById("cookies").submit();
}
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

function searchAdditional(){
	var str = document.getElementById("search-field").value;
	location.href = location.origin + "/tools-registry/admin/search-additional?text=" + str;
}

function searchInToolHistory(){
	var str = document.getElementById("search-field").value;
	location.href = location.origin + "/tools-registry/admin/search-in-tool-history?text=" + str;
}

function searchInToolsInUse(){
	var str = document.getElementById("search-field").value;
	location.href = location.origin + "/tools-registry/admin/search-in-tools-in-use?text=" + str;
}

function cancelRestoreInstrument(id){
	
	location.href = location.origin + "/tools-registry/admin/cancel-restore-instrument?id=" + id;
}

function cancelRestoreAdditional(id){
	
	location.href = location.origin + "/tools-registry/admin/cancel-restore-additional?id=" + id;
}

function takeawayInstrument(id){
	var workerid = document.getElementById(id+"worker").value;
	if("-" === workerid){
		alert("Nem választottál dolgozót az eszköz felvételéhez.");
	}
	else{
	var place = document.getElementById(id+"place").value;
	var comment = document.getElementById(id+"comment").value;
	document.getElementById("from-instrument-id").value = id;
	document.getElementById("from-worker-id").value = workerid;
	document.getElementById("from-location").value = place;
	document.getElementById("from-msg").value = comment;
	document.getElementById("takeaway-instrument-form").submit();
}
}

function takeawayAdditional(id){
	var workerid = document.getElementById(id+"worker").value;
	if("-" === workerid){
		alert("Nem választottál dolgozót az eszköz felvételéhez.");
		return;
	}
	var instrumentid = document.getElementById(id+"instrument").value;
	var place = document.getElementById(id+"place").value;
	var comment = document.getElementById(id+"comment").value;
	if("-" === instrumentid){
		if(confirm("Biztos, hogy műszer nélkül veszed fel a kiegészítőt?")){
			sendDataForTakeAway(id, instrumentid, workerid, place, comment);
			return;
		}
		return;
	}
	sendDataForTakeAway(id, instrumentid, workerid, place, comment);
}
	
function sendDataForTakeAway(additionalId, instrumentId, workerId, place, comment){
	
	document.getElementById("from-additional-id").value = additionalId;
	document.getElementById("from-instrument-id").value = instrumentId;
	document.getElementById("from-worker-id").value = workerId;
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

 function addInstrument(){
	
	var newInst = prompt(document.getElementById("newInstMsg").value).trim();
	
	if(isValidInputText(newInst)){
			location.href = location.origin + "/tools-registry/admin/add-instrument?inst=" + newInst;
	}
	else{
			alert(document.getElementById("newInstError").value);
		}
		
	}
	
	function addAdditional(){
	
	var newAdditional = prompt(document.getElementById("newAdditionalMsg").value).trim();
	
	if(isValidInputText(newAdditional)){
			location.href = location.origin + "/tools-registry/admin/add-additional?additional=" + newAdditional;
	}
	else{
			alert(document.getElementById("newAdditionalError").value);
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

function getDate(page){
	var from = document.getElementById("pickupdate").value;
	var to = document.getElementById("putdowndate").value;
	
	if("tools-history" === page){
	location.href = location.origin + "/tools-registry/admin/search-by-dates-in-tools-history?from=" + from + "&to=" + to;
	}
	else if("tools-in-use" === page){
	location.href = location.origin + "/tools-registry/admin/search-by-dates-in-tools-in-use?from=" + from + "&to=" + to;
	}
}

function sendPickUpDateInToolsInUse(){
	var date = document.getElementById("pickupdate").value;
	location.href = location.origin + "/tools-registry/admin/search-by-pick-up-date-in-tools-in-use?date=" + date;
}

function sendPickUpDateInToolsHistory(){
	var date = document.getElementById("pickupdate").value;
	var opt = document.getElementById("search-selection").value;
	location.href = location.origin + "/tools-registry/admin/search-by-pick-up-date-in-tools-history?date=" + date + "&option=" + opt;
}

function sendPutDownDateInToolsHistory(){
	var date = document.getElementById("putdowndate").value;
	var opt = document.getElementById("search-selection").value;
	location.href = location.origin + "/tools-registry/admin/search-by-put-down-date-in-tools-history?date=" + date + "&option=" + opt;
}

function searchInToolHistory(){
	var str = document.getElementById("search-field").value;
	var opt = document.getElementById("search-selection").value;
	location.href = location.origin + "/tools-registry/admin/search-in-tools-history?text=" + str + "&option=" + opt;
} 
