app.controller('demandesCtrl', function($scope, $http, $uibModal, $log, $document) {
    $scope.loading = true;
    $scope.imgDemandes = new Array();

    /* Booléen correspondant à l'affichage de la confirmation de prêt*/
    $scope.hideConfirmation = true;

    /* Nouvelle requete au serveur pour obtenir le fichier JSON et mise à jour des variables qui en dépendent*/
    $scope.refresh = function(){

            $scope.loading = true;

            /* Tableau contenant les noms des tournois pour le selectTournoi */
            $scope.selectTournament = new Array();
            $scope.selectTournament[0] = "Tous les tournois";

            /* Variable bindée à la valeur du selectTournoi 
               Apres un nouveau pret, on fait un refresh et elle est reset donc l'utilisateur perd la selection de tournois faite.
               On peut la garder mais ca peut poser probleme dans le cas ou le nouveau pret remplit exactement toutes les demandes restantes pour un tournoi*/
            $scope.selectFiltreTournois = "Tous les tournois";

            $http.get("app/components/demandes/demandes.json").then(
              function succes(response){

                $scope.loading = false;
                $scope.chargementOk = true;

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
              },
              function echec(response){
                $scope.loading = false;
                $scope.chargementOk = false;
              }
            );
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
                uId:$scope.tournaments[iParent].demandes[i].uId,
                tournament:$scope.tournaments[iParent].tName,
                tId:$scope.tournaments[iParent].tId,
                cards: $scope.tournaments[iParent].demandes[i].cards
            };
            $scope.cartesNouveauPret = new Array();
            $scope.modal.cards.forEach(function(card,i){
                $scope.cartesNouveauPret[i] = {cId:card.cId, qty:card.qty};
            });
            $scope.open();
    };


    $scope.afficheConfirmation = function(){
        $scope.hideConfirmation=false;
    }

    $scope.fermerConfirmation = function(){
        $scope.hideConfirmation=true;
    }



    /* Gestion du modal*/

    $scope.animationsEnabled = true;

    $scope.open = function (size) {

        var parentElem = angular.element($document[0].querySelector('.myModalParent'));

        var modalInstance = $uibModal.open({
          animation: $scope.animationsEnabled,
          ariaLabelledBy: 'modal-title',
          ariaDescribedBy: 'modal-body',
          templateUrl: 'myModalContent.html',
          controller: 'modalInstCtrlDemandes',
          appendTo: parentElem,
          size: size,
          resolve: {
            modalValues: function () {
              return $scope.modal;
            },
            cartesIds: function() {
                return $scope.cartesNouveauPret;
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
            modalValues: function () {
              return $scope.modal;
            },
            cartesIds: function() {
                return $scope.cartesNouveauPret;
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

    app.controller('modalInstCtrlDemandes', function ($scope, $http, $uibModalInstance, modalValues, cartesIds) {

        $scope.modal = modalValues;
        $scope.cartesNouveauPret = cartesIds;

    $scope.ok = function () {
        var data = {tId:$scope.modal.tId, uId:$scope.modal.uId, cards:$scope.cartesNouveauPret};
        var dataJSON = JSON.stringify(data);

        console.log(dataJSON);

      /*  $http.post("/demande", dataJSON).then(function(){
            $uibModalInstance.close();
        }); */
         $uibModalInstance.close();
        
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
        };
    });