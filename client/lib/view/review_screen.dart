import 'package:client/controller/payment.dart';
import 'package:client/model/connection.dart';
import 'package:client/model/dish.dart';
import 'package:client/model/order.dart';
import 'package:client/model/review.dart';
import 'package:client/view/bottom_navigation_bar.dart';
import 'package:flutter/material.dart';
import 'package:flutter_rating/flutter_rating.dart';

/// This file displays the restaurant reviews,
/// and a screen for the user to submit reviews after paying.
void main() => runApp(ReviewScreen(conn: null));

class ReviewScreen extends StatefulWidget {
  final Connection conn;
  final int restaurantId;

  ReviewScreen({this.restaurantId, this.conn});

  @override
  ReviewState createState() => ReviewState(restaurantId: this.restaurantId);
}

class ReviewState extends State<ReviewScreen>
{
  final Connection conn = Connection().getInstance();
  final int restaurantId;

  ReviewState({this.restaurantId});

  final TextEditingController controller = new TextEditingController();

  int rating = 0;
  int starCount = 5;

  List<Review> reviewsList = [];

  Future<dynamic> _authentication;

  @override
  void initState() {
    super.initState();
    _authentication = null;
    reviewsList.clear();
    getReview(reviewsList).then((List<Review> newList) {
      setState(() {
        reviewsList = newList;
      });
    });
  }

  Future<List<Review>> getReview(List<Review> list) async {
    List<int> ids = [0];
    for (int j = 0; j < list.length; j++) {
      ids.add(list[j].id);
    }
    List<Review> reviews = await conn.getReviews('/restaurant/'+this.restaurantId.toString()+'/review',ids);
    if (reviews.length < 1) {
      return [];
    }
    for (int i = list.length; reviews.length > 0; i++) {
      // Call Connection class to get a new dish
      list.add(reviews[0]);
      reviews.removeAt(0);
    }
    return list;
  }

  /// This widget displays the restaurant reviews
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
          elevation: 0.0,
          backgroundColor: Colors.transparent,
          title: Text("Review"),
        ),
        body: Container(
          child: Column(
            children: <Widget>[
              Expanded(
                  child: ListView.builder(
                    itemCount: reviewsList.length,
                    reverse: true,
                    itemBuilder: (context, index) {
                      return Padding(
                        padding: const EdgeInsets.only(bottom: 8.0),
                        child: ListTile(
                          onTap: () {
                          },
                          trailing: Card(
                            margin: EdgeInsets.symmetric(vertical: 10.0),
                            color: Colors.grey[350],
                            child: Column(
                                mainAxisSize: MainAxisSize.max,
                                mainAxisAlignment: MainAxisAlignment.start,
                                crossAxisAlignment: CrossAxisAlignment.end,
                                children: <Widget>[
                                  Padding(
                                    padding: const EdgeInsets.all(8.0),
                                    child:
                                    Row(
                                    children: <Widget>[
                                      RotatedBox(child: Icon(Icons.format_quote, size: 20),
                                      quarterTurns: 2,),
                                      Padding(padding: const EdgeInsets.all(5.0)),
                                      Text(reviewsList[index].comment.toString(),
                                          textAlign: TextAlign.right,
                                          textScaleFactor: 1.2,
                                          style: new TextStyle(color: Colors.black.withOpacity(1)),
                                      ),
                                      Padding(padding: const EdgeInsets.all(5.0)),
                                      Icon(Icons.format_quote, size: 20,),
                                    ],
                                      ),
                                  ),
                                Row(
                                  mainAxisAlignment: MainAxisAlignment.end,
                                  crossAxisAlignment: CrossAxisAlignment.end,
                                  children: <Widget>[
                                    StarRating(
                                      size: 15.0,
                                      rating: reviewsList[index].stars.ceilToDouble(),
                                      color: Colors.orange,
                                      borderColor: Colors.grey,
                                      starCount: starCount,
                                    ),

                                  ],
                                ),
                                ]
                        ),
                          ),

                        ),
                      );
                      },
                  ),
              ),
            ],
          ),
        ),
        bottomNavigationBar: NavigationBar(),
      ),
    );
  }
}

class SendReviewScreen extends StatefulWidget {
  final Connection conn;
  final int restaurantId;
  final List<Dish> dishes;
  final int seats;
  final int orderNumber;

