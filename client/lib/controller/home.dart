import 'package:client/controller/cards.dart';
import 'package:client/model/connection.dart';
import 'package:flutter/material.dart';

/// This class handles the access control and based on user roles
/// it displays an admin's screen, owner's screen or a user's screen.

class HomeScreen extends StatelessWidget {
  Connection conn = Connection().getInstance();
  HomeScreen({this.conn});

  /// This widget controls building the specific screen
  /// by calling each role's class.
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "Foodu",
      home: Container(
        decoration: BoxDecoration(
            gradient: new LinearGradient(
              begin: FractionalOffset.topLeft,
              end: FractionalOffset.bottomRight, // 10% of the width, so there are ten blinds.
              colors: [const Color(0xFF915FB5), const Color(0xFFCA436B)], // whitish to gray
              stops: [0.0,1.0],
              tileMode: TileMode.clamp, // repeats the gradient over the canvas
            )
        ),
        child: Scaffold(
          backgroundColor: Colors.transparent,
          body: Center(
            child: new Container(
              child: Column(
                children: <Widget>[
                  Padding(padding: const EdgeInsets.all(20.0)),
                  Container(
                    height: MediaQuery.of(context).size.height/4,
                    width: 110.0,
                    decoration: BoxDecoration(
                      image: DecorationImage(
                        image: AssetImage('assets/graphics/logo.png'),
                        fit: BoxFit.scaleDown,

                      ),
                    ),
                  ),
                  Padding(padding: const EdgeInsets.all(30.0)),
                  Text('Welcome to Foodu', textAlign: TextAlign.center, style: TextStyle(fontSize: 45.0 ,color: Colors.white)),
                  Padding(padding: const EdgeInsets.all(10.0)),
                  SizedBox(
                    width: MediaQuery.of(context).size.width/1.5,
                    height: MediaQuery.of(context).size.height/5,
                    child: Text('if you like a dish swipe right to view the details or left to see more', textAlign: TextAlign.center, style: TextStyle(fontSize: 25.0 ,color: Colors.white)),
                  ),
                  Padding(padding: const EdgeInsets.all(20.0)),
                  RaisedButton(
                      textColor: Colors.white,
                      color: Colors.deepOrange[600],
                      shape: new RoundedRectangleBorder(borderRadius: new BorderRadius.circular(5.0)),
                      onPressed: () {
                        Route route = MaterialPageRoute(
                            builder: (context) => Cards(tagName: null));
                        return Navigator.push(context, route);
                      },
                      child: Text('Got it', textAlign: TextAlign.center, style: TextStyle(fontSize: 20.0, color: Colors.white))),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}
