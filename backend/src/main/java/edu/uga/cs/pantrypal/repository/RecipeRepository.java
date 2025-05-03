package edu.uga.cs.pantrypal.repository;

import edu.uga.cs.pantrypal.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    List<Recipe> findByCreatedByUserId(Integer userId);
}
