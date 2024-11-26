import React from 'react';
import PropTypes from 'prop-types';
import SignUpFrom from './SignUpForm';

const LoginForm = props =>
    <div>
        <form onSubmit={props.onSubmit}>
            <div className="form-group">
                <label htmlFor="exampleInputEmail1">Email address</label>
                <input type="email" className="form-control" id="exampleInputEmail1"
                       aria-describedby="emailHelp" placeholder="Enter email" onChange={props.onChangeEmail}/>
                <small id="emailHelp" className="form-text text-muted">We'll never share your email with
                    anyone else.
                </small>
            </div>
            <div className="form-group">
                <label htmlFor="exampleInputPassword1">Password</label>
                <input type="password" className="form-control" id="exampleInputPassword1"
                       placeholder="Password" onChange={props.onChangePassword}/>
            </div>

            {!props.isLogin ? <SignUpFrom handleGivenNamesInput={props.handleGivenNamesInput} handleSurnameInput={props.handleSurnameInput}/> : ""}

            <div className="form-group form-check">
                <input type="checkbox" className="form-check-input" id="exampleCheck1"/>
                <label className="form-check-label" htmlFor="exampleCheck1">Keep me logged in</label>
            </div>

            <div className="d-flex justify-content-between">
                <div>
                    <button type="submit" className="btn btn-primary">{props.isLogin ? "Log In" : "Sign Up"}</button>
                </div>
                <div>
                    <button className="btn btn-dark" onClick={props.handleLoginSignUpChange}>{props.isLogin ? "Sign Up" : "Back to Login"}</button>
                </div>
            </div>
        </form>
    </div>;

LoginForm.propTypes = {
    onSubmit: PropTypes.func.isRequired,
    onChangeEmail: PropTypes.func.isRequired,
    onChangePassword: PropTypes.func.isRequired,
    isLogin: PropTypes.bool.isRequired,
    handleLoginSignUpChange: PropTypes.func.isRequired,
    handleGivenNamesInput: PropTypes.func.isRequired,
    handleSurnameInput: PropTypes.func.isRequired
};

export default LoginForm;