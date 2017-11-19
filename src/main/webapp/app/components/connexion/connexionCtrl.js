app.controller('connexionCtrl', function($scope, $http, $route) {
	 
	$scope.erreurConnexion = false;
    $scope.succesConnexion = false;

	$scope.connexion = function () {
	 	$scope.fermerErreurConnexion();
        $scope.fermerSuccesConnexion();
	 	
        var data = {typeRequest:"connexion", login: $scope.login, password: $scope.password};
        var dataJSON = JSON.stringify(data);

      	$http.post("/user", dataJSON).then(
            function success(response){
                $scope.refresh();
                $scope.ouvrirSuccesConnexion();
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