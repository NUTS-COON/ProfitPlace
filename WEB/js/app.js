var seekbar_cost = new Seekbar.Seekbar({
        renderTo: "#seekbar-cost",
        valueListener: function (value) {
        	document.getElementById('cost-value').innerText = Math.round(value)+'₽';
        },

        thumbColor: '#ccff0000',
        negativeColor: '#ff0000',
        positiveColor: '#CCC',
        barSize: 3,
        thumbSize: 1,
        needleSize: 0.2,
        minValue: 1100,
        maxValue: 500000,
        value: 100000
    });

var seekbar_space = new Seekbar.Seekbar({
        renderTo: "#seekbar-space",
        valueListener: function (value) {
        	document.getElementById('space-value').innerHTML = Math.round(value)+' М<sup>2</sup>';
        },

        thumbColor: '#ccff0000',
        negativeColor: '#ff0000',
        positiveColor: '#CCC',
        barSize: 3,
        thumbSize: 1,
        needleSize: 0.2,
        minValue: 1,
        maxValue: 100,
        value: 20
    });

var ratingbar_a = new Seekbar.Seekbar({
        renderTo: "#ratingbar-a",
        valueListener: function (value) {
        	document.getElementById('space-value').innerHTML = Math.round(value)+' М<sup>2</sup>';
        },

        thumbColor: '#ccff0000',
        negativeColor: '#ff0000',
        positiveColor: '#CCC',
        barSize: 3,
        thumbSize: 1,
        needleSize: 0.2,
        minValue: 1,
        maxValue: 9,
        value: 10
    });


var ratingbar_b = new Seekbar.Seekbar({
        renderTo: "#ratingbar-b",
        valueListener: function (value) {
        	document.getElementById('space-value').innerHTML = Math.round(value)+' М<sup>2</sup>';
        },

        thumbColor: '#ccff0000',
        negativeColor: '#ff0000',
        positiveColor: '#CCC',
        barSize: 3,
        thumbSize: 1,
        needleSize: 0.2,
        minValue: 1,
        maxValue: 9,
        value: 10
    });


var ratingbar_c = new Seekbar.Seekbar({
        renderTo: "#ratingbar-c",
        valueListener: function (value) {
        	document.getElementById('space-value').innerHTML = Math.round(value)+' М<sup>2</sup>';
        },

        thumbColor: '#ccff0000',
        negativeColor: '#ff0000',
        positiveColor: '#CCC',
        barSize: 3,
        thumbSize: 1,
        needleSize: 0.2,
        minValue: 1,
        maxValue: 9,
        value: 10
    });
 
var business_types = ["Cafe", "Shop", ""];
var selected_type  = business_types[0];

function setup() {
    map = L.map('mapContainer').setView([45.0448400, 38.9760300], 13);
    L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
		attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
		maxZoom: 18,
		id: 'mapbox.streets',
		accessToken: 'pk.eyJ1IjoiZXBweTEiLCJhIjoiY2p0dGUzZ3dlMHRlNDRkbW01NnRrcWliOCJ9.-veJzgAvXB_SAGWwE-Ylew'
	}).addTo(map);

	setCurrentParams('Baker street 221b', '9999', '13', '88005553535', 2, 1, 2, 3);
}

function setElemValue(element, value) {
	document.getElementById(element).innerHTML = value
}

function fixAddress(address) {
	var result = ''
	var arr = address.split(',')

	for(var i=2; i<arr.length; i++) result += arr[i];

	return result
}

function setCurrentParams(address, price, space, phone, rating_total, rating_a, rating_b, rating_c) {
	document.getElementById('result-address').innerHTML = fixAddress(address);
	document.getElementById('result-price').innerHTML = price + '₽';
	document.getElementById('result-space').innerHTML = space + ' М<sup>2</sup>';
	document.getElementById('result-phone').innerHTML = phone;
	document.getElementById('rating-total').innerText = '' + Math.round(rating_total);
	document.getElementById('rating-a').innerText = '' + Math.round(rating_a, -1);
	document.getElementById('rating-b').innerText = '' + Math.round(rating_b, -1);
	document.getElementById('rating-c').innerText = '' + Math.round(rating_c, -1);
}

var API_URL = "http://192.168.43.197:8080/";

var results = undefined;
var markers = [];
var current_point = -1;

function updateMarkers() {
	console.log(results[0].coords);
	for(var i=0; i<results.length; i++) {
		markers.push(
			L.marker([results[i].coords.latitude, results[i].coords.longitude]).addTo(map)
	    	.bindPopup('<b>'+results[i].address+'</b><br>'+
	    	results[i].price+'₽<br>'+results[i].space+
	    	' М<sup>2</sup><br>'+'<b>'+results[i].rating+'<b>'));
	}
	markers[current_point].openPopup();
}


function acceptResults() {
	setCurrentParams(
		results[current_point].address,
		results[current_point].price,
		results[current_point].space,
		'+7(928)'+Math.round(Math.random()*9999999),
		results[current_point].rating,
		results[current_point].numberOfCompetitors,
		results[current_point].numberOfBusStop,
		results[current_point].numberOfInterestingPlaces);

	return location.href='#mapContainer';
}

function startSearch() {
	var xhr = new XMLHttpRequest();
	var url = API_URL + "findRecommendedPlaces/";
	xhr.open("POST", url, true);
	xhr.setRequestHeader("Content-Type", "application/json");
	xhr.onreadystatechange = function () {
	    var json = JSON.parse(xhr.responseText);
	    results = json;
	    //console.log(results);
	    current_point = 0;
	    acceptResults(json);
	    updateMarkers();
	};

	var r = 0.5 * 6378137 * (map.getBounds().getEast() * Math.PI / 180 - map.getBounds().getWest() * Math.PI / 180);

	//console.log(map.getCenter());

	var t = {
		zone: {latitude: map.getCenter().lat, longitude: map.getCenter().lng, radius: r},
		type: selected_type,
		maxPrice: seekbar_cost.value,
		minSpace: seekbar_space.value
	}

	xhr.send(JSON.stringify(t));
}

function makeHeatMap(pts) {
    var arr = []

    for(var i=0; i<pts.length; i++) {
        arr.push([pts[i].latitude, pts[i].longitude, pts[i].weight])
    }

    console.log(arr)

    var heat = L.heatLayer(arr, {radius: 25}).addTo(map);
}

function requestDetailInfo(lat1, len1) {
	var xhr = new XMLHttpRequest();
	var url = API_URL + "getThermalMapping/";
	xhr.open("POST", url, true);
	xhr.setRequestHeader("Content-Type", "application/json");
	xhr.onreadystatechange = function () {
        var json = JSON.parse(xhr.responseText);
	    makeHeatMap(json);
    };
    
	var t = {
		latitude: lat1, longitude: len1
	}

    console.log()
	xhr.send(JSON.stringify(t));
}

function prevResult() {
	current_point = Math.abs((current_point-1)%results.length);
	acceptResults();
	markers[current_point].openPopup();
}

function nextResult() {
	current_point = Math.abs((current_point+1)%results.length);
	acceptResults();
    markers[current_point].openPopup();
    requestDetailInfo(results[current_point].coords.latitude, results[current_point].coords.longitude);
}

window.onload = setup;