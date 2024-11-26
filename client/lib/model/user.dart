import 'package:json_annotation/json_annotation.dart';

part 'package:client/model/user.g.dart';

/// This following class is used to generate code to encode and decode
/// the user's data fields from Json.

/// This annotation is used to generate Json serialization by the code generator.
@JsonSerializable()
class User {
  String givenNames;
  String surname;
  String password;
  String email;

  User(String firstName, String lastName, String passWord, String email) {
    this.givenNames = firstName;
    this.surname = lastName;
    this.password = passWord;
    this.email = email;
  }

  /// This method declares support for serialization and is used to convert
  /// an User's instance into a map.
  Map<String, dynamic> toJson() => {
        'givenNames': this.givenNames,
        'surname': this.surname,
        'login': this.password,
        'email': this.email,
      };

  @override
  String toString() {
    return 'User{givenNames: $givenNames, lastName: $surname, login: $password, email: $email}';
  }
}
