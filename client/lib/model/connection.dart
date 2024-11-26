import 'dart:convert';

import 'package:client/model/category.dart';
import 'package:client/model/dish.dart';
import 'package:client/model/order.dart';
import 'package:client/model/tag.dart';
import 'package:client/model/review.dart';
import 'package:client/model/restaurant.dart';
import 'package:client/model/user.dart';
import 'package:http/http.dart' as http;

import 'dart:async';

///Connecting Client to the Server
///url is server url
///_Client is Client dart object
class Connection {
  String _token;

//  List<Dish> _dishesList;
  Map<String, String> headers = {"Content-Type": "application/json"};

  String url = 'https://foodu-app.herokuapp.com';
  static Connection instance;
  http.Client _newClientConnection;

  String get token => _token;
  //List<Dish> get dishesList => _dishesList;

  Connection getInstance() {
    if (instance == null) {
      instance = new Connection();
    }
    return instance;
  }

  Connection() {
    this._newClientConnection = this.getNewClient();
  }

  http.Client getNewClient() {
    http.Client client = null;
    try {
      client = new http.Client();
    } catch (ClientException) {
      ///TO DO: make proper exception ?
      print("Failed to create new client");
    }
    return client;
  }

  ///Method to throw Exception according to server response
  void _statusCodeChecker(int statusCode) {
    if (statusCode == 500) {
      throw new Exception("Server error, please try again later");
    } else if (statusCode == 400) {
      throw new Exception("Application error, try again");
    } else if (statusCode == 403) {
      throw new Exception("Access Denied");
    } else if (statusCode == 404) {
      throw new Exception("Magic 404 not found");
    } else if (statusCode != 200) {
      throw new Exception("Error authenticating user");
    }
  }

  ///Method to authenticate a user and setting the _token from body to headers
  ///also the method sets the role
  Future<dynamic> auth(
      String endpoint, String username, String password) async {
    Map<String, String> body = {'email': username, 'password': password};
    final response = await _newClientConnection.post(
        Uri.encodeFull(url + endpoint),
        headers: headers,
        body: jsonEncode(body));
    Map<String, dynamic> bodyJsonDecoded = jsonDecode(response.body);
    _token = bodyJsonDecoded['token'];
    headers['Authorization'] = _token;
    _statusCodeChecker(response.statusCode);

    return response.statusCode;
  }

  Future<dynamic> getAuthenticatedUser() async {
    final response =
    await _newClientConnection.get(url + "/user", headers: headers);
    Map<String, dynamic> bodyJsonDecoded = jsonDecode(response.body);
    print(bodyJsonDecoded);
    print("givenNames" + bodyJsonDecoded['givenNames']);
    print("surname" + bodyJsonDecoded['surname']);
    print("email" + bodyJsonDecoded['email']);
    return bodyJsonDecoded;
  }
  Future<User> getUserProfile() async{
    final response =
    await _newClientConnection.get(url + "/user/profile", headers: headers);
    Map<String, dynamic> bodyJsonDecoded = jsonDecode(response.body);
    print(bodyJsonDecoded);
    User user = User(bodyJsonDecoded['givenNames'],
        bodyJsonDecoded['surname'], '', bodyJsonDecoded['email']);
    return user;
  }

  Future<dynamic> confirmPaidOrder(int orderId) async {
    //TODO: to implement call to server
//    final response = await _newClientConnection.post(url + '/order/' + orderId + "");
  }

  ///Method to create a user
  Future<dynamic> createUser(String endpoint, User user) async {
    final response = await _newClientConnection.post(url + endpoint,
        headers: headers, body: jsonEncode(user));

    _statusCodeChecker(response.statusCode);

    return response.statusCode;
  }

  ///Method to create a restaurant
  Future<dynamic> createRestaurant(
      String endpoint, Restaurant restaurant) async {
    final response = await _newClientConnection.post(url + endpoint,
        headers: headers, body: jsonEncode(restaurant));

    _statusCodeChecker(response.statusCode);

    return response.statusCode;
  }

