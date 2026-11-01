#include <stdio.h>
#include <string.h>
#include <ctype.h>

#define MAX_LENGTH 1000

void encrypt(char text[], int key)
{
  key = ((key % 26) + 26) % 26;

  for (int i = 0; text[i] != '\0'; i++)
  {
    if (isupper((unsigned char)text[i]))
    {
      text[i] = 'A' + (text[i] - 'A' + key) % 26;
    }
    else if (islower((unsigned char)text[i]))
    {
      text[i] = 'a' + (text[i] - 'a' + key) % 26;
    }
  }
}

void decrypt(char text[], int key)
{
  key = ((key % 26) + 26) % 26;

  for (int i = 0; text[i] != '\0'; i++)
  {
    if (isupper((unsigned char)text[i]))
    {
      text[i] = 'A' + (text[i] - 'A' - key + 26) % 26;
    }
    else if (islower((unsigned char)text[i]))
    {
      text[i] = 'a' + (text[i] - 'a' - key + 26) % 26;
    }
  }
}

int main(void)
{
  char text[MAX_LENGTH];
  int key;
  int choice;

  printf("\nCaesar Cipher\n");
  printf("1. Encrypt\n");
  printf("2. Decrypt\n");
  printf("Choose an option: ");

  if (scanf("%d", &choice) != 1)
  {
    printf("Invalid choice.\n");
    return 1;
  }

  if (choice != 1 && choice != 2)
  {
    printf("Please choose 1 or 2.\n");
    return 1;
  }

  printf("Enter text: ");
  scanf(" %999[^\n]", text);

  printf("Enter key: ");

  if (scanf("%d", &key) != 1)
  {
    printf("Invalid key.\n");
    return 1;
  }

  if (choice == 1)
  {
    encrypt(text, key);
    printf("Encrypted text: %s\n", text);
  }
  else
  {
    decrypt(text, key);
    printf("Decrypted text: %s\n", text);
  }

  return 0;
}
