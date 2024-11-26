import 'package:client/model/connection.dart';
import 'package:client/controller/login.dart';
import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:http/http.dart' as http;
import 'package:mockito/mockito.dart';

class MockClient extends Mock implements http.Client {}

void main() {
  Connection conn = new Connection().getInstance();
  testWidgets('Test if the formField widget can be created and hold text',
      (WidgetTester tester) async {
    Widget formField =
        LoginFormState(conn).formField('Username', TextEditingController());

    await tester.pumpWidget(StatefulBuilder(
      builder: (BuildContext context, StateSetter setState) {
        return MaterialApp(home: Material(child: formField));
      },
    ));

    await tester.enterText(find.byWidget(formField), 'test');

    expect(find.text('test'), findsOneWidget);
  });

  testWidgets('Test if Form can be created and hold a username and password',
      (WidgetTester tester) async {
    await tester.pumpWidget(StatefulBuilder(
      builder: (BuildContext context, StateSetter setState) {
        return MaterialApp(
            home: Material(child: LoginFormState(conn).form(context)));
      },
    ));

    await tester.enterText(find.byTooltip('Username'), 'testUser');
    await tester.enterText(find.byTooltip('Password'), 'testPassword');

    expect(find.text('testUser'), findsOneWidget);
    expect(find.text('testPassword'), findsOneWidget);
    expect(find.byTooltip('Login'), findsOneWidget);
  });

  testWidgets('Test if LoginScreen can be created',
      (WidgetTester tester) async {
    Widget loginScreen = LoginScreen(conn: conn);

    await tester.pumpWidget(StatefulBuilder(
      builder: (BuildContext context, StateSetter setState) {
        return MaterialApp(home: Material(child: loginScreen));
      },
    ));

    //Verifying the interaction was done in the previous test

    expect(find.byTooltip('Username'), findsOneWidget);
    expect(find.byTooltip('Password'), findsOneWidget);
    expect(find.byTooltip('Login'), findsOneWidget);
    expect(find.byTooltip('Create Profile'), findsOneWidget);
  });

  group('Test authentication functionality', () {
    final MockClient client = new MockClient();

    testWidgets('Test pressing login will send query to client',
        (WidgetTester tester) async {
      when(client.post('https://www.example.com/login', body: {
        'email': 'john@mail.com',
        'login': {'password': 'pass123'}
      })).thenAnswer((_) async =>
          http.Response('{}', 200, headers: {'Authorization': 'abcdefg'}));

      Connection connection =  Connection().getInstance();
      await tester.pumpWidget(StatefulBuilder(
        builder: (BuildContext context, StateSetter setState) {
          return MaterialApp(
              home: Material(
                  child:
                      LoginFormState(conn).form(context)));
        },
      ));

      await tester.enterText(find.byTooltip('Username'), 'john@mail.com');
      await tester.enterText(find.byTooltip('Password'), 'pass123');
      await tester.tap(find.byTooltip('Login'));

      expect(connection.token, 'abcdefg');
    });
  });
}
