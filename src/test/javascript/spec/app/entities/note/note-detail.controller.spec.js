'use strict';

describe('Note Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockNote, MockNoteType, MockLine, MockNoteSet;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockNote = jasmine.createSpy('MockNote');
        MockNoteType = jasmine.createSpy('MockNoteType');
        MockLine = jasmine.createSpy('MockLine');
        MockNoteSet = jasmine.createSpy('MockNoteSet');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Note': MockNote,
            'NoteType': MockNoteType,
            'Line': MockLine,
            'NoteSet': MockNoteSet
        };
        createController = function() {
            $injector.get('$controller')("NoteDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'omsApp:noteUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
