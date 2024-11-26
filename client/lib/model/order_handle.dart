import 'package:json_annotation/json_annotation.dart';

/// This following class is used to generate code to encode and decode
/// the order's data fields from Json.

/// This annotation is used to generate Json serialization by the code generator.
@JsonSerializable()
class Order{
  String orderName;
  var orderID;
  var userPhone;
  bool accepted;

  Order(String orderName, String orderID, String userPhone, bool accepted ){
    this.orderName = orderName;
    this.orderID = orderID;
    this.userPhone = userPhone;
    this.accepted = accepted;
  }

  /// This method declares support for serialization and is used to convert
  /// an order's instance into a map.
  Map<String, dynamic> toJson() => {
    'orderName': this.orderName,
    'orderID': this.orderID,
    'userPhone': this.userPhone,
    'accepted': this.accepted,
  };

  ///The setters and getters below may be redundant but keeping
  /// in case otherwise.
  set setName(var orderName){
    this.orderName = orderName;
  }

  set setID(String orderID) {
    this.orderID = orderID;
  }

  set setPhone(String userPhone){
    this.userPhone = userPhone;
  }

  set setAccept(bool accepted){
    this.accepted = accepted;
  }

  String get retOrderName {
    return orderName;
  }

  String get retOrderID {
    return orderID;
  }

  String get retUserPhone {
    return userPhone;
  }

  bool get retBoolVal{
    return accepted;
  }

  @override
  String toString() {
    return 'Order: {orderName: $orderName, orderID: $orderID, userPhone: $userPhone, accepted: $accepted}';
  }
}