package edu.uga.cs.pantrypal.repository;

import edu.uga.cs.pantrypal.model.SavedRecipe;
import edu.uga.cs.pantrypal.model.SavedRecipe.SavedRecipeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavedRecipeRepository extends JpaRepository<SavedRecipe, SavedRecipeId> {
    List<SavedRecipe> findByUser_UserId(Integer userId);
    boolean existsByUser_UserIdAndRecipe_RecipeId(Integer userId, Integer recipeId);
}
