'use strict';

describe('CaissesCtrl', function () {
    
    beforeEach(module('SgmapRetraiteConfig'));
    
    var $scope, controller, ApiCaisseDepartementale;
    
    beforeEach(inject(function (_ApiCaisseDepartementale_) {
        ApiCaisseDepartementale = _ApiCaisseDepartementale_;
    }));
    
    var mockCaisses = [{
        "nom": "CARSAT Aquitaine",
        "adresse1": "80 avenue de la Jallère",
        "adresse2": "Quartier du Lac",
        "adresse3": "33053 BORDEAUX CEDEX",
        "id": 29
    }, {
        "nom": "CARSAT Auvergne",
        "adresse1": "63036 CLERMONT FERRAND CEDEX 1",
        "adresse2": "",
        "adresse3": "",
        "id": 30
    }];

    beforeEach(function() {
        spyOn(ApiCaisseDepartementale, 'all').and.callFake(function(name) {
            
            if (name == "CNAV") {
                return {
                    $promise: {
                        then: function(onSuccess, onError) {
                            onSuccess(mockCaisses);
                        }
                    }
                };
            }
            
        });
    });

    beforeEach(inject(function ($rootScope, $controller) {

        $scope = $rootScope.$new();
        var $stateParams = {
            name: "CNAV"
        };
        
        controller = $controller('CaissesCtrl', {
            $scope: $scope,
            $stateParams: $stateParams
        });

    }));
    
    it('should have init data in scope', function () {
        expect($scope.caisses).toEqual(mockCaisses);
    });

});

