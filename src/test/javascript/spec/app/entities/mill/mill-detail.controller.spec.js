'use strict';

describe('Mill Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockMill, MockAddresses, MockNoteSet, MockPrice, MockQuality, MockSimpleGsmShade, MockCustomerGroup, MockDerivedGsmShade;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockMill = jasmine.createSpy('MockMill');
        MockAddresses = jasmine.createSpy('MockAddresses');
        MockNoteSet = jasmine.createSpy('MockNoteSet');
        MockPrice = jasmine.createSpy('MockPrice');
        MockQuality = jasmine.createSpy('MockQuality');
        MockSimpleGsmShade = jasmine.createSpy('MockSimpleGsmShade');
        MockCustomerGroup = jasmine.createSpy('MockCustomerGroup');
        MockDerivedGsmShade = jasmine.createSpy('MockDerivedGsmShade');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Mill': MockMill,
            'Addresses': MockAddresses,
            'NoteSet': MockNoteSet,
            'Price': MockPrice,
            'Quality': MockQuality,
            'SimpleGsmShade': MockSimpleGsmShade,
            'CustomerGroup': MockCustomerGroup,
            'DerivedGsmShade': MockDerivedGsmShade
        };
        createController = function() {
            $injector.get('$controller')("MillDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'omsApp:millUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
