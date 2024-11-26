import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:client/controller/profile.dart';
import 'package:client/main.dart';

void main() {
  Widget makeTestableWidget({Widget child}) {
    return MaterialApp(
      home: child,
    );
  }

  testWidgets('Number of ListTile Widgets', (WidgetTester tester) async {
    ProfileScreen screen = ProfileScreen();
    await tester.pumpWidget(makeTestableWidget(child: screen));
    expect(find.widgetWithText(ListTile, ''), findsNWidgets(8));
  });

  testWidgets(('Insert dragos firstName'), (WidgetTester tester) async {
    ProfileScreen screen = ProfileScreen();
    await tester.pumpWidget(makeTestableWidget(child: screen));

    String firstNameValue = screen.createState().firstNameController.text;
    expect(firstNameValue, "dragos");
  });
  testWidgets(('Insert lala secondName'), (WidgetTester tester) async {
    ProfileScreen screen = ProfileScreen();
    await tester.pumpWidget(makeTestableWidget(child: screen));

    String secondNameValue = screen.createState().secondNameController.text;
    expect(secondNameValue, "lala");
  });
}
