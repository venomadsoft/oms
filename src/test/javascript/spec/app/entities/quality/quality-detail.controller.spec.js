'use strict';

describe('Quality Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockQuality, MockMill;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockQuality = jasmine.createSpy('MockQuality');
        MockMill = jasmine.createSpy('MockMill');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Quality': MockQuality,
            'Mill': MockMill
        };
        createController = function() {
            $injector.get('$controller')("QualityDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'omsApp:qualityUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
