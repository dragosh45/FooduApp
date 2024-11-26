import React from 'react';
import PropTypes from "prop-types";

const RestaurantList = props =>
    <div>
      <div className="list-group">

        {props.restaurantList.map((item, key) => {
          return (
              <div key={key} className="list-group-item list-group-item-action">
                <div className="d-flex w-100 justify-content-between">
                  <h5 className="mt-2" data-id={key} onClick={props.redirectRestaurantAt.bind(this)}>{item.name}</h5>
                  <div>
                    {/*TODO: link to actual page*/}
                    <button className="btn btn-primary mr-1" data-id={key} onClick={props.acceptRestaurantAt.bind(this)}>
                      Accept
                    </button>
                    <button type="button" className="btn btn-danger" data-id={key} onClick={props.rejectRestaurantAt.bind(this)}>Reject
                    </button>
                  </div>
                </div>
              </div>
          );
        })}


      </div>
    </div>;

RestaurantList.propTypes = {
  restaurantList: PropTypes.array.isRequired,
  rejectRestaurantAt: PropTypes.func.isRequired,
  acceptRestaurantAt: PropTypes.func.isRequired,
  redirectRestaurantAt: PropTypes.func.isRequired
};

export default RestaurantList;