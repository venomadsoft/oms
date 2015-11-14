'use strict';

describe('Addresses Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockAddresses, MockAddress;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockAddresses = jasmine.createSpy('MockAddresses');
        MockAddress = jasmine.createSpy('MockAddress');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Addresses': MockAddresses,
            'Address': MockAddress
        };
        createController = function() {
            $injector.get('$controller')("AddressesDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'omsApp:addressesUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
