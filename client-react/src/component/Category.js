import React, {Component} from 'react';
import MainLayout from "../layout/MainLayout";
import DishList from "../list/DishList";
import Constants from "../util/Constants";
import {Redirect} from "react-router";

class Category extends Component {

    state = {
        redirect: false,
        categoryId: 0,
        categoryName: "",
        dishList: []
    };

    async componentDidMount() {
        const {id} = this.props.match.params;
        console.log("Mounting, Id", id);
        this.setState({categoryId: id});
        sessionStorage.setItem('category_id', id);
        await this.fetchCategoryAndDishes(id);
    }

    render() {
        return (
            <div>
                <MainLayout showNavigation={true} authenticationRequired={true}>
                    <div className="mt-5">

                        <div className="d-flex justify-content-between mb-5">
                            <h2>Category: {this.state.categoryName}</h2>
                            <button className="btn btn-primary" onClick={this.handleAddNewDishButton}>Add new dish</button>
                        </div>

                        <DishList listOfDishes={this.state.dishList}/>

                        {this.state.redirect ? <Redirect to="/add_dish"/> : ""}
                    </div>
                </MainLayout>
            </div>
        );
    }

    async fetchCategoryAndDishes(id) {
        const headers = new Headers();
        headers.append("Content-Type", "application/json");
        headers.append("Authorization", sessionStorage.getItem('jwt'));

        const options = {
            method: 'GET',
            headers
        };
        const url = Constants.url + "/category/" + id;

        const request = new Request(url, options);
        const response = await fetch(request);
        const statusCode = await response.status;
        const body = await response.json();

        console.log("Body", body);
        if (statusCode !== 200) {
            alert("Error fetching dishes for this category");
        } else {
            this.setState({dishList: body.dishes, categoryName: body.name});
        }

    }

    handleAddNewDishButton = e => {
        e.preventDefault();
        this.setState({redirect: true});
    }
}

export default Category;