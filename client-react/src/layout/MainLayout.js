import React, {Component} from 'react';
import Navigation from "../navbar/Navigation";
import PropTypes from 'prop-types';
import {Redirect} from "react-router";

class MainLayout extends Component {

  state = {
    navTiles: [],
    loggedIn: true
  };

  handleOnSignOut = () => {
    sessionStorage.clear();
    this.setState({loggedIn: false});
  };

  componentDidMount() {
    if (this.props.authenticationRequired
        && sessionStorage.getItem('jwt') == null) {
      this.setState({loggedIn: false});
    }
  }

  render() {
    return (
        <div>
          {this.props.showNavigation ? <Navigation
              navTiles={this.state.navTiles}
              handleSignOut={this.handleOnSignOut}/> : ""}
          <div className="container mt-auto">
            {this.props.children}

            {this.state.loggedIn ? "" : <Redirect to="/login"/>}
          </div>
        </div>
    );
  };

  async componentWillMount() {
    const role = sessionStorage.getItem('role_name');
    if (role === 'OWNER') {
      this.state.navTiles.push({url: '/home', name: 'Home'})
      this.state.navTiles.push({url: '/menu', name: 'Menu'})
      this.state.navTiles.push({url: '/orders', name: 'Orders'})
    } else {
      this.state.navTiles.push(
          {url: '/restaurant_approvals', name: 'Restaurant Approvals'})

      this.setState({navTiles: this.state.navTiles});
    }
  }
}

MainLayout.propTypes = {
  showNavigation: PropTypes.bool.isRequired,
  authenticationRequired: PropTypes.bool.isRequired
};

export default MainLayout;
