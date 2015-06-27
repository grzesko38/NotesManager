NotesDetailscripts = {
	bindGoogleMap: function() {
		$(document).ready(function() {
			var lat = parseFloat($('#latlng').data('lat'));
			var lng = parseFloat($('#latlng').data('lng'));
			var latlng = new google.maps.LatLng(lat, lng);
			GoogleMapScripts.codeLatLng(latlng);
		});
	},
	
	bindAll: function() {
		with (NotesDetailscripts) {
			bindGoogleMap();
		}
	}
}

$(document).ready(function() {
	with (NotesDetailscripts) {
		bindAll();
	}
});