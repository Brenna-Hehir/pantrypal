INSERT INTO User (username, email, password_hash) VALUES
('alice', 'alice@example.com', '$2a$10$hashedalice'),
('bob', 'bob@example.com', '$2a$10$hashedbob'),
('carol', 'carol@example.com', '$2a$10$hashedcarol');

INSERT INTO Recipe (title, instructions, created_by) VALUES
('Spaghetti Carbonara', 'Boil pasta. Cook pancetta. Mix with eggs and cheese. Combine.', 1),
('Avocado Toast', 'Toast bread. Smash avocado with salt and pepper. Spread and serve.', 2),
('Pancakes', 'Mix ingredients. Pour batter on skillet. Flip when bubbles form.', 3);

INSERT INTO Ingredient (name) VALUES
('Spaghetti'),
('Eggs'),
('Pancetta'),
('Avocado'),
('Bread'),
('Flour'),
('Milk'),
('Baking Powder'),
('Salt');

INSERT INTO RecipeIngredient (recipe_id, ingredient_id, quantity) VALUES
(1, 1, 100.00),
(1, 2, 2.00),
(1, 3, 50.00),
(2, 4, 1.00),
(2, 5, 2.00),
(3, 6, 200.00),
(3, 7, 150.00),
(3, 8, 10.00),
(3, 9, 2.00);

INSERT INTO Comment (recipe_id, user_id, content) VALUES
(1, 2, 'Loved this recipe! Very tasty.'),
(1, 3, 'Too salty for me.'),
(2, 1, 'Simple and healthy.'),
(3, 1, 'Great for breakfast!');

INSERT INTO SavedRecipe (user_id, recipe_id) VALUES
(1, 2),
(2, 1),
(3, 3);
