app.controller('demandesCtrl', function($scope, $http) {

    $scope.imgDemandes = new Array();

    /* Booléen correspondant à l'affichage de la confirmation de prêt*/
    $scope.hideConfirmation = true;

    $scope.selectTournament = new Array();
    //$scope.selectTournament[0] = "Tous les tournois";

    $scope.selectFiltreTournois = "Tous les tournois";

    $http.get("demandes.json").then(function(response){

        /* On stocke les données récupérées*/
    	$scope.tournaments = response.data.tournaments;

        /* On s'assure que les tournois sont en ordre chronologique */
        $scope.tournaments.sort(function(a,b){
            if(a.date < b.date){
                return -1;
            }
            else {
                return 1;
            }
        });

        /* On initialise les cardPreview à la première carte de chaque liste*/
    	$scope.tournaments.forEach(function(tournament, i){
    		$scope.imgDemandes[i] = new Array();
            tournament.demandes.forEach(function(demande, j){
                $scope.imgDemandes[i][j] = demande.cards[0].img;
            });

            $scope.selectTournament[i] = tournament.tName;
    	});
    });

    /* On met à jour la cardPreview correspondante au hover sur une carte */
    $scope.updateImg = function(iGParent, iParent, img){
    		$scope.imgDemandes[iGParent][iParent] = img;
    };

    /* On met à jour les variables utilisées par le modal d'organisation de prêt */
    $scope.orgPret = function(iParent, i){
            $scope.modal = {
                uName:$scope.tournaments[iParent].demandes[i].uName,
                tournament:$scope.tournaments[iParent].tName,
                cards: $scope.tournaments[iParent].demandes[i].cards
            };
    };

    $scope.afficheConfirmation = function(){
        $scope.hideConfirmation=false;
    }

    $scope.fermerConfirmation = function(){
        $scope.hideConfirmation=true;
    }




});