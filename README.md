
# 🍽️ FooduApp – Find & Pre-Order Dishes with a Swipe

A university project built with **Java** and **Flutter** (with a React UI), Foodu combines the best of **Tinder** and **Yelp** to match users with restaurants and allow dish pre-ordering.

---

## 📱 App Description

Foodu is designed for food lovers who want to discover dishes (not just restaurants) and order them ahead of time. Users swipe through dishes and pre-order directly from within the app.

### 👤 User Flow
- Create account with profile + payment details
- Choose food categories or search dishes
- Swipe:
  - ⬅️ Skip dish
  - ➡️ Like dish → view details (dish, restaurant, price, rating, ingredients)
- Add dish to order, modify basket, checkout
- Show up at restaurant at the confirmed time
- Leave a review after dining

### 🍽️ Restaurant Side
- Accept/decline orders with timestamps
- Manage menu and business profile
- Promote dishes via premium listing

### 🔧 Admin Panel
- Moderate reviews and images
- Edit restaurant/dish data

---

## 🛠️ Tech Stack

| Layer       | Tech                     |
|-------------|--------------------------|
| Mobile App  | Flutter, Dart            |
| Frontend    | React (client-react/)    |
| Backend     | Java (Spring Boot style) |
| Database    | (not detailed - assumed MySQL/PostgreSQL) |

---

## 🚀 How to Run

### 📦 Prerequisites
- Node.js & npm (for React client)
- Flutter SDK (for mobile app)
- Java JDK 11+ and Maven/Gradle (for backend)

### 📱 React Client
```bash
cd client-react
npm install
npm start
```

### 📱 Flutter Mobile App
```bash
cd client-flutter
flutter pub get
flutter run
```

### ☕ Java Backend
> Check `/server` or `backend/` (if applicable) for Spring Boot-style API
```bash
cd backend
./gradlew bootRun
```

---

## 📦 Project Structure

```
FooduApp/
├── client-react/           # Web frontend (React)
├── client-flutter/         # Mobile client (Flutter/Dart)
├── backend/ or server/     # Java backend API (Spring-style)
└── README.md               # You’re here
```

---

## 🧪 Team Collaboration (Trimmed Summary)

- Scrum-based Agile methodology (2-week sprints)
- Trello for task management
- Git branching model:
  - `master`: stable releases
  - `Devel`: ongoing development
  - `feat/*`, `bug/*`, `wip/*`: feature & fix branches
- Commit messages follow Conventional Commits

---

## 🧹 Conventions & Practices

- ✅ Java: Google Checkstyle
- ✅ Dart: `dartfmt`
- ✅ Tests written before new Dart features
- ✅ Code reviews before merge into `Devel` or `master`

---

## 🏁 Versioning

Tags follow sprint progression:
- `v0.1`, `v0.2`, ..., `v1.0` → End-of-sprint tags
- Final release = `v1.0`

---

## 📢 Contributions

Contributions were made as part of an academic team project. The repo uses protected branches and pull requests for quality assurance.

---
