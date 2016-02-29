'use strict';

describe('Controller Tests', function() {

    describe('Address Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockAddress, MockAddresses, MockAddressLine;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockAddress = jasmine.createSpy('MockAddress');
            MockAddresses = jasmine.createSpy('MockAddresses');
            MockAddressLine = jasmine.createSpy('MockAddressLine');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Address': MockAddress,
                'Addresses': MockAddresses,
                'AddressLine': MockAddressLine
            };
            createController = function() {
                $injector.get('$controller')("AddressDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'omsApp:addressUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
