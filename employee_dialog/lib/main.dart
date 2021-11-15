import 'package:flutter/material.dart';
import 'employee.dart';
import 'employee_detail.dart';

void main(){
  runApp(const EmployeeApp());
}

class EmployeeApp extends StatelessWidget{
  const EmployeeApp({Key? key}) : super(key: key);
  @override
  Widget build(BuildContext context) {
    final ThemeData theme = ThemeData();
    return MaterialApp(
      title: 'Employee App',
      theme: theme.copyWith(
        colorScheme: theme.colorScheme.copyWith(
          primary: Colors.blue.shade900,
          secondary: Colors.black,
        ),
      ),
      home: const MyHomePage(title: 'Employee App'),
    );
  }
}


class MyHomePage extends StatefulWidget{
  const MyHomePage({Key? key, required this.title}) : super(key: key);
  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        centerTitle:true,
        title: Text(widget.title),
      ),
      body: SafeArea(
        child: ListView.builder(
          itemCount: Employee.samples.length,
          itemBuilder: (BuildContext context, int index){
            return GestureDetector(
              onTap: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) {
                      return EmployeeDetail(employee: Employee.samples[index]);;
                    },
                  ),
                );
              },
              child: buildEmployeeCard(Employee.samples[index]),
            );
          },
        ),
      ),
    );
  }

  @override
  Widget buildEmployeeCard(Employee employee){
    return Card(
      elevation: 2.0,
      shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(10.0)),
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: <Widget>[
            Image(image: AssetImage(employee.imageUrl)),
            const SizedBox(
              height: 14.0,
            ),
            Text(
              employee.label,
              style: const TextStyle(
                fontSize: 20.0,
                fontWeight: FontWeight.w700,
                fontFamily: 'Raleway',
              ),
            )
          ],
        ),
      ),
    );
  }
}