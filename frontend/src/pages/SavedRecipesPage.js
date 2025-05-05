import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import './SavedRecipesPage.css';

export default function SavedRecipesPage() {
  const [savedRecipes, setSavedRecipes] = useState([]);
  const userId = parseInt(localStorage.getItem("userId"));

  useEffect(() => {
    if (!userId) return;
    fetch(`http://localhost:8080/api/saved/user/${userId}`)
      .then((res) => res.json())
      .then((data) => setSavedRecipes(data))
      .catch((err) => console.error('Error fetching saved recipes:', err));
  }, [userId]);

  if (!userId) {
    return (
      <div className="saved-recipes-container">
        <h2>Saved Recipes</h2>
        <p>Please log in to view your saved recipes.</p>
      </div>
    );
  }

  return (
    <div className="saved-recipes-container">
      <h2>Saved Recipes</h2>
      {savedRecipes.length === 0 ? (
        <p>No saved recipes.</p>
      ) : (
        <ul className="saved-recipe-list">
          {savedRecipes.map((item, idx) => (
            <Link
              key={idx}
              to={`/recipes/${item.recipe.recipeId}`}
              className="saved-recipe-card-link"
            >
              <li className="saved-recipe-card">
                <h3>{item.recipe?.title}</h3>
                <p><strong>Created by:</strong> {item.recipe?.createdBy?.username}</p>
                <p><strong>Saved on:</strong> {new Date(item.savedAt).toLocaleString()}</p>
              </li>
            </Link>
          ))}
        </ul>
      )}
    </div>
  );
}
