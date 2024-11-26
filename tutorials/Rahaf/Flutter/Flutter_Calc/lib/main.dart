import 'package:flutter/material.dart';

void main() => runApp(MyCalculatorApp());

class MyCalculatorApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'My Calc',
      home: MyCalculator()
    );
  }
}

class MyCalculator extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => Calculator();
}

class Calculator extends State<MyCalculator> {

  final numberA = TextEditingController();
  final numberB = TextEditingController();

  final formKey = GlobalKey<FormState>();
  String result = "";
  void sum(){
    if(formKey.currentState.validate()){
      int a = int.parse(numberA.text);
      int b = int.parse(numberB.text);
      int sum = a + b;

      setState(() {
        result = "$a + $b = $sum";
      });
    }

  }

  void times(){
    if(formKey.currentState.validate()){
      int a = int.parse(numberA.text);
      int b = int.parse(numberB.text);
      int times = a * b;

      setState(() {
        result = "$a * $b = $times";
      });
    }

  }
  void divide(){
    if(formKey.currentState.validate()){
      int a = int.parse(numberA.text);
      int b = int.parse(numberB.text);
      double divide = a / b;

      setState(() {
        result = "$a / $b = $divide";
      });
    }

  }
  void minus(){
    if(formKey.currentState.validate()){
      int a = int.parse(numberA.text);
      int b = int.parse(numberB.text);
      int minus = a - b;

      setState(() {
        result = "$a - $b = $minus";
      });
    }

  }

  @override
  Widget build(BuildContext context) {
    return new Scaffold(body:Form(key: formKey, child:Column(mainAxisAlignment: MainAxisAlignment.center, children: <Widget>[
      TextFormField(
        controller: numberA,
        validator: (value){
          if(value.isEmpty)
            return "please enter your first number";
        },
        decoration: InputDecoration(hintText: "Number"),
        keyboardType: TextInputType.text),

      TextFormField(
        controller: numberB,
        validator: (value){
          if(value.isEmpty)
            return "please enter your second number";
        },
        decoration: InputDecoration(hintText: "Number"),
        keyboardType: TextInputType.text),

      Text(result, style:TextStyle(fontSize: 20.0),),
      Row(mainAxisAlignment: MainAxisAlignment.center,children: <Widget>[
        RaisedButton(onPressed: sum, child: Text("+"),),
        RaisedButton(onPressed: times, child: Text("*"),),
        RaisedButton(onPressed: divide, child: Text("/"),),
        RaisedButton(onPressed: minus, child: Text("-"),),

      ], )
     ],
    )));
  }
  }