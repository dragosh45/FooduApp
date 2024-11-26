import 'bootstrap/dist/css/bootstrap.min.css';
import React, {Component} from 'react';
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import './App.css';

import Home from "./component/Home";
import Restaurant from "./component/Restaurant";
import Login from "./component/Login";
import Menu from "./component/Menu";
import Orders from "./component/Orders";
import AddRestaurant from "./component/AddRestaurant";
import Category from "./component/Category";
import AddDish from "./component/AddDish";
import RestaurantApprovals from "./component/RestaurantApprovals";
import ViewOrder from "./component/ViewOrder";
import LandingPage from "./component/LandingPage";

class App extends Component {
  render() {
    return (
        <BrowserRouter>
          <div>
            <Switch>
              <Route exact path="/" component={LandingPage}/>
              <Route path="/home" component={Restaurant}/>
              <Route path={"/login"} component={Login}/>
              {/*<Route path={"/restaurant"} component={Restaurant}/>*/}
              <Route path={"/restaurant_approvals"}
                     component={RestaurantApprovals}/>
              <Route path={"/menu"} component={Menu}/>
              <Route path={"/add_restaurant"} component={AddRestaurant}/>
              <Route path={"/category/:id"} component={Category}/>
              <Route path={"/add_dish"} component={AddDish}/>
              <Route path={"/orders"} component={Orders}/>
              <Route path={"/view_order/:id"} component={ViewOrder}/>
            </Switch>
          </div>
        </BrowserRouter>
    );
  }
}

export default App;
