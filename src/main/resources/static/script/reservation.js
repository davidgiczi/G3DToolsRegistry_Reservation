/**
 * 
 */
 let noWorker = document.getElementById("noWorker");
 let noChosenTool = document.getElementById("noTool");
 let invalidDates = document.getElementById("invalidDates");
 let invalidReservation = document.getElementById("invalidReservation");

if(noWorker != null){
	alert("Nem választottál dolgozót az előjegyzéshez.");
}
if(noChosenTool != null){
	alert("Nem választottál eszközt az előjegyzéshez.");
}
if(invalidDates != null){
	alert("Nem adtad meg az előjegyzés felvételének és/vagy leadásának napját.")
}
if(invalidReservation != null){
	alert("A választott eszköz(ök) nem jegyezhető(k) elő az adott időszakra, mivel egy korábbi előjegyzés ideje érinti a megadott időszakot.")
}
 
 function sendDataForUser(){
	let instrumentId = document.getElementById("reserved-instrument").value;
	let additionalId = document.getElementById("reserved-additional").value;
	let startDate = document.getElementById("start-date").value;
	let endDate  = document.getElementById("end-date").value;
	document.getElementById("send-instrument").value = instrumentId;
	document.getElementById("send-additional").value = additionalId;
	document.getElementById("start-day").value = startDate;
	document.getElementById("end-day").value = endDate;
	document.getElementById("reservation-form").submit();
}

function sendDataForAdmin(){
	let workerId = document.getElementById("reserver-worker").value;
	let instrumentId = document.getElementById("reserved-instrument").value;
	let additionalId = document.getElementById("reserved-additional").value;
	let startDate = document.getElementById("start-date").value;
	let endDate  = document.getElementById("end-date").value;
	document.getElementById("send-worker").value = workerId;
	document.getElementById("send-instrument").value = instrumentId;
	document.getElementById("send-additional").value = additionalId;
	document.getElementById("start-day").value = startDate;
	document.getElementById("end-day").value = endDate;
	document.getElementById("reservation-form").submit();
}

function deleteReservationForUser(id){
	if(confirm("Biztos, hogy törlöd az előjegyzést?")){
	location.href = location.origin + "/tools-registry/user/reservation/delete?id=" + id;
	}
}
function deleteReservationForAdmin(id){
	if(confirm("Biztos, hogy törlöd az előjegyzést?")){
	location.href = location.origin + "/tools-registry/admin/reservation/delete?id=" + id;
	}
}