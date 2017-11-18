app.controller('appCtrl', function($scope, $http) {

    $scope.isConnected = false;

    $scope.connexionMagique = function(){
        $scope.isConnected = true;
    }

    $scope.refresh = function(){
        var data = {typeRequest:"getUser", Name:""};
        var dataJSON = JSON.stringify(data);
        console.log(dataJSON);
        /* app/components/profil/serveur/getUser.json */
        $http.post("/teamrenaissance/user", dataJSON).then(
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

        $http.post("/teamrenaissance/user", $scope.dataJSON).then(
            function succes(response){
                $scope.isConnected = false;
                console.log("deconnexion reussie");
            },
            function echec(response){
                if(response.status === -1){
                    console.log("deconnexion ratee : impossible de contacter le serveur");
                }
                else{
                    console.log("deconnexion ratee : erreur serveur");
                }
            }
        );
    }
});