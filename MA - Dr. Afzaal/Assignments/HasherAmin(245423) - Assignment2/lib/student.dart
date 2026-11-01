import 'package:flutter/material.dart'; // for Color

class Student {
  final String name;
  final String email;
  final int age;
  final String programme;
  final String semester;
  final List<String> courses;
  final bool updates;
  final Color color;

  Student({
    required this.name,
    required this.email,
    required this.age,
    required this.programme,
    required this.semester,
    required this.courses,
    required this.updates,
    required this.color,
  });

  // First letters of the first two words of the name, in capitals.
  String initials() {
    List<String> words = name.trim().split(' ');
    String result = '';
    for (int i = 0; i < words.length; i++) {
      if (words[i].isNotEmpty && result.length < 2) {
        result = result + words[i][0].toUpperCase();
      }
    }
    if (result.isEmpty) {
      return '?';
    } else {
      return result;
    }
  }

  // Text like 'BS IT, 5th semester, 2 courses, updates ON'
  String summary() {
    String courseText;
    if (courses.length == 1) {
      courseText = '1 course';
    } else {
      courseText = '${courses.length} courses';
    }

    String updatesText;
    if (updates) {
      updatesText = 'ON';
    } else {
      updatesText = 'OFF';
    }

    return '$programme, $semester semester, $courseText, updates $updatesText';
  }
}
