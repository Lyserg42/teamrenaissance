app.controller('menuCtrl', function($scope, $http) {

    $scope.isConnected = false;
    $scope.activeTab = "Accueil"

    $scope.Wrefresh = function(){

    }

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
        $http.post("app/components/profil/profil.json").then(
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

    $scope.isTabActive = function(tabName){
    	if(tabName === $scope.activeTab){
    		return true;
    	}
    	else {
    		return false;
    	}
    }

    $scope.activateTab = function(tabName){
    	$activeTab = tabName;
    };

});