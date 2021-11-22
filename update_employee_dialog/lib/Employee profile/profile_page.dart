import 'package:animated_theme_switcher/animated_theme_switcher.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'employee.dart';
import 'employee_preferences.dart';
import '../Widgets/appbar_widget.dart';
import '../Widgets/profile_widget.dart';
import 'edit_profile_page.dart';

class ProfilePage extends StatefulWidget {
  final Employee employee;

  const ProfilePage({
    Key? key,
    required this.employee,
  }) : super(key: key);

  @override
  _ProfilePageState createState() {
    return _ProfilePageState();
  }
}

class _ProfilePageState extends State<ProfilePage> {
  @override
  Widget build(BuildContext context) {
    final employee = EmployeePreferences.myEmployee;
    return Scaffold(
      appBar: buildAppBar(context),
      body: SingleChildScrollView(
          child: Column(
        children: [
          SizedBox(
              width: double.infinity,
              height: MediaQuery.of(context).size.height,
              child: Column(
                children: [
                  SizedBox(
                      width: double.infinity,
                      height: MediaQuery.of(context).size.height * 0.30,
                      child: ProfileWidget(
                        imagePath: employee.imagePath,
                        onClicked: () {},
                      )),
                  const SizedBox(height: 24),
                  buildName(employee),
                  const SizedBox(height: 5),
                  buildContact(employee),
                ],
              )),
        ],
      )),
    );
  }

  Widget buildName(Employee employee) => SizedBox(
      width: double.infinity,
      height: MediaQuery.of(context).size.height *
          0.18, // Abstand zwischen Detail und Contact
      child: Column(children: [
        Text(
          employee.name,
          style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 24),
        ),
        const SizedBox(height: 5),
        Column(
          children: <Widget>[
            ListTile(
                title: Column(
              children: [
                Text(employee.profession.toString()),
                Text(employee.country.toString()),
              ],
            ))
          ],
        )
      ]));

  Widget buildContact(Employee employee) => Container(
        padding: EdgeInsets.symmetric(
            horizontal: MediaQuery.of(context).size.width * 0.1),
        child: SizedBox(
            height: MediaQuery.of(context).size.height * 0.3,
            child: ListView(
              children: <Widget>[
                Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 15),
                  child: Text("Contact",
                      textAlign: TextAlign.left,
                      style:
                          TextStyle(fontSize: 16, fontWeight: FontWeight.w700)),
                ),
                ListTile(
                  leading: const Icon(Icons.phone),
                  title: Text(employee.phone.toString()),
                ),
                ListTile(
                  leading: const Icon(Icons.email),
                  title: Text(employee.email.toString()),
                ),
                ListTile(
                  leading: const Icon(Icons.location_on),
                  title: Text(employee.location.toString()),
                ),
              ],
            )),
      );
}
