'use strict';

describe('CustomerGroup Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockCustomerGroup, MockPriceList, MockMill, MockCustomer, MockNoteSet, MockFormulae;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockCustomerGroup = jasmine.createSpy('MockCustomerGroup');
        MockPriceList = jasmine.createSpy('MockPriceList');
        MockMill = jasmine.createSpy('MockMill');
        MockCustomer = jasmine.createSpy('MockCustomer');
        MockNoteSet = jasmine.createSpy('MockNoteSet');
        MockFormulae = jasmine.createSpy('MockFormulae');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'CustomerGroup': MockCustomerGroup,
            'PriceList': MockPriceList,
            'Mill': MockMill,
            'Customer': MockCustomer,
            'NoteSet': MockNoteSet,
            'Formulae': MockFormulae
        };
        createController = function() {
            $injector.get('$controller')("CustomerGroupDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'omsApp:customerGroupUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
