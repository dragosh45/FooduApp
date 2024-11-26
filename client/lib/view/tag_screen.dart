
import 'package:client/controller/cards.dart';
import 'package:client/model/dish.dart';
import 'package:client/view/bottom_navigation_bar.dart';
import 'package:flutter/material.dart';
import 'package:client/model/tag.dart';
import 'package:client/model/connection.dart';
import 'package:client/controller/cards.dart';

/// This file displays the tags, and routes to the menu screen.
class TagScreen extends StatefulWidget {
  final Connection conn;
  List dishes;
  final WidgetBuilder content;

  TagScreen({this.conn, this.dishes, this.content});

  @override
  TagState createState() {
    return TagState(dishes);
  }
}

class TagState extends State<TagScreen> {
  final Connection conn = Connection().getInstance();
  List dishes;
  static List<Tag> tagsList = [];
  static List<Dish> dishList = [];
  var tagController = new TextEditingController();

  TagState(this.dishes);

  @override
  void initState() {
    super.initState();
    getTags(110, tagsList).then((List<Tag> newList) {
      setState(() {
        tagsList = newList;
      });
    });
    getDishes(100, dishList).then((List<Dish> newList) {
      setState((){
        dishList = newList;
      });
    });
  }

  Future<List<Tag>> getTags(int len, List<Tag> list) async {
    List<int> ids = [0];
    for (int j = 0; j < list.length; j++) {
      ids.add(list[j].id);
    }
    List<Tag> tags =
    await conn.getTagsByIds('/tag', ids);
    if (tags.length < 0) {
      return [];
    }
    for (int i = list.length; i < len && tags.length > 0; i++) {
      list.add(tags[0]);
      tags.removeAt(0);
    }
    return list;
  }
  Future<List<Dish>> getDishes(int len, List<Dish> list) async {
    List<int> ids = [0];
    for (int j = 0; j < list.length; j++) {
      ids.add(list[j].id);
    }
    List<Dish> dishes =
    await conn.getDishList('/dish/getNewDish', ids);
    if (dishes.length < 0) {
      return [];
    }
    for (int i = list.length; i < len && dishes.length > 0; i++) {
      list.add(dishes[0]);
      dishes.removeAt(0);
    }
    return list;
  }
  List<String> topTags (List<Tag> tagsList) {
    var topNameMap = new Map<String,int>();
    List<String> topNames = [];
    for(int i=0; i<tagsList.length; i++) {
      topNameMap.putIfAbsent(tagsList[i].name, () => 0);
    }
    for(int i=0; i<tagsList.length; i++) {
      if(topNameMap.containsKey(tagsList[i].name)){
        topNameMap.update(tagsList[i].name,(int val) => ++val);
      }
    }
    for(String name in topNameMap.keys) {
        topNames.add(name); // display tags that appear more or equal then 2
    }
    return topNames;
  }


  /// This widget displays the tags as a list.
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
        resizeToAvoidBottomPadding: false,
        appBar: new AppBar(
          elevation: 0.0,
          backgroundColor: Colors.transparent,
          title: Text("Search"),
        ),
        body: Column(
          children: <Widget>[
            Padding(padding: const EdgeInsets.all(25.0)),
            new ListTile(
              title: new TextFormField(
                style: new TextStyle(color: Colors.white.withOpacity(1)),
                controller: this.tagController,
                decoration: new InputDecoration(
                  labelStyle: TextStyle(
                      color: Colors.white,
                      letterSpacing: 1.3
                  ),
                  labelText: 'Enter a keyword',
                  hintText: 'eg: pasta, burger',
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(25.0),
                  ),
                  contentPadding: const EdgeInsets.fromLTRB(20.0, 15.0, 20.0, 15.0),
                ),
              ),
            ),
            Padding(padding: const EdgeInsets.all(5.0)),
            new ListTile(
              title: new Material(
                color: Colors.transparent,
                elevation: 0.0,
                borderRadius: BorderRadius.circular(25.0),
                child: new MaterialButton(
                  height: 50.0,
                  onPressed: () async {
                    String query = this.tagController.text.trim();
                    if(query.isEmpty) {
                      null;
                    } else if((await tagExists(tagController.text))==true) {
                      navigate_from_keyword();
                    } else {
                      _showDialog(tagController.text);
                      tagController.clear();
                    }
                  },
                  child: Text(
                    'Search',
                    style: TextStyle(
                        fontSize: 22.0,
                        fontWeight: FontWeight.bold,
                        color: Colors.white
                    ),
                  ),
                ),
              ),
            ),
            Padding(padding: const EdgeInsets.all(15.0)),
            Expanded(
              child: GridView.builder(
                gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                    crossAxisCount: 3, mainAxisSpacing: 25.0),
                padding: const EdgeInsets.all(10.0),
                itemCount: topTags(tagsList).length,
                itemBuilder: (context, index) {
                  return GridTile(
//                    leading: Icon(Icons.fastfood),
//                    title: Text(topTags(tagsList)[index]),
//                    onTap: () {
//                      navigate_from_list(topTags(tagsList)[index]);
//                    }
                    footer: new Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: <Widget>[
                          new Flexible(
                            fit: FlexFit.tight,
                            child: new SizedBox(
                              height: 16.0,
                              width:  100,
                              child: new Text(
                                topTags(tagsList)[index],
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
                          navigate_from_list(topTags(tagsList)[index]);
                        },
                      ),
                    ),
                      );
                },
              ),
            ),
          ],
        ),
        bottomNavigationBar: NavigationBar(),
      ),
    );
  }

  void _showDialog(String tag) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: new Text("Sorry " + tag + " Not Found"),
          actions: <Widget>[
            new FlatButton(
              child: new Text("Ok"),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }
  dynamic navigate_from_list(String tagName) {
    Route route = MaterialPageRoute(
        builder: (context) => Cards(tagName: tagName));
    return Navigator.push(context, route);
  }
  /// This method routes to cards and passes the keyword searched for.
  dynamic navigate_from_keyword() {
    Route route = MaterialPageRoute(
        builder: (context) => Cards(tagName:this.tagController.text));
    return Navigator.push(context, route);
  }

  /// This method checks if a tag exists by querying the database, getting
  /// all tags and comparing them to keyword.
  Future<bool> tagExists(String keyword) async {
    List<Tag> tags = await conn.getTags('/tag');
    for(Tag tag in tags) {
      if(tag.name.compareTo(keyword) == 0) {
        return true;
      }
    }
    return false;
  }


}