  ///Method to create a Dish
  Future<dynamic> createDish(String endpoint, Dish dish) async {
    final response = await _newClientConnection.post(url + endpoint,
        headers: headers, body: jsonEncode(dish));

    return response.statusCode;
  }

  Future<dynamic> addDishToOrder(String endpoint) async {
    final response =
    await _newClientConnection.post(url + endpoint, headers: headers);
    _statusCodeChecker(response.statusCode);
    print("statusCode: " + response.statusCode.toString());
    return response.statusCode;
  }

  ///Method to create an Order

  Future<dynamic> createOrder(String endpoint, Order order) async {
    print("Order: " + jsonEncode(order));
    final response = await _newClientConnection.post(url + endpoint,
        headers: headers, body: jsonEncode(order));
    _statusCodeChecker(response.statusCode);
    print("status code: " + response.statusCode.toString());
    var body = response.body;
    print("body " + body);
    return response.body;
  }

  ///Method to add an Order
  Future<dynamic> addOrder(String endpoint, Order order) async {
    final response = await _newClientConnection.post(url + endpoint,
        headers: headers, body: jsonEncode(order));

    _statusCodeChecker(response.statusCode);

    return response.statusCode;
  }

  Future<List<Dish>> getDishList(String endpoint, List<int> ids) async {
    Map<String, dynamic> body = {'ids': ids};
    final response = await _newClientConnection.post(
        Uri.encodeFull(url + endpoint),
        headers: headers,
        body: jsonEncode(body));
    _statusCodeChecker(response.statusCode);
    List<dynamic> responseList = jsonDecode(response.body);

    List<Dish> dishes = [];
    for (Map<String, dynamic> i in responseList) {
      dishes.add(Dish.fromJson(i));
    }
    return dishes;
  }

  Future<dynamic> createCategory(String endpoint, Category category) async {
    category.dishes = [];
    print("category: " + category.jsonify());

    final response = await _newClientConnection.post(url + endpoint,
        headers: headers, body: jsonEncode(category));
    _statusCodeChecker(response.statusCode);
    print("status code: " + response.statusCode.toString());
    return response;
  }

  Future<dynamic> createReview(String endpoint, Review review) async {
    print("review: " + jsonEncode(review));

    final response = await _newClientConnection.post(url + endpoint,
        headers: headers, body: jsonEncode(review));
    _statusCodeChecker(response.statusCode);
    print("status code: " + response.statusCode.toString());
    return response;
  }

  Future<List<Review>> getReviews(String endpoint, List<int> ids) async {
    Map<String, dynamic> body = {'ids': ids};
    final response = await _newClientConnection.get(
      Uri.encodeFull(url + endpoint),
      headers: headers,
    );
    _statusCodeChecker(response.statusCode);
    List<dynamic> responseList = jsonDecode(response.body);
    List<Review> review = [];
    for (Map<String, dynamic> i in responseList) {
      review.add(Review.fromJson(i));
    }
    print(responseList);
    return review;
  }

  Future<dynamic> updateOrder(String endpoint, Order order) async {
    print("order ---->: " + jsonEncode(order));

    final response = await _newClientConnection.put(url + endpoint,
        headers: headers, body: jsonEncode(order));
    _statusCodeChecker(response.statusCode);
    print("status code: " + response.statusCode.toString());
    return response;
  }

    ///method to get list of orders
    Future<List<Order>> getOrders(String endpoint) async{
      final response = await _newClientConnection.get(
          Uri.encodeFull(url + endpoint),
          headers: headers);
      _statusCodeChecker(response.statusCode);
      List<dynamic> responseList = jsonDecode(response.body);
      List<Order> orders = [];
      for (Map<String, dynamic> i in responseList){
        orders.add(Order.fromJson(i));
      }
      print(responseList);
      return orders;
    }


