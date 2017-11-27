
/**
 * First we will load all of this project's JavaScript dependencies which
 * includes Vue and other libraries. It is a great starting point when
 * building robust, powerful web applications using Vue and Laravel.
 */

require('./bootstrap');

window.Vue = require('vue');

/**
 * Next, we will create a fresh Vue application instance and attach it to
 * the page. Then, you may begin adding components to this application
 * or customize the JavaScript scaffolding to fit your unique needs.
 */

var Bus = new Vue({});
window.Bus = Bus; // Now we bind our event bus to window, so its global.

// Widgets
if( document.querySelector('[data-vue="widgets"]') ) {
    const Widgets = new Vue({
        el: '[data-vue="widgets"]',
        components: {
            'widgets': require('./components/widget/index.vue'),
        }
    });
}