#include <iostream>
#include <string>
#include <cctype>
#include <limits>

using namespace std;

string encryptText(string text, int key)
{
  for (int i = 0; i < text.length(); i++)
  {
    char ch = text[i];

    if (ch >= 'A' && ch <= 'Z')
    {
      ch = ch + key;

      if (ch > 'Z')
      {
        ch = ch - 26;
      }
    }
    else if (ch >= 'a' && ch <= 'z')
    {
      ch = ch + key;

      if (ch > 'z')
      {
        ch = ch - 26;
      }
    }

    text[i] = ch;
  }

  return text;
}

string decryptText(string text, int key)
{
  for (int i = 0; i < text.length(); i++)
  {
    char ch = text[i];

    if (ch >= 'A' && ch <= 'Z')
    {
      ch = ch - key;

      if (ch < 'A')
      {
        ch = ch + 26;
      }
    }
    else if (ch >= 'a' && ch <= 'z')
    {
      ch = ch - key;

      if (ch < 'a')
      {
        ch = ch + 26;
      }
    }

    text[i] = ch;
  }

  return text;
}

int main()
{
  string text;
  int key;
  int choice;

  cout << "\nCaesar Cipher" << endl;
  cout << "1. Encrypt" << endl;
  cout << "2. Decrypt" << endl;
  cout << "Choose: ";
  cin >> choice;

  cin.ignore(1000, '\n');

  cout << "Enter text: ";
  getline(cin, text);

  cout << "Enter key: ";
  cin >> key;

  if (choice == 1)
  {
    cout << "Encrypted text: " << encryptText(text, key) << endl;
  }
  else if (choice == 2)
  {
    cout << "Decrypted text: " << decryptText(text, key) << endl;
  }
  else
  {
    cout << "Invalid choice!" << endl;
  }

  return 0;
}