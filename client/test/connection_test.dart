import "package:test/test.dart";
import 'package:client/model/connection.dart';
import 'package:mockito/mockito.dart';
import 'package:client/model/user.dart';
import 'package:client/model/dish.dart';
import 'package:client/model/tag.dart';
import 'package:client/model/order.dart';
import 'package:client/model/restaurant.dart';
import 'package:http/http.dart' as http;

class MockClient1 extends Mock implements http.Client {}

class MockClient2 extends Mock implements http.Client {}

void main() {
  Connection connection1 = new Connection();
  Connection connection2 = new Connection();
  Connection instanceOfClient = Connection().getInstance();

  setUp(() async {});
  tearDown(() async {});
  group('Test Singleton Pattern', () {
    test('Asserting 2 instances of the server with different clients', () {
      expect(connection1.getInstance(),
          equals(connection2.getInstance()));
    });
  });
  group('Test auth method', () { /// insert new value to pass
    test('Asserting auth method gets token', () async {
      await instanceOfClient.auth('/auth', 'owner@mail.com', 'pass123');
      expect(instanceOfClient.token.substring(0,6),
          'Bearer');
    });
  });
  group('Test createUser method', () {
    test('Test response', () async {
      User user = new User("try1", "another1", "name121", "make_test_work@com");
      await instanceOfClient.createUser('/user/sign-up', user);
    });
  });
  group('Test createRestaurant method', () { /// fails as need to create new restaurant owner
    test('Test response', () async {
      Restaurant restaurant = new Restaurant ('american','fastfood','+44makemefat');
      await instanceOfClient.createRestaurant("/restaurant", restaurant);
    });
  });
  group('Test createDish method', () {
    test('Test response', () async {
      var tagsList = List<String>();
      tagsList.add('Tomato sauce');
      tagsList.add('Cheese');
      Dish dish = new Dish();
      await instanceOfClient.createDish("/category/2/dish", dish);
    });
  });
  group('Test createOrder method', () { /// cant authenticate user
    test('Test', () async {
      List<Dish> dishes = new List<Dish>();
      var tagsList = List<String>();
      tagsList.add('Tomato sauce1');
      tagsList.add('Cheese1');
      Dish dish1 = new Dish();
      Dish dish2 = new Dish();
      dishes.add(dish1);
      dishes.add(dish2);
      Order order = new Order(tip: 11, seats: 4, dishes: dishes);
      User user = new User("try10000011011", "another120000011011", "name12111011", "0000011011make_test_work@com");
      await instanceOfClient.auth('/auth', 'owner@mail.com', 'pass123');
      //await instanceOfClient.createUser('/user/sign-up', user);
      //await instanceOfClient.auth('/auth', '000000110make_test_work@com', 'another12000000110');
      await instanceOfClient.createOrder("/order/15",order);
    });
  });
  group('Test addOrder method', () { /// cant authenticate user
    test('Test', () async {
      List<Dish> dishes = new List<Dish>();
      var tagsList = List<String>();
      tagsList.add('Tomato saucee');
      tagsList.add('Cheeses');
      Dish dish1 = new Dish();
      Dish dish2 = new Dish();
      dishes.add(dish1);
      dishes.add(dish2);
      Order order = new Order(tip: 2, seats: 1, dishes: dishes);
      User user = new User("try12122", "another122", "name1212", "13make_test_work2@com");
      await instanceOfClient.createUser('/user/sign-up', user);
      await instanceOfClient.auth('/auth', '13make_test_work2@com', 'another122');
      await instanceOfClient.createOrder("/order/add", order);
    });
  });
  group('Test Tag methods', ()  {
    List<Tag> tagsList= [];
    Tag tag = new Tag(name:"Italiann",id:1,);
    Tag tag2 = new Tag(name:"American",id:2);
    Tag tag3 = new Tag(name:"American",id:10);
    Tag tag4 = new Tag(name:"DogFood",id:80);
    Dish dish = new Dish(id:11,name:"Pizza");
    test('Test response on creating Tag', () async {
      await instanceOfClient.addTag("/tag/"+tag.id.toString(), tag);
      await instanceOfClient.addTag("/tag/"+tag.id.toString(), tag);
      await instanceOfClient.addTag("/tag/"+tag.id.toString(), tag);
      await instanceOfClient.addTag("/tag/"+tag2.id.toString(), tag2);
      await instanceOfClient.addTag("/tag/"+tag3.id.toString(), tag3);
    });
    test('Test response on getting Tag by Id',() async {
      await instanceOfClient.getTagById("/tag/"+tag.id.toString());
    });
    test('Test response on adding Dishes to Tag',() async {
      await instanceOfClient.addDishToTag("/tag/"+tag.id.toString()+"/dish", dish);
      //printing tag to see the dish added:
      await instanceOfClient.getTagById("/tag/"+tag.id.toString());
    });
    test('Test endpoint to get tagsList with all tags', () async {
      tagsList = await instanceOfClient.getTags("/tag");
      print("tagsList from test endpoint: " + tagsList.toString());
      print(tagsList.length);
    });
  });
}