var app = angular.module("myApp", ["ngRoute"]);
app.config(function($routeProvider){
	$routeProvider
		.when("/accueil", {
			templateUrl : "app/components/demandes/demandes.html",
			controller : "demandesCtrl"
		})
		.when("/emprunter", {
			templateUrl : "app/components/emprunter/emprunter.html"
		})
		.when("/mesprets", {
			templateUrl : "app/components/mesPrets/mesPrets.html",
			controller : "mesPretsController"
		})
		.when("/profil", {
			templateUrl : "app/components/profil/profil.html"
		})
		.otherwise({
			templateUrl : "app/components/demandes/demandes.html",
			controller : "demandesCtrl"
		});
});