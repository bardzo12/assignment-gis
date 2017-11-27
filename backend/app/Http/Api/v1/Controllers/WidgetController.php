<?php
namespace App\Http\Api\v1\Controllers;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Event;
use App\Http\Controllers\Controller;
use App\Widget;

class WidgetController extends Controller
{
    /**
     * Create a new controller instance.
     *
     * @return void
     */

    public function __construct()
    {
        $this->middleware('auth');
    }

    /**
     * Get all widgets.
     *
     * @param  \Illuminate\Http\Request $request
     * @return \Illuminate\Http\Response
     */
    public function index(Request $request)
    {
        $widgets = Widget::orderBy('weight', 'asc')->get();
        return compact('widgets');
    }

    /**
     * Add a new widget.
     *
     * @param  \Illuminate\Http\Request $request
     * @return \Illuminate\Http\Response
     */
    public function add(Request $request)
    {
        $widget                 =   new Widget;
        $widget->description    =   $request->description;
        $widget->weight         =   $request->weight;

        if($widget->save())
            return $widget;
    }

    /**
     * Edit an existing widget.
     *
     * @param  \Illuminate\Http\Request $request
     * @return \Illuminate\Http\Response
     */
    public function edit(Request $request)
    {
        $widget     =   Widget::findOrFail($request->id);

        if( isset($request->description) )
            $widget->description            =   $request->description;

        if( isset($request->weight) )
            $widget->weight                 =   $request->weight;

        if($widget->save())
            return compact(widget);

        return compact('widget');
    }

    /**
     * Delete a widget.
     *
     * @param  \Illuminate\Http\Request $request
     * @return \Illuminate\Http\Response
     */
    public function delete(Request $request)
    {
        $widget  =   Widget::findOrFail($request->id);
        $widget->delete();

        return compact('widget');
    }

    /**
     * Sort e a widgets.
     *
     * @param  \Illuminate\Http\Request $request
     * @return \Illuminate\Http\Response
     */
    public function sort(Request $request)
    {
        $widgets  =   $request->widgets;

        $i = 1;
        foreach ($widgets as $value)
        {
            $widget         =   Widget::findOrFail($value['id']);
            $widget->weight =   $i++;
            $widget->save();
        }

        return 'ok';
    }
}
