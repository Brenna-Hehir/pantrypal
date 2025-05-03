package edu.uga.cs.pantrypal.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "RecipeIngredient")
@IdClass(RecipeIngredient.RecipeIngredientId.class)
public class RecipeIngredient {

    @Id
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Id
    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @Column(nullable = false)
    private Double quantity;

    // Composite key class
    public static class RecipeIngredientId implements Serializable {
        private Integer recipe;
        private Integer ingredient;

        public RecipeIngredientId() {}

        public RecipeIngredientId(Integer recipe, Integer ingredient) {
            this.recipe = recipe;
            this.ingredient = ingredient;
        }

        // equals and hashCode required for composite keys
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof RecipeIngredientId)) return false;
            RecipeIngredientId that = (RecipeIngredientId) o;
            return recipe.equals(that.recipe) && ingredient.equals(that.ingredient);
        }

        @Override
        public int hashCode() {
            return recipe.hashCode() + ingredient.hashCode();
        }
    }

    // Getters and setters
    public Recipe getRecipe() { return recipe; }
    public void setRecipe(Recipe recipe) { this.recipe = recipe; }

    public Ingredient getIngredient() { return ingredient; }
    public void setIngredient(Ingredient ingredient) { this.ingredient = ingredient; }

    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }
}
