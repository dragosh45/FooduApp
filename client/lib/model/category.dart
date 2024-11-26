import 'dart:convert';

import 'package:client/model/dish.dart';
import 'package:json_annotation/json_annotation.dart';
/// This following class is used to generate code to encode and decode
/// the category's data fields from Json.

/// This annotation is used to generate Json serialization by the code generator.
@JsonSerializable()
class Category {
  String name;
  int id;
  List dishes;

  Category(
    {this.id,
    this.name,});


  /// This method declares support for serialization and is used to convert
  /// a category's instance into a map.
  Map<String, dynamic> toJson() =>
  {
    'id' : this.id,
    'name': this.name,
  };

  /// This method is used to convert a JSON map to a Category object
  factory Category.fromJson(Map<String, dynamic> json) {
    return Category(
        id: json['id'],
        name: json['name']);
  }

  /// This method generates the appropriate content-type header for use
  /// with json responses.
  String jsonify() {
    String res = "{\"id\": " +
        id.toString() +
        ", \"name\": " +
        name.toString() +
        ",";
    res += "\"dishes\": " + "[]" + "}";
    return res;
  }

  /// This method returns the Json representation of the dish's value.
  static String mapDishes(dishes) {
    String res = '';
    for (Dish dish in dishes) {
      res += jsonEncode(dish) + ", ";
    }
    return res;
  }

}