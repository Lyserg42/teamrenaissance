app.controller('demandesCtrl', function($scope, $http) {

    $http.get("demandes.json").then(function(response){
    	$scope.demandes = response.data.demandes;

    	$scope.imgDemandes = new Array();
    	$scope.demandes.forEach(function(demande, index){
    		$scope.imgDemandes[index] = demande.cards[0].img;
    	});


    });

    $scope.updateImg = function(indexParent, index){
    		$scope.imgDemandes[indexParent] = $scope.demandes[indexParent].cards[index].img;
    };
});