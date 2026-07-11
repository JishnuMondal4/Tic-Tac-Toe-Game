# 🎮 Tic Tac Toe

A native Android Tic Tac Toe game built entirely with **Kotlin** and **Jetpack Compose**. Clean architecture, smooth animations, and full score tracking across rounds.

<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-1.9.22-7F52FF?logo=kotlin&logoColor=white" alt="Kotlin">
  <img src="https://img.shields.io/badge/Jetpack%20Compose-Material3-4285F4?logo=jetpackcompose&logoColor=white" alt="Jetpack Compose">
  <img src="https://img.shields.io/badge/minSdk-24-brightgreen" alt="minSdk 24">
  <img src="https://img.shields.io/badge/License-MIT-yellow.svg" alt="License MIT">
</p>

---

## About

A simple two-player Tic Tac Toe game for Android, built natively with Kotlin and Jetpack Compose — featuring animated gameplay, win/draw detection with highlighted winning lines, and persistent score tracking across rounds.

## Features

- 🎯 Classic 3x3 two-player gameplay (X vs O)
- 🏆 Automatic win/draw detection across all 8 winning combinations
- ✨ Highlighted winning line when a player wins
- 📊 Running scoreboard (X wins / O wins / Draws) that persists across rounds
- 🔄 **New Round** button — alternates who starts next for fairness
- ♻️ **Reset Match** button — clears the board and scoreboard entirely
- 🎨 Smooth Compose animations and a clean navy/teal Material 3 design
- 🧩 Game logic fully decoupled from UI (`GameEngine.kt`) for easy testing and reuse

## 🛠️ Tech Stack

| Component        | Details                          |
|-------------------|-----------------------------------|
| Language          | Kotlin                            |
| UI Toolkit        | Jetpack Compose + Material3       |
| Architecture      | Single-Activity, Compose state management |
| Build System      | Gradle (Kotlin DSL)               |
| Min SDK           | 24 (Android 7.0)                  |
| Target SDK        | 34 (Android 14)                   |

##  Project Structure

```
TicTacToe/
├── app/
│   └── src/main/java/com/example/tictactoe/
│       ├── MainActivity.kt        # Compose UI: board, scoreboard, controls
│       ├── GameEngine.kt          # Pure game logic (win/draw detection)
│       └── ui/theme/              # Color palette & Material3 theme
├── build.gradle.kts
├── settings.gradle.kts
└── gradle/
```

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or newer
- JDK 8+

### Setup
1. Clone the repository
   ```bash
   git clone https://github.com/jishnuMondal4/Tic-Tac-Toe-Game.git
   ```
2. Open the project folder in Android Studio
3. Let Gradle sync finish
4. Run on an emulator or physical device (▶️ Run, or Shift+F10)

## 🎮 How to Play

1. Players take turns tapping empty cells — X always goes first in the match
2. First player to align three marks horizontally, vertically, or diagonally wins the round
3. Tap **New Round** to play again (starting player alternates)
4. Tap **Reset Match** to clear the board and scoreboard completely

## 📄 License

This project is licensed under the MIT License — feel free to use, modify, and distribute.

## 🙋 Author

Built by Jishnu Mondal — B.Tech CSE student, Android developer.
