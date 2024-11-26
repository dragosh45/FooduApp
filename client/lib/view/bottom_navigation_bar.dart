import 'package:client/controller/cards.dart';
import 'package:client/view/profile_screen.dart';
import 'package:client/view/tag_screen.dart';
import 'package:flutter/material.dart';

/// These classes displays bottom navigation bar.

class NavigationBar extends StatefulWidget {
  @override
  _NavigationBarState createState() => _NavigationBarState();
}

class _NavigationBarState extends State<NavigationBar> {
  @override
  Widget build(BuildContext context) {
    return Container(
      height: 55.0,
      child: BottomAppBar(
        color: Colors.transparent,
        elevation: 0.0,
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: <Widget>[
            IconButton(
              icon: Icon(Icons.fastfood, color: Colors.white),
              onPressed: () {
                Route route = MaterialPageRoute(
                    builder: (context) => new Cards(tagName: null,));
                Navigator.push(context, route);
              },
            ),
            IconButton(
              icon: Icon(Icons.search, color: Colors.white),
              onPressed: () {Route route = MaterialPageRoute(
                  builder: (context) => new TagScreen());
              Navigator.push(context, route);},
            ),
            IconButton(
              icon: Icon(Icons.person, color: Colors.white),
              onPressed: () {
                Route route = MaterialPageRoute(
                  builder: (context) => new ProfilePage());
              Navigator.push(context, route);
              },
            ),
          ],
        ),
      ),
    );
  }
}
