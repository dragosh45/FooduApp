import React from 'react';
import PropTypes from 'prop-types';

const SignUpForm = props =>
    <div>
        <div className="form-group">
            <label htmlFor="inputGivenNames">Name</label>
            <input type="text" className="form-control" id="inputGivenNames"
                   aria-describedby="givenNamesHelp" placeholder="Enter given names" onChange={props.handleGivenNamesInput}/>
        </div>

        <div className="form-group">
            <label htmlFor="inputSurname">Surname</label>
            <input type="text" className="form-control" id="inputSurname"
                   aria-describedby="surnameHelp" placeholder="Enter surname" onChange={props.handleSurnameInput} />
        </div>

        <div className="form-group">
            <button className="btn btn-info">Upload Photo</button>
        </div>
    </div>;

SignUpForm.propTypes = {
    handleGivenNamesInput: PropTypes.func.isRequired,
    handleSurnameInput: PropTypes.func.isRequired
};

export default SignUpForm;