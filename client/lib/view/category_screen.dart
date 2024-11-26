import 'package:client/view/bottom_navigation_bar.dart';
import 'package:client/view/menu_screen.dart';
import 'package:flutter/material.dart';
import 'package:client/model/category.dart';
import 'package:client/model/connection.dart';
import 'package:client/view/menu_screen.dart';
import 'package:flutter/material.dart';

void main() => runApp(CategoryScreen(conn: null));

/// This file displays the categories, and routes to the menu screen.
class CategoryScreen extends StatefulWidget {
  final Connection conn;
  List dishes;
  final int restaurantId;

  CategoryScreen({this.conn, this.dishes, this.restaurantId});

  @override
  CategoryState createState() {
    return CategoryState(restaurantId, dishes);
  }
}

class CategoryState extends State<CategoryScreen> {
  final Connection conn = Connection().getInstance();
  final int restaurantId;
  List dishes;
  static List<Category> categoryList = [];
  Category cate = new Category(name: "i");

  Future<dynamic> _authentication;

  CategoryState(this.restaurantId, this.dishes);

  @override
  void initState() {
    super.initState();
    // categoryList.add(cate);
    _authentication = null;
    categoryList.clear();
    getCategories(categoryList).then((List<Category> newList) {
      setState(() {
        categoryList = newList;
      });
    });
  }

  Future<List<Category>> getCategories(List<Category> list) async {
    List<int> ids = [0];
    for (int j = 0; j < list.length; j++) {
      ids.add(list[j].id);
    }
    List<Category> categories =
        await conn.getCategories('/restaurant/'+restaurantId.toString()+'/category', ids);
    if (categories.length < 1) {
      return [];
    }
    for (int i = list.length; categories.length > 0; i++) {
      // Call Connection class to get a new dish
      list.add(categories[0]);
      categories.removeAt(0);
    }
    return list;
  }

  static List<Category> finalCate = List.unmodifiable(categoryList);

  void _postCategory() {
    setState(() {
      Category category = Category(id: 1, name: "drinks");

      _authentication =
          conn.createCategory('/restaurant/'+restaurantId.toString()+'/category', category).then((_) {
        print("category created!");
      });
    });
  }

  /// This widget displays the categories as a list.
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
        appBar: new AppBar(
          backgroundColor: Colors.transparent,
          title: Text("Menu"),
          elevation: 0.0,
        ),
        body:
        new GridView.builder(
        gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
        crossAxisCount: 3, mainAxisSpacing: 25.0),
        padding: const EdgeInsets.all(10.0),
          itemCount: categoryList.length,
          itemBuilder: (context, index) {
            return new GridTile(
              footer: new Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: <Widget>[
                    new Flexible(
                      fit: FlexFit.tight,
                      child: new SizedBox(
                        height: 16.0,
                        width:  100,
                        child: new Text(
                          categoryList[index].name,
                          maxLines: 2,
                          textAlign: TextAlign.center,
                          overflow: TextOverflow.ellipsis,
                          textScaleFactor: 1.5,
                          style: new TextStyle(color: Colors.white.withOpacity(1)),
                        ),
                      ),
                    )
                  ]),
              child: new Container(
               height: 150.0,
                child: new GestureDetector(
                  child: new Column(
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      Padding(padding: const EdgeInsets.all(5.0)),
                      new SizedBox(
                        height: 100.0,
                        width: 100.0,
                        child: new Row(
                          children: <Widget>[
                            new Stack(
                              children: <Widget>[
                                new SizedBox(
                                  child: new Container(
                                    child: new CircleAvatar(
                                      backgroundColor: Colors.transparent,
                                      radius: 40.0,
                                      child: new Icon(
                                          Icons.fastfood,
                                          size: 40.0,
                                          color: Colors.orange[400]
                                      ),
                                    ),
                                    padding: const EdgeInsets.only(
                                        left: 10.0, right: 10.0),
                                  ),
                                ),
                              ],
                            ),
                          ],
                        ),
                      ),
                    ],
                  ),
                  onTap: () {
                    Navigator.push(
                        context,
                        new MaterialPageRoute(
                          builder: (context) => MenuScreen(
                              dishes: dishes, category: categoryList[index],restaurantId: restaurantId,)));
                  },
                ),
              ),
            );
          },
        ),
        bottomNavigationBar: NavigationBar(),
      ),
    );
  }
}
