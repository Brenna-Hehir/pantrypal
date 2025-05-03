package edu.uga.cs.pantrypal.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "SavedRecipe")
@IdClass(SavedRecipe.SavedRecipeId.class)
public class SavedRecipe {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Column(nullable = false)
    private LocalDateTime savedAt = LocalDateTime.now();

    public static class SavedRecipeId implements Serializable {
        private Integer user;
        private Integer recipe;

        public SavedRecipeId() {}

        public SavedRecipeId(Integer user, Integer recipe) {
            this.user = user;
            this.recipe = recipe;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SavedRecipeId)) return false;
            SavedRecipeId that = (SavedRecipeId) o;
            return user.equals(that.user) && recipe.equals(that.recipe);
        }

        @Override
        public int hashCode() {
            return user.hashCode() + recipe.hashCode();
        }
    }

    // Getters and setters
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Recipe getRecipe() { return recipe; }
    public void setRecipe(Recipe recipe) { this.recipe = recipe; }

    public LocalDateTime getSavedAt() { return savedAt; }
    public void setSavedAt(LocalDateTime savedAt) { this.savedAt = savedAt; }
}
