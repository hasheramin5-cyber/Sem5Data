#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

void prepareKeyMatrix(char key[], char matrix[5][5])
{
  int dict[26] = {0};
  int len = strlen(key);
  int index = 0;

  // Convert key to upper case and clean J
  for (int i = 0; i < len; i++)
  {
    if (key[i] == 'j' || key[i] == 'J')
      key[i] = 'I';
    key[i] = toupper(key[i]);
  }

  // Insert key into matrix
  for (int i = 0; i < len; i++)
  {
    if (isalpha(key[i]) && dict[key[i] - 'A'] == 0)
    {
      dict[key[i] - 'A'] = 1;
      matrix[index / 5][index % 5] = key[i];
      index++;
    }
  }

  // Fill remaining alphabets
  for (int i = 0; i < 26; i++)
  {
    if (i == ('J' - 'A'))
      continue; // Skip J
    if (dict[i] == 0)
    {
      matrix[index / 5][index % 5] = (char)(i + 'A');
      index++;
    }
  }
}

void findPosition(char matrix[5][5], char c, int *row, int *col)
{
  if (c == 'J')
    c = 'I';
  for (int i = 0; i < 5; i++)
  {
    for (int j = 0; j < 5; j++)
    {
      if (matrix[i][j] == c)
      {
        *row = i;
        *col = j;
        return;
      }
    }
  }
}

void encrypt(char text[], char matrix[5][5])
{
  int len = strlen(text);
  int r1, c1, r2, c2;

  for (int i = 0; i < len; i += 2)
  {
    findPosition(matrix, text[i], &r1, &c1);
    findPosition(matrix, text[i + 1], &r2, &c2);

    if (r1 == r2)
    { // Same row
      text[i] = matrix[r1][(c1 + 1) % 5];
      text[i + 1] = matrix[r2][(c2 + 1) % 5];
    }
    else if (c1 == c2)
    { // Same column
      text[i] = matrix[(r1 + 1) % 5][c1];
      text[i + 1] = matrix[(r2 + 1) % 5][c2];
    }
    else
    { // Rectangle rule
      text[i] = matrix[r1][c2];
      text[i + 1] = matrix[r2][c1];
    }
  }
}

int main()
{
  char matrix[5][5];
  char key[] = "MONARCHY";

  // Fixed prepared plaintext block (even length, duplicate padded)
  char text[50] = "INSTRUMENTS";

  // Convert to upper case dynamically
  for (int i = 0; text[i]; i++)
    text[i] = toupper(text[i]);

  prepareKeyMatrix(key, matrix);
  encrypt(text, matrix);

  printf("Key: %s\n", key);
  printf("Ciphertext: %s\n", text);

  return 0;
}
