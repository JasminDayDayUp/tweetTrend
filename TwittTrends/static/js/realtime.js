/**
 * Created by Jingmei on 16/11/26.
 */
var circles=[];
var markers = [];
var map;
var socket;
var latitude=0;
var longitude=0;
window.onload = initWs;

function initWs(){
    socket = io('http://160.39.192.247:5000/gmapnew');
    socket.emit('hello', {data: 'hello server'});

    socket.on('message', function(data){
        res=JSON.parse(data);
        console.log(res);
        var att='';
        if( res['sentiment']=='positive'){
            att='http://maps.google.com/mapfiles/ms/icons/red-dot.png'
        }else if(res['sentiment']=='negative') {
            att = 'http://maps.google.com/mapfiles/ms/icons/green-dot.png'
        }else{
            att = 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png'
        }
        var latt=parseFloat(res['geo'].split(",")[0])
        var long=parseFloat(res['geo'].split(",")[1])
        console.log(latt,long)
         var marker = new google.maps.Marker({
            position: {lat: latt, lng: long},
                icon:att,
            map: map
            })
            markers.push(marker)
            var infowindow = new google.maps.InfoWindow( {maxWidth: 200})
            var content=res['user'].bold()+':</br>'+res['text']
            google.maps.event.addListener(marker, 'click', (function (marker, content, infowindow) {
                return function () {
                    infowindow.setContent(content);
                    infowindow.open(map, marker);
                };
            })(marker, content, infowindow));
        });
}

function initMap() {
   map = new google.maps.Map(document.getElementById('map'), {
             zoom: 5,
             center: {lat: 40.730610, lng: -73.935242}
        });
}

function clearMarkers() {
  for (var i = 0; i < markers.length; i++) {
    markers[i].setMap(null);
  }
  markers = [];
}

function clearCircles() {
  for (var i = 0; i < circles.length; i++) {
    circles[i].setMap(null);
  }
  circles = [];
}
