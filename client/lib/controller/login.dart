import 'package:client/model/connection.dart';
import 'package:flutter/material.dart';

/// This file displays a screen for the user to enter their credential's
/// to log into the application.
class LoginScreen extends StatefulWidget {
  Connection conn = new Connection().getInstance();

  LoginScreen({@required this.conn});

  @override
  LoginFormState createState() {
    return LoginFormState(conn);
  }
}

class LoginFormState extends State<LoginScreen> {
  final usernameController = TextEditingController();
  final passwordController = TextEditingController();

  final Connection conn;

  LoginFormState(this.conn);

  Future<dynamic> _authentication;

  @override
  void initState() {
    super.initState();
    _authentication = null;
  }

  /// This function sends the username and password provided to connection class
  /// to authenticate the user's details and navigates to the home screen.
  void _logIn() {
    setState(() {
      _authentication = conn
          .auth('/auth', usernameController.text, passwordController.text)
          .then((_) {
        Navigator.pushNamed(
          context,
          '/home',
        );
      });
    });
  }

  Widget formField(String hintText, TextEditingController controller,
      {hideText = false}) {
    return TextFormField(
      obscureText: hideText,
      decoration: InputDecoration(
        border: OutlineInputBorder(
          borderRadius: BorderRadius.circular(25.0),
        ),
        hintText: hintText,
      ),
      controller: controller,
    );
  }

  /// This widget displays text fields for the user to enter their details and
  /// shows a message if the details are invalid.
  Widget form(BuildContext context) {
    return ListView(
      shrinkWrap: false,
      children: <Widget>[
        Padding(padding: const EdgeInsets.all(15.0)),
        Container(
          height: MediaQuery.of(context).size.height/4,
          width: 110.0,
          decoration: BoxDecoration(
            image: DecorationImage(
                image: AssetImage('assets/graphics/logo.png'),
                fit: BoxFit.scaleDown,

            ),
          ),
        ),
        Padding(padding: const EdgeInsets.all(5.0)),
        Text(
          'Foodu',
          textAlign: TextAlign.center,
          overflow: TextOverflow.ellipsis,
          style: TextStyle(fontSize: 30.0, color: Colors.black),
        ),
        Container(
        padding: EdgeInsets.all(20.0),
        child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
          Tooltip(
            child: formField('Username', usernameController),
            message: 'Username',
          ),
          Padding(padding: const EdgeInsets.all(5.0)),
          Tooltip(
            child: formField('Password', passwordController, hideText: true),
            message: 'Password',
          ),
          Padding(padding: const EdgeInsets.all(10.0)),
          SizedBox(
            height: 40.0,
            width: MediaQuery.of(context).size.width/2.5,
            child: Tooltip(
              child: RaisedButton(
                  textColor: Colors.white,
                  color: Colors.deepOrange[600],
                  shape: new RoundedRectangleBorder(borderRadius: new BorderRadius.circular(5.0)),
                  onPressed: _logIn,
                  child: FutureBuilder(
                      future: _authentication,
                      builder: (context, snapshot) {
                        switch (snapshot.connectionState.toString()) {
                          case 'ConnectionState.none':
                            return Text('Login');
                          case 'ConnectionState.waiting':
                            return CircularProgressIndicator();
                          case 'ConnectionState.done':
                            if (snapshot.data == 200) {
                              return Text('Success!');
                            } else {
                              return Text('Invalid Login!');
                            }
                        }
                      })),
              message: 'Login',
            ),
          ),
        ]),
      ),
    ]);
  }

  Widget _signup() {
    return Row(
      mainAxisAlignment: MainAxisAlignment.center,
      children: <Widget>[
        Text(
          'new to Foodu?',
          textAlign: TextAlign.center,
          overflow: TextOverflow.ellipsis,
          style: TextStyle(fontSize: 15.0, color: Colors.black),
        ),
        Tooltip(
          child: FlatButton(
              color: Colors.transparent,
              onPressed: () => Navigator.pushNamed(context, '/profile'),
              child: Text('Sign up', textAlign: TextAlign.center, overflow: TextOverflow.ellipsis, style: TextStyle(fontSize: 15.0, decoration: TextDecoration.underline ,color: Colors.deepOrange))),
          message: 'Sign up',
        ),
      ],
    );
  }
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(child: form(context)),
      bottomNavigationBar: _signup(),
    );
  }
}
