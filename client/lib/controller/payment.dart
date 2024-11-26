import 'package:client/model/connection.dart';
import 'package:client/model/dish.dart';
import 'package:client/view/review_screen.dart';
import 'package:flutter/material.dart';
import 'package:flutter_paystack/flutter_paystack.dart';
import 'package:uuid/uuid.dart';

final String _publicKey = "pk_test_8f2ff53c3eea91c102777b6bd004c44503087194";

class PaymentScreen extends StatefulWidget {
  final double amount;
  final Connection conn = Connection().getInstance();
  final int order;
  final List<Dish> dishes;
  final int seats;
  final int restaurantId ;

  PaymentScreen({@required this.amount, this.order,this.dishes, this.seats, this.restaurantId});

  @override
  State<StatefulWidget> createState() {
    return PaymentScreenState(
        connection: conn, amount: amount, orderNumber: order, dishes: dishes, seats:seats, restaurantId: restaurantId);
  }
}

class PaymentScreenState extends State<PaymentScreen> {
  final _scaffoldKey = GlobalKey<ScaffoldState>();
  final _formKey = GlobalKey<FormState>();

  final _verticalSizeBox = const SizedBox(height: 20);
  final _horizontalSizeBox = const SizedBox(width: 10);

  final _uuid = Uuid();

  String _cardNumber;

  String _expiryMonth;

  String _cvv;

  String _expiryYear;

  Map<String, dynamic> jsonUserObject;

  final double amount;
  final Connection connection;
  final int orderNumber;
  final int restaurantId ;


  bool reviewButton = false;

  final List<Dish> dishes;
  final int seats;

  PaymentScreenState({this.connection, this.amount, this.orderNumber, this.restaurantId, this.dishes, this.seats,});

