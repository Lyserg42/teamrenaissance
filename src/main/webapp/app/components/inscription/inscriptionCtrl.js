app.controller('inscriptionCtrl', function($scope, $http) {

	$scope.erreurInscription = false;
	$scope.succesInscription = false;

	$scope.inscription = function(){

		$scope.fermerErreurInscription();
		$scope.fermerSuccesInscription();

		var data = {typeRequest:"inscription",
					login:$scope.pseudo,
					name:$scope.nom,
					firstname:$scope.prenom,
					email:$scope.email,
					password:$scope.password};
        var dataJSON = JSON.stringify(data);

        console.log(dataJSON);

      	$http.post("/teamrenaisance/user", dataJSON).then(
      		function succes(response){
      			$scope.ouvrirSuccesInscription();
      		},
      		function echec(response){

      			if(response.data != null){
      				$scope.raisonEchecInscription = response.data.newuser;
      			}
      			else{
      				$scope.raisonEchecInscription = "Impossible de connecter le serveur.";
      			}
      			$scope.ouvrirErreurInscription();
      		}
      	);
	}

	$scope.reset = function(){
		$scope.pseudo = "";
		$scope.nom = "";
		$scope.prenom = "";
		$scope.email = "";
		$scope.password = "";
		$scope.checkpw = "";
	}

	$scope.ouvrirErreurInscription = function(){
		$scope.erreurInscription = true;
	}

	$scope.fermerErreurInscription = function(){
		$scope.erreurInscription = false;
	}

	$scope.ouvrirSuccesInscription = function(){
		$scope.succesInscription = true;
	}

	$scope.fermerSuccesInscription = function(){
		$scope.succesInscription = false;
	}
});