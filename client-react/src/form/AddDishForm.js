import React from "react";
import PropTypes from "prop-types";

const AddDishForm = props =>
    <div>

        <form onSubmit={props.handleSubmitEvent}>
            <div className="form-group">
                <label htmlFor="inputName">Dish name</label>
                <input type="text" className="form-control" aria-describedby="dishNameHelp"
                       placeholder="Chicken stew" onChange={props.handleDishNameInput}/>
            </div>

            <div className="form-group">
                <label htmlFor="inputName">Image URL</label>
                <input type="text" className="form-control" aria-describedby="dishNameHelp"
                       placeholder="Picture of your dish" onChange={props.handleImgUrl}/>
            </div>

            <div className="form-group">
                <label htmlFor="inputName">Description</label>
                <input type="text" className="form-control" aria-describedby="dishNameHelp"
                       placeholder="Chicken stew with mushrooms and ..." onChange={props.handleDescriptionInput}/>
            </div>

            <div className="form-group">
                <label htmlFor="priceInput">Price</label>
                <input type="number" min="0" className="form-control input-medium bfh-phone"
                       id="priceInput" placeholder="10$" onChange={props.handlePriceInput}/>
            </div>

            <div className="form-group">
                <label htmlFor="timeInput">Minutes to prepare</label>
                <input type="number" min="0" className="form-control input-medium bfh-phone"
                       id="timeInput" placeholder="15" onChange={props.handleMinutesInput}/>
            </div>

            <div className="form-group">
                <label htmlFor="tagsInput">Tags (separated by comma)</label>
                <input type="text" className="form-control" placeholder="dinner, pasta, italian, lunch" onChange={props.handleTagsInput}/>
            </div>

            <button type="submit" className="btn btn-primary">Submit</button>
        </form>


    </div>;

AddDishForm.propTypes = {
    handleDishNameInput: PropTypes.func.isRequired,
    handleImgUrl: PropTypes.func.isRequired,
    handleDescriptionInput: PropTypes.func.isRequired,
    handlePriceInput: PropTypes.func.isRequired,
    handleMinutesInput: PropTypes.func.isRequired,
    handleSubmitEvent: PropTypes.func.isRequired,
    handleTagsInput: PropTypes.func.isRequired,
};


export default AddDishForm;