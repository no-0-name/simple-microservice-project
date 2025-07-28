# Simple Microservice Project

![Java](https://img.shields.io/badge/java-red)
![Spring Boot](https://img.shields.io/badge/spring%20boot-brightgreen)
![PostgreSQL](https://img.shields.io/badge/postgresql-blue)
![Docker](https://img.shields.io/badge/docker-blue)

REST API for an online marketplace with authentication, product management, shopping cart, and order processing.

## 🌟 Features
- 🔐 JWT Authentication
- 📧 Event-driven email notifications
- ✨ CRUD operations for user management
- 👮 Role-Based Access Control (USER/ADMIN)
- 🔌 Microservice Communication using HTTP

  
## 🛠 Technologies
- **Java**
- **PostgresQL**
- **Lombok**
- **Spring Boot**
- **JWT**
- **Maven**
- **Docker**
- **FlyWay**


## ⚙ Configuration
#### Without docker 🐳:
1. Create a PostgreSQL database
2. Configure `application.properties` in auth service
3. Create google email app on site `https://myaccount.google.com/security` and get your own app password
4. Configure `application.properties` in notification service
5. Run your project
#### With docker 🐳:
1. Create and configure `.env` file in main directory
2. Create google email app on site `https://myaccount.google.com/security` and get your own app password
3. Configure `application.properties` in notification service
4. Run your project with comand `docker-compose up --build`

## 👥 Contributing

Contributions are welcome!

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request
