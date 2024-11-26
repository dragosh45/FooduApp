import React, {Component} from 'react';
import {Redirect} from "react-router";
import Constants from "../util/Constants";


class LandingPage extends Component {

    state = {
        redirect: false
    };

    handleLoginRedirect = () => {
        this.setState({redirect: true});
    };


    render() {
        return (<div>
                <nav className="navbar navbar-expand-lg navbar-light fixed-top py-3" id="mainNav">
                    <div className="container">
                        <a className="navbar-brand js-scroll-trigger text-white" href="#page-top">Foodu</a>
                    </div>
                </nav>

                <header className="masthead">
                    <div className="container h-100">
                        <div className="row h-100 align-items-center justify-content-center text-center">
                            <div className="col-lg-10 align-self-end">
                                <img src={Constants.logoUrl}/>
                            </div>
                            <div className="col-lg-10 align-top">
                                <h1 className="text-uppercase text-white font-weight-bold">Differentiate your restaurant
                                    by being part of Foodu</h1>
                                <hr className="divider my-4"/>
                            </div>
                            <div className="col-lg-8 align-self-baseline">
                                <p className="text-white-75 font-weight-light mb-5 text-white">Get more customers by
                                    presenting your food directly
                                    to customers through the Foodu app. Register and upload your dishes in matter of
                                    minutes.</p>
                                <button className="btn btn-info btn-xl js-scroll-trigger"
                                        onClick={this.handleLoginRedirect}>Register or login
                                </button>
                            </div>
                        </div>
                    </div>
                </header>
                {this.state.redirect ? <Redirect to="/login"/> : ""}
            </div>
        );
    }
}

export default LandingPage;