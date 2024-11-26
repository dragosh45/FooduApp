import React from 'react';
import PropTypes from 'prop-types';
import Constants from "../util/Constants";

const FormLayout = props =>
    <div className="mt-3 mb-3">
        <div className="row justify-content-center align-top">
            <img src={Constants.logoUrl}/>
        </div>

        <div className="row justify-content-center align-items-center">
            <div className="col-4">
                <h3 className="mt-4">{props.subtitle}</h3>

                {props.children}
            </div>
        </div>
    </div>;

FormLayout.propTypes = {
    title: PropTypes.string.isRequired,
    subtitle: PropTypes.string.isRequired,
};
export default FormLayout;