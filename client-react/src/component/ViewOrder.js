import React, {Component} from 'react';
import MainLayout from "../layout/MainLayout";
import DishList from "../list/DishList";
import Constants from "../util/Constants";

const moment = require('moment');

class ViewOrder extends Component {

  state = {
    // dishes needs to be initialized to [] since render (which will use it) is called before getOrderAndCustomer finishes
    order: {dishes: []},
    customer: {}
  };

  async componentWillMount() {
    const {id} = this.props.match.params;
    await this.getOrderAndCustomer(id);
  }

  render() {
    return (
        <div>
          <MainLayout showNavigation={true}>
            <div className="mt-5">

              <div className="d-flex justify-content-between mb-5">
                <h2>Order Number: {this.state.order.id}</h2>
                <h3>Time: {moment(this.state.order.serveTime).format(
                    'MM/D, h:mm a')}</h3>
                <h3>Seats: {this.state.order.seats}</h3>
              </div>

              <DishList listOfDishes={this.state.order.dishes}/>
            </div>
          </MainLayout>
        </div>
    );
  }

  async getOrderAndCustomer(id) {
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append('Authorization', sessionStorage.getItem('jwt'));

    let options = {
      method: 'GET',
      headers
    };

    const endpoint = "/restaurant/" + sessionStorage.getItem("restaurant_id")
        + "/order";

    const request = new Request(Constants.url + endpoint, options);
    const response = await fetch(request);
    const status = await response.status;
    const body = await response.json();

    if (status !== 200) {
      alert("Error in getting orders!");
      console.log(
          "Processed Status: " + status);
    } else {
      let newOrder = body.find(function (element) {
        // === will break this comparison
        // eslint-disable-next-line
        return element.id == id;
      });
      this.setState({order: newOrder});
    }
  }
}

export default ViewOrder;