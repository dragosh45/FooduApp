import 'package:client/controller/home.dart';
import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';

void main() {
  group('Test widgets are displayed', () {
    testWidgets('Test if button is loading', (WidgetTester tester) async {
      await tester.pumpWidget(HomeScreen());
      expect(find.byIcon(Icons.menu), findsOneWidget);
    });

    testWidgets('Test if images are found on the page',
        (WidgetTester tester) async {
      await tester.pumpWidget(HomeScreen());
      expect(find.byTooltip('Image'), findsOneWidget);
    });
  });
}

