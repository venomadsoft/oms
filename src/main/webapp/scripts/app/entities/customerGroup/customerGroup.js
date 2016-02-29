'use strict';

angular.module('omsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('customerGroup', {
                parent: 'entity',
                url: '/customerGroups',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'CustomerGroups'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/customerGroup/customerGroups.html',
                        controller: 'CustomerGroupController'
                    }
                },
                resolve: {
                }
            })
            .state('customerGroup.detail', {
                parent: 'entity',
                url: '/customerGroup/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'CustomerGroup'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/customerGroup/customerGroup-detail.html',
                        controller: 'CustomerGroupDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'CustomerGroup', function($stateParams, CustomerGroup) {
                        return CustomerGroup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('customerGroup.new', {
                parent: 'customerGroup',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/customerGroup/customerGroup-dialog.html',
                        controller: 'CustomerGroupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    freight: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('customerGroup', null, { reload: true });
                    }, function() {
                        $state.go('customerGroup');
                    })
                }]
            })
            .state('customerGroup.edit', {
                parent: 'customerGroup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/customerGroup/customerGroup-dialog.html',
                        controller: 'CustomerGroupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CustomerGroup', function(CustomerGroup) {
                                return CustomerGroup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('customerGroup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('customerGroup.delete', {
                parent: 'customerGroup',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/customerGroup/customerGroup-delete-dialog.html',
                        controller: 'CustomerGroupDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['CustomerGroup', function(CustomerGroup) {
                                return CustomerGroup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('customerGroup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
