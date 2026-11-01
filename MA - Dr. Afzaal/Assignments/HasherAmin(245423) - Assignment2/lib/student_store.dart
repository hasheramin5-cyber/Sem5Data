import 'student.dart';

class StudentStore {
  final List<Student> _students = [];

  bool emailExists(String email) {
    String wanted = email.trim().toLowerCase();
    for (int i = 0; i < _students.length; i++) {
      if (_students[i].email.toLowerCase() == wanted) {
        return true;
      }
    }
    return false;
  }

  String add(Student student) {
    if (emailExists(student.email)) {
      return 'This email is already registered';
    } else {
      _students.add(student);
      return 'Registered successfully';
    }
  }

  List<Student> all() {
    return _students;
  }

  int count() {
    return _students.length;
  }
}
