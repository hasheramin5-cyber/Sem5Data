# Playfair Cipher Method

The **Playfair Cipher** is the first practical **digraph substitution cipher**, which means it encrypts pairs of letters (digraphs) together instead of substituting single letters one by one. It was invented in 1854 by Sir Charles Wheatstone and popularized by Lord Playfair for military use.

---

## 1. How the Method Works

### Step A: The 5x5 Key Matrix

* A **5x5 grid** of 25 squares is created using a secret keyword.
* Letters of the keyword are written first (with duplicate letters removed).
* The remaining spaces are filled with the rest of the alphabet in standard alphabetical order.
* Because the English alphabet has 26 letters and the grid has only 25 slots, **I and J share a single cell** (usually treated as **I**).

### Step B: Preparing the Plaintext

* Strip out all spaces, numbers, and punctuation.
* Split the message into pairs of two letters (digraphs).
* If a pair has two identical letters side-by-side (e.g., "LL"), insert a filler letter like **X** (making it "LX" and "L...").
* If the final message has an odd number of letters, append a filler letter like **Z** (or **X**) to complete the final pair.

### Step C: The Three Encryption Rules

For each pair of letters, locate them on the 5x5 grid and apply one of these rules:

1. **Same Row Rule:** If both letters are in the *same row*, replace each with the letter immediately to its **right** (wrapping around to the far left if you reach the edge of the grid).
2. **Same Column Rule:** If both letters are in the *same column*, replace each with the letter immediately **below** it (wrapping around to the top if you reach the bottom).
3. **Rectangle Rule:** If the letters form the **opposite corners of a rectangle** on the grid, replace them with the letters on the *other two horizontal corners* of that same rectangle. Order matters: pick the horizontal mirror of the first letter first.

---

## 2. Complete Example

### Matrix Generation

* **Keyword:** `MONARCHY` (No duplicate letters to remove)
* **Filled Alphabet Grid:**

| | | | | |
| :---: | :---: | :---: | :---: | :---: |
| **M** | **O** | **N** | **A** | **R** |
| **C** | **H** | **Y** | **B** | **D** |
| **E** | **F** | **G** | **I/J** | **K** |
| **L** | **P** | **Q** | **S** | **T** |
| **U** | **V** | **W** | **X** | **Z** |

### Message Preparation

* **Plaintext:** `INSTRUMENTS`
* **Digraphs:** `IN`, `ST`, `RU`, `ME`, `NT` *(Even length, no duplicate letter clashes)*

### Step-by-Step Encryption

* **`IN` (Rectangle Rule):** `I` and `N` form a rectangle. The horizontal corners give **`GA`**.
* **`ST` (Same Row Rule):** `S` and `T` are in row 4. Moving right (and wrapping around for T) gives **`TL`**.
* **`RU` (Same Column Rule):** `R` and `U` are in column 5. Moving down (and wrapping around for Z) gives **`MI`** (or **`MJ`**).
* **`ME` (Same Column Rule):** `M` and `E` are in column 1. Moving down gives **`CL`**.
* **`NT` (Rectangle Rule):** `N` and `T` form a rectangle. The horizontal corners give **`RQ`**.

**Final Ciphertext:** `GATLMCLLRQ`
