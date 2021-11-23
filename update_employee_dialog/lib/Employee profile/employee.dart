class Employee {
  final String imagePath;
  final String name;
  final String profession;
  final String country;
  final String email;
  final String phone;
  final String location;
  final bool isDarkMode;

  const Employee({
    required this.imagePath,
    required this.name,
    required this.profession,
    required this.country,
    required this.email,
    required this.phone,
    required this.location,
    required this.isDarkMode,
  });

  Employee copy({
    String? imagePath,
    String? name,
    String? profession,
    String? country,
    String? email,
    String? phone,
    String? location,
    bool? isDarkMode,
  }) =>
      Employee(
          imagePath: imagePath ?? this.imagePath,
          name: name ?? this.name,
          profession: profession ?? this.profession,
          country: country ?? this.country,
          email: email ?? this.email,
          phone: phone ?? this.phone,
          location: location ?? this.location,
          isDarkMode: isDarkMode ?? this.isDarkMode);

  static Employee fromJson(Map<String, dynamic> json) => Employee(
        imagePath: json['imagePath'],
        name: json['name'],
        profession: json['profession'],
        country: json['country'],
        email: json['email'],
        phone: json['phone'],
        location: json['location'],
        isDarkMode: json['isDarkMode'],
      );

  Map<String, dynamic> toJson() => {
        'imagePath': imagePath,
        'name': name,
        'profession': profession,
        'country': country,
        'email': email,
        'phone': phone,
        'location': location,
        'isDarkMode': isDarkMode,
      };
}
