-- Query 1: Retrieve the 5 most saved recipes (aggregation + join)
-- URL: /api/recipes/popular
SELECT r.recipe_id, r.title, COUNT(sr.user_id) AS save_count
FROM Recipe r
JOIN SavedRecipe sr ON r.recipe_id = sr.recipe_id
GROUP BY r.recipe_id
ORDER BY save_count DESC
LIMIT 5;

-- Query 2: Get all ingredients and quantities for a given recipe (join)
-- URL: /api/recipe-ingredients/{recipeId}
SELECT i.name, ri.quantity
FROM RecipeIngredient ri
JOIN Ingredient i ON ri.ingredient_id = i.ingredient_id
WHERE ri.recipe_id = 1;

-- Query 3: Count how many recipes a user has created (aggregation)
-- URL: /api/recipes/user/{userId}
SELECT COUNT(*) AS total_recipes
FROM Recipe
WHERE created_by = 1;

-- Query 4: Insert a new user (signup form)
-- URL: /api/signup
INSERT INTO User (username, email, password_hash)
VALUES ('brenna', 'brenna@example.com', 'hashed_password_here');

-- Query 5: Update a comment's content
-- URL: (would be a future PUT endpoint, e.g. /api/comments/{id})
UPDATE Comment
SET content = 'Updated comment here'
WHERE comment_id = 1;

-- Query 6: Delete a saved recipe
-- URL: /api/saved (DELETE)
DELETE FROM SavedRecipe
WHERE user_id = 1 AND recipe_id = 2;
