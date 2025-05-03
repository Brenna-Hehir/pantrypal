package edu.uga.cs.pantrypal.controller;

import edu.uga.cs.pantrypal.model.*;
import edu.uga.cs.pantrypal.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/saved")
@CrossOrigin(origins = "*")
public class SavedRecipeController {

    @Autowired
    private SavedRecipeRepository savedRecipeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    public static class SaveRequest {
        public Integer userId;
        public Integer recipeId;
    }

    @PostMapping
    public String saveRecipe(@RequestBody SaveRequest request) {
        if (savedRecipeRepository.existsByUser_UserIdAndRecipe_RecipeId(request.userId, request.recipeId)) {
            return "Recipe already saved";
        }

        Optional<User> userOpt = userRepository.findById(request.userId);
        Optional<Recipe> recipeOpt = recipeRepository.findById(request.recipeId);

        if (userOpt.isEmpty() || recipeOpt.isEmpty()) {
            return "Invalid user or recipe ID";
        }

        SavedRecipe saved = new SavedRecipe();
        saved.setUser(userOpt.get());
        saved.setRecipe(recipeOpt.get());

        savedRecipeRepository.save(saved);
        return "Recipe saved";
    }

    @GetMapping("/user/{userId}")
    public List<SavedRecipe> getSavedRecipes(@PathVariable Integer userId) {
        return savedRecipeRepository.findByUser_UserId(userId);
    }

    @DeleteMapping
    public String unsaveRecipe(@RequestBody SaveRequest request) {
        SavedRecipe.SavedRecipeId id = new SavedRecipe.SavedRecipeId(request.userId, request.recipeId);
        if (!savedRecipeRepository.existsById(id)) {
            return "Not found";
        }
        savedRecipeRepository.deleteById(id);
        return "Recipe unsaved";
    }
}
