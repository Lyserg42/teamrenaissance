app.controller('profilCtrl', function($scope, $http, $routeParams) {

	/* TODO Remplacer "Lyserg" par le nom de l'utilisateur connecte actuellement */
	var data = {typeRequest:"getUser",
				login:"Lyserg"};

	if (typeof $routeParams.uName !== 'undefined') {
    	data.login = $routeParams.uName;
	}

    var dataJSON = JSON.stringify(data);

    console.log(data);

    /* TOOD mettre /user en premier parametre et dataJSON en second parametre */
	$http.get("app/components/profil/profil.json").then(function(response){

                /* On stocke les données récupérées*/
            	$scope.profil = response.data;
            	/*$scope.map = "https://www.google.com/maps/embed/v1/place?key=AIzaSyD91MpqapwxhA44W0VvxzNTqmfohKGDraI&amp;q="+$scope.profil.adresse+"+"+$scope.profil.ville+" allowfullscreen>";*/
            	/*console.log($scope.map);*/

	});

});