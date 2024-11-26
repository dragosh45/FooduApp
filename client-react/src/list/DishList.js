import React from "react";
import PropTypes from 'prop-types';
import DishCard from  "../item/DishCard";

const DishList = props =>
    <div>
        <div className="card-columns">

            {props.listOfDishes.map((value, index) => {
                return <DishCard key={index} name={value.name} description={value.description} price={value.price} img={value.imageUrl} prepTime={value.timeInMinutes} tags={value.tags}/>
            })}
        </div>
    </div>;

DishList.propTypes = {
    listOfDishes: PropTypes.array.isRequired,
};
export default DishList;