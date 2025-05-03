package edu.uga.cs.pantrypal.repository;

import edu.uga.cs.pantrypal.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByRecipe_RecipeId(Integer recipeId);
}
