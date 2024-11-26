import 'dart:convert';

import 'package:client/model/dish.dart';
import 'package:json_annotation/json_annotation.dart';
/// This following class is used to generate code to encode and decode
/// the category's data fields from Json.

/// This annotation is used to generate Json serialization by the code generator.
@JsonSerializable()
class Review {
  String comment;
  int id;
  int stars;

  Review(
      { this.id,
        this.comment,
        this.stars});


  /// This method declares support for serialization and is used to convert
  /// a category's instance into a map.
  Map<String, dynamic> toJson() =>
      {
        'id' : this.id,
        'comment': this.comment,
        'stars': this.stars
      };

  /// This method is used to convert a JSON map to a Category object
  factory Review.fromJson(Map<String, dynamic> json) {
    return Review(
        id: json['id'],
        comment: json['comment'],
        stars: json['stars']
    );
  }

  /// This method generates the appropriate content-type header for use
  /// with json responses.
  String jsonify() {
    String res = "{\"id\": " +
        id.toString() +
        ", \"comment\": " +
        comment.toString() +
        ","
            ", \"stars\": " +
        stars.toString() +
        "}";
    return res;
  }

}