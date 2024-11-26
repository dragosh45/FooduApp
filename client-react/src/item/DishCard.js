import React from "react";
import PropTypes from "prop-types";

const DishCard = props =>
    <div>
        <div className="card card-width">
            <img className="card-img-top" src={props.img} alt="Not loading"/>
            <div className="card-body">
                <h5 className="card-title">{props.name}</h5>
                <p className="card-text text-info">{props.description}</p>
                <p className="card-text"><strong>Preparation time: </strong>{props.prepTime + " minutes"} </p>
                <p className="card-text"><strong>Price: </strong>{props.price} </p>
                <p className="card-text"><strong>Tags: </strong>
                    {props.tags.map((tag) => {
                        return <button className="btn btn-warning m-1">{tag.name}</button>;
                    })}
                </p>

                {/*<a href="#" className="btn btn-primary">Go somewhere</a>*/}
            </div>
        </div>

    </div>;

DishCard.propTypes = {
    name: PropTypes.string.isRequired,
    description: PropTypes.string.isRequired,
    price: PropTypes.number.isRequired,
    img: PropTypes.string.isRequired,
    prepTime: PropTypes.number.isRequired,
    tags: PropTypes.array.isRequired
};

export default DishCard;