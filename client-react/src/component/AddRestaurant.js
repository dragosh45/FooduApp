import React, {Component} from 'react';
import FormLayout from "../layout/FormLayout";
import MainLayout from "../layout/MainLayout";
import Constants from "../util/Constants";
import {Redirect} from "react-router";


class AddRestaurant extends Component {

    state = {
        pendingNameInput: "",
        pendingNumberInput: "",
        redirect: false
    };

    handleNameInput = e =>
        this.setState({pendingNameInput: e.target.value});

    handleNumberInput = e =>
        this.setState({pendingNumberInput: e.target.value});

    handleSubmit = async e => {
        e.preventDefault();
        await this.callCreateRestaurant();
    };

    render() {
        return (
            <div>
                <MainLayout showNavigation={true} authenticationRequired={true}>
                    <FormLayout title="Add your restaurant" subtitle="Details ">
                        <form onSubmit={this.handleSubmit}>

                            <div className="form-group">
                                <label htmlFor="nameInput">Name</label>
                                <input type="text" className="form-control" id="nameInput"
                                       placeholder="Enter restaurant name" onChange={this.handleNameInput}/>
                            </div>

                            <div className="form-group">
                                <label htmlFor="phoneInput">Number</label>
                                <input type="number" min="0" className="form-control input-medium bfh-phone"
                                       id="phoneInput"
                                       placeholder="0881313123" data-format="+1 (ddd) ddd-dddd"
                                       onChange={this.handleNumberInput}/>
                            </div>


                            <div className="form-group">
                                <button className="btn btn-info">Upload Logo</button>
                            </div>

                            <button type="submit" className="btn btn-primary">Create</button>
                            {this.state.redirect ? <Redirect to={"/home"}/> : ""}
                        </form>
                    </FormLayout>
                </MainLayout>
            </div>
        );
    };

    async callCreateRestaurant() {
        let req_body = {
            "name" : this.state.pendingNameInput,
            "phone" : this.state.pendingNumberInput,
            "logoUrl" : "logoUrl"
        };

        const headers = new Headers();
        headers.append("Content-Type", "application/json");
        headers.append("Authorization", sessionStorage.getItem('jwt'));

        const options = {
            method: 'POST',
            headers,
            body: JSON.stringify(req_body)
        };

        const request = new Request(Constants.url + "/restaurant", options);
        const response = await fetch(request);
        const status = await response.status;

        if(status !== 200){
            alert("Restaurant already exists!");
        } else {
            alert("Congrats! Restaurant created!");
            this.setState({redirect: true});
        }
    }
}

export default AddRestaurant;