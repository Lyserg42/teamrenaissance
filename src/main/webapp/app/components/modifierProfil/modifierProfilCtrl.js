app.controller('modifierProfilCtrl', function($scope, $http, $uibModal, $log, $document) {

  $scope.succesModification = false;
  $scope.erreurModification = false;

  $scope.refresh = function(){

    $scope.loading = true;
    
    var data = {typeRequest:"getUser", uName:""};
    var dataJSON = JSON.stringify(data);
    
    /* app/components/profil/serveur/getUser.json */
  	$http.post("/teamrenaissance/user", dataJSON).then(
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
			   if(codeRetour === 1){
          $scope.ouvrirSuccesModification();
          $scope.refresh();
         }
         else if(codeRetour === -1){
          $scope.ouvrirErreurModification();
          $scope.refresh();
         }
         else{
          console.log(codeRetour);
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
			 console.log("Succes");
      }, 
      function () {
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

app.controller('modalInstCtrlProfil', function ($scope, $http, $uibModalInstance, profilValues) {

	    $scope.profil = profilValues;

	$scope.ok = function () {

    $scope.erreurMDP = false;

		$scope.data = {	typeRequest:"",
						name:"",
						firstname:"",
						email:"",
						address:"",
						zipCode:"",
						city:"",
						avatar:"",
						phoneNumber:"",
						dciNumber:"",
						facebook:"",
						twitter:"",
						password:"",
						newPassword:""};

    $scope.data.typeRequest = "setUserProfil";
    $scope.data.name = $scope.profil.lastName;
    $scope.data.firstname = $scope.profil.firstName;
    $scope.data.email = $scope.profil.email;
    $scope.data.address = $scope.profil.adresse;
    $scope.data.zipCode = $scope.profil.codePostal;
    $scope.data.city = $scope.profil.ville;
    $scope.data.avatar = $scope.profil.avatar;
    $scope.data.phoneNumber = $scope.profil.telephone;
    $scope.data.dciNumber = $scope.profil.DCI;
    $scope.data.facebook = $scope.profil.facebook;
    $scope.data.twitter = $scope.profil.twitter;
    $scope.data.password = $scope.password;
    $scope.data.newPassword = $scope.profil.newPassword;



		$scope.dataJSON = JSON.stringify($scope.data);

		console.log($scope.dataJSON);
    

	  $http.post("/user", $scope.dataJSON).then(
        function succes(response){
          $uibModalInstance.close(1);
        },
        function echec(response){
          if(response.status == -1){
            $uibModalInstance.close(-1);
          }
          else{
            $scope.erreurMDP = true;
          }
       }
     ); 
	    
	};

	$scope.cancel = function () {
	    $uibModalInstance.dismiss('cancel');
	};
});