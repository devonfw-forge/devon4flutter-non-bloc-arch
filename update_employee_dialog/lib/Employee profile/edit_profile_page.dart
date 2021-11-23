import 'package:animated_theme_switcher/animated_theme_switcher.dart';
import 'package:employee/Employee%20profile/employee.dart';
import 'package:employee/Employee%20profile/employee_preferences.dart';
import 'package:employee/Widgets/appbar_widget.dart';
import 'package:employee/Widgets/profile_widget.dart';
import 'package:employee/Widgets/textfield_widget.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';

class EditProfilePage extends StatefulWidget {
  const EditProfilePage({Key? key}) : super(key: key);

  @override
  _EditProfilePageState createState() => _EditProfilePageState();
}

class _EditProfilePageState extends State<EditProfilePage> {
  Employee employee = EmployeePreferences.myEmployee;

  @override
  Widget build(BuildContext context) => Scaffold(
      appBar: buildAppBar(context),
      body: SingleChildScrollView(
          child: Container(
        padding: EdgeInsets.symmetric(
            horizontal: MediaQuery.of(context).size.width * 0.1),
        child: Column(children: [
          SizedBox(
              width: double.infinity,
              height: MediaQuery.of(context).size.height,
              child: ListView(
                children: [
                  SizedBox(
                      width: double.infinity,
                      height: MediaQuery.of(context).size.height * 0.28,
                      child: ProfileWidget(
                        imagePath: employee.imagePath,
                        isEdit: true,
                        onClicked: () {},
                      )),
                  const SizedBox(height: 40),
                  TextFieldWidget(
                    label: 'First name and last name',
                    text: employee.name,
                    onChanged: (name) {},
                  ),
                  const SizedBox(height: 20),
                  TextFieldWidget(
                    label: 'Profession',
                    text: employee.profession,
                    onChanged: (profession) {},
                  ),
                  const SizedBox(height: 20),
                  TextFieldWidget(
                    label: 'Country',
                    text: employee.country,
                    onChanged: (country) {},
                  ),
                  const SizedBox(height: 20),
                  TextFieldWidget(
                    label: 'Phone number',
                    text: employee.phone,
                    onChanged: (phone) {},
                  ),
                  const SizedBox(height: 20),
                  TextFieldWidget(
                    label: 'Email address',
                    text: employee.email,
                    onChanged: (email) {},
                  ),
                  const SizedBox(height: 20),
                  TextFieldWidget(
                    label: 'Location',
                    text: employee.location,
                    onChanged: (location) {},
                  ),
                  const SizedBox(height: 100),
                ],
              )),
        ]),
      )));
}
