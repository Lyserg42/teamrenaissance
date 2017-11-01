app.controller('demandesCtrl', function($scope, $http) {

    $scope.imgDemandes = new Array();

    /* Booléen correspondant à l'affichage de la confirmation de prêt*/
    $scope.hideConfirmation = true;

    

    /* Nouvelle requete au serveur pour obtenir le fichier JSON et mise à jour des variables qui en dépendent*/
    $scope.refresh = function(){

            /* Tableau contenant les noms des tournois pour le selectTournoi */
            $scope.selectTournament = new Array();
            $scope.selectTournament[0] = "Tous les tournois";

            /* Variable bindée à la valeur du selectTournoi 
               Apres un nouveau pret, on fait un refresh et elle est reset donc l'utilisateur perd la selection de tournois faite.
               On peut la garder mais ca peut poser probleme dans le cas ou le nouveau pret remplit exactement toutes les demandes restantes pour un tournoi*/
            $scope.selectFiltreTournois = "Tous les tournois";

            $http.get("app/components/demandes/demandes.json").then(function(response){

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

            	$scope.tournaments.forEach(function(tournament, i){
            	
                    /* On initialise les cardPreview à la première carte de chaque liste*/
                	$scope.imgDemandes[i] = new Array();
                    tournament.demandes.forEach(function(demande, j){
                        $scope.imgDemandes[i][j] = demande.cards[0].img;
                    });

                    /* On rentre les noms des tournois dans le selectTournoi */
                    $scope.selectTournament[i+1]=tournament.tName;
            	});
            });
    };

    $scope.refresh();

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
            $scope.cartesNouveauPret = new Array();
            $scope.modal.cards.forEach(function(card,i){
                $scope.cartesNouveauPret[i] = {cName:card.cName, qty:card.qty};
            });
            
    };

    $scope.validerPret = function(){

        $scope.cartesNouveauPret.forEach(function(carte){
                console.log(carte);
        });

        /* TODO envoi des données au serveur*/

        $scope.refresh(); 
        $scope.afficheConfirmation();
    };

    $scope.afficheConfirmation = function(){
        $scope.hideConfirmation=false;
    }

    $scope.fermerConfirmation = function(){
        $scope.hideConfirmation=true;
    }




});