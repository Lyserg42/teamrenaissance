app.controller('profilCtrl', function($scope, $http, $routeParams) {

	if (typeof $routeParams.uName !== 'undefined') {
    	$scope.param = "?uName="+$routeParams.uName;
	}

	

	$http.get("app/components/profil/profil.json").then(function(response){

                /* On stocke les données récupérées*/
            	$scope.profil = response.data;
            	/*$scope.map = "https://www.google.com/maps/embed/v1/place?key=AIzaSyD91MpqapwxhA44W0VvxzNTqmfohKGDraI&amp;q="+$scope.profil.adresse+"+"+$scope.profil.ville+" allowfullscreen>";*/
            	/*console.log($scope.map);*/

	});

});