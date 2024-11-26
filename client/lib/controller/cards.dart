import 'dart:convert';
import 'dart:io';
import 'dart:math';

import 'package:client/model/connection.dart';
import 'package:client/model/dish.dart';
import 'package:client/view/bottom_navigation_bar.dart';
import 'package:client/view/dish_screen.dart';
import 'package:cryptoutils/cryptoutils.dart';
import 'package:flutter/material.dart';
import 'package:flutter_advanced_networkimage/provider.dart';

// This file controls the images that are displayed for the user to swipe through
// and navigates to the desired dish page when the user swipes right.

class Cards extends StatefulWidget {
  String tagName;
  List dishesFromTag;
  Cards({this.tagName});
  @override
  _CardsState createState() {
    return _CardsState(this.tagName);
  }
}

class _CardsState extends State<Cards> with TickerProviderStateMixin {
  Offset cardOffset = const Offset(0.0, 0.0);
  Offset start;
  Offset position;
  Tween<Offset> slideOut;
  AnimationController animation;
  bool inRightRegion = true;
  int index = 0;
  List<Dish> cards = [];
  String tagName;
  Connection conn = Connection().getInstance();
  _CardsState(this.tagName);

  /// Whenever the animation has stopped initState will null out specific vars
  /// to display another card in the same position and with same animations.
  @override
  void initState() {
    super.initState();
    generateStack(5, cards, tagName).then((List<Dish> newCards) {
      setState(() {
        cards = newCards;
      });
    });

    animation = new AnimationController(
      duration: const Duration(milliseconds: 500),
      vsync: this,
    )
      ..addListener(() {
        setState(() {
          cardOffset = slideOut.evaluate(animation);
        });
      })
      ..addStatusListener((AnimationStatus status) {
        if (status == AnimationStatus.completed) {
          if (inRightRegion) {
            navigateToDish();
          } else {
            _updateImage();
          }
          setState(() {
            start = null;
            position = null;
            slideOut = null;
            cardOffset = new Offset(0.0, 0.0);
          });
        }
      });
  }

  /// This method generates a new stack of images
  /// when the end of the current stack is almost reached.
  int _updateImage() {
    if (index >= cards.length - 1) {
      index = 0;
      generateStack(cards.length * 2, cards, this.tagName)
          .then((List newCards) {
        setState(() {
          if (newCards.length < 1) {
            return;
          }
          index = (cards.length / 2).round();
          cards = newCards;
        });
      });
    } else {
      return index++;
    }
  }

  /// This method controls the rotation value for the card based on
  /// the specified [width] and [height].
  double _rotateValue(double width, double height) {
    if (start != null) {
      return (pi / 8) * (cardOffset.dx / (width + height));
    }
    return 0.0;
  }

  /// This method generates a list of dishes that will be displayed on the screen.
  Future<List<Dish>> generateStack(
      int len, List<Dish> list,String tagName) async {
    List<int> ids = [0];
    List<Dish> newDishes;
    for (int j = 0; j < list.length; j++) {
      ids.add(list[j].id);
    }
    if (this.tagName != null) {
      newDishes = await conn.getDishesByTagName('/tag/name/' + tagName + '/dishes');
    }
    else {
      newDishes = await conn.getDishList("/dish/getNewDish", ids);
    }

    if (newDishes.length <= 0) {
      return [];
    }
    for (int i = list.length; i < len && newDishes.length > 0; i++) {
      // Call Connection class to get a new dish
      list.add(newDishes[0]);
      newDishes.removeAt(0);
    }
    return list;
  }

  /// This method converts the image to a string format.
  void imageToBase64(String newImage) {
    //Part of backup image handler
    File imageFile = new File(newImage);
    List<int> imageBytes = imageFile.readAsBytesSync();
    String base64Image = base64Encode(imageBytes);
    String imageString = 'data:image/png;base64,$base64Image';
    print(imageString);
  }

