import 'package:client/model/dish.dart';
import 'package:client/view/bottom_navigation_bar.dart';
import 'package:client/view/order_screen.dart';
import 'package:client/view/review_screen.dart';
import 'package:flutter/material.dart';

// This class displays the chosen dish details.
class DishScreen extends StatefulWidget {
  final Dish dish;

  DishScreen(this.dish);

  @override
  DishScreenState createState() => DishScreenState(this.dish);
}

class DishScreenState extends State<DishScreen> {
  final Dish dish;

  DishScreenState(this.dish);

  /// This widget shows an image of the dish, price, ingredients
  /// and a button to add the dish to the order.
  @override
  Widget build(BuildContext context) {
    final coursePrice = Container(
      padding: const EdgeInsets.all(10.0),
      decoration: BoxDecoration(
        border: Border.all(color: Colors.white),
        borderRadius: BorderRadius.circular(20.0),
      ),
      child: Text(
        dish.price.toStringAsFixed(2) + "£",
        style: TextStyle(color: Colors.white),
      ),
    );

    final topContentText = Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        SizedBox(height: 50.0),
        Icon(Icons.restaurant, color: Colors.white, size: 50.0),
        Container(width: 50.0, child: new Divider(color: Colors.deepOrange)),
        SizedBox(height: 20.0),
        Row(
          mainAxisAlignment: MainAxisAlignment.start,
          children: <Widget>[
            Expanded(
              flex: 5,
              child: Text(
                dish.name,
                style: TextStyle(color: Colors.white, fontSize: 30.0),
              ),
            ),
            Expanded(
              flex: 2,
              child: Padding(
                  padding: EdgeInsets.only(left: 5.0, right: 5.0),
                  child: coursePrice),
            )
          ],
        ),
      ],
    );

    final bottomContentText = Padding(
      padding: const EdgeInsets.only(left: 10.0),
      child: Text(
      dish.description,
      style: TextStyle(color: Colors.white, fontSize: 18.0),
      ));

    final bottomContentHeader = Text('Discription', style: TextStyle(fontSize: 25.0, color: Colors.white, fontWeight: FontWeight.bold),);

    final addButton = RaisedButton(
        shape: new RoundedRectangleBorder(borderRadius: new BorderRadius.circular(40.0)),
        onPressed: () {
          navigateToOrder();
        },
        color: Colors.deepOrange,
        child: Text(
          "Add to order",
          style: TextStyle(color: Colors.white),
        ),
      );

    final restaurantReview =
    Align(child: RaisedButton(
        shape: new RoundedRectangleBorder(borderRadius: new BorderRadius.circular(40.0)),
        onPressed: () {
          Navigator.push(context,
              MaterialPageRoute(builder: (context) => ReviewScreen(restaurantId: 2,)));
        },

        color: Colors.grey[300],
        child: Text(
          "Restaurant reviews",
          style: TextStyle(color: Colors.black),
        ),
      ),
      alignment: Alignment.bottomCenter,
    );

    final bottomContent = Container(
      width: MediaQuery.of(context).size.width,
      padding: EdgeInsets.all(30.0),
      child: Center(
        child: Row(
          children: <Widget>[Expanded(flex: 1, child: addButton), Padding(padding: const EdgeInsets.all(15.0)), Expanded(flex: 1,child: restaurantReview)],
        ),
      ),
    );

    final padding = Padding(padding: const EdgeInsets.all(5.0));

    final topContent = Stack(
      children: <Widget>[
        Container(
          padding: EdgeInsets.only(left: 10.0),
          height: MediaQuery.of(context).size.height * 0.5,
          decoration: BoxDecoration(
            image: DecorationImage(
                image: NetworkImage(dish.imageUrl), fit: BoxFit.cover,
            ),
          ),
        ),
        Container(
          height: MediaQuery.of(context).size.height * 0.5,
          padding: EdgeInsets.all(20.0),
          width: MediaQuery.of(context).size.width,
          decoration: BoxDecoration(color: Color.fromRGBO(58, 66, 86, 0.5)),
          child: Center(
            child: topContentText,
          ),
        ),
        Positioned(
          left: 8.0,
          top: 40.0,
          child: InkWell(onTap: () {
            Navigator.pop(context);
          },
            child: Icon(Icons.arrow_back, color: Colors.white),),

        )
      ],
    );

    return Container(
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
        body: Column(
          children: <Widget>[topContent, padding, bottomContentHeader, padding, bottomContentText, bottomContent],
        ),
        bottomNavigationBar: NavigationBar(),
      ),
    );
  }

  /// A navigator to add dish to the order and display order screen.
  dynamic navigateToOrder() {
    try {
      Route route = MaterialPageRoute(
          builder: (context) => OrderScreen(
              dishes: [this.dish], restaurantId: 2));
      return Navigator.push(context, route);
    } catch (e) {
      print(e.toString());
    }
  }
}