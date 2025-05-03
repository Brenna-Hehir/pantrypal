package edu.uga.cs.pantrypal.controller;

import edu.uga.cs.pantrypal.model.*;
import edu.uga.cs.pantrypal.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/recipe-ingredients")
@CrossOrigin(origins = "*")
public class RecipeIngredientController {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;

    // DTO class to receive input
    public static class IngredientAssignmentRequest {
        public Integer recipeId;
        public Integer ingredientId;
        public Double quantity;
    }

    @PostMapping
    public String assignIngredientToRecipe(@RequestBody IngredientAssignmentRequest request) {
        Optional<Recipe> recipeOpt = recipeRepository.findById(request.recipeId);
        Optional<Ingredient> ingredientOpt = ingredientRepository.findById(request.ingredientId);

        if (recipeOpt.isEmpty() || ingredientOpt.isEmpty()) {
            return "Invalid recipe or ingredient ID";
        }

        RecipeIngredient assignment = new RecipeIngredient();
        assignment.setRecipe(recipeOpt.get());
        assignment.setIngredient(ingredientOpt.get());
        assignment.setQuantity(request.quantity);

        recipeIngredientRepository.save(assignment);
        return "Ingredient assigned to recipe";
    }

    @GetMapping("/{recipeId}")
    public List<RecipeIngredient> getIngredientsForRecipe(@PathVariable Integer recipeId) {
        return recipeIngredientRepository.findByRecipe_RecipeId(recipeId);
    }
}
