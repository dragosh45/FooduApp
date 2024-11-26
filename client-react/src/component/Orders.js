import React, {Component} from 'react';
import MainLayout from "../layout/MainLayout";
import Toggle from 'react-toggle';
import "react-toggle/style.css";
import Constants from "../util/Constants";
import {IncomingOrderList, AcceptedOrderList} from "../list/OrdersList";

class Orders extends Component {

  state = {
    searchField: "",
    displayedOrders: [],
    acceptedOrders: [],
    incomingOrders: [],
    onlyAccepted: false
  };

  async componentWillMount() {
    await this.getOrders();
    this.setState({displayedOrders: this.state.incomingOrders});
  }

  render() {
    if (this.state.onlyAccepted) {
      return (
          <MainLayout showNavigation={true}>
            <div className="mt-5">
              <h2>Orders</h2>
              <div className="input-group">
                <Toggle
                    id='order-type'
                    onChange={this.handleToggle}/>
                <span className="col-4"
                      id='biscuit-label'>Show Accepted Orders</span>
              </div>
              <AcceptedOrderList
                  orderList={this.state.displayedOrders}
                  rejectOrderAt={this.handleRejectOrder}/>
            </div>
          </MainLayout>
      );
    } else {
      return (
          <MainLayout showNavigation={true}>
            <div className="mt-5">
              <h2>Orders</h2>
              <div className="input-group">
                <Toggle
                    id='order-type'
                    onChange={this.handleToggle}/>
                <span className="col-4"
                      id='biscuit-label'>Show Accepted Orders</span>
              </div>
              <IncomingOrderList
                  orderList={this.state.displayedOrders}
                  acceptOrderAt={this.handleAcceptOrder}
                  rejectOrderAt={this.handleRejectOrder}/>
            </div>
          </MainLayout>
      );
    }
  }

  handleAcceptOrder = event => {
    const orderId = event.target.dataset.id;

    const order = this.state.incomingOrders[orderId];
    order.processed = true;
    this.state.incomingOrders.splice(orderId, 1);
    this.state.acceptedOrders.push(order);
    this.updateOrder(order);
    this.setState({
      acceptedOrders: this.state.acceptedOrders,
      incomingOrders: this.state.incomingOrders,
      displayedOrders: this.state.incomingOrders
    });
  };
  handleRejectOrder = event => {
    const orderId = event.target.dataset.id;

    const order = this.state.displayedOrders[orderId];
    console.log(order);
    order.rejected = true;
    this.state.displayedOrders.splice(orderId, 1);
    this.updateOrder(order);
    this.setState({
      displayedOrders: this.state.displayedOrders
    });
  };

  handleToggle = event => {
    let orders = [];
    if (this.state.onlyAccepted) {
      orders = this.state.incomingOrders;
    } else {
      orders = this.state.acceptedOrders;
    }
    this.setState(
        {onlyAccepted: !this.state.onlyAccepted, displayedOrders: orders});
  };

  async updateOrder(order) {
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append('Authorization', sessionStorage.getItem('jwt'));
    const options = {
      method: 'POST',
      headers,
      body: JSON.stringify(order)
    };

    const endpoint = "/restaurant/" + sessionStorage.getItem("restaurant_id")
        + "/order";

    const request = new Request(Constants.url + endpoint, options);
    const response = await fetch(request);
    const status = await response.status;
    await response.json();

    if (status !== 200) {
      alert("Error in updating orders!");
      console.log("Status: " + status);
    }
  }

  async getOrders() {
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append('Authorization', sessionStorage.getItem('jwt'));

    let options = {
      method: 'GET',
      headers
    };

    const endpoint = "/restaurant/" + sessionStorage.getItem("restaurant_id")
        + "/order";

    const unprocessedURL = endpoint + "?filter=not_processed";
    const processedURL = endpoint + "?filter=processed";

    const requestProcessed = new Request(Constants.url + processedURL, options);
    const requestUnprocessed = new Request(Constants.url + unprocessedURL,
        options);
    const processedResponse = await fetch(requestProcessed);
    const unprocessedResponse = await fetch(requestUnprocessed);
    const processedStatus = await processedResponse.status;
    const unprocessedStatus = await unprocessedResponse.status;
    const processedBody = await processedResponse.json();
    const unprocessedBody = await unprocessedResponse.json();

    if (processedStatus !== 200 || unprocessedStatus !== 200) {
      alert("Error in getting orders!");
      console.log(
          "Processed Status: " + processedStatus + "\nUnprocessed Status: "
          + unprocessedStatus);
    } else {
      this.setState(
          {incomingOrders: unprocessedBody, acceptedOrders: processedBody});
    }
  }
}

export default Orders;