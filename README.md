A. Project Overview

Purpose of API:
This project implements a Java REST-like API for managing a music store. 
It allows the creation, retrieval, updating, and deletion of various products,
including instruments, vinyl records, accessories, audio equipment, and software.
The API demonstrates proper object-oriented design, JDBC database interaction, and multi-layer architecture.

Summary of Entities and Relationships:

ProductItem (Abstract Class): Base class for all products.
Subclasses: Instrument, Vinyl, Accessory, AudioEquipment, Software.
Category: Each product belongs to a category.
Relationships: Each product has a foreign key to a Category.

OOP Design Overview:

Abstract classes and inheritance for products.
Interfaces Validatable and PricedItem implemented in services or models.
Composition: Product contains a Category object.
Polymorphism: CRUD and display operations are demonstrated via base class references.

B. OOP Design Documentation

Abstract Class and Subclasses:
    ProductItem 
    ├─ Instrument
    ├─ Vinyl
    ├─ Accessory
    ├─ AudioEquipment
    └─ Software

Interfaces Implemented:

Validation: Ensures product data is valid.
FinalPrice: Provides method calculatePrice() for polymorphic pricing.

Composition / Aggregation:

ProductItem aggregates a Category object.
Each product’s database record includes a foreign key category_id.

Polymorphism Example:

ProductItem product = new Instrument();
System.out.println(product.calculatePrice()); 

C. Database Description

Schema:

CREATE TABLE category (
id SERIAL PRIMARY KEY,
name VARCHAR(50) UNIQUE NOT NULL,
description TEXT
);

CREATE TABLE accessories (
id SERIAL PRIMARY KEY,
name VARCHAR(50) NOT NULL,
price DOUBLE NOT NULL,
stock_quantity INT NOT NULL,
manufacturer VARCHAR(50),
accessory_type VARCHAR(50),
description TEXT,
category_id INT,
FOREIGN KEY (category_id) REFERENCES category(id)
);

Sample Inserts:

INSERT INTO categories(name, description) VALUES
('Guitars', 'All types of guitars'),
('Vinyl', 'Vinyl records and albums');

INSERT INTO accessory(name, price, stock_quantity, manufacturer, accessory_type, description, category_id) VALUES
('Guitar Strings', 15.99, 50, 'Ernie Ball', 'Strings', 'Premium guitar strings', 1);

D. Controller

Example Requests & Responses:

Enter accessory name: Guitar Strings
Enter price: 15.99
Enter manufacturer: Ernie Ball
Enter stock quantity: 50
Enter accessory type: Strings
Enter description: Premium guitar strings
Enter category ID: 1

Output: Accessory created successfully with ID: 1

Enter product type: 3
Enter product ID: 1
Enter new stock quantity: 60

Output: Stock updated successfully

E. Instructions to Compile and Run

javac -d bin src/**/*.java

java -cp bin Main

G. Reflection Section

What I Learned:

How to apply advanced OOP concepts in a project.
Demonstrating real world scenario in an OOP project.

Challenges Faced:
Mapping ResultSet to complex objects with composition.
Connecting JDBC to my project.

Benefits of JDBC and Multi-Layer Design:
Clear separation between controller, service, and repository.
Easier to extend and maintain.
Supports robust error handling and validation.


