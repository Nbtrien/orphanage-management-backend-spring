# Orphanage Management System - REST API Application Using Spring Boot
This is a REST API project for an orphanage management system using Spring Boot. The project helps manage information about orphans, staff, donation, and important documents in an orphanage.


## Technologies Used

The application leverages a set of technologies and libraries to provide a comprehensive and reliable orphanage management system. The key technologies used include:
- Language - Java
- Framework - Spring Boot
- Build tool - Gradle
- Database mapping - Hibernate
- Database - MySQL

## Explore Rest APIs

### Authentication

| Method | Url | Decription |
| ------ | --- | ---------- |
| POST   | /api/admin/register | Sign up for admin|
| POST   | /api/admin/login | Log in admin|
| POST   | /api/register | Sign up for user|
| POST   | /api/login | Log in user|

### Children

| Method | Url | Decription |
| ------ | --- | ---------- |
| POST   | /api/admin/children | Create new children|
| GET    | /api/admin/children | Get children|
| GET    | /api/admin/children/{id} | Get children by id|
| PUT    | /api/admin/children/{id} | Update children information|
| DELETE | /api/admin/children | Delete children|
