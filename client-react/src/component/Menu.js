import React, {Component} from 'react';
import MainLayout from "../layout/MainLayout";
import CategoryList from "../list/CategoryList";
import Constants from "../util/Constants";

class Menu extends Component {


    componentDidMount() {
        this.getCategories();
    }

    state = {
        categoryField: "",
        categories: []
    };

    handleFieldInput = e =>
        this.setState({categoryField: e.target.value});

    handleAddCategory = e => {
        e.preventDefault();

        if (this.state.categoryField === "") {
            alert("Field required");
            return;
        }

        this.addCategory(this.state.categoryField);
    };

    handleRemoveItemAt = e => {
        console.log("Index: " + e.target.dataset.id);
        console.log(this.state.categories[e.target.dataset.id]);
        this.removeCategory(this.state.categories[e.target.dataset.id].id);
    };

    render() {
        return (
            <MainLayout showNavigation={true} authenticationRequired={true}>
                <div className="mt-5">
                    <h2>Menu</h2>

                    <div className="input-group mb-2">
                        <input type="text" className="form-control col-8" placeholder="Category name ..."
                               aria-label="Recipient's username" aria-describedby="button-addon2"
                               onChange={this.handleFieldInput}/>

                        <div className="input-group-append">
                            <button className="btn btn-outline-primary" type="button"
                                    id="button-addon2" onClick={this.handleAddCategory}>Add
                            </button>
                        </div>
                    </div>

                    <CategoryList categoryList={this.state.categories} removeItemAt={this.handleRemoveItemAt}/>

                </div>
            </MainLayout>
        );
    }

    async addCategory(catName) {
        const headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', sessionStorage.getItem('jwt'));

        const req_body = {
            name: catName
        };

        const options = {
            method: 'POST',
            headers,
            body: JSON.stringify(req_body)
        };

        const endpoint = "/restaurant/" + sessionStorage.getItem("restaurant_id") + "/category";

        const request = new Request(Constants.url + endpoint, options);
        const response = await fetch(request);
        const status = await response.status;

        if (status !== 200) {
            alert("Error in creating category!");
            console.log("Status: " + status);
        } else {
            this.getCategories();
        }
    }

    async removeCategory(catId) {
        const headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', sessionStorage.getItem('jwt'));

        const options = {
            method: 'DELETE',
            headers,
        };

        const endpoint = "/category/" + catId;

        const request = new Request(Constants.url + endpoint, options);
        const response = await fetch(request);
        const status = await response.status;

        if (status !== 200) {
            alert("Error in deleting category!");
            console.log("Status: " + status);
        } else {
            this.getCategories();
        }
    }

    async getCategories() {
        const headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('Authorization', sessionStorage.getItem('jwt'));

        const options = {
            method: 'GET',
            headers
        };

        const endpoint = "/restaurant/" + sessionStorage.getItem("restaurant_id");

        const request = new Request(Constants.url + endpoint, options);
        const response = await fetch(request);
        const status = await response.status;
        const body = await response.json();

        if (status !== 200) {
            alert("Error in getting menu!");
            console.log("Status: " + status);
        } else {
            this.setState({categories: body.categories});
        }
    }


}

export default Menu;