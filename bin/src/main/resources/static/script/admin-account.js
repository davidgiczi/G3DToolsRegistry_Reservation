/**
 * 
 */
function enterWorkerAccount(id){
	location.href = location.origin + "/tools-registry/admin/enter?id=" + id;
}

function enabledUserAccount(id){
	location.href = location.origin + "/tools-registry/admin/enabled?id=" + id;
	}
	
function changeRole(id){
	location.href = location.origin + "/tools-registry/admin/change-role?id=" + id;
}