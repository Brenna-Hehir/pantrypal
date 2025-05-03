package edu.uga.cs.pantrypal.controller;

import edu.uga.cs.pantrypal.model.Recipe;
import edu.uga.cs.pantrypal.model.User;
import edu.uga.cs.pantrypal.repository.RecipeRepository;
import edu.uga.cs.pantrypal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
    public List<Map<String, Object>> getAllRecipes() {
        return jdbcTemplate.query(
            "SELECT r.recipe_id, r.title, u.username AS createdByUsername " +
            "FROM Recipe r JOIN User u ON r.created_by = u.user_id",
            (rs, rowNum) -> {
                Map<String, Object> map = new HashMap<>();
                map.put("recipe_id", rs.getInt("recipe_id"));
                map.put("title", rs.getString("title"));
                map.put("createdByUsername", rs.getString("createdByUsername"));
                return map;
            }
        );
    }

    @GetMapping("/user/{userId}")
    public List<Recipe> getRecipesByUser(@PathVariable Integer userId) {
        return recipeRepository.findByCreatedByUserId(userId);
    }

    @GetMapping("/{id}")
    public Map<String, Object> getRecipeById(@PathVariable Integer id) {
        String sql = "SELECT r.recipe_id, r.title, r.instructions, u.username AS createdByUsername " +
                    "FROM Recipe r JOIN User u ON r.created_by = u.user_id WHERE r.recipe_id = ?";

        return jdbcTemplate.queryForObject(
            sql,
            new Object[]{id},
            (rs, rowNum) -> {
                Map<String, Object> map = new HashMap<>();
                map.put("recipe_id", rs.getInt("recipe_id"));
                map.put("title", rs.getString("title"));
                map.put("instructions", rs.getString("instructions"));
                map.put("createdByUsername", rs.getString("createdByUsername"));
                return map;
            }
        );
    }

    @PostMapping
    public String createRecipe(@RequestBody Recipe recipe) {
        if (recipe.getCreatedBy() == null || recipe.getCreatedBy().getUserId() == null) {
            return "Missing creator";
        }

        Optional<User> user = userRepository.findById(recipe.getCreatedBy().getUserId());
        if (user.isEmpty()) {
            return "Invalid user";
        }

        recipe.setCreatedBy(user.get());
        recipeRepository.save(recipe);
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
