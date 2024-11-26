import 'dart:async';
import 'dart:convert';

import 'package:http/http.dart' as http;

class Methods {
  var value;
  Methods({this.value});

  void increaseValue(int inc) {
    value += inc;
  }

  void decreaseValue(int dec) {
    value -= dec;
  }
}

Future<Post> fetchPost(http.Client client) async {
  final response =
  await client.get('https://jsonplaceholder.typicode.com/posts/1');

  if (response.statusCode == 200) {
    // If the call to the server was successful, parse the JSON
    return Post.fromJson(json.decode(response.body));
  } else {
    // If that call was not successful, throw an error.
    throw Exception('Failed to load post');
  }
}

class Post {
  final int userId;
  final int id;
  final String title;
  final String body;

  Post({this.userId, this.id, this.title, this.body});

  factory Post.fromJson(Map<String, dynamic> json) {
    return Post(
      userId: json['userId'],
      id: json['id'],
      title: json['title'],
      body: json['body'],
    );
  }
}