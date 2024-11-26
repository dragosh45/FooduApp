import 'package:flutter/material.dart';

void main() => runApp(CreateProfile());

class CreateProfile extends StatelessWidget {
  @override
  final PrimaryColor = const Color(0xFFC62828);
  Widget build(BuildContext context) {
    return MaterialApp(
        title: 'Create Profile',
      theme: ThemeData(
        primaryColor: PrimaryColor,
      ),
        home: Scaffold(
          appBar: AppBar(
            title: Text('Getting Started'),

          ),
          backgroundColor: PrimaryColor,//Colors.red,
          body: Center(
            //child: Text('Hello World'),
            //child: Text(wordPair.asPascalCase),
            child: Create(),
          ),
        ),
          //home: Create()
    );
  }
}

class Create extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => Profile();
}

class Profile extends State<Create> {

  final username = TextEditingController();
  final password = TextEditingController();
  final location = TextEditingController();
  final number = TextEditingController();
  final userKey = GlobalKey<FormState>();
  String result = "";

  bool isEmpty(TextEditingController txt)
  {
    if(userKey.currentState.validate())
      return txt == null;
    return false;
  }


  /*Widget check() {
    return ListTile(
      title: Text(
        "create Profile",
      ),
      trailing: new Icon(
        !isEmpty() ? Icons.check : null,
        color: Colors.lightGreen,
      ),
    );
  }*/

  @override
  Widget build(BuildContext context) {
    return new Scaffold(body:Form(key: userKey, child:Column(mainAxisAlignment: MainAxisAlignment.center, children: <Widget>[
      TextFormField(
          controller: username,

          validator: (value){
            if(value.isEmpty)
                return "please enter your username";
          },
          decoration: InputDecoration(hintText: "rhul",
              labelText: "Username"
              //, border: OutlineInputBorder()
              ),
          textCapitalization: TextCapitalization.words,

          keyboardType: TextInputType.text),

      TextFormField(
          controller: password,
          validator: (value){
            if(value.isEmpty)
              return "please enter your password";
          },
          decoration: InputDecoration(hintText: "Fo0d!",
              labelText: "password"
              //, border: OutlineInputBorder()
          ),
          obscureText: true,
          keyboardType: TextInputType.text),

      TextFormField(
          controller: location,
          validator: (value){
            if(value.isEmpty)
              return "please enter your location";
          },
          decoration: InputDecoration(
            //prefixIcon: location != null ? Icon(Icons.check, color: Colors.lightGreen)
                 // : Icon(Icons.clear, color: Colors.red),
              hintText: "Royal Holloway, Egham, TW200EX",
              labelText: "Location"
              //border: OutlineInputBorder()
          ),
          textCapitalization: TextCapitalization.words,
          keyboardType: TextInputType.text),

      TextFormField(
          controller: number,
          validator: (value){
            if(value.isEmpty)
              return "please enter your phone number";
          },
          decoration: InputDecoration(hintText: "0000000000",
              labelText: "Phone Number"
            //, border: OutlineInputBorder()
          ),
          textCapitalization: TextCapitalization.words,
          keyboardType: TextInputType.text),

      Row(mainAxisAlignment: MainAxisAlignment.center,children: <Widget>[
        RaisedButton(onPressed: createProfile, child: Text("Create Profile"),),


      ], ),
      //Text(result, style:TextStyle(fontSize: 25.0),)
    ],
    )));
  }

  void createProfile() {
    if(!isEmpty(username) && (password != null) && (location != null)
        && (number!= null)) {
      result = "Profile created";
      //return true;
    }
    //return false;
  }
}