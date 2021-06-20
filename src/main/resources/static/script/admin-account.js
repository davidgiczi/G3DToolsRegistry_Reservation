/**
 * 
 */

if(document.getElementById("search-field") != null){	
document.getElementById("search-field").addEventListener("focus", setup);
document.getElementById("search-field").focus();
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
	location.href = location.origin + "/tools-registry/admin/search?text=" + str;
}

