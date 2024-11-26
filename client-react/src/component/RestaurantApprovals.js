import React, {Component} from 'react';
import MainLayout from "../layout/MainLayout";
import Constants from "../util/Constants";
import RestaurantList from "../list/RestaurantList";
import {Redirect} from "react-router";

//import Constants from "../util/Constants";

class RestaurantApprovals extends Component {
  state = {
    restaurants: [],
    redirect: false
  };

  async componentDidMount() {
    if (this.state.restaurants.length === 0) {
      await this.getUnapprovedRestaurants();
    }
  }

  render() {
    return (
        <div>
          <MainLayout showNavigation={true} authenticationRequired={true}>
            <div className="mt-5">
              <h2>Restaurant Approvals</h2>
              <RestaurantList restaurantList={this.state.restaurants}
                              rejectRestaurantAt={this.rejectRestaurantHandler}
                              acceptRestaurantAt={this.acceptRestaurantHandler}
                              redirectRestaurantAt={this.redirectHandler}/>
            </div>
          </MainLayout>
          {this.state.redirect ? <Redirect to="/restaurant"/> : ""}

        </div>
    );
  }

  redirectHandler = event => {
    const restaurantId = event.target.dataset.id;

    const restaurant = this.state.restaurants[restaurantId];
    sessionStorage.setItem('restaurant_id', restaurant.id);

    this.setState({redirect: true});
  }

  acceptRestaurantHandler = event => {
    const restaurantId = event.target.dataset.id;

    const restaurant = this.state.restaurants[restaurantId];
    this.acceptRestaurant(restaurant);
  };

  rejectRestaurantHandler = event => {
    const restaurantId = event.target.dataset.id;

    const restaurant = this.state.restaurants[restaurantId];
    this.deleteRestaurant(restaurant);
  };

  async deleteRestaurant(restaurant) {

    const headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append('Authorization', sessionStorage.getItem('jwt'));

    const options = {
      method: 'DELETE',
      headers,
    };

    const endpoint = "/restaurant/" + restaurant.id;

    const request = new Request(Constants.url + endpoint, options);
    const response = await fetch(request);
    const status = await response.status;

    if (status !== 200) {
      alert("Error Deleting restaurant!");
      console.log("Status: " + status);
    } else {
      this.getUnapprovedRestaurants();
    }
  }

  async acceptRestaurant(restaurant) {

    const headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append('Authorization', sessionStorage.getItem('jwt'));
    restaurant.verified = true;

    const req_body = restaurant;

    const options = {
      method: 'PUT',
      headers,
      body: JSON.stringify(req_body)
    };

    const endpoint = "/restaurant/" + restaurant.id;

    const request = new Request(Constants.url + endpoint, options);
    const response = await fetch(request);
    const status = await response.status;

    if (status !== 200) {
      alert("Error updating restaurant!");
      console.log("Status: " + status);
    } else {
      this.getUnapprovedRestaurants();
    }
  }

  async getUnapprovedRestaurants() {
    const headers = new Headers();
    headers.append("Content-Type", "application/json");
    headers.append("Authorization", sessionStorage.getItem('jwt'));

    const options = {
      method: 'GET',
      headers
    };

    const request = new Request(Constants.url + "/restaurant/unverified",
        options);
    const response = await fetch(request);
    const status = await response.status;
    const body = await response.json();

    if (status !== 200) {
      console.log("Error getting restaurant data. Status" + status);
      alert("Error getting restaurant data");
    } else {
      this.setState({restaurants: body});
    }
  }

}

export default RestaurantApprovals;