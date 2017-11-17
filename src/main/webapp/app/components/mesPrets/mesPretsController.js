app.controller('mesPretsController', function($scope, $http, $uibModal, $log, $document) {

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

    $scope.validerLent = function(){

        var data = {uId:$scope.modal.uId,tId:$scope.modal.tId, cards:$scope.modifPret};
        var dataJSON = JSON.stringify(data);

        console.log(dataJSON);

        $http.post("/pret", dataJSON).then(function(){
            $scope.refresh();
            $scope.afficheConfirmation();
        });
    };


    $scope.modification = function(iParent, i, typeModif){
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
        else{
            $scope.modal = {
                uName:$scope.tournaments[iParent].lentCards[i].uName,
                uId:$scope.tournaments[iParent].lentCards[i].uId,
                tournament:$scope.tournaments[iParent].tName,
                tId:$scope.tournaments[iParent].tId,
                cards: $scope.tournaments[iParent].lentCards[i].cards
            };
            
        }
        $scope.modifIds = new Array();
        $scope.modal.cards.forEach(function(card,i){
            $scope.modifIds[i] = {cId:card.cId, qty:card.qty};
        });
        
        $scope.open();
    };

    $scope.validerBorrow = function(){

        var data = {uId:$scope.modal.uId,tId:$scope.modal.tId, cards:$scope.modifEmprunts};
        var dataJSON = JSON.stringify(data);

        console.log(dataJSON);

        $http.post("/emprunt", dataJSON).then(function(){
            $scope.refresh();
            $scope.afficheConfirmation();
        });
    };



    /*annuler un prêt/emprunts*/

    /*clic sur le profil de preteur/emprunteur*/

    /*clic prêt effectué*/

    /*clic rendu effectué*/

    /*missing cards*/



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

        modalInstance.result.then(function (selectedItem) {
            $scope.refresh();
        }, function () {
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

        modalInstance.result.then(function () {
            $scope.refresh();
        }, 
        function () {
            $log.info('modal-component dismissed at: ' + new Date());
        });
    };


    $scope.toggleAnimation = function () {
        $scope.animationsEnabled = !$scope.animationsEnabled;
    };


});


app.controller('modalInstCtrlPrets', function ($scope, $http, $uibModalInstance, modalValues, cartesIds, typeModif) {

    $scope.modal = modalValues;
    $scope.modifIds = cartesIds;

    $scope.ok = function () {
        var data = {tId:$scope.modal.tId, uId:$scope.modal.uId, type:$scope.typeModif,  cards:$scope.modifIds};
        var dataJSON = JSON.stringify(data);
        console.log(dataJSON);
        $uibModalInstance.close();

    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});

