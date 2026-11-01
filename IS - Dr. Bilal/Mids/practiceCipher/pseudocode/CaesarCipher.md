# What is Caesar Cipher?

The **Caesar Cipher** is a simple substitution cipher that replaces each letter in a message with another letter located a fixed number of positions further down or up the alphabet.

---

## 1. Mathematical Formulas

To process the alphabet mathematically, we map each letter to a number from **0 to 25** ($A = 0, B = 1, \dots, Z = 25$).

### Encryption Formula

\[E_k(p) = (p + k) \pmod{26}\]

### Decryption Formula

\[D_k(c) = (c - k + 26) \pmod{26}\]

*Where:*

* $p$ = Plaintext letter index
* $c$ = Ciphertext letter index
* $k$ = Secret shift key

---

## 2. Encryption Algorithm

1. **Start**
2. Input the plaintext.
3. Input the encryption key ($k$).
4. Reduce the key to the valid range using:
   \[k = k \pmod{26}\]
5. For each character in the plaintext:
   * If it is an English letter, convert it to its alphabet index.
   * Calculate the encrypted index ($c$):
     \[c = (p + k) \pmod{26}\]
   * Convert the new index $c$ back into a letter.
   * Preserve non-alphabetic characters (spaces, punctuation) unchanged.
6. **Display the ciphertext.**
7. **Stop**

---

## 3. Decryption Algorithm

1. **Start**
2. Input the ciphertext.
3. Input the encryption key ($k$).
4. Reduce the key to the valid range using:
   \[k = k \pmod{26}\]
5. For each character in the ciphertext:
   * If it is an English letter, convert it to its alphabet index.
   * Calculate the decrypted index ($p$):
     \[p = (c - k + 26) \pmod{26}\]
   * Convert the new index $p back into a letter.
   * Preserve non-alphabetic characters unchanged.
6. **Display the plaintext.**
7. **Stop**
