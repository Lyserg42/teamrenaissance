app.controller('demandesCtrl', function($scope, $http, $uibModal, $log, $document) {
    $scope.loading = true;
    $scope.imgDemandes = new Array();

    /* Booléens correspondant à l'affichage de la confirmation ou de l'echec de prêt*/
    $scope.succesPret = false;
    $scope.erreurPret = false;

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

            /* app/components/demandes/demandes.json */
            $http.get("/loan?request=demandes").then(
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


    $scope.ouvrirSuccesPret = function(){
        $scope.succesPret=true;
    }

    $scope.fermerSuccesPret = function(){
        $scope.succesPret=false;
    }
    $scope.ouvrirErreurPret = function(){
        $scope.ErreurPret=true;
    }

    $scope.fermerErreurPret = function(){
        $scope.ErreurPret=false;
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

    modalInstance.result.then(
      function succes(codeRetour) {
        $scope.refresh(); 
        if(codeRetour === 1){
          $scope.ouvrirSuccesPret();
        }
        else if(codeRetour === 0){
          $scope.ouvrirErreurPret();
          $scope.raisonErreur="Erreur interne au serveur";
        }
        else if(codeRetour === -1){
          $scope.ouvrirErreurPret();
          $scope.raisonErreur="Impossible de contacter le serveur";
        }
        }, 
        function echec() {
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

    modalInstance.result.then(
      function succes() {
          $scope.refresh(); 
          $scope.afficheConfirmation();
        }, 
      function echec() {
        $log.info('modal-component dismissed at: ' + new Date());
        $scope.fermerConfirmation();
      });
    };


    $scope.toggleAnimation = function () {
        $scope.animationsEnabled = !$scope.animationsEnabled;
    };




    });

    app.controller('modalInstCtrlDemandes', function ($scope, $http, $uibModalInstance, modalValues, cartesIds) {

        $scope.modal = modalValues;
        $scope.cartesNouveauPret = cartesIds;

    $scope.ok = function () {
        var data = {tId:$scope.modal.tId, uId:$scope.modal.uId, cards:$scope.cartesNouveauPret};
        var dataJSON = JSON.stringify(data);

        $http.put("/loan?request=preter", dataJSON).then(
            function succes(response){
              $uibModalInstance.close(1);
            },
            function echec(response){
              if(response.status == -1){
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