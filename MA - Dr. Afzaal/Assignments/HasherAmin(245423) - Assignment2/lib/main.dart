import 'package:flutter/material.dart';

import 'student.dart';
import 'student_store.dart';

void main() {
  runApp(const StudentRegistrationApp());
}

class StudentRegistrationApp extends StatelessWidget {
  const StudentRegistrationApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Student Registration',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(useMaterial3: true, colorSchemeSeed: Colors.teal),
      home: const RegisterPage(),
    );
  }
}

class RegisterPage extends StatefulWidget {
  const RegisterPage({super.key});

  @override
  State<RegisterPage> createState() => _RegisterPageState();
}

class _RegisterPageState extends State<RegisterPage> {
  // ---------- backend ----------
  final StudentStore _store = StudentStore();

  // ---------- controllers ----------
  final TextEditingController _nameController = TextEditingController();
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _ageController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  final TextEditingController _confirmController = TextEditingController();

  // ---------- fixed lists ----------
  final List<String> _semesters = [
    '1st',
    '2nd',
    '3rd',
    '4th',
    '5th',
    '6th',
    '7th',
    '8th',
  ];
  final List<String> _courseNames = [
    'Mobile App Dev',
    'Web Engineering',
    'Artificial Intelligence',
    'Database Systems',
  ];

  // ---------- state ----------
  bool _hide = true;
  Color _color = Colors.teal;
  String? _programme;
  String? _semester;
  final List<bool> _ticked = [false, false, false, false];
  bool _updates = true;
  bool _rules = false;
  String _message = '';
  Color _messageColor = Colors.red;

  @override
  void dispose() {
    _nameController.dispose();
    _emailController.dispose();
    _ageController.dispose();
    _passwordController.dispose();
    _confirmController.dispose();
    super.dispose();
  }

  // =====================================================
  //  Helper functions
  // =====================================================

