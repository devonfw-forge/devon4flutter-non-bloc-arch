import 'package:employee/Employee profile/employee_preferences.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'Employee profile/profile_page.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  static final String title = 'Employee Profile';

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        primaryColor: Colors.blue.shade900,
        dividerColor: Colors.black,
      ),
      title: title,
      home: ProfilePage(employee: EmployeePreferences.myEmployee),
    );
  }
}
