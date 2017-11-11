app.controller('emprunterCtrl', function($scope, $http) {

    $http.get("/tournament").then(function(response){
        $scope.tournaments = response.data.tournaments;
    });

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

            /* TODO  Envoyer la string au serveur */
            console.log(data);
            $http.post("/loan", dataJSON).then(function(response){
                $scope.retourServeur = response.headers();
                $scope.hideCodeRetour = false;
            });
        }
    };

    $scope.fermerConfirmation = function(){
        $scope.hideCodeRetour = true;
    };

});