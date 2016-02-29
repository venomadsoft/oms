'use strict';

describe('Controller Tests', function() {

    describe('Price Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPrice, MockQuality, MockSimpleGsmShade, MockMill, MockPriceList;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPrice = jasmine.createSpy('MockPrice');
            MockQuality = jasmine.createSpy('MockQuality');
            MockSimpleGsmShade = jasmine.createSpy('MockSimpleGsmShade');
            MockMill = jasmine.createSpy('MockMill');
            MockPriceList = jasmine.createSpy('MockPriceList');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Price': MockPrice,
                'Quality': MockQuality,
                'SimpleGsmShade': MockSimpleGsmShade,
                'Mill': MockMill,
                'PriceList': MockPriceList
            };
            createController = function() {
                $injector.get('$controller')("PriceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'omsApp:priceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
