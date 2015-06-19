Map = {
		init: function() {

		}
		
}



function initialize() {
  var mapOptions = {
    center: new google.maps.LatLng(-33.8688, 151.2195),
    zoom: 13
  };
  var map = new google.maps.Map(document.getElementById('map-canvas'),
    mapOptions);

  var input = /** @type {HTMLInputElement} */(
      document.getElementById('pac-input'));

  map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

  var autocomplete = new google.maps.places.Autocomplete(input);
  autocomplete.setTypes([]);
  autocomplete.bindTo('bounds', map);
//  google.maps.event.addListener(map, "rightclick", function(event) {
//	    var lat = event.latLng.lat();
//	    var lng = event.latLng.lng();
//	    // populate yor box/field with lat, lng
//	    alert("Lat=" + lat + "; Lng=" + lng);
//	});

  var infowindow = new google.maps.InfoWindow();
  var marker = new google.maps.Marker({
    map: map,
    anchorPoint: new google.maps.Point(0, -29)
  });

  google.maps.event.addListener(autocomplete, 'place_changed', function() {
    infowindow.close();
    marker.setVisible(false);
    var place = autocomplete.getPlace();
    if (!place.geometry) {
      window.alert("Autocomplete's returned place contains no geometry");
      return;
    }

    // If the place has a geometry, then present it on a map.
    if (place.geometry.viewport) {
      map.fitBounds(place.geometry.viewport);
    } else {
      map.setCenter(place.geometry.location);
      map.setZoom(17);  // Why 17? Because it looks good.
    }
    marker.setIcon(/** @type {google.maps.Icon} */({
      url: place.icon,
      size: new google.maps.Size(71, 71),
      origin: new google.maps.Point(0, 0),
      anchor: new google.maps.Point(17, 34),
      scaledSize: new google.maps.Size(35, 35)
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

    infowindow.setContent('<div><strong>' + place.name + '</strong><br>' + address);
    infowindow.open(map, marker);
  });
}

google.maps.event.addDomListener(window, 'load', initialize);









//$(document).ready(function() {
//	
//	var pac_input = document.getElementById('pac-input');
//	(function pacSelectFirst(input){
//	    // store the original event binding function
//	    var _addEventListener = (input.addEventListener) ? input.addEventListener : input.attachEvent;
//
//	    function addEventListenerWrapper(type, listener) {
//	    // Simulate a 'down arrow' keypress on hitting 'return' when no pac suggestion is selected,
//	    // and then trigger the original listener.
//
//	    if (type == "keydown") {
//	      var orig_listener = listener;
//	      listener = function (event) {
//	        var suggestion_selected = $(".pac-item-selected").length > 0;
//	        if (event.which == 13 && !suggestion_selected) {
//	          var simulated_downarrow = $.Event("keydown", {keyCode:40, which:40})
//	          orig_listener.apply(input, [simulated_downarrow]);
//	        }
//
//	        orig_listener.apply(input, [event]);
//	      };
//	    }
//
//	    // add the modified listener
//	    _addEventListener.apply(input, [type, listener]);
//	  }
//
//	  if (input.addEventListener)
//	    input.addEventListener = addEventListenerWrapper;
//	  else if (input.attachEvent)
//	    input.attachEvent = addEventListenerWrapper;
//
//	})(pac_input);
//	
//	
//	$(function(){
//		  var autocomplete = new google.maps.places.Autocomplete(pac_input);
//	});
//});


