'use strict';

describe('Controller Tests', function() {

    describe('Line Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockLine, MockNote;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockLine = jasmine.createSpy('MockLine');
            MockNote = jasmine.createSpy('MockNote');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Line': MockLine,
                'Note': MockNote
            };
            createController = function() {
                $injector.get('$controller')("LineDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'omsApp:lineUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
