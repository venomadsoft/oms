'use strict';

describe('NoteSet Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockNoteSet, MockNote;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockNoteSet = jasmine.createSpy('MockNoteSet');
        MockNote = jasmine.createSpy('MockNote');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'NoteSet': MockNoteSet,
            'Note': MockNote
        };
        createController = function() {
            $injector.get('$controller')("NoteSetDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'omsApp:noteSetUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