  /// This method converts the encoded string to image.
  void base64ToImage(String base64Img) {
    //Part of backup image handler
    var prefix = "data:image/png;base64,";
    var bStr = base64Img.substring(prefix.length);
    var bytes = CryptoUtils.base64StringToBytes(bStr);
    var file = new File("reassembled.png");
    file.writeAsBytesSync(bytes);
  }

  /// This widget displays, transforms and detects whether a card has been moved.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        debugShowCheckedModeBanner: false,
        title: "Foodu",
        home: Container(
          decoration: BoxDecoration(
              gradient: new LinearGradient(
                begin: FractionalOffset.topLeft,
                end: FractionalOffset.bottomRight, // 10% of the width, so there are ten blinds.
                colors: [const Color(0xFF915FB5), const Color(0xFFCA436B)], // whitish to gray
                stops: [0.0,1.0],
                tileMode: TileMode.clamp, // repeats the gradient over the canvas
              )
          ),
          child: Scaffold(
            backgroundColor: Colors.transparent,
            appBar: AppBar(title: Text('Home Page')/*, centerTitle: true*/, backgroundColor: Colors.transparent, elevation: 0.0,),
            body: new Transform(
              transform:
                  new Matrix4.translationValues(cardOffset.dx, cardOffset.dy, 0.0)
                    ..rotateZ(_rotateValue(350.0, 400.0)),
              origin: new Offset(0.0, 0.0),
              child: new Stack(fit: StackFit.expand, children: <Widget>[
                new Container(
                    alignment: Alignment.center,
                    width: 350.0,
                    height: 400.0,
                    padding: const EdgeInsets.all(15.0),
                    child: new GestureDetector(
                      onPanStart: _onPanStart,
                      onPanUpdate: _onPanUpdate,
                      onPanEnd: _onPanEnd,
                      child: cardWidget(),
                    )),
              ]),
            ),
            bottomNavigationBar: NavigationBar(),
          ),
        ));
  }

  Widget cardWidget() {
    if (cards.length == 0) {
      return CircularProgressIndicator();
    } else {
      return Container(
          decoration: new BoxDecoration(
            borderRadius: BorderRadius.circular(10.0),
              boxShadow: [BoxShadow(color: Colors.black54, blurRadius: 17)],
              image: DecorationImage(
                  image: AdvancedNetworkImage(
                    cards[index].imageUrl,
                    useDiskCache: true,
                  ),
                  fit: BoxFit.cover)));
    }
  }

  /// This method detects that pointer has contacted the screen.
  void _onPanStart(DragStartDetails details) {
    start = details.globalPosition;
  }

  /// This method updates the card based on the interaction between a
  /// pointer and the card on the screen.
  void _onPanUpdate(DragUpdateDetails details) {
    setState(() {
      position = details.globalPosition;
      cardOffset = position - start;
    });
  }

  /// This method informs the program there is no more pointers in contact
  /// with the screen and the card has reached a position on the screen
  /// where it should either change image or navigates to another screen.
  void _onPanEnd(DragEndDetails details) {
    final dragVector = cardOffset / cardOffset.distance;
    final isInLeftRegion = (cardOffset.dx / context.size.width) < -0.45;
    final isInRightRegion = (cardOffset.dx / context.size.width) > 0.45;

    setState(() {
      if (isInLeftRegion) {
        slideOut = new Tween(
            begin: cardOffset, end: dragVector * (2 * context.size.width));
        animation.forward(from: 0.0);
        inRightRegion = false;
      } else if (isInRightRegion) {
        slideOut = new Tween(
            begin: cardOffset, end: dragVector * (2 * context.size.width));
        animation.forward(from: 0.0);
        inRightRegion = true;
      }
      start = null;
      position = null;
      cardOffset = const Offset(0.0, 0.0);
    });
  }

  /// A navigator for when the user swipes right it takes them to
  /// the displayed dish details screen.
  dynamic navigateToDish() {
    try {
      Route route = MaterialPageRoute(
          builder: (context) => DishScreen(cards[index]));
      return Navigator.push(context, route);
    } catch (e) {
      print(e.toString());
    }
  }
}
