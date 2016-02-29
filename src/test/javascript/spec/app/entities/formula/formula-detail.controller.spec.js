'use strict';

describe('Controller Tests', function() {

    describe('Formula Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFormula, MockFormulae;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFormula = jasmine.createSpy('MockFormula');
            MockFormulae = jasmine.createSpy('MockFormulae');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Formula': MockFormula,
                'Formulae': MockFormulae
            };
            createController = function() {
                $injector.get('$controller')("FormulaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'omsApp:formulaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
