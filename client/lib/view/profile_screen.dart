import 'package:client/model/connection.dart';
import 'package:client/model/user.dart';
import 'package:client/view/bottom_navigation_bar.dart';
import 'package:client/view/orders_record.dart';
import 'package:flutter/material.dart';

class ProfilePage extends StatefulWidget{
  User user;
  @override
  ProfilePageState createState() => ProfilePageState(this.user);
}

class ProfilePageState extends State<ProfilePage>{

  User user;
  String givenName;
  String surname;
  String email;
  final Connection conn = Connection().getInstance();

  ProfilePageState(this.user);

  @override
  void initState() {
    super.initState();
    getUser().then((User newUser) async{
      setState(() {
        user = newUser;
      });
    });
  }

  Future<User> getUser()async{
    User newUser = await conn.getUserProfile();
    if(newUser == null){
      print('no user');
      return null;
    }

    givenName = newUser.givenNames;
    surname = newUser.surname;
    email = newUser.email;

    print('User is not null');
    return newUser;
  }

  @override
  Widget build(BuildContext context) {

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
        body: new Stack(
          children: <Widget>[
            ClipPath(
              child: Container(color: Colors.white),
              clipper: getClipper(),
            ),
            Positioned(
              width: MediaQuery.of(context).size.width,
              top: MediaQuery.of(context).size.height/4.5,
              child: Column(
                children: <Widget>[
                  Container(
                    width: 150.0,
                    height: 150.0,
                    decoration: BoxDecoration(
                      color: Colors.white,
                      image: DecorationImage(image: NetworkImage('http://chittagongit.com//images/default-user-icon/default-user-icon-8.jpg'),
                          fit: BoxFit.cover
                      ),
                      borderRadius: BorderRadius.all(Radius.circular(75.0)),
                      boxShadow: [
                        BoxShadow(blurRadius: 7.0, color: Colors.purple)
                      ]
                    )
                  ),
                  SizedBox(height: 50.0,),
                  Text(
                    '$givenName $surname',
                    style: TextStyle(
                      color: Colors.white,
                      fontSize: 30.0,
                      fontWeight: FontWeight.bold
                    ),
                  ),
                  SizedBox(height: 15.0,),
                  Text(
                    '$email',
                    style: TextStyle(
                        color: Colors.white,
                        fontSize: 20.0,
                        fontStyle: FontStyle.italic
                    ),
                  ),
                  SizedBox(height: 25.0,),
                  Container(
                    height: 30.0,
                    width: 95.0,
                    child: Material(
                      borderRadius: BorderRadius.circular(20.0),
                      shadowColor: Colors.grey,
                      color: Colors.deepOrange[600],
                      elevation: 7.0,
                      child: GestureDetector(
                        onTap: (){
                          Route route = MaterialPageRoute(builder: (context)
                          => OrderRecord());
                          Navigator.push(context, route);
                        },
                          child: Center(
                            child: Text('My Orders',
                            style: TextStyle(color: Colors.white),
                            ),
                          ),
                      ),
                    ),
                  ),
                ],
              ),
            )
          ],
        ),
        bottomNavigationBar: NavigationBar(),
      ),
    );
  }
}

class getClipper extends CustomClipper<Path>{

  @override
  Path getClip(Size size){
    var path = new Path();

    path. lineTo(0.0, size.height/1.9);
    path. lineTo(size.width + 125, 0.0);
    path. close();

    return path;
  }

  @override
  bool shouldReclip(CustomClipper<Path> addClipper){
      return true;
  }
}