import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './AllRecipesPage.css';

export default function AllRecipesPage() {
  const [recipes, setRecipes] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    fetch('https://pantrypal-backend-218e.onrender.com/api/recipes')
      .then(res => res.json())
      .then(data => setRecipes(data))
      .catch(err => console.error('Failed to fetch recipes:', err));
  }, []);

  return (
    <div className="recipes-container">
      <h2>All Recipes</h2>
      {recipes.length === 0 ? (
        <p>No recipes found.</p>
      ) : (
        <ul className="recipe-list">
          {recipes.map(recipe => (
            <li
              key={recipe.recipeId}
              className="recipe-card"
              onClick={() => navigate(`/recipes/${recipe.recipeId}`)}
            >
              <h3>{recipe.title}</h3>
              <p><strong>Created by:</strong> {recipe.createdByUsername || 'Unknown'}</p>
              <p><strong>Created at:</strong> {new Date(recipe.createdAt).toLocaleString()}</p>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}
