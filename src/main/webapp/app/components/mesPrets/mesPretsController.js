app.controller('mesPretsController', function($scope, $http, $uibModal, $log, $document) {

    $scope.if = true;
    $scope.succesModifPret = false;
    $scope.erreurModifPret = false;

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
        $http.get("/loan?request=mesprets").then(function(response){

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


    $scope.modification = function(iParent, i, typeModif){

        $scope.fermerSuccesModifPret();
        $scope.fermerErreurModifPret();

        $scope.typeModif = typeModif;

        if($scope.typeModif==="emprunt"){
            $scope.modal = {
                uName:$scope.tournaments[iParent].borrowedCards[i].uName,
                uId:$scope.tournaments[iParent].borrowedCards[i].uId,
                tournament:$scope.tournaments[iParent].tName,
                tId:$scope.tournaments[iParent].tId,
                cards: $scope.tournaments[iParent].borrowedCards[i].cards
            };
        }
        else if($scope.typeModif==="pret"){
            $scope.modal = {
                uName:$scope.tournaments[iParent].lentCards[i].uName,
                uId:$scope.tournaments[iParent].lentCards[i].uId,
                tournament:$scope.tournaments[iParent].tName,
                tId:$scope.tournaments[iParent].tId,
                cards: $scope.tournaments[iParent].lentCards[i].cards
            };
        }
        else{
            $scope.modal = {
                tournament:$scope.tournaments[i].tName,
                tId:$scope.tournaments[i].tId,
                cards: $scope.tournaments[i].demands
            };
        }
        $scope.modifIds = new Array();
        $scope.modal.cards.forEach(function(card,i){
            $scope.modifIds[i] = {cId:card.cId, qty:card.qty};
        });
        
        $scope.open();
    };


    $scope.ouvrirSuccesModifPret = function(){
        $scope.succesModifPret=true;
    }

    $scope.fermerSuccesModifPret = function(){
        $scope.succesModifPret=false;
    }
    $scope.ouvrirErreurModifPret = function(){
        $scope.erreurModifPret=true;
    }

    $scope.fermerErreurModifPret = function(){
        $scope.erreurModifPret=false;
    }

    /* Gestion du modal */
    $scope.animationsEnabled = true;

    $scope.open = function (size) {

        var parentElem = angular.element($document[0].querySelector('.myModalParent'));

        var modalInstance = $uibModal.open({
            animation: $scope.animationsEnabled,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'myModalContent.html',
            controller: 'modalInstCtrlPrets',
            appendTo: parentElem,
            size: size,
            resolve: {
                modalValues: function () {
                    return $scope.modal;
                },
                cartesIds: function() {
                    return $scope.modifIds;
                },
                typeModif: function(){
                    return $scope.typeModif;
                }
            }
        });

        modalInstance.result.then(
        function succes(codeRetour) {
            $scope.refresh();
            if(codeRetour === 1){
                $scope.ouvrirSuccesModifPret();
            }
            else{
                $scope.ouvrirErreurModifPret();
                if(codeRetour === 0){
                    $scope.raisonErreur = "Erreur interne au serveur.";
                }
                else{
                    $scope.raisonErreur = "Impossible de contacter le serveur.";
                }
            }
        }, 
        function echec() {
            $log.info('Modal dismissed at: ' + new Date());
        });

    };

    $scope.openComponentModal = function () {
        var modalInstance = $uibModal.open({
            animation: $scope.animationsEnabled,
            component: 'modalComponent',
            resolve: {
                modalValues: function () {
                    return $scope.modal;
                },
                cartesIds: function() {
                    return $scope.modifIds;
                },
                typeModif: function(){
                    return $scope.typeModif;
                }

            }
        });

        modalInstance.result.then(function succes(codeRetour) {
            $scope.refresh();
            if(codeRetour === 1){
                $scope.ouvrirSuccesModifPret();
            }
            else{
                $scope.ouvrirErreurModifPret();
                if(codeRetour === 0){
                    $scope.raisonErreur = "Erreur interne au serveur.";
                }
                else{
                    $scope.raisonErreur = "Impossible de contacter le serveur.";
                }
            }
        }, 
        function echec() {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };


    $scope.toggleAnimation = function () {
        $scope.animationsEnabled = !$scope.animationsEnabled;
    };


});


app.controller('modalInstCtrlPrets', function ($scope, $http, $uibModalInstance, modalValues, cartesIds, typeModif) {

    $scope.modal = modalValues;
    $scope.modifIds = cartesIds;
    $scope.type = typeModif;

    $scope.ok = function () {
        var data = {tId:$scope.modal.tId, uId:$scope.modal.uId, type:$scope.type, cards:$scope.modifIds};
        var dataJSON = JSON.stringify(data);
        console.log(dataJSON);

        $http.put("/loan?request=modifier",dataJSON).then(
            function succes(response){
                $uibModalInstance.close(1);
            },
            function echec(response){
                if(response.status === -1){
                    $uibModalInstance.close(-1);
                }
                else{
                    $uibModalInstance.close(0);
                }
            }
        );


        
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});

