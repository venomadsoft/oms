'use strict';

angular.module('omsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('formula', {
                parent: 'entity',
                url: '/formulas',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Formulas'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/formula/formulas.html',
                        controller: 'FormulaController'
                    }
                },
                resolve: {
                }
            })
            .state('formula.detail', {
                parent: 'entity',
                url: '/formula/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Formula'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/formula/formula-detail.html',
                        controller: 'FormulaDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Formula', function($stateParams, Formula) {
                        return Formula.get({id : $stateParams.id});
                    }]
                }
            })
            .state('formula.new', {
                parent: 'formula',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/formula/formula-dialog.html',
                        controller: 'FormulaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    operator: null,
                                    operand: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('formula', null, { reload: true });
                    }, function() {
                        $state.go('formula');
                    })
                }]
            })
            .state('formula.edit', {
                parent: 'formula',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/formula/formula-dialog.html',
                        controller: 'FormulaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Formula', function(Formula) {
                                return Formula.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('formula', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('formula.delete', {
                parent: 'formula',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/formula/formula-delete-dialog.html',
                        controller: 'FormulaDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Formula', function(Formula) {
                                return Formula.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('formula', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
