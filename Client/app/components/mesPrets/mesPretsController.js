app.controller('mesPretsController',function(){

});

$scope.cardList = [];

$scope.deleteCard = function(itemIndex){
  $scope.cardList.splice(itemIndex,1);
};
