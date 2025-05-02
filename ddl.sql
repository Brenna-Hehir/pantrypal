CREATE DATABASE IF NOT EXISTS pantrypal_db;

USE pantrypal_db;

CREATE TABLE User (
user_id INT AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(50) UNIQUE NOT NULL,
email VARCHAR(100) UNIQUE NOT NULL,
password_hash VARCHAR(255) NOT NULL
);

CREATE TABLE Recipe (
recipe_id INT AUTO_INCREMENT PRIMARY KEY,
title VARCHAR(100) NOT NULL,
instructions TEXT NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
created_by INT,
FOREIGN KEY (created_by) REFERENCES User(user_id)
);

CREATE TABLE Ingredient (
ingredient_id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE RecipeIngredient (
recipe_id INT,
ingredient_id INT,
quantity DECIMAL(5,2),
PRIMARY KEY (recipe_id, ingredient_id),
FOREIGN KEY (recipe_id) REFERENCES Recipe(recipe_id),
FOREIGN KEY (ingredient_id) REFERENCES Ingredient(ingredient_id)
);

CREATE TABLE Comment (
comment_id INT AUTO_INCREMENT PRIMARY KEY,
recipe_id INT,
user_id INT,
content TEXT NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (recipe_id) REFERENCES Recipe(recipe_id),
FOREIGN KEY (user_id) REFERENCES User(user_id)
);

CREATE TABLE SavedRecipe (
user_id INT,
recipe_id INT,
saved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (user_id, recipe_id),
FOREIGN KEY (user_id) REFERENCES User(user_id),
FOREIGN KEY (recipe_id) REFERENCES Recipe(recipe_id)
);