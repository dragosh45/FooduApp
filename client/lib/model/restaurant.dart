import 'package:json_annotation/json_annotation.dart';

@JsonSerializable()
class Restaurant {
  String name;
  String logo;
  String phone;
  String address;

  Restaurant(String name, String logo, String phone) {
    this.name = name;
    this.logo = logo;
    this.phone = phone;
    //this.address = address;
  }
  Map<String, dynamic> toJson() => {
        'name': this.name,
        'logoUrl': this.logo,
        'phone': this.phone,
        //TODO: refactor this to use Location(lat, long) object
       // 'location': this.address,
      };

  @override
  String toString() {
    return 'Restaurant{name: $name, phone: $phone, address: $address}';
  }
}
