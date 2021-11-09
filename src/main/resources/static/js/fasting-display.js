const startFastInstant = new Date(startTime);
const startMoment = moment(startFastInstant);
const endMoment = moment(startFastInstant).add(duration, 'hours');

const canvas = document.getElementById("canvas");
var ctx = canvas.getContext("2d");
ctx.lineWidth = 10;
const gradient = ctx.createLinearGradient(0, 0, 200, 200);
gradient.addColorStop(0, "#FF8C8C");
gradient.addColorStop(1, "#FDCD65");
ctx.strokeStyle = gradient;
const fontSize = 25;
ctx.font = `${fontSize}px Roboto`;
ctx.textAlign = "center";
ctx.fillStyle = "#555555";
ctx.lineCap = 'round'
const radius = 70;
function semiCircle(deg) {
  percent = Math.round((deg * 100) / 2);

  const now = new moment();
  const hoursUntil = String(moment.duration(endMoment.diff(now)).hours()).padStart(2, '0');
  const minutesUntil = String(moment.duration(endMoment.diff(now)).minutes()).padStart(2, '0');
  const secondsUntil = String(moment.duration(endMoment.diff(now)).seconds()).padStart(2, '0');
  const label = moment().isAfter(endMoment) ? '00:00:00' : `${hoursUntil}:${minutesUntil}:${secondsUntil}`;

  ctx.clearRect(0, 0, 200, 200);

  ctx.clearRect(0,0,200,200);
	
	ctx.beginPath();
	ctx.strokeStyle = "#EFEFEF";
	ctx.arc(100, 75, radius, 1.5 * Math.PI, 2 * Math.PI + 1.5*Math.PI);
	ctx.stroke();
	
	ctx.strokeStyle = gradient;
	ctx.beginPath();
	ctx.arc(100, 75, radius, 1.5 * Math.PI, deg * Math.PI + 1.5*Math.PI);
	ctx.fillText(label, canvas.width/2, canvas.height/2 - fontSize/1.5);
	ctx.stroke();
}

let rad = 2;

const intervalMillis = endMoment.diff(startMoment, 'milliseconds');
const elapsedTimeLabel = document.getElementById('elapsed-time')
const interval = window.setInterval(() => {
  const elapsedMillis = moment().diff(startMoment, 'milliseconds');
  const elapsedTime = moment.utc(moment.duration(moment().diff(startMoment)).as('milliseconds')).format('HH:mm:ss');
  elapsedTimeLabel.innerText = elapsedTime;

  rad = (2 * elapsedMillis) / intervalMillis;

  if (rad >= 2) {
    rad = 2;
  }

  semiCircle(rad);

}, 1000);
