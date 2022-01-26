/**
 * 
 */
 
 
 function sendData(){
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