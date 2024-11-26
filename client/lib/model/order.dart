import 'dart:convert';

import 'package:client/model/dish.dart';
import 'package:json_annotation/json_annotation.dart';

/// This following class is used to generate code to encode and decode
/// the order's data fields from Json.

/// This annotation is used to generate Json serialization by the code generator.
@JsonSerializable()
class Order {
  int tip;
  int seats;
  List<Dish> dishes;

  Order({this.tip, this.seats, this.dishes});
  /// This method declares support for serialization and is used to convert
  /// a dish's instance into a map.
  Map<String, dynamic> toJson() => {
        'tip': tip,
        'seats': seats,
    'dishes': dishes
      };

  /// This method generates the appropriate content-type header for use
  /// with json responses.
  String jsonify() {
    String res = "{\"tip\": " +
        tip.toString() +
        ", \"seats\": " +
        seats.toString() +
        ",";
    res += "\"dishes\": " + "[]" + "}";//+ Order.mapDishes(dishes) + "}";
    return res;
  }

  factory Order.fromJson(Map<String, dynamic> json){
    return Order(
      tip: json['tip'],
      seats: json['seats'],
      dishes: getDishes(json['dishes'])
    );
  }

  static List<Dish> getDishes(dishes){
    List<Dish> newDishes = [];
    for (Map<String, dynamic> i in dishes){
      newDishes.add(Dish.fromJson(i));
    }
    return newDishes;
  }

  /// This method returns the Json representation of the dish's value.
  static String mapDishes(dishes) {
    String res = '';
    for (Dish dish in dishes) {
      res += dish.toJson().toString() + ", ";
    }
    return res;
  }
}

//main() {
//  Order order = Order(10, 1, Dish().getDishes());
//  print(jsonEncode(order));
//}
