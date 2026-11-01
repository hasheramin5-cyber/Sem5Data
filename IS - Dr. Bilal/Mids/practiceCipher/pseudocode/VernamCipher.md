# What is Vernam Cipher?

The **Vernam Cipher** is a symmetric encryption technique that combines plaintext with a key of equal length to produce ciphertext. In a character-based implementation, letters are assigned numerical values (\(A = 0, B = 1, \dots, Z = 25\)).

---

## 1. Mathematical Formulas

### Encryption Formula

For each character at position i:
\[C_i = (P_i + K_i) \pmod{26}\]

### Decryption Formula

For each character at position i:
\[P_i = (C_i - K_i + 26) \pmod{26}\]

*Where:*

* \(P_i\) = Plaintext letter index at position i
* \(C_i\) = Ciphertext letter index at position i
* \(K_i\) = Key letter index at position i

---

## 2. Encryption Algorithm

1. **Start**
2. Input the plaintext.
3. Input the encryption key (must be of the exact same length as the plaintext).
4. For each character:
   * Convert the plaintext and key characters into numerical values (\(A = 0, B = 1, \dots, Z = 25\)).
   * Apply the encryption formula:
     \[C_i = (P_i + K_i) \pmod{26}\]
5. Convert the resulting numeric values back into alphabetic letters.
6. **Display the ciphertext.**
7. **Stop**

---

## 3. Decryption Algorithm

1. **Start**
2. Input the ciphertext.
3. Input the original encryption key.
4. For each character:
   * Convert the ciphertext and key characters into numerical values.
   * Apply the decryption formula:
     \[P_i = (C_i - K_i + 26) \pmod{26}\]
5. Convert the resulting numeric values back into alphabetic letters.
6. **Display the plaintext.**
7. **Stop**

---

## 4. Complete Example

* **Plaintext:** `HELLO`
* **Key:** `XMCKL`

### Step-by-Step Breakdown

| Character Position (i) | Plaintext (\(P_i\)) | Key (\(K_i\)) | Encryption: \((P_i + K_i) \pmod{26}\) | Ciphertext (\(C_i\)) |
| :---: | :---: | :---: | :---: | :---: |
| 0 | `H` (7) | `X` (23) | \((7 + 23) \pmod{26} = 4\) | `E` |
| 1 | `E` (4) | `M` (12) | \((4 + 12) \pmod{26} = 16\) | `Q` |
| 2 | `L` (11) | `C` (2) | \((11 + 2) \pmod{26} = 13\) | `N` |
| 3 | `L` (11) | `K` (10) | \((11 + 10) \pmod{26} = 21\) | `V` |
| 4 | `O` (14) | `L` (11) | \((14 + 11) \pmod{26} = 25\) | `Z` |

* **Final Ciphertext:** `EQNVZ`
* **Decrypted Text:** `HELLO`

---

## 5. Advantages & Disadvantages

### Advantages

* **Perfect Secrecy:** Provides perfect secrecy when implemented as a one-time pad with a truly random key used only once.
* **Strong Security:** A properly implemented one-time pad is theoretically unbreakable.
* **No Patterns:** Ciphertext does not reveal plaintext patterns when the one-time pad requirements are met.
* **Simple Algorithm:** Encryption and decryption use straightforward mathematical operations.

### Disadvantages

* **Key Management:** Requires secure generation and distribution of the key.
* **Key Reuse:** Reusing a key completely compromises security.
* **Key Storage:** Keys must be stored and protected securely.
* **Practicality:** Generating and distributing long random keys can be difficult.
* **Key Length:** The key must be at least as long as the message for a one-time pad.
