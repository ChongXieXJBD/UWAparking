 var map

      function initMap() {
        map = new google.maps.Map(document.getElementById('map'), {
          zoom: 16,
          center: {lat: -31.980624, lng: 115.818479},
          mapTypeId: 'roadmap'
        });

      

      }

     


      // Heatmap data from db
      //Google Points API
      //Heatmap data: example Points
      //function getPoints() {
      //  return [
      //    new google.maps.LatLng(37.782551, -122.445368)]

      