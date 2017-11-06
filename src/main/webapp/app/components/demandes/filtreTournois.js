/* Filtre permettant de savoir quels tournois afficher */
app.filter('filtreTournois', function() {
  return function(tournois, filtre) {
   ret = new Array();
   if(filtre === "Tous les tournois"){
      ret = tournois;
   }
   else{
      tournois.forEach(function(tournoi){
        if(tournoi.tName === filtre){
          ret.push(tournoi);
        }
      })
   }
   return ret;
  };
});