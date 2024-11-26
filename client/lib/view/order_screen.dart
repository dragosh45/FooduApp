import 'dart:convert';

import 'package:client/controller/payment.dart';
import 'package:client/model/connection.dart';
import 'package:client/model/dish.dart';
import 'package:client/model/order.dart';
import 'package:client/view/bottom_navigation_bar.dart';
import 'package:client/view/category_screen.dart';
import 'package:client/view/dish_screen.dart';
import 'package:client/view/review_screen.dart';
import 'package:flutter/material.dart';
import 'package:flutter_picker/flutter_picker.dart';
import 'package:fluttertoast/fluttertoast.dart';

void main() {
  Dish dish = Dish(
      id: 1,
      description: "macaroni time",
      imageUrl: "https://i.imgur.com/eTuCPxM.jpg",
      name: "macaroni",
      price: 100.0,
      prepTime: 20,
      tags: [],
      ingredients: []);

  runApp(OrderScreen(dishes: [dish], restaurantId: 1));
}

/// This file displays the order placed by the customer and an option to
/// add more food to the current order before making the payment.
class OrderScreen extends StatefulWidget {
  final Connection conn = Connection().getInstance();
  final List<Dish> dishes;
  final int restaurantId;

  OrderScreen({@required this.dishes, @required this.restaurantId});

  @override
  OrderState createState() {
    return OrderState(this.conn, this.dishes, this.restaurantId);
  }
}

class OrderState extends State<OrderScreen> {
  final Connection conn;
  int orderNumber;
  List<Dish> dishes;
  final int restaurantId;
  int seats = 1;

  OrderState(this.conn, this.dishes, this.restaurantId);

  Future<dynamic> _authentication;

  @override
  void initState() {
    super.initState();
    _authentication = null;
  }

  void _postOrder() {
    setState(() {
      print("Dishes count: " + dishes.length.toString());

      Order order = Order(tip: 0, seats: seats, dishes: []);
      print("Order dishes count: " + order.dishes.length.toString());

      _authentication = conn
          .createOrder(
          '/restaurant/' + restaurantId.toString() + "/order", order)
          .then((res) {
        print("Order created!");
        Map<String, dynamic> bodyJsonDecoded = jsonDecode(res);
        print("Body id: " + bodyJsonDecoded['id'].toString());
        orderNumber = bodyJsonDecoded['id'];
        _addDishToOrder();
      });
    });
  }

