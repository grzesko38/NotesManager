GoogleMapScripts = {
	init: function(callback) {
		var map = this.createMap(true);
		var input = this.createPacInput();
		var autocomplete = this.createAutocomplete(map, input);
		var infowindow = new google.maps.InfoWindow();
		var marker = this.createMarker(map);
		
		map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

		this.addAutocompleteListener(map, autocomplete, infowindow, marker, callback);
		return map;
	},
	
	codeLatLng: function(latlng, callback) {
		var map = this.createMap(false);
		var geocoder = new google.maps.Geocoder();
		var infowindow = new google.maps.InfoWindow();
		var marker = this.createMarker(map);
		if (callback) {
			var input = this.createPacInput();
			var autocomplete = this.createAutocomplete(map, input);
			map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);
		}
		geocoder.geocode({
			'latLng' : latlng
		}, function(results, status) {
			if (status == google.maps.GeocoderStatus.OK) {
				if (results[1]) {
					map.setZoom(11);
					marker = new google.maps.Marker({
						position : latlng,
						map : map
					});
					infowindow.setContent(results[1].formatted_address);
					infowindow.open(map, marker);
				} else {
					alert('No results found');
				}
			} else {
				alert('Geocoder failed due to: ' + status);
			}
		});
		
		if (callback) {
			this.addAutocompleteListener(map, autocomplete, infowindow, marker, callback);
		}
		return map;
	},
	
	createMap: function(findUserPos) {
		
		var mapOptions = {
			center : new google.maps.LatLng(75, 140),
			zoom : 2
		};
		
		var map = new google.maps.Map($('#map-canvas')[0], mapOptions);
		
		if(findUserPos) {
			function findUserPosition() {
			    if (navigator.geolocation) {
			        navigator.geolocation.getCurrentPosition(success);
			    } 
			}
			
			function success(position) {
			    var coords = new google.maps.LatLng(
			        position.coords.latitude,
			        position.coords.longitude
			    );
			    
			    map.setCenter(coords);
			    map.setZoom(15);
			    
			    return map;
			}
			
			findUserPosition();
		}
		
		return map;
	},
	
	createPacInput: function() {
		var input = $('#pac-input')[0];
		$(input).val('');
		return input;
	},
	
	createAutocomplete: function(map, input) {
		var autocomplete = new google.maps.places.Autocomplete(input);
		autocomplete.setTypes([]);
		autocomplete.bindTo('bounds', map);
		return autocomplete
	},
	
	createMarker: function(map) {
		var marker = new google.maps.Marker({
			map : map,
			anchorPoint : new google.maps.Point(0, -29)
		});
		return marker;
	},
	
	addAutocompleteListener: function(map, autocomplete, infowindow, marker, callback) {
		google.maps.event.addListener(
			autocomplete,
			'place_changed',
			function() {
				infowindow.close();
				marker.setVisible(false);
				var place = autocomplete.getPlace();
				if (!place.geometry) {
					window.alert("Autocomplete's returned place contains no geometry");
					return;
				}
				if (place.geometry.viewport) {
					map.fitBounds(place.geometry.viewport);
				} else {
					map.setCenter(place.geometry.location);
					map.setZoom(17);
				}
				marker.setIcon(
				({
					url : place.icon,
					size : new google.maps.Size(71, 71),
					origin : new google.maps.Point(0, 0),
					anchor : new google.maps.Point(17, 34),
					scaledSize : new google.maps.Size(35, 35)
				}));
				marker.setPosition(place.geometry.location);
				marker.setVisible(true);

				var address = '';
				if (place.address_components) {
					address = [
						(place.address_components[0] && place.address_components[0].short_name || ''),
						(place.address_components[1] && place.address_components[1].short_name || ''),
						(place.address_components[2] && place.address_components[2].short_name || '')
					].join(' ');
				}

				infowindow.setContent('<div><strong>' + place.name	+ '</strong><br>' + address);
				infowindow.open(map, marker);
				
				callback(place);
			}
		);
	}
}
