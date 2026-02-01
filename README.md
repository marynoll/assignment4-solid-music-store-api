### **A. SOLID Documentation**

SRP:
Each class has a single responsibility, for example:
VinylService handles business logic for vinyls,
VinylRepository handles database access,
ProductController handles user interaction.

OCP:
Classes can be extended without modification. Example:
ProductService<T> is generic, allowing VinylService and AccessoryService to extend it.

LSP:
Subclasses like Vinyl and Accessory can replace the base ProductItem without affecting program correctness.

ISP:
Narrow interfaces ensure clients only implement what they need.
Example: ProductService<T> provides only relevant methods for each product type.

DIP:
High-level modules depend on interfaces, not concrete classes.
Example: ProductController receives services via constructor injection, rather than creating them.

### **B. Advanced OOP Features**

Generics:
ProductService<T> allows uniform service behavior for different product types.

Lambdas:
Sorting vinyls by price is implemented using:
SortingUtils.sortByPrice(vinyls, Vinyl::getPrice);

Reflection:
Inspect class structure at runtime:
ReflectionUtils.inspectClass(vinyls.get(0));

Interface Default/Static Methods:
Default methods in service interfaces provide reusable behavior without forcing every class to implement it.

### **C. OOP Documentation**

Abstract Class + Subclasses:

ProductItem (abstract): Vinyl, Accessory, AudioEquipment, Category, Software, Instrument.(concrete subclasses)

Composition Relationships:

Vinyl, Accessory, AudioEquipment, Software, Instrument have a Category.

Polymorphism Examples:

ProductItem vinyl = new Vinyl("Abbey Road", 29.99, "Apple Records", 50, "The Beatles", "Rock", 1969, 33, null);
ProductItem accessory = new Accessory("Guitar Strap", 14.99, 75, "Ernie Ball", "Guitar Strap", "Adjustable strap", null);

### **D. Database Section**

Schema:
6 main tables: category, vinyl, accessory, instrument, audio_equipment, software.
Relationships:

vinyl.category_id → category.id (foreign key)
accessory.category_id → category.id

Constraints:

Primary keys, foreign keys, and CHECK constraints on numeric fields (price > 0, stock >= 0).
Unique category names.

Sample Inserts:

INSERT INTO category (name, description) VALUES ('Rock Vinyls', 'Classic and modern rock music');
INSERT INTO vinyl (name, price, stock_quantity, manufacturer, artist, genre, release_year, speed_rpm, category_id)
VALUES ('Abbey Road', 29.99, 50, 'Apple Records', 'The Beatles', 'Rock', 1969, 33, 5);
INSERT INTO accessory (name, price, stock_quantity, manufacturer, accessory_type, description, category_id)
VALUES ('Mogami Gold Studio Cable', 29.99, 100, 'Mogami', 'Audio Cable', '10ft XLR cable', 11);

### **E. Architecture Explanation**

Controller:
Handles user input, delegates requests to services, and displays results.
ProductController is the single entry point for operations.

Service:
Contains business logic, validation, and exception handling.
Example: VinylService.create() validates vinyl data and ensures category exists.

Repository:
Handles database access via JDBC.
Example: VinylRepository.getAll() fetches all vinyls from the database.

Request/Response Example:
Request: User inputs “Create Vinyl” with name, price, etc.
Response: Vinyl is saved, ID returned, success message displayed.

### **F. Execution Instructions**

Requirements:

Java 17+
PostgreSQL database
JDBC driver

Compile & Run:

javac -d bin src/**/*.java
java -cp bin Main


Database Configuration:
Update DatabaseConnection with your DB URL, username, and password.

### **G. Screenshots**

In docs/screenshots/

Demonstrate multiple outputs in Main

### **H. Reflection**

What I Learned:

How to structure a Java project using SOLID principles.
The power of generics, lambdas, and reflection in simplifying code.
Separating responsibilities clearly reduces bugs and improves maintainability.

Challenges:

Handling database exceptions and mapping rows to objects.
Designing generic services without violating SOLID principles.

Value of SOLID Architecture:

Ensures that code is modular, extendable, testable, and easy to maintain.
Makes the project more robust and understandable.