  Future<List<Category>> getCategories(String endpoint, List<int> ids) async {
    Map<String, dynamic> body = {'ids': ids};
    final response = await _newClientConnection.get(
      Uri.encodeFull(url + endpoint),
      headers: headers,
    );
    _statusCodeChecker(response.statusCode);
    List<dynamic> responseList = jsonDecode(response.body);
    List<Category> categories = [];
    for (Map<String, dynamic> i in responseList) {
      categories.add(Category.fromJson(i));
    }
    print(responseList);
    return categories;
  }

  Future<List<Dish>> getDishesFromCategory(
      String endpoint) async {
    //Map<String, dynamic> body = {'ids': category.id};
    final response = await _newClientConnection.get(
      Uri.encodeFull(url + endpoint),
      headers: headers,
    );
    _statusCodeChecker(response.statusCode);
    List<dynamic> responseList = jsonDecode(response.body);
    List<Dish> dishes = [];
    for (Map<String, dynamic> i in responseList) {
      dishes.add(Dish.fromJson(i));
    }
    print(dishes);
    return dishes;
  }

  Future<List<Dish>> getDishListFromTag(String endpoint, Tag tag) async {
    Map<String, dynamic> body = {'ids': tag.id};
    final response = await _newClientConnection.get(
      Uri.encodeFull(url + endpoint),
      headers: headers,
    );
    _statusCodeChecker(response.statusCode);
    List<dynamic> responseList = jsonDecode(response.body);

    List<Dish> dishes = [];
    for (Map<String, dynamic> i in responseList) {
      dishes.add(Dish.fromJson(i));
    }
    return dishes;
  }

  Future<List<Dish>> getDishesByTagName(
      String endpoint) async {
    final response = await _newClientConnection.get(
        Uri.encodeFull(url + endpoint),
        headers: headers,
    );
    _statusCodeChecker(response.statusCode);
    List<dynamic> responseList = jsonDecode(response.body);
    List<Dish> dishes = [];
    for (Map<String, dynamic> i in responseList) {
      dishes.add(Dish.fromJson(i));
    }
    print(dishes);
    return dishes;
  }

  Future<dynamic> addDishToCategory(String endpoint, Dish dish) async {
    final response = await _newClientConnection.post(url + endpoint,
        headers: headers, body: jsonEncode(dish));
    _statusCodeChecker(response.statusCode);
    print("status code: " + response.statusCode.toString());
    print(response.body);
    return response;
  }

  Future<dynamic> addTag(String endpoint, Tag tag) async {
    final response = await _newClientConnection.post(url + endpoint,
    headers: headers, body: jsonEncode(tag));
    print(response);
    _statusCodeChecker(response.statusCode);
    return response;
  }
  Future<dynamic> getTagById(String endpoint) async {
    final response = await _newClientConnection.get(
      url + endpoint,
      headers: headers,
    );
    dynamic tag = jsonDecode(response.body);
    print(tag.toString());
    _statusCodeChecker(response.statusCode);
    return tag;
  }
  Future<dynamic> addDishToTag(String endpoint, Dish dish) async {
    final response = await _newClientConnection.post(url + endpoint,
    headers: headers, body: jsonEncode(dish));
    print("status code: " + response.statusCode.toString());
    _statusCodeChecker(response.statusCode);
    return response;
  }
  Future<List<Tag>> getTags(String endpoint) async {
    final response = await _newClientConnection.get(url + endpoint, headers: headers,);
    List<dynamic> responseList = jsonDecode(response.body);
    List<Tag> tags = [];
    for (Map<String, dynamic> i in responseList) {
      tags.add(Tag.fromJson(i));
    }
    print("tags list printfrom connection:" +tags.toString());
    return tags;
  }
  Future<List<Tag>> getTagsByIds(String endpoint, List<int> ids) async {
    Map<String, dynamic> body = {'ids': ids};
    final response = await _newClientConnection.get(url + endpoint, headers: headers,);
    List<dynamic> responseList = jsonDecode(response.body);
    List<Tag> tags = [];
    for (Map<String, dynamic> i in responseList) {
      tags.add(Tag.fromJson(i));
    }
    print(tags.toString());
    _statusCodeChecker(response.statusCode);
    return tags;
  }
}
