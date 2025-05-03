import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './AllRecipesPage.css';

export default function AllRecipesPage() {
  const [recipes, setRecipes] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    fetch('http://localhost:8080/api/recipes')
      .then(res => res.json())
      .then(data => setRecipes(data))
      .catch(err => console.error('Failed to fetch recipes:', err));
  }, []);

  const handleCardClick = (id) => {
    navigate(`/recipes/${id}`);
  };

  return (
    <div className="recipes-container">
      <h2>All Recipes</h2>
      {recipes.length === 0 ? (
        <p>No recipes found.</p>
      ) : (
        <ul className="recipe-list">
          {recipes.map(recipe => (
            <li
              key={recipe.recipe_id}
              className="recipe-card"
              onClick={() => handleCardClick(recipe.recipe_id)}
            >
              <h3>{recipe.title}</h3>
              <p><strong>Created by:</strong> {recipe.createdByUsername || 'Unknown'}</p>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}
