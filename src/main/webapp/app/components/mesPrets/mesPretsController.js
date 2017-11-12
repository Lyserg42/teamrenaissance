app.controller('mesPretsController', function($scope, $http) {
    $scope.refresh = function(){

        /* Tableau contenant les noms des tournois pour le selectTournoi */
        $scope.selectTournament = new Array();
        $scope.selectTournament[0] = "Tous les tournois";

        /*****************************Types Borrow/Lent/Demands*****************/
        $scope.selectEmpruntsPretsDemandes = new Array();
        $scope.selectEmpruntsPretsDemandes [0] = "Tous";

        $scope.selectFiltreEmpruntsPretsDemandes = "Tous";


        $scope.selectEmpruntsPretsDemandes[1]="lentCards";
        $scope.selectEmpruntsPretsDemandes[2]="borrowedCards";
        $scope.selectEmpruntsPretsDemandes[3]="demands";

        /****************************************************************************/

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
                /* On rentre les noms des tournois dans le selectTournoi */
                $scope.selectTournament[i+1]=tournament.tName;
            });



        });
    };

    $scope.refresh();

    $scope.borowSubmit = function(idT,idC,numC,uId){
        /*si la quantité est différente que celle de départ présent dans le tableau du JSON,
        envoyer une requete POST avec la nouvelle qty au serveur,
        et récuperer un nouveau JSON avec la MAJ*/
        $http.post();
    };

    $scope.lentSubmit = function(idT,idC,numC,uId){
        $http.post();
    };




    $scope.orgBorrow = function(iParent, i){


    };


    $scope.orgLent= function(iParent, i){


    };



    /*annuler un prêt/emprunts*/

    /*clic sur le profil de preteur/emprunteur*/

    /*clic prêt effectué*/

    /*clic rendu effectué*/

    /*missing cards*/

















});