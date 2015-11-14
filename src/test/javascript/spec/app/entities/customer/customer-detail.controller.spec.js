'use strict';

describe('Customer Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockCustomer, MockAddresses, MockNoteSet, MockFormulae, MockCustomerGroup;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockCustomer = jasmine.createSpy('MockCustomer');
        MockAddresses = jasmine.createSpy('MockAddresses');
        MockNoteSet = jasmine.createSpy('MockNoteSet');
        MockFormulae = jasmine.createSpy('MockFormulae');
        MockCustomerGroup = jasmine.createSpy('MockCustomerGroup');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Customer': MockCustomer,
            'Addresses': MockAddresses,
            'NoteSet': MockNoteSet,
            'Formulae': MockFormulae,
            'CustomerGroup': MockCustomerGroup
        };
        createController = function() {
            $injector.get('$controller')("CustomerDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'omsApp:customerUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
