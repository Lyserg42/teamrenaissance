app.controller('profilCtrl', function($scope, $http, $routeParams) {

    $scope.loading = true;

	var data = {typeRequest:"getUser",
				uName:""};

	if (typeof $routeParams.uName !== 'undefined') {
    	data.uName = $routeParams.uName;
	}

    var dataJSON = JSON.stringify(data);

    console.log(data);

    /* TOOD mettre /user en premier parametre et dataJSON en second parametre */
    /* app/components/profil/serveur/getUser.json */
	$http.post("/teamrenaissance/user",data).then(
        function succes(response){

            console.log("Succes communication serveur");
            $scope.loading = false;
            $scope.chargementOk = true;

            /* On stocke les données récupérées*/
            $scope.profil = response.data;
            /*$scope.map = "https://www.google.com/maps/embed/v1/place?key=AIzaSyD91MpqapwxhA44W0VvxzNTqmfohKGDraI&amp;q="+$scope.profil.adresse+"+"+$scope.profil.ville+" allowfullscreen>";*/
            /*console.log($scope.map);*/
        },
        function echec(response){
            if(response.status === -1){
                console.log("Impossible de contacter le serveur");
            }
            else{
                console.log("Erreur Serveur");
            }
            $scope.loading = false;
            $scope.chargementOk = false;
        }
                
    );

});