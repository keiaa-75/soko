# Sōko

**Sōko** (倉庫) means "warehouse" in Japanese. I'm out of name ideas.

This is a simple, responsive web application for managing a product inventory. It is built with Spring Boot, H2 Database, JavaScript, and Bootstrap 5. The project demonstrates a full-stack application with RESTful API services and a mobile-first user interface.

## Features

- **View Products**: See a complete list of all products in the inventory.
- **Add Products**: Add new products through a simple form.
- **Delete Products**: Remove products from the inventory.
- **Search**: Dynamically search for products by name.
- **Sort**: Sort the product list by price (ascending or descending).

## Getting Started

Follow these instructions to get a copy of the project up and running on your local machine.

### Prerequisites

- Java Development Kit (JDK) 17 or later.
- Apache Maven.

### Installation & Running

1.  **Clone the repository:**
    ```sh
    git clone https://github.com/nozomi-75/soko.git
    cd soko
    ```

2.  **Run the application using the Maven wrapper:**

    On macOS/Linux:
    ```sh
    ./mvnw spring-boot:run
    ```

    On Windows:
    ```sh
    mvnw.cmd spring-boot:run
    ```

3.  **Access the application:**
    Open your web browser and navigate to `http://localhost:9090`.

## API Endpoints

The backend exposes the following RESTful endpoints under the base path `/api/products`.

| Method | Endpoint                                       | Description                               |
|--------|------------------------------------------------|-------------------------------------------|
| `GET`    | `/`                                            | Get a list of all products.               |
| `GET`    | `/{id}`                                        | Get a single product by its ID.           |
| `POST`   | `/`                                            | Create a new product.                     |
| `PUT`    | `/{id}`                                        | Update an existing product.               |
| `DELETE` | `/{id}`                                        | Delete a product by its ID.               |
| `GET`    | `/search?name={name}`                          | Search for products by name.              |
| `GET`    | `/sort?by={id,name,price}&order={asc,desc}`    | Sort products by ID, name, or price.      |
| `GET`    | `/filter?category={...}&minPrice={...}`        | Filter products by various criteria.      |