import 'package:client/controller/home.dart';
import 'package:client/controller/login.dart';
import 'package:client/controller/profile.dart';
import 'package:client/model/connection.dart';
import 'package:flutter/material.dart';

/// The main method includes the program's initial and other routes that will be
/// used to navigate to screens implemented in the application.

void main() {
  final Connection conn = Connection().getInstance();
  runApp(MaterialApp(
    debugShowCheckedModeBanner: false,
    title: 'Foodu',
    initialRoute: '/',
    routes: {
      '/': (context) => LoginScreen(conn: conn),
      '/profile': (context) => ProfileScreen(),
      '/home': (context) => HomeScreen(conn: conn),
    },
  ));
}