  @override
  void initState() {
    PaystackPlugin.initialize(publicKey: _publicKey);
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
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
            key: _scaffoldKey,
            appBar: AppBar(title: const Text("Pay your order"),
            backgroundColor: Colors.transparent, elevation: 0.0,),
            body: Container(
                padding: const EdgeInsets.all(20.0),
                child: Form(
                    key: _formKey,
                    child: SingleChildScrollView(
                      child: ListBody(
                        children: <Widget>[
                          Text("You will be charged",
                              style: TextStyle(
                                  fontSize: 20, fontWeight: FontWeight.bold, color: Colors.white.withOpacity(1))),
                          _verticalSizeBox,
                          Text(this.amount.toString(),
                              style: TextStyle(
                                  fontSize: 30, fontWeight: FontWeight.bold, color: Colors.white)),
                          TextFormField(
                              decoration: const InputDecoration(
                                  border: const UnderlineInputBorder(),
                                  labelText: "Card number",),
                              keyboardType: TextInputType.number,
                              initialValue: "408 408 408 408 408 1",
                              style: new TextStyle(color: Colors.white.withOpacity(1)),
                              onSaved: (String value) => _cardNumber = value),
                          _verticalSizeBox,
                          Row(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            crossAxisAlignment: CrossAxisAlignment.center,
                            children: <Widget>[
                              Expanded(
                                  child: TextFormField(
                                    decoration: const InputDecoration(
                                        border: const UnderlineInputBorder(),
                                        labelText: 'CVV'),
                                    keyboardType: TextInputType.number,
                                    initialValue: "408",
                                    style: new TextStyle(color: Colors.white.withOpacity(1)),
                                    onSaved: (String value) => _cvv = value,
                                  )),
                              Expanded(
                                  child: TextFormField(
                                    decoration: const InputDecoration(
                                        border: const UnderlineInputBorder(),
                                        labelText: 'Expiry Month'),
                                    initialValue: "12",
                                    keyboardType: TextInputType.number,
                                    style: new TextStyle(color: Colors.white.withOpacity(1)),
                                    onSaved: (String value) =>
                                    _expiryMonth = value,
                                  )),
                              Expanded(
                                  child: TextFormField(
                                    decoration: const InputDecoration(
                                        border: const UnderlineInputBorder(),
                                        labelText: 'Expiry Year'),
                                    initialValue: "23",
                                    keyboardType: TextInputType.number,
//                          keyboardType: TextInputType.number,
                                    style: new TextStyle(color: Colors.white.withOpacity(1)),
                                    onSaved: (String value) =>
                                    _expiryYear = value,
                                  ))
                            ],
                          ),
                          Padding(padding: const EdgeInsets.all(10.0)),
                          RaisedButton(
                            padding: EdgeInsets.all(5.0),
                            child: Text("Pay", style: new TextStyle(color: Colors.black.withOpacity(1)),),
                            color: Colors.grey[300],
                            onPressed: () {
                              _onPressPay();
                            },
                          ),
                          reviewButton ?
                          RaisedButton( padding: EdgeInsets.all(5.0),
                          child: Text("write a review", style: new TextStyle(color: Colors.black.withOpacity(1)),),
                          color: Colors.grey[300],
                          onPressed: () {
                            Navigator.push(context,
                                MaterialPageRoute(builder: (context) => SendReviewScreen(dishes: dishes,seats: seats,orderNumber: orderNumber,restaurantId: restaurantId,))
                            );},)
                          : new RaisedButton( padding: EdgeInsets.all(5.0),
                            child: Text("write a review", style: new TextStyle(color: Colors.black.withOpacity(1)),),
                            color: Colors.grey[300],
                            onPressed: null,
                          ),
                        ],
                      ),
                    )))),
      ),
    );
  }

  void _onPressPay() {
    this._formKey.currentState.save();
    _getChargingStarted();
    print("Card" + _getCardFromUI().toString());
  }

  void _getChargingStarted() {
    connection.getAuthenticatedUser().then((jsonObj) {
      print(jsonObj);
      this.jsonUserObject = jsonObj;
      _chargeCard(_getCardFromUI());
    });
  }

  void _chargeCard(PaymentCard card) {
    Charge charge = Charge();
    charge.card = card;
    charge
      ..amount = amount.toInt() * 100
      ..email = jsonUserObject['email']
      ..reference = _getReference()
      ..putCustomField('Charged From', 'Foodu flutter app');

    _performTransaction(charge);
  }

  PaymentCard _getCardFromUI() {
    return PaymentCard(
      number: _cardNumber,
      cvc: _cvv,
      expiryMonth: int.parse(_expiryMonth),
      expiryYear: int.parse(_expiryYear),
    );
  }

  String _getReference() {
    //TODO: include user id and restaurant in the transaction reference
    return _uuid.v1() + "id:" + jsonUserObject['id'].toString();
  }

  void _performTransaction(Charge charge) {
    PaystackPlugin.chargeCard(context,
        charge: charge,
        beforeValidate: (transaction) => _onBeforeValidate(transaction),
        onSuccess: (transaction) => _onSuccessfulTransaction(transaction),
        onError: (error, transaction) =>
            _onChargeCardError(error, transaction));
  }

  void _onBeforeValidate(Transaction transaction) {
    _updateStatus(transaction.reference, 'validating ...');
  }

  void _onSuccessfulTransaction(Transaction transaction) {
    _updateStatus(transaction.reference, "Success!");
    _confirmOrderPaid();
    setState(() {
      reviewButton = true;
    });
  }

  void _onChargeCardError(Object e, Transaction transaction) {
    _updateStatus(transaction.reference, e.toString());
  }

  void _updateStatus(String reference, String message) {
    _showMessage("Reference: $reference \n\ Response: $message",
        const Duration(seconds: 3));
  }

  void _showMessage(String message, Duration duration) {
    _scaffoldKey.currentState.showSnackBar(SnackBar(
        content: Text(message),
        duration: duration,
        action: SnackBarAction(
            label: 'Close',
            onPressed: () =>
                _scaffoldKey.currentState.removeCurrentSnackBar())));
  }

  void _confirmOrderPaid() {
    //TODO: to call the api
//    connection.confirmOrderPaid();
  }
}