'use strict';

describe('Controller Tests', function() {

    describe('SimpleGsmShade Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSimpleGsmShade, MockMill, MockPrice;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSimpleGsmShade = jasmine.createSpy('MockSimpleGsmShade');
            MockMill = jasmine.createSpy('MockMill');
            MockPrice = jasmine.createSpy('MockPrice');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'SimpleGsmShade': MockSimpleGsmShade,
                'Mill': MockMill,
                'Price': MockPrice
            };
            createController = function() {
                $injector.get('$controller')("SimpleGsmShadeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'omsApp:simpleGsmShadeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
