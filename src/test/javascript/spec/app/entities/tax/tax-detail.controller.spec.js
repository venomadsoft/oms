'use strict';

describe('Controller Tests', function() {

    describe('Tax Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTax, MockTaxType, MockPriceList;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTax = jasmine.createSpy('MockTax');
            MockTaxType = jasmine.createSpy('MockTaxType');
            MockPriceList = jasmine.createSpy('MockPriceList');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Tax': MockTax,
                'TaxType': MockTaxType,
                'PriceList': MockPriceList
            };
            createController = function() {
                $injector.get('$controller')("TaxDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'omsApp:taxUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
