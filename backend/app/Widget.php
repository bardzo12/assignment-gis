<?php
namespace App;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;
use Auth;

class Widget extends Model
{
    use SoftDeletes;

    protected $table = 'widgets';
    /**
     *
     * Eloquent relations are refined below
     *
     */
}