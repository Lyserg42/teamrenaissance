app.controller('emprunterCtrl', function($scope, $http, $uibModal, $log, $document) {

    $scope.refresh = function(){
        $scope.loading = true;
        $scope.listeNonValide = false;

        /* app/components/emprunter/tournaments.json */
        $http.get("/tournaments").then(
            function succes(response){
                $scope.loading = false;
                $scope.chargementOk = true;
                $scope.tournaments = response.data.tournaments;
            },
            function echec(response){
                $scope.loading = false;
                $scope.chargementOk = false;
            }
        );

        $scope.hideCodeRetour = true;
        $scope.qtyAjoutCarte = 1;
        $scope.cardList = "";

        $scope.ajouterCarte = function(){
            if(!$scope.nameAjoutCarte == ""){
                if(!$scope.cardList[$scope.cardList.length-1] == "\n" && !$scope.cardList == ""){
                    $scope.cardList += "\n";
                }
                $scope.cardList += $scope.qtyAjoutCarte +" "+ $scope.nameAjoutCarte;
                $scope.qtyAjoutCarte = 1;
                $scope.nameAjoutCarte = "";
            }
        };

    };

    $scope.refresh();

    $scope.suggestCardName = function(){
        console.log("hello");
    };

    $scope.validerFormulaire = function(){
        
        var isValid = true;

        if (typeof $scope.selectedTournament == 'undefined') {
            console.log("Tournoi non selectionne");
            $scope.tournoiNonSelectionne = true;
            isValid = false;
        }
        else{
            $scope.tournoiNonSelectionne = false;
        }

        return isValid;
    };

    $scope.envoyerDemande = function(){

        if($scope.validerFormulaire()){
            var cardListStrings = $scope.cardList.split("\n");
            var cardListObjects = new Array();

            cardListStrings.forEach(function(substring){
                /* Si la ligne commence par une quantité, on la retire de la string et on met la valeur dans une variable*/
                if(/^[0-9]+ /.test(substring)){
                    qty = /^[0-9]+/.exec(substring);
                    substring = substring.replace(/^[0-9]+ /,"");
                }
                else{
                    qty = 1;
                }
                /* Si la ligne n'est pas vide ou pas une suite d'espace, on instancie un objet carte et on le push dans le tableau */
                if(!/^[ ]*$/.test(substring)){
                    cardListObjects.push({  "qty":parseInt(qty),
                                            "cName":substring});
                }


            });

            var data = {tId:$scope.selectedTournament.tId, cards:cardListObjects};
            var dataJSON = JSON.stringify(data);

            console.log(data);
            $http.post("/loan", dataJSON).then(
                function succes(response){
                    $scope.open();
                },
                function echec(response){
                    $scope.listeNonValide = true;
                    $scope.erreurServeur = response.data;
                }
            );
        }
    };

    $scope.fermerConfirmation = function(){
        $scope.hideCodeRetour = true;
    };



/* Gestion du modal*/

    $scope.animationsEnabled = true;

    $scope.open = function (size) {

        var parentElem = angular.element($document[0].querySelector('.myModalParent'));

        var modalInstance = $uibModal.open({
          animation: $scope.animationsEnabled,
          ariaLabelledBy: 'modal-title',
          ariaDescribedBy: 'modal-body',
          templateUrl: 'myModalContent.html',
          controller: 'modalInstCtrlEmprunter',
          appendTo: parentElem,
          size: size,
          resolve: {
            tournoi: function () {
              return $scope.selectedTournament;
            },
            cartes: function() {
                return $scope.cardList;
            }
          }
    });

    modalInstance.result.then(function (selectedItem) {
          $scope.refresh(); 
          $scope.afficheConfirmation();
        }, function () {
          $log.info('Modal dismissed at: ' + new Date());
          $scope.fermerConfirmation();
        });
    };

    $scope.openComponentModal = function () {
        var modalInstance = $uibModal.open({
          animation: $scope.animationsEnabled,
          component: 'modalComponent',
          resolve: {
            tournoi: function () {
              return $scope.selectedTournament;
            },
            cartes: function() {
                return $scope.cardList;
            }

          }
    });

    modalInstance.result.then(function () {
          $scope.refresh(); 
          $scope.afficheConfirmation();
        }, function () {
          $log.info('modal-component dismissed at: ' + new Date());
          $scope.fermerConfirmation();
        });
    };


    $scope.toggleAnimation = function () {
        $scope.animationsEnabled = !$scope.animationsEnabled;
    };


});

// Please note that $uibModalInstance represents a modal window (instance) dependency.
// It is not the same as the $uibModal service used above.

app.controller('modalInstCtrlEmprunter', function ($scope, $http, $uibModalInstance, tournoi, cartes) {

    $scope.selectedTournament = tournoi;
    $scope.cardList = cartes;

    $scope.ok = function () {

         $uibModalInstance.close();
        
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});