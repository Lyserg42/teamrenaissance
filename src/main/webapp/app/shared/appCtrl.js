app.controller('appCtrl', function($scope, $http) {

    $scope.isConnected = false;

    $scope.refresh = function(){
        var data = {typeRequest:"getUser", uName:""};
        var dataJSON = JSON.stringify(data);
        /* app/components/profil/serveur/getUser.json */
        $http.post("/user", dataJSON).then(
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
    	
        $scope.data = {typeRequest:"deconnexion"};
        $scope.dataJSON = JSON.stringify($scope.data);

        $http.post("/user", $scope.dataJSON).then(
            function succes(response){
                $scope.isConnected = false;
            },
            function echec(response){
                if(response.status === -1){
                }
                else{
                }
            }
        );
    }
});