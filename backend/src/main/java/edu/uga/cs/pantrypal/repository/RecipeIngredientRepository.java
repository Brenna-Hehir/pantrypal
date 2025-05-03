package edu.uga.cs.pantrypal.repository;

import edu.uga.cs.pantrypal.model.RecipeIngredient;
import edu.uga.cs.pantrypal.model.RecipeIngredient.RecipeIngredientId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, RecipeIngredientId> {
    List<RecipeIngredient> findByRecipe_RecipeId(Integer recipeId);
}
