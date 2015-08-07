planIt.config(function($stateProvider, $locationProvider) {
    $locationProvider.html5Mode(true);

    $stateProvider
        .state('root', {
            url: "/",
            templateUrl: 'root.html'
        });
});