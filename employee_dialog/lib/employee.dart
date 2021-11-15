import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class Employee{
  String label;
  String imageUrl;
  int servings;
  List<UserDetail> userDetail;

  Employee(
      this.label,
      this.imageUrl,
      this.servings,
      this.userDetail,
  );


  static List<Employee> samples = [
    Employee(
      'Max Mustermann',
      'assets/employee1.jpg',
      4,
      [

        UserDetail('Senior Delivery Architect CSS',),
        UserDetail('Germany',),
        UserDetail(' ',),
        UserDetail('max.mustermann@muster.com',),
        UserDetail('+490123456789',),
      ],
    ),
    Employee(
      'Ela Mustermann',
      'assets/employee2.jpg',
      4,
      [
        UserDetail('Managing Consultant',),
        UserDetail('Poland',),
        UserDetail(' ',),
        UserDetail('ela.mustermann@muster.com',),
        UserDetail('+489876543210',),
      ],
    ),
    Employee(
      'Alexandra Mustermann',
      'assets/employee3.jpg',
      4,
      [
        UserDetail('intern - student CSS',),
        UserDetail('Italy',),
        UserDetail(' ',),
        UserDetail('alexandra.mustermann@muster.com',),
        UserDetail('+39147852369',),
      ],
    ),
  ];
}

class UserDetail {
  String detail;

  UserDetail(
      this.detail,
  );
}