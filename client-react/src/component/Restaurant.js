import React, {Component} from 'react';
import MainLayout from "../layout/MainLayout";
import Constants from "../util/Constants";

class Restaurant extends Component {


    state = {
        restaurant: {
            name: "",
            logoUrl: "",
            phone: ""
        }
    };

    render() {
        return (
            <MainLayout showNavigation={true} authenticationRequired={true}>
                <h2 className="mt-5">Your Restaurant</h2>
                <div className="col mt-5 align-content-center" align="center">

                    <img src={"https://i.imgur.com/S3OVfWX.jpg" /*this.state.logoUrl*/} alt="Restaurant Logo"
                         className="img-fluid img-thumbnail w-25"/>

                    <h2>{this.state.name}</h2>

                    <h4><strong>Phone: </strong>{this.state.phone}</h4>
                </div>

            </MainLayout>
        );
    }

    async componentDidMount() {
        await this.getRestaurantInfo();
        const id = sessionStorage.getItem('restaurant_id');
        this.getRestaurant(id);
    }

    async getRestaurant(id) {
        const headers = new Headers();
        headers.append("Content-Type", "application/json");
        headers.append("Authorization", sessionStorage.getItem('jwt'));


        const options = {
            method: 'GET',
            headers
        };

        const url = Constants.url + "/restaurant/" + id;
        const request = new Request(url, options);
        const response = await fetch(request);
        const statusCode = await response.status;
        const body = await response.json();

        if (statusCode !== 200) {
            alert("Error fetching restaurant info!");
        } else {
            this.setState({name: body.name, logoUrl: body.logoUrl, phone: body.phone});
        }
    }

    async getRestaurantInfo() {
        const headers = new Headers();
        headers.append("Content-Type", "application/json");
        headers.append("Authorization", sessionStorage.getItem('jwt'));

        const options = {
            method: 'GET',
            headers
        };

        const request = new Request(Constants.url + "/user", options);
        const response = await fetch(request);
        const status = await response.status;
        const body = await response.json();

        if (status !== 200) {
            console.log("Error getting user data. Status" + status);
            alert("Error getting user data");
        } else {
            if (body.role.name === "OWNER") {
                console.log("Successfully fetched restaurant id.");
                sessionStorage.setItem('restaurant_id', body.restaurant.id);
                this.setState({restaurant: body.restaurant});
            }
        }
    }
}

export default Restaurant;