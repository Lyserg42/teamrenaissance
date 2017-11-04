app.controller('mesPretsController', function($scope, $http) {
    $scope.refresh = function(){

        /* Tableau contenant les noms des tournois pour le selectTournoi */
        $scope.selectTournament = new Array();
        $scope.selectTournament[0] = "Tous les tournois";

        /* Variable bindée à la valeur du selectTournoi
           Apres un nouveau pret, on fait un refresh et elle est reset donc l'utilisateur perd la selection de tournois faite.
           On peut la garder mais ca peut poser probleme dans le cas ou le nouveau pret remplit exactement toutes les demandes restantes pour un tournoi*/
        $scope.selectFiltreTournois = "Tous les tournois";

        $http.get("app/components/mesPrets/mesPrets.json").then(function(response){

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

    $scope.arraySize = $scope.tournaments.length;


    /*annuler un prêt/emprunts*/

    /*clic sur le profil de preteur/emprunteur*/

    /*clic prêt effectué*/

    /*clic rendu effectué*/

    /*missing cards*/

















});