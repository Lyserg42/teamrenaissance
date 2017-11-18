app.controller('connexionCtrl', function($scope, $http, $route) {
	 
	$scope.erreurConnexion = false;
    $scope.succesConnexion = false;

	$scope.connexion = function () {
	 	$scope.fermerErreurConnexion();
        $scope.fermerSuccesConnexion();
	 	
        var data = {typeRequest:"connexion", login: $scope.login, password: $scope.password};
        var dataJSON = JSON.stringify(data);

        console.log(dataJSON);

      	$http.post("/teamrenaissance/user", dataJSON).then(
            function success(response){
                $scope.refresh();
                $scope.ouvrirSuccesConnexion();
                console.log("succes");
                console.log(response.status);
          		console.log(response.data);
            },
            function error(response){
                $scope.refresh();
                $scope.ouvrirErreurConnexion();
                if(response.status != -1){
                    $scope.raisonEchecConnexion = "Combinaison Login / Mot de passe incorrecte.";
                }
                else{
                    $scope.raisonEchecConnexion = "Impossible de contacter le serveur.";
                }
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

    $scope.fermerSuccesConnexion = function(){
        $scope.succesConnexion = false;
    }

    $scope.ouvrirSuccesConnexion = function(){
        $scope.succesConnexion = true;
    }
});