  /// This widget shows a summary of the order and two buttons one to select
  /// the number of seats and the other to pay for the order.
  @override
  Widget build(BuildContext context) {
    ListTile makeListTile(Dish dish) => ListTile(
      contentPadding:
      EdgeInsets.symmetric(horizontal: 20.0, vertical: 10.0),
      leading: Container(
        padding: EdgeInsets.only(right: 12.0),
        decoration: BoxDecoration(
            border:
            Border(right: BorderSide(width: 1.0, color: Colors.black))),
        child: Image.network(dish.imageUrl, width: 50.0, height: 50.0),
      ),
      title: Text(
        dish.name,
        style: TextStyle(color: Colors.black, fontWeight: FontWeight.bold),
      ),
      subtitle: Row(
        children: <Widget>[
          Text(dish.price.toStringAsFixed(2) + "£",
              style: TextStyle(color: Colors.black))
        ],
      ),
      trailing:
      Icon(Icons.keyboard_arrow_right, color: Colors.black, size: 30.0),
      onTap: () {
        Navigator.push(context,
            MaterialPageRoute(builder: (context) => DishScreen(dish)));
      },

    );


    void _showToast(String msg) {
      Fluttertoast.showToast(
        msg: msg,
        toastLength: Toast.LENGTH_LONG,
        gravity: ToastGravity.BOTTOM,
        timeInSecForIos: 1,
      );
    }

    final _buyButton = Align(
        alignment: Alignment.bottomCenter,
        child: SizedBox(
          width: double.infinity,
          child: RaisedButton(
            onPressed: () {
              print("onPressed buyButton");
              _postOrder();
            },
            child: FutureBuilder(
                future: _authentication,
                builder: (context, snapshot) {
                  switch (snapshot.connectionState.toString()) {
                    case 'ConnectionState.none':
                      return Text('Checkout', textScaleFactor: 1.2,);
                    case 'ConnectionState.waiting':
                      return CircularProgressIndicator();
                    case 'ConnectionState.done':
                      if (snapshot.data == 200) {
                        return Text('Success!');
                      } else {
//                        _addAllDishesToOrder();
                        return Text('You are eating!');
                      }
                  }
                }),
            color: Colors.grey[300],
            textColor: Colors.black,
          ),
        ));

    final _appBar = AppBar(
      elevation: 0.0,
      backgroundColor: Colors.transparent,
      title: Text("Your Order"),
      leading: IconButton(
          icon: Icon(Icons.arrow_back),
          onPressed: () {
            Navigator.pop(context);
          }
      ),
      actions: <Widget>[
        FlatButton(
            child: Text(
              "Menu",
              style: TextStyle(color: Colors.white),
            ),
            color: Colors.transparent,
            onPressed: () {
              Route route = MaterialPageRoute(
                  builder: (context) => CategoryScreen(dishes: dishes, restaurantId: restaurantId,));
              Navigator.push(context, route);
            })
      ],
    );

    Card makeCard(Dish dish) => Card(
      elevation: 8.0,
      margin: EdgeInsets.symmetric(horizontal: 10.0, vertical: 6.0),
      child: Container(
        decoration: BoxDecoration(color: Colors.grey[350]),
        child: makeListTile(dish),
      ),
    );

    _showSeatPicker(BuildContext context) {
      return Picker(
          adapter: NumberPickerAdapter(data: [
            NumberPickerColumn(begin: 1, end: 8),
          ]),
          hideHeader: true,
          title: Text('Number of seats'),
          onConfirm: (Picker picker, List value) {
            print("Picked value: " + value.toString());
            print(picker.getSelectedValues()[0]);
            seats = picker.getSelectedValues()[0];
            _showToast(
                picker.getSelectedValues()[0].toString() + " seats selected",);
          }).showDialog(context);
    }

    final _pickerButton = Align(
      alignment: Alignment.bottomCenter,
      child: SizedBox(
        width: double.infinity,
        child: RaisedButton.icon(
            onPressed: () {
              _showSeatPicker(context);
            },
            color: Colors.deepOrange[600],
            textColor: Colors.white,
            icon: Icon(Icons.person_add),
            label: Text("Seats", textScaleFactor: 1.2,)),
      ),

    );
    Widget _bodyList() {
      return Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: <Widget>[
          Expanded(
            flex: 9,
            child: ListView.builder(
                scrollDirection: Axis.vertical,
                shrinkWrap: true,
                itemCount: dishes.length,
                itemBuilder: (BuildContext context, int index) {
                  return makeCard(dishes[index]);
                }),
          ),
           SizedBox(
             height: 5,
          width: double.infinity,
          ),
          _pickerButton,
          Expanded(
            flex: 1,
            child: _buyButton,
          ),
          Padding(padding: EdgeInsets.symmetric(vertical: 8.0)),
        ],
      );
    }

    return MaterialApp(
      title: "Foodu",
      debugShowCheckedModeBanner: false,
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
          appBar: _appBar,
          body: _bodyList(),
          bottomNavigationBar: NavigationBar(),
        ),
      ),
    );
  }

  void _addDishToOrder() {
    double totalPrice = 0;
    for (Dish d in dishes) {
      totalPrice += d.price;
      print("URL: " +
          "/order/" +
          orderNumber.toString() +
          "/dish/" +
          d.id.toString());
      conn
          .addDishToOrder(
          "/order/" + orderNumber.toString() + "/dish/" + d.id.toString())
          .then((code) {
        if (code == 200) {
          print("Successful creation! Price: " + totalPrice.toString());
          Route route = MaterialPageRoute(
              builder: (context) =>
                  PaymentScreen(amount: totalPrice, order: orderNumber, restaurantId: restaurantId, dishes: dishes, seats: seats));
          return Navigator.push(context, route);
        }
      });
    }
  }
}
