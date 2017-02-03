var timer;
var totalSeconds;

function createTimer(timerId, time) {
	timer = document.getElementById(timerId);
	totalSeconds = time * 60;
	updateTimer()
	window.setTimeout("tick()", 1000);
}

function tick() {
	totalSeconds -= 1;
	if (totalSeconds == -1) {
		alert("Time is up");
		document.getElementById("test").submit();
	} else {
		updateTimer()
		window.setTimeout("tick()", 1000);
	}
}

function updateTimer() {

	var seconds = totalSeconds;
	var hours = Math.floor(seconds / 3600);
	seconds -= hours * (3600);
	var minutes = Math.floor(seconds / 60);
	seconds -= minutes * (60);

	var timeRemaining = leadingZero(hours) + ":" + leadingZero(minutes) + ":"
			+ leadingZero(seconds)

	timer.innerHTML = "Time left  " + timeRemaining;
}

function leadingZero(time) {

	return (time < 10) ? "0" + time : +time;

}
