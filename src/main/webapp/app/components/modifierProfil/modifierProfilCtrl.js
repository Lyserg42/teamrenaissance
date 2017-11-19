app.controller('modifierProfilCtrl', function($scope, $http, $uibModal, $log, $document) {

  $scope.succesModification = false;
  $scope.erreurModification = false;

  $scope.refresh = function(){

    $scope.loading = true;
    
    var data = {typeRequest:"getUser", uName:""};
    var dataJSON = JSON.stringify(data);
    /* app/components/profil/serveur/getUser.json */
  	$http.post("/user", dataJSON).then(

      function succes(response){

        $scope.loading = false;
        $scope.chargementOk = true;

        /* On stocke les données récupérées*/
        $scope.profil = response.data;

        /* On copie l'url de l'avatar pour ne pas qu'elle soit modifiee à la volée */
        $scope.avatar = $scope.profil.avatar;
      },
      function echec(response){
        $scope.loading = false;
        $scope.chargementOk = false;
      }
    );
  }

  $scope.refresh();


  $scope.modifierProfil = function (){
    $scope.open();
  }

  $scope.fermerErreurModification = function(){
    $scope.erreurModification = false;
  }

  $scope.ouvrirErreurModification = function(){
    $scope.erreurModification = true;
  }

  $scope.fermerSuccesModification = function(){
      $scope.succesModification = false;
  }

  $scope.ouvrirSuccesModification = function(){
      $scope.succesModification = true;
  }


 /* Gestion du modal*/

    $scope.animationsEnabled = true;

    $scope.open = function () {

        var parentElem = angular.element($document[0].querySelector('.myModalParent'));

        var modalInstance = $uibModal.open({
          animation: $scope.animationsEnabled,
          ariaLabelledBy: 'modal-title',
          ariaDescribedBy: 'modal-body',
          templateUrl: 'myModalContent.html',
          controller: 'modalInstCtrlProfil',
          appendTo: parentElem,
          resolve: {
            profilValues: function () {
              return $scope.profil;
            }
          }
    });

    modalInstance.result.then(
      function ok(codeRetour) {
			   if(codeRetour === "Succès."){
          $scope.ouvrirSuccesModification();
          $scope.refresh();
         }
         else {
          $scope.raisonErreur = codeRetour;
          $scope.ouvrirErreurModification();
          $scope.refresh();
         }

      }, 
      function cancel() {
          $log.info('Modal dismissed at: ' + new Date());
      });
    };

    $scope.openComponentModal = function () {
        var modalInstance = $uibModal.open({
          animation: $scope.animationsEnabled,
          component: 'modalComponent',
          resolve: {
            profilValues: function () {
              return $scope.profil;
            }

          }
    });

    modalInstance.result.then(
      function () {
      }, 
      function () {
        $log.info('modal-component dismissed at: ' + new Date());
      });
    };


    $scope.toggleAnimation = function () {
        $scope.animationsEnabled = !$scope.animationsEnabled;
    };
});

// Please note that $uibModalInstance represents a modal window (instance) dependency.
// It is not the same as the $uibModal service used above.

app.controller('modalInstCtrlProfil', function ($scope, $http, $uibModalInstance, profilValues) {

	  $scope.profil = profilValues;

	  $scope.ok = function () {

    $scope.erreurMDP = false;

		$scope.data = {  typeRequest:"setUserProfil",
            name:$scope.profil.lastName,
            firstName:$scope.profil.firstName,
            email:$scope.profil.email,
            address:$scope.profil.address,
            zipCode:$scope.profil.zipCode,
            city:$scope.profil.city,
            avatar:$scope.profil.avatar,
            phoneNumber:$scope.profil.phone,
            dciNumber:$scope.profil.DCI,
            facebook:$scope.profil.facebook,
            twitter:$scope.profil.twitter,
            password:$scope.password,
            newPassword:$scope.profil.newPassword};



		$scope.dataJSON = JSON.stringify($scope.data);
  
	  $http.post("/user", $scope.dataJSON).then(
        function succes(response){
          $uibModalInstance.close("Succès.");
        },
        function echec(response){
          if(response.status === -1){
            $uibModalInstance.close("Impossible de contacter le serveur");
          }
          else if (response.data.setUserProfil === "Password error"){
            $scope.erreurMDP = true;
          }
          else if (response.data.setUserProfil === "Mail already exist"){
            $uibModalInstance.close("Cet e-mail est déjà utilisé.");
          }
          else {
            $uibModalInstance.close("Erreur interne au serveur.");
          }
       }
     ); 
	    
	};

	$scope.cancel = function () {
	    $uibModalInstance.dismiss('cancel');
	};
});