app.controller('modifierProfilCtrl', function($scope, $http) {


	$http.get("app/components/profil/profil.json").then(function(response){

                /* On stocke les données récupérées*/
            	$scope.profil = response.data;

            	/* On copie l'url de l'avatar pour ne pas qu'elle soit modifiee à la volée */
            	$scope.avatar = $scope.profil.avatar;

	});


	$scope.envoiModifs = function(){

		$scope.dataJSON = JSON.stringify($scope.profil);
		console.log($scope.dataJSON);
	}
});