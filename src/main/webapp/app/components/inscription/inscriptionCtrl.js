app.controller('inscriptionCtrl', function($scope, $http) {

	$scope.inscription = function(){
		var data = {typeRequest:"inscription",
					login:$scope.pseudo,
					name:$scope.nom,
					firstname:$scope.prenom,
					email:$scope.email,
					password:$scope.password};
        var dataJSON = JSON.stringify(data);

        console.log(dataJSON);

      	$http.post("/user", dataJSON).then(function(){
      		$route.reload();
        });
	}

	$scope.reset = function(){
		$scope.pseudo = "";
		$scope.nom = "";
		$scope.prenom = "";
		$scope.email = "";
		$scope.password = "";
		$scope.checkpw = "";
	}
});