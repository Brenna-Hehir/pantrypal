import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import './RecipeDetailPage.css';

export default function RecipeDetailPage() {
  const { id } = useParams();
  const [recipe, setRecipe] = useState(null);

  useEffect(() => {
    fetch(`http://localhost:8080/api/recipes/${id}`)
      .then((res) => res.json())
      .then((data) => setRecipe(data))
      .catch((err) => console.error("Error fetching recipe:", err));
  }, [id]);

  if (!recipe) return <div className="detail-container">Loading...</div>;

  return (
    <div className="detail-container">
      <h2>{recipe.title}</h2>
      <p><strong>Instructions:</strong></p>
      <p>{recipe.instructions}</p>
      <p><strong>Created by:</strong> {recipe.createdBy?.username}</p>
    </div>
  );
}
