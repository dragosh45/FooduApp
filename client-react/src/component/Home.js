import React, {Component} from 'react';
import MainLayout from "../layout/MainLayout";
import Constants from "../util/Constants";

class Home extends Component {

    state = {restaurant: {}};

    async componentWillMount() {
        await this.getRestaurantInfo();
    }

    render() {
        let status;
        console.log(this.state.restaurant);
        if(this.state.restaurant.verified === false) {
            status = <h5>Restaurant is being reviewed</h5>
        } else {
            status = <h5>Approved!</h5>
        }
        return (
            <div>
                <MainLayout showNavigation={true} authenticationRequired={true}>
                    <h2 className="mt-5">Hello {sessionStorage.getItem('role_name')}!</h2>
                    <p>Please explore the owner panel</p>
                    <h4>Restaurant Status:</h4>
                    {status}
                </MainLayout>
            </div>
        );
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
            if(body.role.name === "OWNER") {
                console.log("Successfully fetched restaurant id.");
                sessionStorage.setItem('restaurant_id', body.restaurant.id);
                this.setState({restaurant: body.restaurant});
            }
        }
    }

}

export default Home;