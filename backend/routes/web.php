<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

/**
 * Quick Setup New User Password
 * Insert into database, grab hashed password
 ************************
 * use Hash;
 * Route::get('/test', function () {;
 *     echo Hash::make('your_password');
 * });
 ************************
 */
Auth::routes();

Route::get('/', function () {
    return view('welcome');
});
Auth::routes();

Route::get('/home', 'HomeController@index')->name('home');

Route::get('/PointController/select');

Route::get('1', 'MapApiController@first');

Route::get('2', 'MapApiController@second');

Route::get('3', 'BaseController@third');
