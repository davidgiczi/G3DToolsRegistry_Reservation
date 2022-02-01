/**
 * 
 */
 
 let noChosenTool = document.getElementById("noTool");
 let invalidDates = document.getElementById("invalidDates");
 let invalidReservation = document.getElementById("invalidReservation");;
 
if(noChosenTool != null){
	alert("Nem választottál eszközt az előjegyzéshez.");
}
if(invalidDates != null){
	alert("Nem adtad meg az előjegyzés felvételének és/vagy leadásának napját.")
}
if(invalidReservation != null){
	alert("A választott eszköz nem jegyezhető elő az adott időszakra, mivel egy korábbi előjegyzés ideje érinti a megadott időszakot.")
}
 
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
