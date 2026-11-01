def prepare_key(key):
    key = key.upper().replace("J", "I")
    seen = set()
    matrix_chars = []
    
    # Add key characters
    for char in key:
        if char.isalpha() and char not in seen:
            seen.add(char)
            matrix_chars.append(char)
            
    # Fill remaining alphabet
    for i in range(65, 91):
        char = chr(i)
        if char == "J":
            continue
        if char not in seen:
            seen.add(char)
            matrix_chars.append(char)
            
    # Convert to 5x5 grid
    return [matrix_chars[i:i+5] for i in range(0, 25, 5)]

def find_position(matrix, char):
    for r in range(5):
        for c in range(5):
            if matrix[r][c] == char:
                return r, c
    return 0, 0

def prepare_text(text):
    text = "".join([c for c in text.upper() if c.isalpha()]).replace("J", "I")
    prepared = []
    i = 0
    while i < len(text):
        prepared.append(text[i])
        if i + 1 < len(text):
            if text[i] == text[i+1]:
                prepared.append('X')  # Pad duplicate
                i += 1
            else:
                prepared.append(text[i+1])
                i += 2
        else:
            prepared.append('X')  # Pad odd length
            i += 1
    return "".join(prepared)

def encrypt_playfair(text, key_word):
    matrix = prepare_key(key_word)
    text = prepare_text(text)
    ciphertext = []
    
    for i in range(0, len(text), 2):
        r1, c1 = find_position(matrix, text[i])
        r2, c2 = find_position(matrix, text[i+1])
        
        if r1 == r2:  # Same Row
            ciphertext.append(matrix[r1][(c1 + 1) % 5])
            ciphertext.append(matrix[r2][(c2 + 1) % 5])
        elif c1 == c2:  # Same Column
            ciphertext.append(matrix[(r1 + 1) % 5][c1])
            ciphertext.append(matrix[(r2 + 1) % 5][c2])
        else:  # Rectangle Rule
            ciphertext.append(matrix[r1][c2])
            ciphertext.append(matrix[r2][c1])
            
    return "".join(ciphertext)


if __name__ == "__main__":
    plaintext = "INSTRUMENTS"
    key = "MONARCHY"
    cipher = encrypt_playfair(plaintext, key)
    print(f"Key: {key}")
    print(f"Plaintext: {plaintext}")
    print(f"Ciphertext: {cipher}")
