import React from 'react';
import PropTypes from "prop-types";
import NavLink from "react-router-dom/es/NavLink";

const moment = require('moment');

const AcceptedOrderList = props =>
    <div>
      <div className="list-group">
        {props.orderList.map((item, key) => {
          return (
              <div key={key} className="list-group-item list-group-item-action">
                <div className="d-flex w-100 justify-content-between">
                  <NavLink className="mr-1" to={"/view_order/" + item.id}>
                    <h5 className="mt-2" data-id={key}>{item.id}</h5>
                  </NavLink>
                  <h6>Time: {moment(item.serveTime).format(
                      'MM/D, h:mm a')}</h6>
                  <h6>Seats: {item.seats}</h6>

                  <button type="button" className="btn btn-danger"
                          data-id={key}
                          onClick={props.rejectOrderAt.bind(this)}>Cancel
                  </button>
                </div>
              </div>
          );
        })}
      </div>
    </div>;

const IncomingOrderList = props =>
    <div>
      <div className="list-group">
        {props.orderList.map((item, key) => {
          return (
              <div key={key} className="list-group-item list-group-item-action">
                <div className="d-flex w-100 justify-content-between">
                  <NavLink className="mr-1" to={"/view_order/" + item.id}>
                    <h5 className="mt-2" data-id={key}>{item.id}</h5>
                  </NavLink>
                  <h6>Time: {moment(item.serveTime).format(
                      'MM/D, h:mm a')}</h6>
                  <h6>Seats: {item.seats}</h6>
                  <div>
                    <button className="btn btn-primary mr-1" data-id={key}
                            onClick={props.acceptOrderAt.bind(this)}>
                      Accept
                    </button>
                    <button type="button" className="btn btn-danger"
                            data-id={key}
                            onClick={props.rejectOrderAt.bind(this)}>Reject
                    </button>
                  </div>
                </div>
              </div>
          );
        })}
      </div>
    </div>;

IncomingOrderList.propTypes = {
  orderList: PropTypes.array.isRequired,
  acceptOrderAt: PropTypes.func.isRequired,
  rejectOrderAt: PropTypes.func.isRequired
};

AcceptedOrderList.propTypes = {
  orderList: PropTypes.array.isRequired,
  rejectOrderAt: PropTypes.func.isRequired
};

export {IncomingOrderList, AcceptedOrderList};