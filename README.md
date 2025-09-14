# Sōko

<p align="center">
    <img alt = "Spring Framework" src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white"/>
    <img alt = "Bootstrap" src="https://img.shields.io/badge/bootstrap-%238511FA.svg?style=for-the-badge&logo=bootstrap&logoColor=white"/>
    <img alt = "JavaScript" src="https://img.shields.io/badge/javascript-%23F7DF1E.svg?style=for-the-badge&logo=javascript&logoColor=%23323330"/>
    <img alt = "Hibernate ORM" src="https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white"/>
    <img alt = "Java" src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white"/>
</p>

![Sōko running on the browser](program-screenshot.png)

**Sōko** (倉庫) means "warehouse" in Japanese. This is a simple, responsive web application for managing a product inventory. The project demonstrates a full-stack application with [RESTful API](endpoints.md) services and a mobile-first user interface.

## Features

- **Manage Products**: Add, remove, and edit products through a simple form.
- **Search**: Dynamically search for products by name.
- **Sort**: Sort the product list by ID, name, or price.
- **Filter**: Filter products by category.
- **Automatic Currency Conversion**: Convert product prices to different currencies.
- **Export to CSV**: Export the product list to a CSV file.
- **Print Product List**: Print the product list directly from the browser.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java Development Kit (JDK) 17 or higher
- Apache Maven 3.6.0 or higher

**1. Clone the repository:**

```sh
git clone https://github.com/keiaa-75/soko.git
cd soko
```

**2. Build the project:**

```sh
mvn clean install
```

**3. Run the application:**

From your IDE, you can run the `SokoApplication.java` file as a Spring Boot app. Alternatively, you can use the command line:

```sh
mvn spring-boot:run
```

The application will start on port `9090`.

## License

This repository is licensed under MIT License. Please refer to the [LICENSE](LICENSE) file for full details. Other resources included, such as images, are licensed under [Creative Commons Attribution Non-Commercial Share-Alike 4.0 International](https://creativecommons.org/licenses/by-nc-sa/4.0/).