app.directive('passwordConstraintsDirective', function() {
  return {
    require: 'ngModel',
    link: function(scope, element, attr, mCtrl) {
      function myValidation(value) {
        /* Le mot de passe doit faire au moins 8 caractères et contenir au moins une majuscule et au moins un chiffre */
        if (value.length >= 8 && /[A-Z]/.test(value) && /\d/.test(value)) {
          mCtrl.$setValidity('charE', true);
        } else {
          mCtrl.$setValidity('charE', false);
        }
        return value;
      }
      mCtrl.$parsers.push(myValidation);
    }
  };
});