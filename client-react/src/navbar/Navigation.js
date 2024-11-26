import React from 'react';
import PropTypes from 'prop-types';
import NavLink from "react-router-dom/es/NavLink";
import Constants from "../util/Constants";

const Navigation = props =>
    <div>
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
            <NavLink className="navbar-brand" to="/home">
                {/*<strong>Foodu</strong>*/}
                <img src={Constants.logoUrl} height="50px" width="auto"/>
            </NavLink>
            <button className="navbar-toggler" type="button" data-toggle="collapse"
                    data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false"
                    aria-label="Toggle navigation">
                <span className="navbar-toggler-icon"></span>
            </button>
            <div className="collapse navbar-collapse" id="navbarNavAltMarkup">
                <div className="navbar-nav navbar-dark">

                    {props.navTiles.map((link, key) => {
                        return <NavLink key={key} to={link.url}
                                        className="nav-item nav-link text-white">{link.name}</NavLink>;
                    })}

                </div>
            </div>

            <ul className="nav navbar-nav navbar-right">
                <li className="btn btn-info" onClick={props.handleSignOut}>Sign out</li>
            </ul>
        </nav>
    </div>;

Navigation.propTypes = {
    navTiles: PropTypes.array.isRequired,
    handleSignOut: PropTypes.func.isRequired
};

export default Navigation;