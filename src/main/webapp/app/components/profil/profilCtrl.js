app.controller('profilCtrl', function($scope, $http, $routeParams, $sce) {

    $scope.loading = true;
    $scope.isMyProfile = true;

	var data = {typeRequest:"getUser", uName:""};

	if (typeof $routeParams.uName !== 'undefined') {
    	data.uName = $routeParams.uName;
        $scope.isMyProfile = false;
	}

    var dataJSON = JSON.stringify(data);
    /* TOOD mettre /user en premier parametre et dataJSON en second parametre */
    /* app/components/profil/serveur/getUser.json */
	$http.post("/user",dataJSON).then(

        function succes(response){

            $scope.loading = false;
            $scope.chargementOk = true;

            /* On stocke les données récupérées*/
            $scope.profil = response.data;
            $scope.mapAdresse = $scope.profil.address+" "+$scope.profil.zipCode+" "+$scope.profil.city;
            $scope.mapAdresse.replace(/\s/, "+");
            $scope.mapAdresse = "https://www.google.com/maps/embed/v1/place?key=AIzaSyD91MpqapwxhA44W0VvxzNTqmfohKGDraI&q="+$scope.mapAdresse+"&zoom=11&center=48.860892, 2.340860";
            $scope.trustedMapUrl = $sce.trustAsResourceUrl($scope.mapAdresse);
        },
        function echec(response){
            if(response.status === -1){
            }
            else{
            }
            $scope.loading = false;
            $scope.chargementOk = false;
        }
                
    );

    $scope.copyDiscord = function(){
        var textArea = document.createElement("textarea");
        textArea.style.position = 'fixed';
        textArea.style.top = 0;
        textArea.style.left = 0;
        textArea.style.width = '2em';
        textArea.style.height = '2em';
        textArea.style.padding = 0;
        textArea.style.border = 'none';
        textArea.style.outline = 'none';
        textArea.style.boxShadow = 'none';
        textArea.style.background = 'transparent';
        textArea.value = $scope.profil.discord;

        document.body.appendChild(textArea);
        textArea.select();
        document.execCommand('copy');
        document.body.removeChild(textArea);
    }

});