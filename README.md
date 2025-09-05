# GWeather App


A modern Android weather application built with **Kotlin**, **Jetpack Compose**, and **Clean Architecture**. Fetches weather data from **OpenWeather API** and stores historical weather data locally using **Room**. Includes **Firebase Authentication** for user registration and login.

---

## Features

- 🔐 User **Registration** and **Login** via Firebase Authentication  
- 🌍 Fetch **current weather** for the user’s location  
- 🌡️ Display **temperature, sunrise, sunset, and weather icon**  
- 📜 Maintain **historical weather** data locally  
- 📑 Two tabs:  
  - **Current Weather**  
  - **Weather History**  
- 🎨 Modern **Material3 UI design** using Jetpack Compose  
- 📍 Location permission handling  
- 🔄 Logout functionality

---

## UI Design

The app follows **modern Compose UI practices**:  

- **Cards** for weather display with **temperature, city/country, sunrise/sunset, and weather icon**  
- **Dynamic weather icons** based on time of day (sun before 6 PM, moon after 6 PM)  
- **Top AppBar** with logout button  
- **Two tabs layout**:  
  - **Current Weather** – shows the latest weather info  
  - **Weather History** – shows historical entries in a scrollable list with timestamp  
- **CircularProgressIndicator** centered for loading states  
- Responsive and adaptive UI for different screen sizes

---

## Architecture

**Clean Architecture** with three layers:

1. **Data Layer**
   - Remote: Fetches weather via **Retrofit**  
   - Local: Stores historical weather via **Room**  
   - Mapper: DTO ↔ Entity ↔ Domain Model

2. **Domain Layer**
   - Use Cases: `GetCurrentWeatherUseCase`, `GetWeatherHistoryUseCase`  
   - Entities: `Weather`  
   - Repositories: `WeatherRepository`, `AuthRepository`

3. **Presentation Layer**
   - ViewModels: `CurrentWeatherViewModel`, `WeatherHistoryViewModel`, `AuthViewModel`  
   - UI: Jetpack Compose screens  
   - State Management: `UiState` and `AuthState` via `StateFlow`

---

## Libraries Used

| Purpose                  | Library / Tool |
|---------------------------|----------------|
| Dependency Injection      | Dagger Hilt |
| Networking                | Retrofit, OkHttp, Moshi |
| Local Storage             | Room |
| Coroutines & Flow         | kotlinx-coroutines |
| UI                        | Jetpack Compose, Material3 |
| Firebase                  | Firebase Authentication |
| Testing                   | JUnit, Mockito-Kotlin, kotlinx-coroutines-test |
| Logging (optional)        | OkHttp Logging Interceptor |

---

## OpenWeather API Setup

1. Register for a free API key at [OpenWeather](https://openweathermap.org/api).  
2. Add your API key in `local.properties`:

```properties
OPEN_WEATHER_KEY=YOUR_API_KEY_HERE
```

3. Retrofit automatically appends the API key via an **OkHttp interceptor**.  
⚠️ **Do not commit your API key to version control.**

---

## Running the App

1. Clone the repository:

```bash
git clone https://github.com/yourusername/gweather.git
```

2. Add your **OpenWeather API key** in `local.properties`.  

3. Run the app on an Android device or emulator with **location enabled**.

---

## Testing

- **Unit tests** for:  
  - `AuthViewModel` – registration & login success/failure  
  - `CurrentWeatherViewModel` – fetch current weather  
  - `WeatherHistoryViewModel` – historical weather list

- **Mocking**: Mockito-Kotlin  
- **Coroutine testing**: kotlinx-coroutines-test  
- **Flow testing**: `advanceUntilIdle()` ensures all `viewModelScope` coroutines complete before assertions

---

## Permissions

- **Location**: Required to fetch weather for the current location  
- Requests permission **on app launch**; user must accept to proceed

---

## Notes

- Modern **Material3 Compose UI**  
- Weather icons adapt to **time of day**  
- Historical weather saved **only if last saved timestamp is different**  
- Smooth **loading indicators** with `CircularProgressIndicator`

