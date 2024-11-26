import React from 'react';
import PropTypes from "prop-types";
import NavLink from "react-router-dom/es/NavLink";

const CategoryList = props =>
    <div>
        <div className="list-group">

            {props.categoryList.map((item, key) => {
                return (
                    <div key={key} className="list-group-item list-group-item-action">
                        <div className="d-flex w-100 justify-content-between">
                            <h5 className="mt-2">{item.name}</h5>
                            <div>
                                {/*TODO: link to actual page*/}
                                <NavLink className="btn btn-primary mr-1" to={"/category/" + item.id} >
                                    Open
                                </NavLink>
                                <button type="button" className="btn btn-danger" data-id={key}
                                        onClick={props.removeItemAt.bind(this)}>Remove
                                </button>
                            </div>
                        </div>
                    </div>
                );
            })}


        </div>
    </div>;

CategoryList.propTypes = {
    categoryList: PropTypes.array.isRequired,
    removeItemAt: PropTypes.func.isRequired
};

export default CategoryList;