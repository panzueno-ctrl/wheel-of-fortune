# Wheel of Fortune 🎡

A Java console-based implementation of the famous TV game show "Wheel of Fortune". This academic project was developed with a strong focus on Object-Oriented Programming (OOP), encapsulation, inheritance, polymorphism, and advanced exception handling.

## 🚀 Main Features

- **Classic and Extended Modes:** Play with standard rules or enable the extended mode (`--mode extended`) to introduce new special wedges (Jolly and JollyPlus).
- **Custom Phrase Libraries:** Import your own text files (`--lib`) to play with custom categories and phrases, bypassing standard array limits using Java Collections.
- **Repeatable Matches:** Use a specific seed (`--seed`) to generate deterministic matches, useful for testing and debugging.
- **Validation Engine:** Foolproof input validation with Exception Chaining and automatic character conversion.
- **Precise Financial Calculations:** Uses `BigDecimal` for accurate rounding of monetary prizes.

## 📁 Project Structure

The source code is modularly organized:
- `ruotadellafortuna.main`: Application entry point.
- `ruotadellafortuna.coredelgioco`: Logic for turns, rounds, and game progression.
- `ruotadellafortuna.modello`: Physical game entities (Wheel, Wedges, Phrase, Player).
- `ruotadellafortuna.util`: Utility classes for input validation and randomization.

## 🛠️ How to Compile and Run

Make sure you have the **Java JDK** installed on your system (tested on Linux/Ubuntu and Windows).

### 1. Compilation
Open your terminal, navigate to the `src` folder, and compile all Java files:
```bash
cd src
find . -name "*.java" | xargs javac
```

### 2. Basic Execution
To launch the game in its classic mode with default phrases:
```bash
java ruotadellafortuna.main.Main
```

### 3. Launch Options (Arguments)

You can combine different arguments to customize the gaming experience:

- **Extended Mode:** Adds special wedges to the wheel.
  ```bash
  java ruotadellafortuna.main.Main --mode extended
  ```
- **Custom Library:** Uses an external text file for phrases. Try the included `frasiCinema.txt` or `frasiInformatica.txt`.
  ```bash
  java ruotadellafortuna.main.Main --lib ../frasiCinema.txt
  ```
- **Custom Seed:** To replicate the exact same random draw.
  ```bash
  java ruotadellafortuna.main.Main --seed 12345
  ```

*Combined example:*
```bash
java ruotadellafortuna.main.Main --mode extended --lib ../frasiInformatica.txt --seed 42
```

## 📝 How to Create Your Own Phrase Library

You can easily create new text files to expand the game with your favorite topics. Each line in the file must strictly follow this syntax:
`DifficultyLevel#THEME#THE PHRASE TO GUESS`

**Formatting Rules:**
- **Level:** An integer between 1 and 6.
- **Separator:** The special character `#`.
- **Text:** Must be in UPPERCASE.

*Example:*
> 1#FILM#THE LION KING
> 3#ACTORS#LEONARDO DI CAPRIO
> 6#QUOTES#I AM THE KING OF THE WORLD

## 📄 Documentation
For more details on architectural choices, patterns used, and UML diagrams, please refer to the `RelazioneProgetto.pdf` file included in the repository (in Italian).
