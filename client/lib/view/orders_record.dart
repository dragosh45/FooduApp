import 'package:client/model/connection.dart';
import 'package:client/model/dish.dart';
import 'package:client/model/order.dart';
import 'package:client/view/bottom_navigation_bar.dart';
import 'package:flutter/material.dart';

class OrderRecord extends StatefulWidget {
  final Order orders;
  final Connection conn;

  OrderRecord({this.orders, this.conn});

  @override
  OrderRecordState createState() {
    return OrderRecordState(
        this.orders);
  }

}
class OrderRecordState extends State<OrderRecord>{

  final Connection conn = Connection().getInstance();
  final Order orders;
  List<Order> ordersList = [];
  int orderNumber;

  OrderRecordState(this.orders);

  @override
  void initState()
  {
    super.initState();
    getOrders(ordersList).then((List<Order> newList) async{
      setState(() {
        ordersList = newList;
      });
    });
    print("Fetching Successful");
  }

  Future<List<Order>> getOrders(List<Order> list) async{
    List<Order> newOrders = await conn.getOrders('/user/orders');
    if(newOrders.length < 0){
      return [];
    }
    for(int i = 0; newOrders.length > 0 ; i++){
      list.add(newOrders[0]);
      newOrders.removeAt(0);
    }
    return list;
  }

  @override
  Widget build(BuildContext context) {
    final title = 'My Orders';
    return MaterialApp(
      title: title,
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
          appBar: AppBar(
            elevation: 0.0,
            backgroundColor: Colors.transparent,
            title: Text(title),
            leading: new IconButton(
              icon: new Icon(Icons.arrow_back, color: Colors.white),
              onPressed: () => Navigator.pop(context),
            ),
          ),
          body: ListView.builder(
            itemCount: ordersList == null ? new Text('No Orders to View') : ordersList.length,
            reverse: true,
            itemBuilder: (context, index){
              var i = index+1;
              return Column(
                children: <Widget>[
                  Container(
                    color: Colors.transparent,
                    alignment: Alignment.center,
                    child: Card(
                      color: Colors.grey[350],
                      child: Column(
                        mainAxisSize: MainAxisSize.min,
                        children: <Widget>[
                          ListTile(
                            contentPadding: EdgeInsets.symmetric(horizontal: 20.0, vertical: 10.0),
                              leading: Icon(Icons.fastfood, size: 30.0, color: Colors.orange[400],),
                              title: Text(
                                'Tap to view dishes',
                                style: TextStyle(color: Colors.black, fontWeight: FontWeight.bold),
                              ),
                              subtitle: Row(
                                children: <Widget>[
                                  Text('Number of Seats: '+ ordersList[index].seats.toString(),
                                  style: TextStyle(color: Colors.black))
                                ],
                              ),
                            onTap: (){
                              Navigator.push(context,
                                  MaterialPageRoute(builder: (context) =>
                                      DishesPage(ordersList[index].dishes)));
                            },
                            trailing: Icon(Icons.keyboard_arrow_right, size: 30.0, color: Colors.black,),
                          ),
                        ],
                      ),
                    ),
                  )
                ],
              );
          },),
          bottomNavigationBar: NavigationBar(),
        ),
      ),
    );
  }
}

class DishesPage extends StatelessWidget{

  final List<Dish> dishesList;

  DishesPage(this.dishesList);

  @override
  Widget build(BuildContext context) {
    final title = 'My Orders';
    return MaterialApp(
      title: title,
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
          appBar: AppBar(
            backgroundColor: Colors.transparent,
            elevation: 0.0,
            title: Text(title),
            leading: new IconButton(
              icon: new Icon(Icons.arrow_back, color: Colors.white),
              onPressed: () => Navigator.pop(context),
            ),
          ),
          body: ListView.builder(
            itemCount: dishesList == null ? new Text('No dishes to View') : dishesList.length,
            itemBuilder: (context, index){
              return Column(
                children: <Widget>[
                  Container(
                    padding: EdgeInsets.only(left: 5.0, right: 5.0),
                    color: Colors.transparent,
                    alignment: Alignment.center,
                    child: Card(
                      color: Colors.grey[350],
                      child: Column(
                        mainAxisSize: MainAxisSize.min,
                        children: <Widget>[
                          ListTile(
                            contentPadding:
                              EdgeInsets.symmetric(horizontal: 20.0),
                              leading: Container(
                                padding: EdgeInsets.only(right: 12.0),
                                decoration: BoxDecoration(
                                border: Border(right: BorderSide(width: 1.0))),
                                child: Image.network(dishesList[index].imageUrl, width: 50.0, height: 60.0),
                              ),
                            title: Text(
                              dishesList[index].name,
                              style: TextStyle(color: Colors.black, fontWeight: FontWeight.bold),
                            ),
                            subtitle: Row(
                              children: <Widget>[
                                Text(dishesList[index].price.toStringAsFixed(2) + "£",
                                style: TextStyle(color: Colors.black))
                              ],
                            ),
                          ),
                        ],
                      ),
                    ),
                  )
                ],
              );
            },),
          bottomNavigationBar: NavigationBar(),
        ),
      ),
    );
  }
}


