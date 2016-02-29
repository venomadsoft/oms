'use strict';

describe('Controller Tests', function() {

    describe('DerivedGsmShade Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockDerivedGsmShade, MockFormulae, MockSimpleGsmShade, MockMill, MockPriceList;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockDerivedGsmShade = jasmine.createSpy('MockDerivedGsmShade');
            MockFormulae = jasmine.createSpy('MockFormulae');
            MockSimpleGsmShade = jasmine.createSpy('MockSimpleGsmShade');
            MockMill = jasmine.createSpy('MockMill');
            MockPriceList = jasmine.createSpy('MockPriceList');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'DerivedGsmShade': MockDerivedGsmShade,
                'Formulae': MockFormulae,
                'SimpleGsmShade': MockSimpleGsmShade,
                'Mill': MockMill,
                'PriceList': MockPriceList
            };
            createController = function() {
                $injector.get('$controller')("DerivedGsmShadeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'omsApp:derivedGsmShadeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
