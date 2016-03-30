'use strict';

describe('Controller Tests', function() {

    describe('PriceList Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPriceList, MockPrice, MockTax, MockCustomerGroup, MockDerivedGsmShade;
        var createController;
        var entity = new Object();

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            entity.$promise = $injector.get('$q').defer().promise;
            MockPriceList = jasmine.createSpy('MockPriceList');
            MockPrice = jasmine.createSpy('MockPrice');
            MockTax = jasmine.createSpy('MockTax');
            MockCustomerGroup = jasmine.createSpy('MockCustomerGroup');
            MockDerivedGsmShade = jasmine.createSpy('MockDerivedGsmShade');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': entity ,
                'PriceList': MockPriceList,
                'Price': MockPrice,
                'Tax': MockTax,
                'CustomerGroup': MockCustomerGroup,
                'DerivedGsmShade': MockDerivedGsmShade
            };
            createController = function() {
                $injector.get('$controller')("PriceListDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'omsApp:priceListUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
