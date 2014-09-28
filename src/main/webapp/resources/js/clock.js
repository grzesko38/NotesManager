AnalogClock = {
	start: function() {
		setTimeout(updateClocks, 0);
		setInterval(updateClocks, 1000);
	},

	update: function() {
		var now = new Date();
		var h = now.getHours() % 12;
		var m = now.getMinutes();
		var s = now.getSeconds();
		
		// --- Analog clock ---//
	
		var canvas = document.getElementById("clock");
		var context = canvas.getContext("2d");
		
		// You can change this to make the clock as big or small as you want.
		// Just remember to adjust the canvas size if necessary.
		var clockRadius = canvas.width / 2;
	
		// Make sure the clock is centered in the canvas
		var clockX = canvas.width / 2;
		var clockY = canvas.height / 2;
	
		// Make sure TAU is defined (it's not by default)
		Math.TAU = 2 * Math.PI;
		
		context.clearRect(0, 0, canvas.width, canvas.height);
		
		// Draw background
	
		for (var i = 0; i < 12; i++)
		{
			var innerDist		= (i % 3) ? 0.75 : 0.7;
			var outerDist		= (i % 3) ? 0.95 : 1.0;
			context.lineWidth 	= (i % 3) ? clockRadius / 50 : clockRadius / 20;
			context.strokeStyle = '#ececec';
			
			var armRadians = (Math.TAU * (i/12)) - (Math.TAU/4);
			var x1 = clockX + Math.cos(armRadians) * (innerDist * clockRadius);
			var y1 = clockY + Math.sin(armRadians) * (innerDist * clockRadius);
			var x2 = clockX + Math.cos(armRadians) * (outerDist * clockRadius);
			var y2 = clockY + Math.sin(armRadians) * (outerDist * clockRadius);
			
			context.beginPath();
			context.moveTo(x1, y1); // Start at the center
			context.lineTo(x2, y2); // Draw a line outwards
			context.stroke();
		}
	
		
		// Draw arms
	
		function drawArm(progress, armThickness, armLength, armColor, offset) {
			var armRadians = (Math.TAU * progress) - (Math.TAU/4);
			var targetX = clockX + Math.cos(armRadians) * (armLength * clockRadius);
			var targetY = clockY + Math.sin(armRadians) * (armLength * clockRadius);
	
			context.lineWidth = armThickness;
			context.strokeStyle = armColor;
	
			context.beginPath();
			context.moveTo(clockX + offset, clockY + offset); // Start at the center
			context.lineTo(targetX + offset, targetY + offset); // Draw a line outwards
			context.stroke();
		}
		
		var hProgress = (h/12) + (1/12)*(m/60) + (1/12)*(1/60)*(s/60);
		var mProgress =                 (m/60) +        (1/60)*(s/60);
		var sProgress =                                        (s/60);
		
		//shadows
		drawArm( hProgress, clockRadius / 15,  1/2, 'rgba(0, 0, 0, 0.2)', 2); 	// Hour
		drawArm( mProgress, clockRadius / 20,  3/4, 'rgba(0, 0, 0, 0.2)', 2); 	// Minute
		drawArm( sProgress, clockRadius / 25,    1, 'rgba(0, 0, 0, 0.2)', 3); 	// Second
		
		//arms
		drawArm( hProgress, clockRadius / 15,  1/2, '#eeeeee', 0); 	// Hour
		drawArm( mProgress, clockRadius / 20,  3/4, '#eeeeee', 0); 	// Minute
		drawArm( sProgress, clockRadius / 25,    1, '#eeeeee', 0); 	// Second
	}
}
$( document ).ready(function() {
	AnalogClock.start();
});