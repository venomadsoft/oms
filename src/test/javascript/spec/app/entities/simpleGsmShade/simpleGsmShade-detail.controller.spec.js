'use strict';

describe('SimpleGsmShade Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockSimpleGsmShade, MockMill;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockSimpleGsmShade = jasmine.createSpy('MockSimpleGsmShade');
        MockMill = jasmine.createSpy('MockMill');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'SimpleGsmShade': MockSimpleGsmShade,
            'Mill': MockMill
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
