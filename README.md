# 🔐 Backend - Sistema de Autenticação | Authentication System Backend

**🇧🇷 Português** | **🇺🇸 English**

---

## 📘 Descrição | Description

**🇧🇷**  
Este projeto é o backend de um sistema de autenticação robusto com suporte a múltiplos tipos de usuários. Inclui funcionalidades como:

- Registro e login de usuários com JWT.
- Verificação de e-mail com código OTP de 6 dígitos.
- Confirmação de conta via e-mail.
- Recuperação de senha ("Esqueci minha senha") com envio de código OTP por e-mail.

Foi desenvolvido com Spring Boot, utilizando Spring Security, JWT para autenticação e Thymeleaf para envio de e-mails personalizados.

**🇺🇸**  
This project is the backend of a robust authentication system supporting multiple user roles. It includes features like:

- User registration and login with JWT.
- Email verification with 6-digit OTP code.
- Account confirmation via email.
- Password recovery ("Forgot password") with OTP sent by email.

Built with Spring Boot, using Spring Security, JWT for authentication, and Thymeleaf for custom email templates.

---

## 🧰 Tecnologias | Technologies

- Java 17+
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- Thymeleaf (para e-mails | for emails)
- Spring Mail
- JPA & Hibernate
- PostgreSQL

---

## 🚀 Como Executar | How to Run

**🇧🇷**

```bash
git clone https://github.com/seu-usuario/seu-backend.git
cd seu-backend
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080/api/v1`.

**🇺🇸**

```bash
git clone https://github.com/your-user/your-backend.git
cd your-backend
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080/api/v1`.

---

## 📂 Estrutura do Projeto | Project Structure

```
src/
├── config/
├── controller/
├── dto/
├── entity/
├── repository/
├── service/
├── util/
```

---

## 🌐 Frontend

**🇧🇷**
Este backend foi desenvolvido para ser utilizado com o frontend em React:

🔗 Veja o repositório do frontend aqui:
[→ Frontend - React](https://github.com/luiz-matoso/gatewayz-frontend)

**🇺🇸**
This backend was built to work with the React-based frontend:

🔗 Check the frontend repository here:
[→ Frontend - React](https://github.com/luiz-matoso/gatewayz-frontend)

---

## 📄 Licença | License

MIT License

