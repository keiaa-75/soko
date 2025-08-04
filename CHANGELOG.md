# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2025-08-04

### Added

- **Initial Release of Sōko!**
- **Core Inventory Management:** Full CRUD (Create, Read, Update, Delete) functionality for products.
- **Dynamic Frontend:** A responsive, single-page interface built with vanilla JavaScript and Bootstrap 5.
- **Powerful Filtering and Sorting:**
    - Search products by name.
    - Filter products by category.
    - Sort data by ID, name, price, or quantity in ascending or descending order.
- **RESTful API:** A comprehensive set of API endpoints for managing products. See `README.md` for full documentation.
- **Persistent Storage:** Uses an H2 file-based database to persist data between application restarts.
- **UI Theme Toggle:** Switch between light and dark modes, with the preference saved in local storage.