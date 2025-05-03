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

        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            RecipeDTO dto = new RecipeDTO();
            dto.setRecipeId(rs.getInt("recipe_id"));
            dto.setTitle(rs.getString("title"));
            dto.setInstructions(rs.getString("instructions"));
            dto.setCreatedByUsername(rs.getString("createdByUsername"));
            dto.setCreatedAt(rs.getTimestamp("created_at"));
            return dto;
        });
    }

    @GetMapping("/user/{userId}")
    public List<Recipe> getRecipesByUser(@PathVariable Integer userId) {
        return recipeRepository.findByCreatedByUserId(userId);
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
