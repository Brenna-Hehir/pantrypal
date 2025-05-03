package edu.uga.cs.pantrypal.dto;

import java.time.LocalDateTime;

public class RecipeDTO {
    private Integer recipeId;
    private String title;
    private String instructions;
    private String createdByUsername;
    private LocalDateTime createdAt;

    // Constructors
    public RecipeDTO() {}

    public RecipeDTO(Integer recipeId, String title, String instructions, String createdByUsername, LocalDateTime createdAt) {
        this.recipeId = recipeId;
        this.title = title;
        this.instructions = instructions;
        this.createdByUsername = createdByUsername;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getCreatedByUsername() {
        return createdByUsername;
    }

    public void setCreatedByUsername(String createdByUsername) {
        this.createdByUsername = createdByUsername;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
