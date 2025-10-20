# 🎓 EduQuest — Online Quiz Examination Platform

EduQuest is an online quiz-taking examination system built using **Spring Boot**, **Spring Data JPA**, and **Thymeleaf (future frontend)**.  
It allows admins to create quizzes, add questions dynamically, and manage user results — all while ensuring **fairness** by randomizing question sets for each user.

---

## 🚀 Features

✅ **Quiz Management**
- Create quizzes with title, category, and duration.
- Fetch all quizzes or individual quiz details.
- Delete quizzes when needed.

✅ **Question Management**
- Add multiple questions to any quiz by ID.
- Retrieve questions by quiz or question ID.
- Each question is linked to its parent quiz dynamically.

✅ **Result Tracking**
- Store quiz results for each user.
- Fetch all results or filter them by user ID.

✅ **User Management**
- Register and view users.
- Fetch user details by ID.

✅ **Smart Data Handling**
- Reduces complexity by storing **only question IDs** when showing quiz patterns.
- Avoids data redundancy and circular references using `@JsonManagedReference` and `@JsonBackReference`.

---

## 🧱 Tech Stack

| Layer | Technology Used |
|-------|------------------|
| **Backend** | Spring Boot 3, Spring Web, Spring Data JPA |
| **Database** | MySQL |
| **Security** | Spring Security (Basic config for REST testing) |
| **Build Tool** | Maven |
| **Testing** | Postman |
| **Frontend (Planned)** | Thymeleaf + Bootstrap 5 |

---

## 📁 Project Structure
EduQuest/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── deep/
│   │   │           └── EduQuest/
│   │   │               ├── controller/
│   │   │               │   ├── QuestionController.java
│   │   │               │   ├── QuizController.java
│   │   │               │   ├── ResultController.java
│   │   │               │   └── UserController.java
│   │   │               │
│   │   │               ├── model/
│   │   │               │   ├── Question.java
│   │   │               │   ├── Quiz.java
│   │   │               │   ├── Result.java
│   │   │               │   └── User.java
│   │   │               │
│   │   │               ├── repository/
│   │   │               │   ├── QuestionRepository.java
│   │   │               │   ├── QuizRepository.java
│   │   │               │   ├── ResultRepository.java
│   │   │               │   └── UserRepository.java
│   │   │               │
│   │   │               ├── service/
│   │   │               │   ├── QuestionService.java
│   │   │               │   ├── QuizService.java
│   │   │               │   ├── ResultService.java
│   │   │               │   └── UserService.java
│   │   │               │
│   │   │               ├── config/
│   │   │               │   └── SecurityConfig.java
│   │   │               │
│   │   │               └── EduQuestApplication.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/             # (for CSS, JS, images - future frontend)
│   │       └── templates/          # (for Thymeleaf templates)
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── deep/
│                   └── EduQuest/
│                       └── EduQuestApplicationTests.java
│
├── pom.xml
└── README.md
yaml
Copy code

---

## ⚙️ Configuration

Edit your `src/main/resources/application.properties` file:

```properties
# Database Config
spring.datasource.url=jdbc:mysql://localhost:3306/eduquest_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update

# JPA Config
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
🔗 API Endpoints (Phase 1 & 2)
🎯 Quiz APIs
Method	Endpoint	Description
POST	/api/quizzes	Create a new quiz
GET	/api/quizzes	Get all quizzes
GET	/api/quizzes/{id}	Get quiz by ID (with questions)
DELETE	/api/quizzes/{id}	Delete a quiz
GET	/api/quizzes/{id}/questions	Get all questions for a quiz
GET	/api/quizzes/{id}/random-questions	Get shuffled question IDs for randomization

❓ Question APIs
Method	Endpoint	Description
POST	/api/questions/{quizId}	Add questions to a specific quiz
GET	/api/questions	Fetch all questions
GET	/api/questions/{id}	Get a specific question by ID

👤 User APIs
Method	Endpoint	Description
POST	/api/users	Register a new user
GET	/api/users	Get all users
GET	/api/users/{id}	Get user by ID

📊 Result APIs
Method	Endpoint	Description
POST	/api/results	Save a user's quiz result
GET	/api/results	Get all results
GET	/api/results/user/{userId}	Get results by user ID

🧪 Example Test Data (for Postman)
Create Quiz
json
Copy code
{
  "title": "Java Basics",
  "category": "Programming",
  "duration": 10
}
Add Questions
POST /api/questions/1

json
Copy code
[
  {
    "questionText": "What is JVM in Java?",
    "optionA": "Java Virtual Machine",
    "optionB": "Java Vendor Machine",
    "optionC": "Java Version Manager",
    "optionD": "None of these",
    "correctAnswer": "A"
  },
  {
    "questionText": "Which keyword is used to inherit a class in Java?",
    "optionA": "this",
    "optionB": "super",
    "optionC": "extends",
    "optionD": "inherits",
    "correctAnswer": "C"
  }
]
🧩 Upcoming Phases
Phase 3: Frontend Integration (Thymeleaf UI)

User login and quiz-taking UI.

Timer-based quiz attempts.

Result visualization dashboard.

Phase 4: Advanced Features

Question randomization logic per user.

Leaderboard & result analysis.

JWT-based user authentication.

🧑‍💻 Author
👤 Deep Saha
🚀 Java Developer | Backend Enthusiast | Builder of EduQuest
🔗 GitHub • LinkedIn

⭐ Contribute
Fork this repository

Create a new branch: feature/your-feature-name

Commit your changes

Push and create a Pull Request

🏁 License
This project is licensed under the MIT License — free to use, modify, and share!

“EduQuest — Empowering fair and intelligent online assessments.”

yaml
Copy code

---

Would you like me to include **database diagram (ERD)** and a **Postman collection export** section in the README too?  
That’ll make it look even more professional for your GitHub repo.