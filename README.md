# Paws & Pistols

Welcome to **Paws & Pistols** – the best cat cowboy game on this side of the sandbox! Unleash your inner cowboy cat and challenge a friend to a high-speed showdown on a single device. Who will be the quickest whiskers in town?

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [How to Play](#how-to-play)
- [Tech Stack](#tech-stack)


---

## Overview

In **Paws & Pistols**, two feline cowboys face off in a best-of-three reaction duel. The game uses your device's sensors to detect a shake – because sometimes you need a bit of a cat-tastrophe to get things rolling. Once the shake is detected, the showdown begins with a random delay and a "GO!" signal. Each cowboy must tap their half of the screen as quickly as possible to shoot (figuratively, of course) and claim victory!

---

## Features

- **Feline Cowboy Theme:**  
  Play as cool, gunslinging cats with a purr-sonality that’s off the charts.

- **Shake to Start:**  
  Shake your device or click the button to start a game.

- **Random "Get Ready" Delay:**  
  A suspenseful, random delay (between 1-5 seconds) builds the tension before the showdown begins.

- **Split-Screen Reaction Duel:**  
  Each player taps their side of the screen. Player 1 on the bottom, Player 2 (upside down) on the top—for a fair fight!

- **Best-of-Three Rounds:**  
  Show off your reflexes over three rounds. The first to win two rounds is crowned the champion.

- **Early Tap Penalty:**  
  Tapping before "GO!"? Oops! The other cowboy gets the win for that round. No cat-napping allowed here!

- **High Score Tracking:**  
  Our ScoreManager keeps track of the best reaction times—impress your friends with your lightning-fast paws!

---

## How to Play

1. **Start the Game:**  
   Shake your device or tap the start button to awaken your inner cowboy cat.

2. **Get Ready:**  
   Watch as the screen displays "Get Ready..." for a random interval. Keep your paws on standby!

3. **GO! Signal:**  
   When the screen flashes "GO!", tap your side of the screen as quickly as possible.

4. **Round Results:**  
   The game records your reaction time. If you tap too early, the other cowboy wins that round by default.

5. **Win the Match:**  
   Best out of three rounds wins the duel! Show off your reflexes and claim your glory.

---

## Tech Stack

- **Kotlin:**  
  The primary language used for Android development, offering modern syntax, safety, and conciseness.

- **Android Jetpack Compose:**  
  A modern, declarative UI toolkit for building native Android interfaces with less code and more powerful state management.

- **Android Lifecycle & ViewModel:**  
  For managing UI-related data in a lifecycle-conscious manner, ensuring that your game state persists across configuration changes.

- **Android Sensors API:**  
  Used to access the device’s accelerometer for shake detection, which is a core mechanic of the game.

- **Kotlin Coroutines:**  
  For handling asynchronous tasks such as random delays between rounds and timing user reactions without blocking the UI thread.

- **Material3 Components:**  
  To provide modern design components and theming consistent with current Android design guidelines.


