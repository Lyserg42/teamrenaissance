var app = angular.module("myApp", ["ngRoute"]);
app.config(function($routeProvider){
	$routeProvider
		.when("/accueil", {
			templateUrl : "app/components/demandes/demandes.html",
			controller : "demandesCtrl"
		})
		.when("/emprunter", {
			templateUrl : "app/components/emprunter/emprunter.html",
			controller : "emprunterCtrl"
		})
		.when("/mesprets", {
			templateUrl : "app/components/mesPrets/mesPrets.html",
			controller : "mesPretsController"
		})
		.when("/profil", {
			templateUrl : "app/components/profil/profil.html",
			controller : "profilCtrl"
		})
		.when("/modifierprofil", {
			templateUrl : "app/components/modifierProfil/modifierProfil.html",
			controller : "modifierProfilCtrl"
		})
		.when("/connexion", {
			templateUrl : "app/components/connexion/connexion.html",
			controller : "connexionCtrl"
		})
		.when("/inscription", {
			templateUrl : "app/components/inscription/inscription.html",
			controller : "inscriptionCtrl"
		})
		.otherwise({
			templateUrl : "app/components/demandes/demandes.html",
			controller : "demandesCtrl"
		});
});