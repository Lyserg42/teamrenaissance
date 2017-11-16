app.controller('connexionCtrl', function($scope, $http, $route) {
	 $scope.connexion = function () {
        var data = {typeRequest:"connexion", login: $scope.login, password: $scope.password};
        var dataJSON = JSON.stringify(data);

        console.log(dataJSON);

      	$http.post("/user", dataJSON).then(function(response){
      		console.log(response);
        });
        
    };
});