import 'package:flutter/material.dart';

class MyThemes {
  static final primary = Colors.blue;
  static final primaryColor = Colors.blue.shade900;

  static final darkTheme = ThemeData(
    scaffoldBackgroundColor: Colors.black,
    primaryColorDark: primaryColor,
    colorScheme: ColorScheme.dark(primary: primary),
    dividerColor: Colors.white,
  );

  static final lightTheme = ThemeData(
    scaffoldBackgroundColor: Colors.white,
    primaryColor: primaryColor,
    colorScheme: ColorScheme.light(primary: primary),
    dividerColor: Colors.black,
  );
}
