import 'package:test/test.dart';
import 'package:test_tutorial/methods.dart';

void main() {
  group('Testing methods', () {
    var meth;
    test('Creational Test', () {
      meth = Methods(value: 12);
      expect(meth.value, 12);
    });

    test('Increment test', () {
      meth = Methods(value: 10);
      meth.increaseValue(2);
      expect(meth.value, 12);
    });

    test('Decrement test', () {
      meth = Methods(value: 10);
      meth.decreaseValue(2);
      expect(meth.value, 8);
    });
  });
}
