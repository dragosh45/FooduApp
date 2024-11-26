import 'package:client/model/category.dart';
import 'package:json_annotation/json_annotation.dart';

/// This following class is used to generate code to encode and decode
/// the dish's data fields from Json.

/// This annotation is used to generate Json serialization by the code generator.
@JsonSerializable()
class Dish {
  int id;
  String description;
  String imageUrl;
  String name;
  double price;
  int prepTime;
  List<dynamic> ingredients;
  List<dynamic> tags;
  Category category;

  Dish(
      {this.id,
      this.description,
        this.imageUrl,
      this.name,
      this.price,
      this.prepTime,
      this.ingredients,
      this.tags});


  /// This method declares support for serialization and is used to convert
  /// a dish's instance into a map.
  Map<String, dynamic> toJson() => {
        'id': this.id,
        'description': this.description,
    'imageUrl': this.imageUrl,
        'name': this.name,
        'price': this.price,
    'timeInMinutes': this.prepTime,
        'ingredients': this.ingredients,
        'tags': this.tags
      };

  /// This method is used to convert a JSON map to a Dish object
  factory Dish.fromJson(Map<String, dynamic> json) {
    return Dish(
        id: json['id'],
        description: json['description'],
        imageUrl: json['imageUrl'],
        name: json['name'],
        price: json['price'],
        prepTime: json['timeInMinutes'],
        ingredients: json['ingredients'],
        tags: json['tags']);
  }

  List<Dish> getDishes() {
    return [
      Dish(id: 1,
          description: "Description bla",
          imageUrl: "https://i.imgur.com/aqq6b05.png",
          name: "Pizza 1 MyPizza",
          price: 5.0,
          prepTime: 20),
      Dish(id: 2,
          description: "Description bla",
          imageUrl: "https://i.imgur.com/aqq6b05.png",
          name: "Pizza 2 MyPizza",
          price: 5.0,
          prepTime: 20),
      Dish(id: 3,
          description: "Description bla",
          imageUrl: "https://i.imgur.com/aqq6b05.png",
          name: "Pizza 3 MyPizza",
          price: 5.0,
          prepTime: 20),
      Dish(id: 4,
          description: "Description bla",
          imageUrl: "https://i.imgur.com/aqq6b05.png",
          name: "Pizza 4 MyPizza",
          price: 5.0,
          prepTime: 20),
//      Dish(1, "Description bla", "https://i.imgur.com/aqq6b05.png", "Pizza MyPizza", price: 5.0, prepTime: 20),
    ];
  }
}
