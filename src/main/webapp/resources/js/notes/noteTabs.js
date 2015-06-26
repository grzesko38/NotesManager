NoteTabsScripts = {
	bindTabs: function() {	
		$("#tabs").tabs();
	},
	
	bindGoogleMap: function() {
		var map;
		var fillFormWithLatLng = function(place) {
			$('input#longitude').val(place.geometry.location.lng());
			$('input#latitude').val(place.geometry.location.lat());
		};
		
		if (!$('input#altitude').val() && !$('input#longitude').val()) {
			map = GoogleMapScripts.init(fillFormWithLatLng);
		} else {
			var lat = parseFloat($('#noteForm input#latitude').val());
			var lng = parseFloat($('#noteForm input#longitude').val());
			var latlng = new google.maps.LatLng(lat, lng);
			map = GoogleMapScripts.codeLatLng(latlng, fillFormWithLatLng);
		}
		
		$("#map-li").click(function() {
			google.maps.event.trigger(map, "resize");
		});
	},
	
	bindAll: function() {
		with (NoteTabsScripts) {
			bindTabs();
			bindGoogleMap();
		}
	}
}

$(document).ready(function() {
	with (NoteTabsScripts) {
		bindAll();
	}
	
	
});
