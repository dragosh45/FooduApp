/*import 'package:client/order_handle.dart';*/
import 'package:client/model/connection.dart';
import 'package:flutter/material.dart';

/// The following classes displays the order placed by the customer to the
/// restaurant and give them the option to accept or decline the order.

class OrderState extends StatefulWidget{
  final Connection conn = Connection().getInstance();

  OrderState({@required conn});

  @override
  OrderViewing createState() {
    return OrderViewing(conn);
  }
}

class OrderViewing extends State<OrderState> {

  Connection _conn;

  OrderViewing(this._conn);

  String name = "Tim Cook";
  String id = "9083254";
  String userPhone = "07111111111";
  bool accepted;

  /*Future<dynamic> _createOrder; //OUTSIDE OF CURRENT SCOPE

  void _saveDetails() {
    Order order = new Order(name, id, userPhone,
          true);
    setState(() {
      _createOrder = _conn.addOrder("/order", order);
    });
  }

  @override
  void initState() {
    super.initState();
    _createOrder = null;
  }*/

  /// This method sets [accepted] to true when the order is approved
  /// by the restaurant.
  void acceptOrder() {
    setState(() {
      accepted = true;
    });
  }

  /// This function updates the value of [accepted] to false when
  /// the order is declined.
  void declineOrder() {
    setState(() {
      accepted = false;
    });
  }

  /// This widget displays the order details and the customer's phone number.
  @override
  Widget build(BuildContext context) {
    // building widget
    return new Scaffold(
      appBar: new AppBar(
        title: new Text('Order: ' + id)
      ),
      body: Center(
        child: Table(
          border: TableBorder.all(width: 2.0, color: Colors.black),
          children: [TableRow(children: [
            TableCell(
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceAround,
                children: <Widget>[
                new Text("Name"),
                new Text(name),
                ],
              ),
            )
          ]),
          TableRow(children: [
            TableCell(
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceAround,
                children: <Widget>[
                  new Text("Order ID"),
                  new Text(id),
                ],
              )
            )
          ]),
          TableRow(children: [
            TableCell(
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceAround,
                  children: <Widget>[
                    new Text("Customer Phone Number"),
                    new Text(userPhone),
                  ],
                )
            )
          ])]
        ),
      )
    );}

    /// This widget displays buttons to either accept or decline an order
    /// by the restaurant.
    Widget buildButtons(BuildContext context) {
    // building widget
    return new Scaffold(
    body: Row(
      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: <Widget>[
          new RaisedButton(
            padding: const EdgeInsets.all(8.0),
            textColor: Colors.white,
            color: Colors.blue,
            onPressed: acceptOrder,
            child: new Text("Accept"),
          ),
          new RaisedButton(
            onPressed: declineOrder,
            textColor: Colors.white,
            color: Colors.red,
            padding: const EdgeInsets.all(8.0),
            child: new Text("Decline"),
          )
        ]
      ),
    );}

}