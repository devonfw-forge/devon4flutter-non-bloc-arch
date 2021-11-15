import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'employee.dart';

class EmployeeDetail extends StatefulWidget {
  final Employee employee;
  const EmployeeDetail({
    Key? key,
    required this.employee,
  }) : super(key: key);

  @override
  _EmployeeDetailState createState() {
    return _EmployeeDetailState();
  }
}

class _EmployeeDetailState extends State<EmployeeDetail> {
  int _sliderVal = 1;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.employee.label),
        centerTitle: true,
      ),
      body: SafeArea(
        child: Column(
          children: <Widget>[
            SizedBox(
              height: 300,
              width: double.infinity,
              child: Image(image: AssetImage(widget.employee.imageUrl),
              fit: BoxFit.cover,
              ),
            ),
            const SizedBox(
              height: 5,
            ),
            Text(
              widget.employee.label,
              style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            Expanded(
              child: ListView.builder(
                padding: const EdgeInsets.all(7.0),
                itemCount: widget.employee.userDetail.length,
                itemBuilder: (BuildContext context, int index) {
                  final ingredient = widget.employee.userDetail[index];
                  return Text('${ingredient.detail}', textAlign: TextAlign.center,);
                },
              ),
            ),
          ],
        ),
      ),
    );
  }
  @override
  Widget buildEditIcon(Color color) => Icon(
    Icons.edit,
    size: 20,
  );
}