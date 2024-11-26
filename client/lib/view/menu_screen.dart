import 'package:client/model/category.dart';
import 'package:client/model/connection.dart';
import 'package:client/model/dish.dart';
import 'package:client/view/bottom_navigation_bar.dart';
import 'package:client/view/order_screen.dart';
import 'package:flutter/material.dart';

void main() => runApp(MenuScreen(conn: null));

/// This file displays the menu, and the dishes added to the order from the menu.

class MenuScreen extends StatefulWidget {
  final Connection conn;
  List dishes;
  final int restaurantId;
  Category category;

  MenuScreen({this.conn, this.dishes, this.restaurantId, this.category});

  @override
  MenuState createState() {
    return MenuState(this.restaurantId, this.dishes.cast<Dish>(), this.category);
  }
}

class MenuState extends State<MenuScreen> {
  final Connection conn = Connection().getInstance();
  final int restaurantId;
  Category category;
  List dishes;
  List<Dish> dishesList = [];

  final _scaffoldKey = GlobalKey<ScaffoldState>();


  MenuState(this.restaurantId, this.dishes, this.category);

  @override
  void initState() {
    super.initState();
    dishesList.clear();
    getDishes(dishesList).then((List<Dish> newList) async {
      setState(() {
        dishesList = newList;
      });
    });
  }

  Future<List<Dish>> getDishes(List<Dish> list) async {
    List<Dish> newDishes = await conn.getDishesFromCategory(
        '/category/' + category.id.toString() + '/dishes');
    if (newDishes.length < 1) {
      return [];
    }
    for (int i = list.length; newDishes.length > 0; i++) {
      // Call Connection class to get a new dish
      list.add(newDishes[0]);
      newDishes.removeAt(0);
    }
    return list;
  }

  @override
  Widget build(BuildContext context) {
      return new Container(
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
          key: _scaffoldKey,
          backgroundColor: Colors.transparent,
          appBar: new AppBar(
            backgroundColor: Colors.transparent,
            elevation: 0.0,
            title: Text("Menu"),
            actions: <Widget>[
              IconButton(
                  icon: Icon(Icons.shopping_cart),
                  onPressed: () {
                    Route route = MaterialPageRoute(
                        builder: (context) =>
                            OrderScreen(
                                dishes: dishes, restaurantId: restaurantId));
                    Navigator.push(context, route);
                  })
            ],
          ),
          body: new ListView(
            children: new List.generate(
                dishesList.length,
                    (index) =>
                new Padding(
                  padding: const EdgeInsets.only(
                      left: 10.0, right: 10.0, bottom: 10.0),
                  child: new Card(
                    color: Colors.grey[350],
                    elevation: 15.0,
                    child: new Column(
                      children: [
                        dishesList[index].imageUrl == null ?
                        new Image.network(
                          dishesList[index].imageUrl = 'http://d24oe5tmwdgz7x.cloudfront.net/event/57026/banner/event_1510044183.jpg',
                          width: double.infinity,
                          height: 150.0,
                          fit: BoxFit.cover,
                        ) : new Image.network(
                          dishesList[index].imageUrl,
                          width: double.infinity,
                          height: 150.0,
                          fit: BoxFit.cover,
                        ),
                        new Row(
                          children: [
                            new Padding(
                              padding: const EdgeInsets.all(15.0),
                              child: new Container(
                                padding: const EdgeInsets.all(10.0),
                                decoration: new BoxDecoration(
                                  borderRadius: new BorderRadius.all(
                                      const Radius.circular(15.0)),
                                ),
                              ),
                            ),
                            new Expanded(
                              child: new Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  new Text(
                                    dishesList[index].name,
                                    style: const TextStyle(
                                      fontSize: 25.0,
                                      fontFamily: 'mermaid',
                                    ),
                                  ),
                                  new Text(
                                    dishesList[index].description,
                                    style: const TextStyle(
                                      fontSize: 16.0,
                                      fontFamily: 'bebas-neue',
                                      letterSpacing: 1.0,
                                    ),
                                  ),
                                ],
                              ),
                            ),
                            new Container(
                              width: 2.0,
                              height: 70.0,
                              decoration: new BoxDecoration(
                                gradient: new LinearGradient(
                                  colors: [
                                    Colors.white,
                                    Colors.white,
                                    const Color(0xFFAAAAAA),
                                  ],
                                  begin: Alignment.topCenter,
                                  end: Alignment.bottomCenter,
                                ),
                              ),
                            ),
                            new Padding(
                              padding: const EdgeInsets.only(
                                  left: 15.0, right: 15.0),
                              child: new Column(
                                children: [
                                  new Text(
                                    '${dishesList[index].prepTime} Min',
                                  ),
                                  new Text(
                                    '${dishesList[index].price}£',
                                  ),
                                  new IconButton(
                                    icon: Icon(Icons.add),
                                    color: Colors.red,
                                    onPressed: () {
                                      dishes.add(dishesList[index]);
                                      displaySnackBar(context);
                                      OrderScreen(dishes: dishes);
                                    },
                                  ),
                                  new Text(
                                    'Add to Order',
                                  ),
                                ],
                              ),
                            ),
                          ],
                        ),
                      ],
                    ),
                  ),
                )),
          ),
          bottomNavigationBar: NavigationBar(),
        ),
      );
    }

    void displaySnackBar(BuildContext context)
    {
      final snackBar = SnackBar(content: Text('Dish added', textScaleFactor: 1, style: new TextStyle(color: Colors.black.withOpacity(1)),), backgroundColor: Colors.lightGreen[100],);
      _scaffoldKey.currentState.showSnackBar(snackBar);

    }
}
