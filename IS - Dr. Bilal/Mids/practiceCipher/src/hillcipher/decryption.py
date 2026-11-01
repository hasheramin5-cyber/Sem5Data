# decryption.py - turns secret text back into normal text (method: C x K inverse)

def decrypt(text, key):
    # Step 1: find the "inverse key" (the key that undoes the key)
    det = (key[0][0] * key[1][1] - key[0][1] * key[1][0]) % 26
    inv_det = pow(det, -1, 26)   # works only if det has no common factor with 26

    inv_key = [
        [ key[1][1] * inv_det % 26, -key[0][1] * inv_det % 26],
        [-key[1][0] * inv_det % 26,  key[0][0] * inv_det % 26],
    ]

    # Step 2: ciphertext pair FIRST, then inverse key:  P = C x K^-1  (mod 26)
    result = ""
    for i in range(0, len(text), 2):
        a = ord(text[i]) - 65
        b = ord(text[i + 1]) - 65

        x = (a * inv_key[0][0] + b * inv_key[1][0]) % 26
        y = (a * inv_key[0][1] + b * inv_key[1][1]) % 26

        result += chr(x + 65) + chr(y + 65)
    return result
