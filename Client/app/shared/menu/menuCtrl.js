app.controller('menuCtrl', function($scope) {

    $scope.isConnected = false;
    $scope.activeTab = "Accueil"
    $scope.justConnected = false;
    $scope.justDisconnected = false;

    $scope.deconnexion = function(){
    	$scope.fermerConfirmationConnexion();
    	$scope.isConnected = false;
    	$scope.justDisconnected = true;
    }

    $scope.connexion = function(){
    	$scope.fermerConfirmationDeconnexion();
    	$scope.isConnected = true;
    	$scope.justConnected = true;
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

    $scope.fermerConfirmationConnexion = function(){
    	$scope.justConnected = false;
    };

    $scope.fermerConfirmationDeconnexion = function(){
    	$scope.justDisconnected = false;
    };

    $scope.fermerConfirmations = function(){
    	$scope.fermerConfirmationConnexion();
    	$scope.fermerConfirmationDeconnexion();
    };
});