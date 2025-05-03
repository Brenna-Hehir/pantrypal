package edu.uga.cs.pantrypal.controller;

import edu.uga.cs.pantrypal.model.Recipe;
import edu.uga.cs.pantrypal.model.User;
import edu.uga.cs.pantrypal.repository.RecipeRepository;
import edu.uga.cs.pantrypal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
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
