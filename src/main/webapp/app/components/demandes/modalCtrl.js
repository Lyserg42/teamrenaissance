/* app.controller('modalCtrl', function ($scope, $uibModal, $log, $document) {
  //var $scope = this;
  $scope.items = ['item1', 'item2', 'item3'];

  $scope.animationsEnabled = true;

  $scope.open = function (size) {

    var parentElem = angular.element($document[0].querySelector('.myModalParent'));

    var modalInstance = $uibModal.open({
      animation: $scope.animationsEnabled,
      ariaLabelledBy: 'modal-title',
      ariaDescribedBy: 'modal-body',
      templateUrl: 'myModalContent.html',
      controller: 'modalInstCtrl',
      appendTo: parentElem,
      size: size,
      resolve: {
        items: function () {
          return $scope.items;
        }
      }
    });

    modalInstance.result.then(function (selectedItem) {
      $scope.selected = selectedItem;
    }, function () {
      $log.info('Modal dismissed at: ' + new Date());
    });
  };

  $scope.openComponentModal = function () {
    var modalInstance = $uibModal.open({
      animation: $scope.animationsEnabled,
      component: 'modalComponent',
      resolve: {
        items: function () {
          return $scope.items;
        }
      }
    });

    modalInstance.result.then(function (selectedItem) {
      $scope.selected = selectedItem;
    }, function () {
      $log.info('modal-component dismissed at: ' + new Date());
    });
  };


  $scope.toggleAnimation = function () {
    $scope.animationsEnabled = !$scope.animationsEnabled;
  };
});


// Please note that $uibModalInstance represents a modal window (instance) dependency.
// It is not the same as the $uibModal service used above.

app.controller('modalInstCtrl', function ($scope, $uibModalInstance, items) {
  //var $scope = this;
  $scope.items = items;
  $scope.selected = {
    item: $scope.items[0]
  };

  $scope.ok = function () {
    $uibModalInstance.close($scope.selected.item);
  };

  $scope.cancel = function () {
    $uibModalInstance.dismiss('cancel');
  };
});
 */
// Please note that the close and dismiss bindings are from $uibModalInstance.
/*
angular.module('ui.bootstrap.demo').component('modalComponent', {
  templateUrl: 'myModalContent.html',
  bindings: {
    resolve: '<',
    close: '&',
    dismiss: '&'
  },
  controller: function () {
    var $scope = this;

    $scope.$onInit = function () {
      $scope.items = $scope.resolve.items;
      $scope.selected = {
        item: $scope.items[0]
      };
    };

    $scope.ok = function () {
      $scope.close({$value: $scope.selected.item});
    };

    $scope.cancel = function () {
      $scope.dismiss({$value: 'cancel'});
    };
  }
});*/