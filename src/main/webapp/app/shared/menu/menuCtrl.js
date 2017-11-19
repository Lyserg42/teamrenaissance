app.controller('menuCtrl', function($scope, $http) {


    $scope.activeTab = "Accueil"

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