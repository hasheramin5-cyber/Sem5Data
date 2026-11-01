# main.py - run THIS file:  python main.py

from encryption import encrypt
from decryption import decrypt

# The secret key (2x2 matrix). Keep it secret!
# Rule: this key must be "invertible" - [[3, 3], [2, 5]] is a safe one.
key = [[3, 3],
       [2, 5]]

message = input("Enter your message: ")

secret = encrypt(message, key)
print("Encrypted:", secret)

original = decrypt(secret, key)
print("Decrypted:", original)
