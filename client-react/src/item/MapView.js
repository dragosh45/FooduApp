import React from "react";
import {GoogleMap} from "react-google-maps";
import withGoogleMap from "react-google-maps/src/withGoogleMap";
import withScriptjs from "react-google-maps/src/withScriptjs";

const MapView = withScriptjs(withGoogleMap((props) =>
    <div>
        <GoogleMap
            defaultZoom={8}
            defaultCenter={{lat: -34.397, lng: 150.644}}>
            {props.isMarkerShown}
        </GoogleMap>

    </div>));

export default MapView;