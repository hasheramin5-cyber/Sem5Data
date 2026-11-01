def encrypt_text(text, key):
    result = ""
    for ch in text:
        if 'A' <= ch <= 'Z':
            result += chr((ord(ch) - ord('A') + key) % 26 + ord('A'))
        elif 'a' <= ch <= 'z':
            result += chr((ord(ch) - ord('a') + key) % 26 + ord('a'))
        else:
            result += ch
    return result


def decrypt_text(text, key):
    return encrypt_text(text, -key)


def main():
    print("\nCaesar Cipher")
    print("1. Encrypt")
    print("2. Decrypt")
    choice = input("Choose: ").strip()

    text = input("Enter text: ")
    key = int(input("Enter key: "))

    if choice == "1":
        print("Encrypted text:", encrypt_text(text, key))
    elif choice == "2":
        print("Decrypted text:", decrypt_text(text, key))
    else:
        print("Invalid choice!")


if __name__ == "__main__":
    main()