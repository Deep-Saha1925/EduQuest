# 🎓 EduQuest — Subject-Based Quiz Application

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=flat-square&logo=springboot)
![MySQL](https://img.shields.io/badge/MySQL-8.x-blue?style=flat-square&logo=mysql)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.1-green?style=flat-square)
![Security](https://img.shields.io/badge/Spring%20Security-Protected-red?style=flat-square&logo=springsecurity)

A full-stack quiz web application built with **Java Spring Boot** and **Thymeleaf**. Students can take subject-specific quizzes and admins can manage questions through a protected dashboard with bulk upload support.

---

## 📌 Overview

EduQuest allows students to select a subject, choose how many questions to attempt, take the quiz, and view their results — all without requiring a login. The admin panel is protected by Spring Security and lets admins add, edit, delete, and bulk upload questions via CSV or Excel.

---

## 🚀 Features

### 👨‍🎓 Student Features
- Browse all available subjects on the home page as visual cards
- Select number of questions (5, 10, 15, 20)
- Take randomized subject-specific quizzes
- View detailed results with score and category badge
- No login required — fully open access

### 🔑 Admin Features
- Protected dashboard — login required to access `/admin/**`
- Add, edit, and delete individual questions
- Paginated question table with category filter dropdown
- **Bulk upload** questions via `.csv` or `.xlsx` file
- Flash messages for upload success/error feedback
- Question stats (total, active, categories)

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| **Backend** | Java 17, Spring Boot 3.x, Spring MVC |
| **Frontend** | Thymeleaf, Plain CSS, Bootstrap Icons, DM Sans / DM Serif Display |
| **Database** | MySQL with Spring Data JPA |
| **Security** | Spring Security — admin routes protected, all quiz routes public |
| **File Upload** | Apache POI (Excel) + OpenCSV (CSV) |

---

## 🎨 UI Design

All pages use a consistent CSS variable system with purple/indigo as the primary accent.

### Color Coding by Subject
| Subject | Theme |
|---|---|
| Networking | Blue |
| Java | Red |
| Python | Green |
| Database | Yellow |
| Web Development | Purple |
| Other | Indigo (default) |

---

## 📄 Pages & Routes

| Page | Route | Access |
|---|---|---|
| Home — Subject Cards | `/` | Public |
| Category Selection | `/quiz/category/{category}` | Public |
| Quiz Question | `/quiz/question` | Public |
| Submit Answer | `/quiz/answer` | Public |
| Results | `/quiz/result` | Public |
| Admin Dashboard | `/admin` | Login Required |
| Admin Upload | `/admin/upload` | Login Required |
| Add Question | `/admin/question/new` | Login Required |
| Edit Question | `/admin/question/edit/{id}` | Login Required |
| Delete Question | `/admin/question/delete/{id}` | Login Required |
| Login | `/login` | Public |

---

## 🔐 Security

Only `/admin/**` routes require authentication. Everything else is freely accessible.

```java
.requestMatchers("/admin/**").authenticated()
.anyRequest().permitAll()
```

Admin credentials are configured in `SecurityConfig.java`:
- **Username:** `admin`
- **Password:** `admin123` *(change before deploying)*

---

## 📦 Bulk Upload

Admins can upload questions in bulk via the admin panel. Supported formats: `.csv`, `.xlsx`, `.xls`

### Required Column Order

| Column | Description |
|---|---|
| `questionText` | The question |
| `optionA` | Option A |
| `optionB` | Option B |
| `optionC` | Option C |
| `optionD` | Option D |
| `correctAnswer` | Exact text of correct option |
| `category` | Subject name (e.g. Java, Python) |
| `difficulty` | Easy / Medium / Hard |

### Sample CSV Row
```
What is a pointer in C?,A variable storing address,A loop,A function,A constant,A variable storing address,C,Medium
```

---

## 📂 Project Structure

```
src/main/java/com/deep/EduQuest/
│── controller/
│   ├── QuizController.java       # Student quiz routes
│   ├── AdminController.java      # Admin CRUD + upload
│   └── LoginController.java      # Login + access-denied
│── model/
│   └── Question.java             # Question entity
│── repository/
│   └── QuestionRepository.java   # JPA + custom queries
│── service/
│   ├── QuizService.java          # Quiz logic, pagination, categories
│   └── FileUploadService.java    # CSV and Excel parsing
│── config/
│   └── SecurityConfig.java       # Spring Security config

src/main/resources/templates/
│── index.html                    # Home — subject cards
│── category-selection.html       # Quiz config page
│── question.html                 # Quiz question view
│── result.html                   # Results with category badge
│── admin.html                    # Admin dashboard
│── login.html                    # Admin login page
│── access-denied.html            # 403 page
```

---

## ⚙️ Session Variables

| Variable | Purpose |
|---|---|
| `quizQuestions` | List of selected questions |
| `quizCategory` | Current quiz subject |
| `currentQuestionIndex` | Progress tracker |
| `userAnswers` | Student's responses |

---

## ▶️ Running the Project

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/eduquest.git
cd eduquest
```

### 2. Configure MySQL
Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/quiz_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

### 3. Run the Application
```bash
mvn spring-boot:run
```

Then open [http://localhost:8080](http://localhost:8080)

Admin panel: [http://localhost:8080/admin](http://localhost:8080/admin)

---

## 📊 Demo Data

The project includes sample question CSV files covering:

| Category | Questions |
|---|---|
| C | 25 |
| Java | 25 |
| Python | 25 |
| DBMS | 25 |
| OS | 25 |
| DSA | 25 |

Upload any of these via the bulk upload feature in the admin panel.

---

## 🐛 Troubleshooting

**No subjects showing on home page?**
- Ensure questions exist in the database with a filled `category` field
- Upload sample questions via the admin bulk upload

**Category filter not working?**
- Category names are case-sensitive — ensure they match exactly in the database

**Admin page not accessible?**
- Navigate to `/login` and use credentials `admin` / `admin123`
- Ensure Spring Security dependency is in `pom.xml`

**File upload failing?**
- Ensure Apache POI and OpenCSV are in `pom.xml`
- Check that column order matches exactly: `questionText, optionA, optionB, optionC, optionD, correctAnswer, category, difficulty`

---

## 📌 Future Enhancements
- ⏱️ Timer per question
- 📊 Subject-wise performance analytics
- 🏆 Leaderboard per category
- 👤 Student login and history tracking
- 📱 Progressive Web App (PWA)
- 📄 PDF certificate generation
- 🎮 Gamification — badges and points
- 📧 Email results

---

## 🏆 Conclusion

EduQuest provides a clean, organized, and secure platform for subject-based quizzing. Built on Spring Boot with a modern CSS frontend, it features admin-only protection, bulk question management, category filtering with pagination, and a smooth student quiz experience — all ready to extend further.