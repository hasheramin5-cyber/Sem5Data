# encryption.py - turns normal text into secret text (method: P x K)

def encrypt(text, key):
    # keep only letters and make them CAPITAL
    text = "".join(c for c in text.upper() if c.isalpha())

    # Hill cipher works in pairs, so if odd length add an X
    if len(text) % 2 == 1:
        text += "X"

    result = ""
    for i in range(0, len(text), 2):
        a = ord(text[i]) - 65        # A=0, B=1, ... Z=25
        b = ord(text[i + 1]) - 65

        # plaintext pair FIRST, then key:  C = P x K  (mod 26)
        x = (a * key[0][0] + b * key[1][0]) % 26
        y = (a * key[0][1] + b * key[1][1]) % 26

        result += chr(x + 65) + chr(y + 65)   # numbers back to letters
    return result
