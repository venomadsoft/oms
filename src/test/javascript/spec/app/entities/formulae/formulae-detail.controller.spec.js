'use strict';

describe('Controller Tests', function() {

    describe('Formulae Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFormulae, MockFormula;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFormulae = jasmine.createSpy('MockFormulae');
            MockFormula = jasmine.createSpy('MockFormula');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Formulae': MockFormulae,
                'Formula': MockFormula
            };
            createController = function() {
                $injector.get('$controller')("FormulaeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'omsApp:formulaeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
