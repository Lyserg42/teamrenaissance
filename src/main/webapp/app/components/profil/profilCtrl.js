app.controller('profilCtrl', function($scope, $http) {

	$http.get("app/components/profil/profil.json").then(function(response){

                /* On stocke les données récupérées*/
            	$scope.profil = response.data;


	});

});