  // First letters of the first two words, in capitals; '?' if empty.
  String _makeInitials(String name) {
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

  void _onNameChanged(String text) {
    setState(() {});
  }

  void _pickColor(Color c) {
    setState(() {
      _color = c;
    });
  }

  void _toggleHide() {
    setState(() {
      _hide = !_hide;
    });
  }

  IconData _eyeIcon() {
    if (_hide) {
      return Icons.visibility;
    } else {
      return Icons.visibility_off;
    }
  }

  bool _isValidEmail(String email) {
    if (email.contains(' ')) {
      return false;
    }
    int at = email.indexOf('@');
    if (at < 1) {
      return false;
    }
    int dot = email.lastIndexOf('.');
    if (dot < at + 2) {
      return false;
    }
    if (dot == email.length - 1) {
      return false;
    }
    return true;
  }

  bool _hasDigit(String text) {
    for (int i = 0; i < text.length; i++) {
      if ('0123456789'.contains(text[i])) {
        return true;
      }
    }
    return false;
  }

  int _countTicked() {
    int n = 0;
    for (int i = 0; i < _ticked.length; i++) {
      if (_ticked[i]) {
        n = n + 1;
      }
    }
    return n;
  }

  // Runs rules 1-13 in order. Returns the first message, or null if all pass.
  String? _checkForm() {
    String name = _nameController.text.trim();
    String email = _emailController.text.trim();
    String ageText = _ageController.text.trim();
    String password = _passwordController.text;
    String confirm = _confirmController.text;

    if (name.isEmpty) {
      return 'Please enter your name';
    }
    if (name.length < 3) {
      return 'Name must be at least 3 letters';
    }
    if (email.isEmpty) {
      return 'Please enter your email';
    }
    if (!_isValidEmail(email)) {
      return 'Please enter a valid email';
    }
    int? age = int.tryParse(ageText);
    if (age == null) {
      return 'Age must be a number';
    }
    if (age < 16 || age > 60) {
      return 'Age must be between 16 and 60';
    }
    if (password.length < 6) {
      return 'Password must be at least 6 characters';
    }
    if (!_hasDigit(password)) {
      return 'Password must contain a number';
    }
    if (confirm != password) {
      return 'Passwords do not match';
    }
    if (_programme == null) {
      return 'Please choose a programme';
    }
    if (_semester == null) {
      return 'Please choose a semester';
    }
    int chosen = _countTicked();
    if (chosen < 1) {
      return 'Select at least one course';
    }
    if (chosen > 3) {
      return 'Select at most three courses';
    }
    return null;
  }

  // Register is enabled only when the rules box is ticked.
  VoidCallback? _registerAction() {
    if (_rules) {
      return _register;
    } else {
      return null;
    }
  }

  void _register() {
    String? problem = _checkForm();
    if (problem != null) {
      setState(() {
        _message = problem;
        _messageColor = Colors.red;
      });
      return;
    }

    // Build the list of chosen course names with if statements.
    List<String> chosenCourses = [];
    if (_ticked[0]) {
      chosenCourses.add(_courseNames[0]);
    }
    if (_ticked[1]) {
      chosenCourses.add(_courseNames[1]);
    }
    if (_ticked[2]) {
      chosenCourses.add(_courseNames[2]);
    }
    if (_ticked[3]) {
      chosenCourses.add(_courseNames[3]);
    }

    Student student = Student(
      name: _nameController.text.trim(),
      email: _emailController.text.trim(),
      age: int.parse(_ageController.text.trim()),
      programme: _programme!,
      semester: _semester!,
      courses: chosenCourses,
      updates: _updates,
      color: _color,
    );
    String result = _store.add(student);

    setState(() {
      _message = result;
      if (result == 'Registered successfully') {
        _messageColor = Colors.green;
      } else {
        _messageColor = Colors.red;
      }
    });
  }

  // Clears the form but keeps the saved students.
  void _reset() {
    _nameController.clear();
    _emailController.clear();
    _ageController.clear();
    _passwordController.clear();
    _confirmController.clear();
    setState(() {
      _hide = true;
      _color = Colors.teal;
      _programme = null;
      _semester = null;
      for (int i = 0; i < _ticked.length; i++) {
        _ticked[i] = false;
      }
      _updates = true;
      _rules = false;
      _message = '';
    });
  }

  void _setProgramme(String? value) {
    setState(() {
      _programme = value;
    });
  }

  void _setSemester(String? value) {
    setState(() {
      _semester = value;
    });
  }

  void _tickCourse(int index, bool? v) {
    setState(() {
      _ticked[index] = v!;
    });
  }

  List<DropdownMenuItem<String>> _semesterItems() {
    List<DropdownMenuItem<String>> items = [];
    for (int i = 0; i < _semesters.length; i++) {
      items.add(
        DropdownMenuItem<String>(
          value: _semesters[i],
          child: Text(_semesters[i]),
        ),
      );
    }
    return items;
  }

  List<Widget> _studentCards() {
    List<Widget> cards = [];
    List<Student> students = _store.all();
    for (int i = 0; i < students.length; i++) {
      Student s = students[i];
      cards.add(
        Card(
          child: ListTile(
            leading: CircleAvatar(
              backgroundColor: s.color,
              child: Text(
                s.initials(),
                style: const TextStyle(color: Colors.white),
              ),
            ),
            title: Text(s.name),
            subtitle: Text(s.summary()),
          ),
        ),
      );
    }
    return cards;
  }

  // =====================================================
  //  Screen
  // =====================================================

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Student Registration'),
        actions: [
          Padding(
            padding: const EdgeInsets.only(right: 16),
            child: CircleAvatar(
              radius: 14,
              backgroundColor: Colors.teal.shade700,
              child: Text(
                '${_store.count()}',
                style: const TextStyle(color: Colors.white, fontSize: 13),
              ),
            ),
          ),
        ],
      ),
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.all(16),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              // 1. Banner
              ClipRRect(
                borderRadius: BorderRadius.circular(12),
                child: Image.asset(
                  'assets/images/gcuf.jpg',
                  height: 84,
                  fit: BoxFit.cover,
                ),
              ),
              const SizedBox(height: 12),

