import React, {Component} from 'react';
import LoginForm from '../form/LoginForm';
import Constants from "../util/Constants";
import Redirect from "react-router/es/Redirect";
import FormLayout from '../layout/FormLayout';
import MainLayout from "../layout/MainLayout";

class Login extends Component {

    state = {
        pendingEmailInput: "",
        pendingPasswordInput: "",
        pendingGivenNamesInput: "",
        pendingSurnameInput: "",
        redirect: false,
        isLogin: true
    };

    handleLoginSignUpChange = e => {
        e.preventDefault();
        this.setState({isLogin: !this.state.isLogin});
    };

    handleSubmitEvent = async e => {
        e.preventDefault();
        if (this.state.isLogin)
            await this.loginUser();
        else
            await this.registerUser();

    };


    handleGivenNameInput = e =>
        this.setState({pendingGivenNamesInput: e.target.value});

    handleSurnameInput = e =>
        this.setState({pendingSurnameInput: e.target.value});

    handleEmailInput = e =>
        this.setState({pendingEmailInput: e.target.value});

    handlePasswordInput = e =>
        this.setState({pendingPasswordInput: e.target.value});

    render() {
        return (
            <div>
                <MainLayout showNavigation={false} authenticationRequired={false}>
                    <FormLayout title="Foodu" subtitle="Login">
                        <LoginForm onSubmit={this.handleSubmitEvent} onChangeEmail={this.handleEmailInput}
                                   onChangePassword={this.handlePasswordInput} isLogin={this.state.isLogin}
                                   handleLoginSignUpChange={this.handleLoginSignUpChange}
                                   handleGivenNamesInput={this.handleGivenNameInput}
                                   handleSurnameInput={this.handleSurnameInput}/>

                        {this.state.redirect ? <Redirect to={this.state.isLogin ? "/home" : "/add_restaurant"}/> : ""}
                    </FormLayout>
                </MainLayout>
            </div>
        );
    }

    async loginUser() {
        let req_body = {
            "email": this.state.pendingEmailInput,
            "password": this.state.pendingPasswordInput
        };

        const headers = new Headers();
        headers.append("Content-Type", "application/json");

        const options = {
            method: 'POST',
            headers,
            body: JSON.stringify(req_body),
        };

        const request = new Request(Constants.url + "/auth", options);
        const response = await fetch(request);
        const status = await response.status;
        const body = await response.json();

        if (status !== 200) {
            alert("Wrong email or password!");
        } else {
            sessionStorage.clear();
            sessionStorage.setItem('jwt', body.token);
            sessionStorage.setItem('role_name', body.role.name);
            this.setState({redirect: true});
        }
    };

    async registerUser() {
        let req_body = {
            "givenNames": this.state.pendingGivenNamesInput,
            "surname": this.state.pendingSurnameInput,
            "photoUrl": "blank",
            "email": this.state.pendingEmailInput,
            "login": this.state.pendingPasswordInput
        };

        const headers = new Headers();
        headers.append('Content-Type', 'application/json');

        const options = {
            method: 'POST',
            headers,
            body: JSON.stringify(req_body)
        };

        const request = new Request(Constants.url + "/user/sign-up/owner", options);
        const response = await fetch(request);
        const status = await response.status;
        const body = await response.json();

        if (status !== 200) {
            alert("Profile not created! Please check your details.");
        } else {
            sessionStorage.setItem('role_name', body.role.name);
            await this.loginUser();
        }

    }
}

export default Login;