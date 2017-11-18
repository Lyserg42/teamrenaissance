app.controller('appCtrl', function($scope, $http) {

	    $scope.isConnected = false;

        $scope.connexionMagique = function(){
	        $scope.isConnected = true;
	    }

	    $scope.refresh = function(){
	        $http.get("app/components/profil/profil.json").then(
	        function succes(response){
	            $scope.isConnected = true;
	            $scope.avatar = response.data.avatar;
	            $scope.pseudo = response.data.uName;
	        },
	        function echec(response){
	            $scope.isConnected = false;
	        }
	        );
	    }

	    $scope.refresh();

	    $scope.deconnexion = function(){
	    	$scope.isConnected = false;
	        
	        $scope.data = {typeRequest:"deconnexion"};
	        $scope.dataJSON = JSON.stringify($scope.data);

	        $http.post("/teamrenaissance/user", $scope.dataJSON).then(
	            function succes(response){

	            },
	            function echec(response){
	                if(response.status == -1){

	                }
	                else{
	                    
	                }
	            }
	        );
	    }
});