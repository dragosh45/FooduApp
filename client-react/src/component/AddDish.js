import React, {Component} from "react";
import FormLayout from "../layout/FormLayout";
import MainLayout from "../layout/MainLayout";
import AddDishForm from "../form/AddDishForm";
import Constants from "../util/Constants";
import {Redirect} from "react-router";

class AddDish extends Component {

    state = {
        redirect: false,
        dishNameInput: "",
        imgUrlInput: "",
        descriptionInput: "",
        priceInput: "",
        minutesInput: "",
        tagsInput: ""
    };

    handleDishNameInput = e =>
        this.setState({dishNameInput: e.target.value});

    handleImgUrl = e =>
        this.setState({imgUrlInput: e.target.value});

    handleDescriptionInput = e =>
        this.setState({descriptionInput: e.target.value});

    handlePriceInput = e =>
        this.setState({priceInput: e.target.value});

    handleMinutesInput = e =>
        this.setState({minutesInput: e.target.value});

    handleTagsInput = e =>
        this.setState({tagsInput: e.target.value});

    handleSubmitEvent = e => {
        e.preventDefault();
        const dish = {
            name: this.state.dishNameInput,
            imageUrl: this.state.imgUrlInput,
            description: this.state.descriptionInput,
            price: parseFloat(this.state.priceInput),
            timeInMinutes: parseInt(this.state.minutesInput),
            tags: this.parseStringToTags(this.state.tagsInput)
        };

        console.log(dish);
        this.createDish(dish);
    };

    parseStringToTags(tagsInput) {
        if (tagsInput.trim() !== "") {
            let strArr = tagsInput.split(",");
            let resArr = [];
            strArr.map((str) => {
                return resArr.push({name: str.trim()});
            });
            return resArr;
        }
        return [];
    }

    render() {
        return (
            <div>
                <MainLayout showNavigation={true} authenticationRequired={true}>
                    <FormLayout title={"Add a dish"} subtitle={"Please fill in the following details"}>
                        <AddDishForm handleDishNameInput={this.handleDishNameInput} handleImgUrl={this.handleImgUrl}
                                     handleDescriptionInput={this.handleDescriptionInput}
                                     handlePriceInput={this.handlePriceInput}
                                     handleMinutesInput={this.handleMinutesInput}
                                     handleSubmitEvent={this.handleSubmitEvent}
                                     handleTagsInput={this.handleTagsInput}/>
                        {this.state.redirect ?
                            <Redirect to={"/category/" + sessionStorage.getItem("category_id")}/> : ""}
                    </FormLayout>
                </MainLayout>
            </div>
        );
    }

    async createDish(dish) {
        const headers = new Headers();
        headers.append("Content-Type", "application/json");
        headers.append("Authorization", sessionStorage.getItem('jwt'));

        const categoryId = sessionStorage.getItem('category_id');

        const options = {
            method: "POST",
            headers,
            body: JSON.stringify(dish)
        };


        const url = Constants.url + "/category/" + categoryId + "/dish";
        const request = new Request(url, options);
        const response = await fetch(request);
        const statusCode = await response.status;

        console.log("response", response);
        console.log("statusCode", statusCode);

        if (statusCode !== 200) {
            alert("Error in creating dish");
        } else {
            alert("Dish has been created");
            this.setState({redirect: true});
        }
    }
}

export default AddDish;