# codefellowship
CodeFellowship project allow users to create accounts, log in, and view profile. Users can provide their basic information, such as username, password, first name, last name, date of birth, and a short bio.

Features
User signup: Users can create an account by providing their username, password, first name, last name, date of birth, and a bio.

User Login: signed up users can log in to their accounts.

User Profile: Users can view their own profile information, including username, first name, last name, date of birth, and bio.

Security: The application uses Spring Security for user authentication and authorization. Passwords are encrypted using BCrypt.

Project Structure
ApplicationUser: This class represents the user entity and implements UserDetails for Spring Security integration.

ApplicationUserController: This controller handles user-related operations such as registration, login, and profile viewing.

ApplicationUserRepository: This repository interface provides methods to interact with the database for user-related operations.

UserDetailsServiceImpl: This service class implements UserDetailsService to load user details during authentication.

WebSecurityConfig: This class configures security settings, authentication manager, and defines URL access rules.

Dependencies
Spring Boot
Spring Security
Thymeleaf (for template rendering)
Spring Data JPA (for database interaction)
BCrypt (for password encryption)
Getting Started
Clone the repository to your local machine.
Set up a database (e.g., PostgreSQL) and configure the application's application.properties file with the database URL, username, and password.
Run the application using an IDE or with ./mvnw spring-boot:run in the terminal.
Access the application at http://localhost:8080.
Usage
Visit http://localhost:8080/signup to create a new account.
Log in using your credentials at http://localhost:8080/login.
After logging in, you can view your profile or log out.
Notes
This is a basic implementation. Additional features like user interaction, posts, and social networking capabilities can be added in the future.

Make sure to handle user sessions securely, especially in a production environment.

Always follow best practices for web application security, such as using HTTPS, input validation, and secure coding practices.

This project serves as a foundation and can be extended based on specific requirements and use cases.





