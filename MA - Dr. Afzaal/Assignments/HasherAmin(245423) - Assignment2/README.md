# Student Registration App

`CIT-646 Mobile Application Development - Assignment 2 BS (IT), 5th semester, Government College University, Faisalabad.`

**Name:** Hasher Amin
<br>
**Roll No:** 245423


## About

A Flutter app with one scrolling screen where a student registers for the semester.
The form is checked in a fixed order, and each valid registration is saved in a small
backend class and shown as a Card in a list.

## Features

- Banner image, avatar with initials, and three colour circles (teal, indigo, orange)
- Fields: name, email, age, password (with show/hide eye) and confirm password
- Programme (radio), semester (dropdown), courses (checkboxes) and an updates switch
- Register button stays disabled until "I accept the rules" is ticked
- Validation with exact messages (red for problems, green for success)
- Duplicate emails are refused, even in a different letter case
- Reset clears the form but keeps the saved students

## Project structure

```
lib/
  main.dart             RegisterPage: screen, validation and helper functions
  student.dart          Student class (initials(), summary())
  student_store.dart    StudentStore (add(), emailExists(), all(), count())
assets/images/
  gcuf.jpg              Banner shown at the top of the screen
pubspec.yaml            Dependencies and asset list
```

## How to run

Flutter 3.35 or newer is needed (the programme buttons use `RadioGroup`).

```
flutter pub get
flutter run
```

## Notes

- Students are stored in memory only, so they disappear when the app is closed.
- Flutter version used: (paste the output of `flutter --version`)
