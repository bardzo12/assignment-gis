<!DOCTYPE html>
<html lang="{{ config('app.locale') }}">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Test">
    <meta name="author" content="Scott W. Brown">
    <title>{{ config('app.name', 'Laravel') }}</title>
    <!-- Site core CSS -->
    <link href="{{ asset('css/app.css') }}" rel="stylesheet">
</head>

<body>

<div class="site-wrapper">

    <div class="site-wrapper-inner">

        <div class="cover-container">

            <div class="masthead clearfix">
                <div class="inner">
                    <h3 class="masthead-brand text-white">{{ config('app.name', 'Laravel') }}</h3>
                    <nav class="nav nav-masthead">
                        @if (Auth::check())
                            <a href="{{ url('/home') }}" class="nav-link">Home</a>
                        @else
                            <a href="{{ url('/login') }}" class="nav-link">Login</a>
                        @endif
                    </nav>
                </div>
            </div>

            <div class="inner cover">
                <h1 class="cover-heading text-shadow">Laravel Test Project</h1>
                <p class="lead text-center">
                    @if (Auth::check())
                        <a href="{{ url('/home') }}" class="btn btn-lg btn-secondary">Home</a>
                    @else
                        <a href="{{ url('/login') }}" class="btn btn-lg btn-secondary">Login</a>
                    @endif
                </p>
            </div>

            <div class="mastfoot">
                <div class="inner text-center">
                    <p>by <a href="https://sbrown.sandbox.services">Scott Brown</a>.</p>
                </div>
            </div>

        </div>

    </div>

</div>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <!-- Required External Vue libraries -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/marked/0.3.6/marked.min.js"></script>
    <script src="{{ asset('js/jquery.min.js') }}"></script>
    <script src="{{ asset('js/bootstrap.min.js') }}"></script>
</body>
</html>