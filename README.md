# 🌤 Weather Forecast App

A simple Android weather app built with Kotlin, Jetpack Compose, 
and MVVM architectureMVVM + Repository pattern following Google’s Architecture Template (Base), 
using the OpenWeatherMap API to fetch real-time weather data.

# 📱 Features
- Fetch and display current weather (temperature, feels like, humidity, wind, visibility, etc.)
- Show 3-hourly forecast and 5-day forecast with daily min/max temperatures
- Loading screen while fetching data using Lottie Animation
- Error handling for network and API failures (e.g. “Please try again”)
- Detect and handle no internet connection
- Background changes automatically for day/night
- Temperature toggle between Celsius and Fahrenheit
- MVVM + Repository pattern following Google’s Architecture Template (Base)
- Dependency Injection with Hilt
- Network Layer with Retrofit

# 🧩 Tech Stack
| Layer                | Technology                             |
| -------------------- | -------------------------------------- |
| Language             | Kotlin                                 |
| UI                   | Jetpack Compose + Material 3           |
| Architecture         | MVVM (ViewModel, Repository, UI State) |
| Dependency Injection | Hilt                                   |
| Networking           | Retrofit + OkHttp3 + Gson Converter    |
| Image Loading        | Coil                                   |
| Animation            | Lottie Compose                         |
| Async                | Kotlin Coroutines + Flow               |

# Project Setup
- Android Studio Koala+
- Android Gradle Plugin 8+
- JDK 11+
- Minimum SDK level 24

# API
- The app uses OpenWeatherMap API to fetch weather data (free tier).
  (https://openweathermap.org/api)

# Tested on:
-  Tested on Pixel 7a Emulator
-  Tablet responsive (tested on Pixel Tablet emulator)
-  Tested on Physical Device - Oppo A93

# 🚀 How to Run
1. Download or Clone the project
2. Open project in Android Studio
3. Add your API key to local.properties -> Ex. API_KEY=123456677
4. Clean Project
5. Re-build Project
6. Run on emulator (Pixel 7a)

[Note1]: 
To test error handling for network or API failures, 
temporarily modify the API endpoint to an incorrect URL and re-run the project.
When the app attempts to call the API, the error handling UI will automatically appear on the screen.

[Note2]:
If the emulator cannot connect to the API and shows “Internet Error Message”,
Please toggle Wi-Fi or airplane mode inside the emulator to reset the network connection.

## 👩‍💻 Author - Pichamol Phookpun (Mint) Android Developer