app.controller('connexionCtrl', function($scope, $http, $route) {
	 
	$scope.erreurConnexion = false;

	 $scope.connexion = function () {
	 	$scope.erreurConnexion = false;
	 	
        var data = {typeRequest:"connexion", login: $scope.login, password: $scope.password};
        var dataJSON = JSON.stringify(data);

        console.log(dataJSON);

      	$http.post("/user", dataJSON).then(
            function success(response){
                console.log("succes");
                console.log(response.status);
          		console.log(response.data);
            },
            function error(response){
                console.log("erreur");
                console.log(response.status);
                console.log(response.data);
            }
        );
        
    };

    $scope.fermerErreurConnexion = function(){
    	$scope.erreurConnexion = false;
    }

    $scope.ouvrirErreurConnexion = function(){
    	$scope.erreurConnexion = true;
    }

});