/* Filtre permettant de faire des ng-repeat sur un intervalle

	<div ng-repeat="n in [] | range:10">
	    do something number {{$index}}
	</div>  
	
*/
app.filter('range', function() {
  return function(input, total) {
    total = parseInt(total);

    for (var i=0; i<total; i++) {
      input.push(i);
    }

    return input;
  };
});