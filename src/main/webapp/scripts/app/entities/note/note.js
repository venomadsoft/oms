'use strict';

angular.module('omsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('note', {
                parent: 'entity',
                url: '/notes',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Notes'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/note/notes.html',
                        controller: 'NoteController'
                    }
                },
                resolve: {
                }
            })
            .state('note.detail', {
                parent: 'entity',
                url: '/note/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Note'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/note/note-detail.html',
                        controller: 'NoteDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Note', function($stateParams, Note) {
                        return Note.get({id : $stateParams.id});
                    }]
                }
            })
            .state('note.new', {
                parent: 'note',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/note/note-dialog.html',
                        controller: 'NoteDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('note', null, { reload: true });
                    }, function() {
                        $state.go('note');
                    })
                }]
            })
            .state('note.edit', {
                parent: 'note',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/note/note-dialog.html',
                        controller: 'NoteDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Note', function(Note) {
                                return Note.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('note', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('note.delete', {
                parent: 'note',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/note/note-delete-dialog.html',
                        controller: 'NoteDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Note', function(Note) {
                                return Note.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('note', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
