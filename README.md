# Blog System Project

This project is a blog system developed using 
- Maven 3.8.1
- Java 17
- Spring Framework (Security, Data Jpa, Scheduling Task, application event)
- Redis
- Google API for SMTP
- Cloudinary for file storage
- Github Action for CI/CD
- Docker && Docker Compose for deployment
- Flyway for migrating database
## Features

- User Authentication: Users can register, login, and logout securely.
- Article Management: Users can create, read, update, and delete articles.
- Comment System: Users can comment on articles and engage in discussions.
- Search Functionality: Users can search for articles based on keywords or categories.
- Responsive Design: The application is responsive and accessible across various devices.

## Technologies Used

- **Maven**: Dependency management and build automation tool.
- **Spring Boot**: Framework for building web applications and microservices.
- **Java**: Programming language used for backend development.
- **Flyway**: Database migration tool for managing database schema changes.

## Getting Started

# Getting Started

### Prepare database

* Make sure Postgres is running on your machine (version 9.6 or later)
* Create new database (if not exists) with name `emarketrental`
* Access to created database and create schema named `emarket`

### Setup environment variables
* Add/Update the following environment variables
    * DB_HOST: the host:port where Postgres instance is running (e.g., localhost:5432)
    * DB_USER: your database username
    * DB_PASSWORD: your database password
    * CONFIG_URI: fully qualified URI where the Config service is running (e.g., http://localhost:9000)
    * CONFIG_PROFILE: the deployment environment (e.g, local, dev, staging, prod). For local development, it should be set to `local`
    * CONFIG_LABEL: your customized label (e.g., `duynt`, `duoctt`). For `prod` environment, it is a good practice to set it to `master`

### Repair database versioning (optional)

Sometimes, you might have an issue on database version (Flyway exceptions). It may be caused due to inconsistent changes on versioned SQL files.

* Create new `flyway.conf` file under project's root directory
* Copy content from `flyway.example.conf` into it
* Modify the file using your own parameters
* Open terminal from project's root directory and run `flyway-repair.sh` file
* Restart Spring Boot instance

### Start Spring Boot instance

* Start Spring Boot instance as usual
