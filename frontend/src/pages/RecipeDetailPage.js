// src/pages/RecipeDetailPage.js
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import './RecipeDetailPage.css';

export default function RecipeDetailPage() {
  const { id } = useParams();
  const [recipe, setRecipe] = useState(null);
  const [isSaved, setIsSaved] = useState(false);
  const [message, setMessage] = useState('');

  const userId = parseInt(localStorage.getItem("userId"));

  useEffect(() => {
    // Fetch recipe details
    fetch(`http://localhost:8080/api/recipes/${id}`)  
      .then(res => res.json())
      .then(data => setRecipe(data))
      .catch(err => console.error("Error fetching recipe:", err));

    // Check if saved
    if (userId) {
      fetch(`http://localhost:8080/api/saved/user/${userId}`)
        .then(res => res.json())
        .then(data => {
          const found = data.some(r => r.recipe.recipeId === parseInt(id));
          setIsSaved(found);
        })
        .catch(err => console.error("Error checking saved status:", err));
    }
  }, [id, userId]);

  const handleSaveToggle = async () => {
    const endpoint = 'http://localhost:8080/api/saved';
    const body = { userId, recipeId: parseInt(id) };

    try {
      const response = await fetch(endpoint, {
        method: isSaved ? 'DELETE' : 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body)
      });

      const text = await response.text();
      setMessage(text);
      setIsSaved(!isSaved);
    } catch (error) {
      console.error('Save toggle failed:', error);
      setMessage('Something went wrong.');
    }
  };

  if (!recipe) return <div className="detail-container">Loading...</div>;

  return (
    <div className="detail-container">
      <h2>{recipe.title}</h2>

      {recipe.ingredients && recipe.ingredients.length > 0 && (
        <>
          <p><strong>Ingredients:</strong></p>
          <ul>
            {recipe.ingredients.map((ing, i) => (
              <li key={i}>{ing}</li>
            ))}
          </ul>
        </>
      )}

      <p><strong>Instructions:</strong></p>
      <p>{recipe.instructions}</p>

      <p><strong>Created by:</strong> {recipe.createdByUsername}</p>
      <p className="timestamp"><strong>Created at:</strong> {new Date(recipe.createdAt).toLocaleString()}</p>

      {userId ? (
        <>
          <button onClick={handleSaveToggle}>
            {isSaved ? 'Unsave Recipe' : 'Save Recipe'}
          </button>
          {message && <p className="save-message">{message}</p>}
        </>
      ) : (
        <p style={{ color: 'red' }}>Log in to save this recipe.</p>
      )}
    </div>
  );
}
