import 'package:client/model/connection.dart';
import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';

import 'package:client/model/user.dart';

/// This file displays a screen for a user to create a profile
/// by inserting their name, password and email address, also saves their details
/// and notifies the user if there is an error in creating their profile
/// by showing an error message.

void main() => runApp(MaterialApp(home: ProfileScreen(), debugShowCheckedModeBanner: false,));

class ProfileScreen extends StatefulWidget {
  @override
  ProfileFormState createState() {
    return ProfileFormState();
  }
}

class ProfileFormState extends State<ProfileScreen> {
  TextEditingController firstNameController = TextEditingController();
  TextEditingController secondNameController = TextEditingController();
  TextEditingController passWordController = TextEditingController();
  TextEditingController retypePasswordController = TextEditingController();
  TextEditingController eMailController = TextEditingController();

  String givenNames = "a";
  String surname = "";
  String password = "";
  String retypePass = "";
  String mail = "";

  Connection connection =
      Connection().getInstance();
  Future<dynamic> _update;

  @override
  void initState() {
    super.initState();
    _update = null;
  }

  /// Creates a new user object with given details and calls method
  /// createUser in connection class.
  void _saveDetails() {
    User newUser = User(givenNames, surname, password, mail);

    setState(() {
      _update = connection.createUser("/user/sign-up", newUser);
    });
  }

  /// This widget displays a list view where the user could
  /// enter their details, save it and notify the user when their profile
  /// has been created.
  Widget build(BuildContext context) {
    // building widget
    return Container(
      decoration: BoxDecoration(
          gradient: new LinearGradient(
            begin: FractionalOffset.topLeft,
            end: FractionalOffset.bottomRight, // 10% of the width, so there are ten blinds.
            colors: [const Color(0xFF915FB5), const Color(0xFFCA436B)], // whitish to gray
            stops: [0.0,1.0],
            tileMode: TileMode.clamp, // repeats the gradient over the canvas
          )
      ),
      child: new Scaffold(
        backgroundColor: Colors.transparent,
        appBar: new AppBar(
          elevation: 0.0,
          backgroundColor: Colors.transparent,
          title: new Text('Sign up'),
        ),
        body: ListView(
            // new ListTitle Widget for each field
            children: <Widget>[
              Padding(padding: const EdgeInsets.all(10.0)),
              new ListTile(
                leading: const Icon(Icons.person, color: Colors.white),
                title: new TextField(
                  cursorColor: Colors.white,
                  decoration: new InputDecoration(
                    hintText: "First-Name",
                  ),
                  style: new TextStyle(color: Colors.white),
                  controller: firstNameController,
                  onChanged: (String text) {
                    setState(() {
                      this.givenNames = firstNameController.text;
                    });
                  },
                  onSubmitted: (String text) {
                    this.givenNames = firstNameController.text;
                    print(givenNames);
                    //firstNameController.clear();
                  },
                ),
              ),
              Padding(padding: const EdgeInsets.all(10.0)),
              new ListTile(
                  leading: const Icon(Icons.person, color: Colors.white),
                  title: new TextField(
                    cursorColor: Colors.white,
                    decoration: new InputDecoration(
                      hintText: "Second-Name",
                    ),
                    style: new TextStyle(color: Colors.white),
                    controller: secondNameController,
                    onChanged: (String text) {
                      setState(() {
                        surname = secondNameController.text;
                      });
                    },
                    onSubmitted: (String text) {
                      surname = secondNameController.text;
                      print(surname);
                      //secondNameController.clear();
                    },
                  )),
              Padding(padding: const EdgeInsets.all(10.0)),
              new ListTile(
                  leading: const Icon(Icons.alternate_email, color: Colors.white),
                  title: new TextField(
                    cursorColor: Colors.white,
                    decoration: new InputDecoration(
                      hintText: "Email",
                    ),
                    style: new TextStyle(color: Colors.white),
                    controller: eMailController,
                    onChanged: (String text) {
                      setState(() {
                        this.mail = eMailController.text;
                      });
                    },
                    onSubmitted: (String text) {
                      mail = eMailController.text;
                      print(mail);
                    },
                  )),
              Padding(padding: const EdgeInsets.all(10.0)),
              new ListTile(
                  leading: const Icon(Icons.lock_outline, color: Colors.white),
                  title: new TextField(
                    cursorColor: Colors.white,
                    decoration: new InputDecoration(
                      hintText: "Password",
                    ),
                    obscureText: true,
                    style: new TextStyle(color: Colors.white),
                    controller: passWordController,
                    onChanged: (String text) {
                      setState(() {
                        this.password = passWordController.text;
                      });
                    },
                    onSubmitted: (String text) {
                      password = passWordController.text;
                      print(password);
                    },
                  )),
              Padding(padding: const EdgeInsets.all(10.0)),
              new ListTile(
                  leading: const Icon(Icons.lock_outline, color: Colors.white),
                  title: new TextField(
                    cursorColor: Colors.white,
                    decoration: new InputDecoration(
                      hintText: "Re-Type Password",
                    ),
                    style: new TextStyle(color: Colors.white),
                    obscureText: true,
                    controller: retypePasswordController,
                    onChanged: (String text) {
                      setState(() {
                        this.retypePass = retypePasswordController.text;
                      });
                    },
                    onSubmitted: (String text) {
                      retypePass = retypePasswordController.text;
                      print(retypePass);
                    },
                  )),
              Padding(padding: const EdgeInsets.all(15.0)),
              FutureBuilder(
                future: _update,
                builder: (context, snapshot) {
                  print(snapshot.connectionState.toString());
                  switch (snapshot.connectionState.toString()) {
                    case 'ConnectionState.none':
                      return new IconButton(tooltip: 'Save', iconSize: 50.0, color: Colors.white, icon: const Icon(Icons.save),
                          onPressed: _saveDetails);
                    case 'ConnectionState.waiting':
                      return Container(
                          child: Center(
                            child: Column(
                                crossAxisAlignment: CrossAxisAlignment.center,
                                children: <Widget>[
                            SizedBox(
                            child: CircularProgressIndicator(),
                            height: 35.0,
                            width: 35.0,
                          ),]),));
                    case 'ConnectionState.done':
                      if (snapshot.data == 200) {
                        Navigator.pop(context);
                        return Icon(Icons.check, color: Colors.green);
                      } else {
                        Fluttertoast.showToast(
                            msg: "Error! Sign up unsuccessful!",
                            toastLength: Toast.LENGTH_SHORT,
                            gravity: ToastGravity.BOTTOM,
                            timeInSecForIos: 1,
                            backgroundColor: Colors.red,
                            textColor: Colors.white,
                            fontSize: 16.0);
                        return Icon(Icons.error, color: Colors.red, size: 50.0,);
                      }
                  }
                },
              )
            ]),
      ),
    );
  }
}
