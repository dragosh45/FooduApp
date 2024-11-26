import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';

void main() {
  testWidgets('my first widget test', (WidgetTester tester) async {
    var sliderKey = UniqueKey();
    var value = 0.0;

    await tester.pumpWidget(
      StatefulBuilder(
          builder: (BuildContext context, StateSetter setState) {
            return MaterialApp(
              home: Material(
                child: Center(
                  child: Slider(
                    key: sliderKey,
                    value: value,
                    onChanged: (double newValue) {
                      setState(() {
                        value = newValue;
                      });
                    },
                  ),
                ),
              ),
            );
          }
      ),
    );
    expect(value, equals(0.0));

    await tester.tap(find.byKey(sliderKey));

    expect(value, equals(0.5));
  });
}