  SendReviewScreen({this.restaurantId, this.conn, this.dishes, this.seats, this.orderNumber});

  @override
  SendReviewState createState() => SendReviewState(this.dishes, this.seats, this.restaurantId, this.orderNumber);
}

class SendReviewState extends State<SendReviewScreen>
{
  final Connection conn = Connection().getInstance();
  final int restaurantId;
  List<Dish> dishes;
  int seats;
  int orderNumber;

  SendReviewState(this.dishes, this.seats, this.restaurantId, this.orderNumber);

  final TextEditingController controller = new TextEditingController();

  final tip = TextEditingController();


  int rating=0;
  int starCount = 5;

  List<Review> reviewsList = [];

  Future<dynamic> _authentication;

  @override
  void initState() {
    super.initState();
    _authentication = null;
    reviewsList.clear();
  }

  /// This widget shows a textfield for the user to enter a review, stars for rating,
  /// and a popup message after sending the review
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
          elevation: 0.0,
          backgroundColor: Colors.transparent,
          title: Text("Review"),
        ),
        body: Column(
          children: <Widget>[
            Padding(padding: const EdgeInsets.all(10.0)),
            new StarRating(
              size: 40.0,
              rating: rating.ceilToDouble(),
              color: Colors.orange,
              borderColor: Colors.grey,
              starCount: starCount,
              onRatingChanged: (rating) => setState(
                    () {
                  this.rating = rating.round();
                  rating = 0;
                },
              ),
            ),
            Padding(padding: const EdgeInsets.all(10.0)),
            new ListTile(
              title: new TextFormField(
                controller: controller,
                decoration: new InputDecoration(
                  labelText: 'tell us about your experience',
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
                  height: 100.0,
                  onPressed: (){
                      String comment = controller.text.trim();
                      comment.isEmpty ? null : _postReview(controller.text, rating);
                    controller.text="";
                  },
                  child: Text(
                    'Leave a comment',
                    style: TextStyle(
                        fontSize: 22.0,
                        fontWeight: FontWeight.bold,
                        color: Colors.white
                    ),
                  ),
                ),
              ),
            ),
            new ListTile(
              title: new TextFormField(
                keyboardType: TextInputType.number,
                controller: tip,
                decoration: new InputDecoration(
                  labelText: 'Add a Tip',
                  contentPadding: const EdgeInsets.fromLTRB(20.0, 15.0, 0.0, 15.0),
                  suffixIcon: new Material(
                    color: Colors.grey[300],
                    elevation: 0.0,
                    borderRadius: BorderRadius.circular(25.0),
                    child: new MaterialButton(
                      height: 50.0,
                      onPressed: (){
                        double orderTip = int.parse(tip.text).ceilToDouble();
                        orderTip == 0 ? null : _updateOrder();
                      },
                      child: Text(
                        'Add',
                        style: TextStyle(
                            fontSize: 18.0,
                            fontWeight: FontWeight.bold,
                            color: Colors.black,

                        ),
                      ),
                    ),
                  ),
                ),
              ),
            ),
          ],
        ),
        bottomNavigationBar: NavigationBar(),
      ),
    );
  }

  void _showDialog() {
    // flutter defined function
    showDialog(
      context: context,
      builder: (BuildContext context) {
        // return object of type Dialog
        return AlertDialog(
          title: new Text("Thank you for your time"),
          content: new Text("Your review helps other people, looking for a restaurant to order from."),
          actions: <Widget>[
            // usually buttons at the bottom of the dialog
            new FlatButton(
              child: new Text("You're Welcome"),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }

  void _postReview(String comment, int rate) {
    setState(() {
      Review review = Review(comment: comment, stars: rating);

      _authentication =
          conn.createReview('/restaurant/'+this.restaurantId.toString()+'/review', review).then((_) {
            _showDialog();
            print("review Added!");
          });
    });
  }

  void _updateOrder() {
    double orderTip = int.parse(tip.text).ceilToDouble();
    Route route = MaterialPageRoute(
        builder: (context) =>
            PaymentScreen(amount: orderTip));
      Navigator.push(context, route);
      Order  order = Order(tip:int.parse(tip.text), seats:this.seats, dishes:this.dishes);
      print("id ----> " + this.orderNumber.toString());
      _authentication =
          conn.updateOrder('/order/'+this.orderNumber.toString(), order).then((_) {
            print("order updated!");
          });
  }
}