              // 2 + 3. Avatar and colour circles
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  CircleAvatar(
                    radius: 36,
                    backgroundColor: _color,
                    child: Text(
                      _makeInitials(_nameController.text),
                      style: const TextStyle(fontSize: 24, color: Colors.white),
                    ),
                  ),
                  const SizedBox(width: 12),
                  GestureDetector(
                    onTap: () {
                      _pickColor(Colors.teal);
                    },
                    child: const CircleAvatar(
                      radius: 15,
                      backgroundColor: Colors.teal,
                    ),
                  ),
                  const SizedBox(width: 12),
                  GestureDetector(
                    onTap: () {
                      _pickColor(Colors.indigo);
                    },
                    child: const CircleAvatar(
                      radius: 15,
                      backgroundColor: Colors.indigo,
                    ),
                  ),
                  const SizedBox(width: 12),
                  GestureDetector(
                    onTap: () {
                      _pickColor(Colors.orange);
                    },
                    child: const CircleAvatar(
                      radius: 15,
                      backgroundColor: Colors.orange,
                    ),
                  ),
                ],
              ),
              const SizedBox(height: 12),

              // 4. Name
              TextField(
                controller: _nameController,
                onChanged: _onNameChanged,
                decoration: const InputDecoration(
                  labelText: 'Name',
                  prefixIcon: Icon(Icons.person),
                  border: OutlineInputBorder(),
                ),
              ),
              const SizedBox(height: 12),

              // 5. Email
              TextField(
                controller: _emailController,
                keyboardType: TextInputType.emailAddress,
                decoration: const InputDecoration(
                  labelText: 'Email',
                  prefixIcon: Icon(Icons.email),
                  border: OutlineInputBorder(),
                ),
              ),
              const SizedBox(height: 12),

              // 6. Age
              TextField(
                controller: _ageController,
                keyboardType: TextInputType.number,
                decoration: const InputDecoration(
                  labelText: 'Age',
                  prefixIcon: Icon(Icons.cake),
                  border: OutlineInputBorder(),
                ),
              ),
              const SizedBox(height: 12),

              // 7. Password
              TextField(
                controller: _passwordController,
                obscureText: _hide,
                decoration: InputDecoration(
                  labelText: 'Password',
                  prefixIcon: const Icon(Icons.lock),
                  suffixIcon: IconButton(
                    icon: Icon(_eyeIcon()),
                    onPressed: _toggleHide,
                  ),
                  border: const OutlineInputBorder(),
                ),
              ),
              const SizedBox(height: 12),

              // 8. Confirm password
              TextField(
                controller: _confirmController,
                obscureText: true,
                decoration: const InputDecoration(
                  labelText: 'Confirm password',
                  prefixIcon: Icon(Icons.lock),
                  border: OutlineInputBorder(),
                ),
              ),
              const SizedBox(height: 16),

              // 9. Programme
              const Text(
                'Programme',
                style: TextStyle(fontWeight: FontWeight.bold),
              ),
              RadioGroup<String>(
                groupValue: _programme,
                onChanged: _setProgramme,
                child: const Column(
                  children: [
                    RadioListTile<String>(title: Text('BS IT'), value: 'BS IT'),
                    RadioListTile<String>(title: Text('BS CS'), value: 'BS CS'),
                    RadioListTile<String>(title: Text('BS SE'), value: 'BS SE'),
                  ],
                ),
              ),
              const SizedBox(height: 12),

              // 10. Semester
              DropdownButton<String>(
                value: _semester,
                hint: const Text('Select semester'),
                isExpanded: true,
                items: _semesterItems(),
                onChanged: _setSemester,
              ),
              const SizedBox(height: 16),

              // 11. Courses
              const Text(
                'Courses',
                style: TextStyle(fontWeight: FontWeight.bold),
              ),
              CheckboxListTile(
                title: Text(_courseNames[0]),
                value: _ticked[0],
                controlAffinity: ListTileControlAffinity.trailing,
                onChanged: (v) {
                  _tickCourse(0, v);
                },
              ),
              CheckboxListTile(
                title: Text(_courseNames[1]),
                value: _ticked[1],
                controlAffinity: ListTileControlAffinity.trailing,
                onChanged: (v) {
                  _tickCourse(1, v);
                },
              ),
              CheckboxListTile(
                title: Text(_courseNames[2]),
                value: _ticked[2],
                controlAffinity: ListTileControlAffinity.trailing,
                onChanged: (v) {
                  _tickCourse(2, v);
                },
              ),
              CheckboxListTile(
                title: Text(_courseNames[3]),
                value: _ticked[3],
                controlAffinity: ListTileControlAffinity.trailing,
                onChanged: (v) {
                  _tickCourse(3, v);
                },
              ),

              // 12. Updates
              SwitchListTile(
                title: const Text('Email me updates'),
                value: _updates,
                onChanged: (v) {
                  setState(() {
                    _updates = v;
                  });
                },
              ),

              // 13. Rules
              const Divider(),
              CheckboxListTile(
                title: const Text('I accept the rules'),
                value: _rules,
                controlAffinity: ListTileControlAffinity.trailing,
                onChanged: (v) {
                  setState(() {
                    _rules = v!;
                  });
                },
              ),
              const SizedBox(height: 12),

              // 14. Buttons
              Row(
                children: [
                  Expanded(
                    child: ElevatedButton(
                      onPressed: _registerAction(),
                      child: const Text('Register'),
                    ),
                  ),
                  const SizedBox(width: 10),
                  Expanded(
                    child: OutlinedButton(
                      onPressed: _reset,
                      child: const Text('Reset'),
                    ),
                  ),
                ],
              ),
              const SizedBox(height: 12),

              // 15. Message
              Text(
                _message,
                textAlign: TextAlign.center,
                style: TextStyle(
                  color: _messageColor,
                  fontSize: 15,
                  fontWeight: FontWeight.w500,
                ),
              ),
              const SizedBox(height: 12),

              // 16. Student list
              Text(
                'Students (${_store.count()})',
                style: const TextStyle(fontWeight: FontWeight.bold),
              ),
              const SizedBox(height: 8),
              ..._studentCards(),
            ],
          ),
        ),
      ),
    );
  }
}
