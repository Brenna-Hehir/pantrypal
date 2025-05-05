package edu.uga.cs.pantrypal.controller;

import edu.uga.cs.pantrypal.dto.RecipeDTO;
import edu.uga.cs.pantrypal.model.Recipe;
import edu.uga.cs.pantrypal.model.User;
import edu.uga.cs.pantrypal.repository.RecipeRepository;
import edu.uga.cs.pantrypal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping("/api/recipes")
@CrossOrigin(origins = "*")
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public List<RecipeDTO> getAllRecipes() {
        String sql = "SELECT r.recipe_id, r.title, r.created_at, u.username AS createdByUsername " +
                     "FROM Recipe r JOIN User u ON r.created_by = u.user_id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            RecipeDTO dto = new RecipeDTO();
            dto.setRecipeId(rs.getInt("recipe_id"));
            dto.setTitle(rs.getString("title"));
            dto.setCreatedByUsername(rs.getString("createdByUsername"));
            dto.setCreatedAt(rs.getTimestamp("created_at"));
            return dto;
        });
    }

    @GetMapping("/{id}")
    public RecipeDTO getRecipeById(@PathVariable Integer id) {
        String sql = "SELECT r.recipe_id, r.title, r.instructions, r.created_at, u.username AS createdByUsername " +
                     "FROM Recipe r JOIN User u ON r.created_by = u.user_id WHERE r.recipe_id = ?";

        RecipeDTO dto = jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            RecipeDTO result = new RecipeDTO();
            result.setRecipeId(rs.getInt("recipe_id"));
            result.setTitle(rs.getString("title"));
            result.setInstructions(rs.getString("instructions"));
            result.setCreatedByUsername(rs.getString("createdByUsername"));
            result.setCreatedAt(rs.getTimestamp("created_at"));
            return result;
        });

        // Get ingredients for this recipe
        String ingSql = "SELECT i.name FROM Ingredient i " +
                        "JOIN RecipeIngredient ri ON i.ingredient_id = ri.ingredient_id " +
                        "WHERE ri.recipe_id = ?";
        List<String> ingredients = jdbcTemplate.query(ingSql, new Object[]{id},
            (rs, rowNum) -> rs.getString("name"));
        dto.setIngredients(ingredients);

        return dto;
    }

    @GetMapping("/user/{userId}")
    public List<Recipe> getRecipesByUser(@PathVariable Integer userId) {
        return recipeRepository.findByCreatedByUserId(userId);
    }

    @PostMapping
public String createRecipe(@RequestBody RecipeDTO recipeDto) {
    if (recipeDto.getCreatedByUserId() == 0) {
        return "Missing creator";
    }

    Optional<User> user = userRepository.findById(recipeDto.getCreatedByUserId());
    if (user.isEmpty()) {
        return "Invalid user";
    }

    Recipe recipe = new Recipe();
    recipe.setTitle(recipeDto.getTitle());
    recipe.setInstructions(recipeDto.getInstructions());
    recipe.setCreatedBy(user.get());

    recipeRepository.save(recipe);

    // Insert ingredients and RecipeIngredient links
    Integer recipeId = recipe.getRecipeId();
    for (String ing : recipeDto.getIngredients()) {
        // Insert into Ingredient table if it doesn't exist
        Integer ingredientId = jdbcTemplate.query(
            "SELECT ingredient_id FROM Ingredient WHERE name = ?",
            new Object[]{ing},
            rs -> rs.next() ? rs.getInt("ingredient_id") : null
        );

        if (ingredientId == null) {
            jdbcTemplate.update("INSERT INTO Ingredient (name) VALUES (?)", ing);
            ingredientId = jdbcTemplate.queryForObject(
                "SELECT LAST_INSERT_ID()",
                Integer.class
            );
        }

        // Link to recipe in RecipeIngredient
        jdbcTemplate.update(
            "INSERT INTO RecipeIngredient (recipe_id, ingredient_id, quantity) VALUES (?, ?, ?)",
            recipeId, ingredientId, 1.0
        );
    }

    return "Recipe created successfully";
}


    @DeleteMapping("/{id}")
    public String deleteRecipe(@PathVariable Integer id) {
        if (!recipeRepository.existsById(id)) {
            return "Recipe not found";
        }
        recipeRepository.deleteById(id);
        return "Recipe deleted";
    }
}
