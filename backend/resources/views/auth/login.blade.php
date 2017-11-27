@extends('layouts.app')

@section('content')
<div class="container">

    <div class="form-signin">
        <div class="card bg-inverse p-4">
            <h2 class="form-signin-heading text-shadow">Please sign in</h2>

            <form class="form-horizontal" method="POST" action="{{ route('login') }}">
                {{ csrf_field() }}

                <label for="inputEmail" class="sr-only">Email address</label>
                <input name="email" type="email" id="inputEmail" class="form-control{{ $errors->has('email') ? ' has-error' : '' }}" placeholder="Email address" value="{{ old('email') }}" required autofocus>

                <label for="inputPassword" class="sr-only">Password</label>
                <input name="password" type="password" id="inputPassword" class="form-control{{ $errors->has('password') ? ' has-error' : '' }}" placeholder="Password" required>

                @if ($errors->has('email'))
                    <div class="help-block pb-1 text-shadow">
                        <strong>{{ $errors->first('email') }}</strong>
                    </div>
                @endif

                @if ($errors->has('password'))
                    <div class="help-block pb-1 text-shadow">
                         <strong>{{ $errors->first('password') }}</strong>
                    </div>
                @endif

                <div class="checkbox text-shadow">
                    <label>
                        <input type="checkbox" name="remember" {{ old('remember') ? 'checked' : '' }}> Remember Me
                    </label>
                </div>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
            </form>
        </div>
    </div>

</div> <!-- /container -->
@endsection