import React, { useState } from 'react';
import './CreateRecipePage.css';

export default function CreateRecipePage() {
  const [formData, setFormData] = useState({
    title: '',
    instructions: '',
  });

  const [ingredients, setIngredients] = useState([]);
  const [newIngredient, setNewIngredient] = useState('');
  const [message, setMessage] = useState('');

  const handleChange = (e) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const handleAddIngredient = () => {
    const trimmed = newIngredient.trim();
    if (trimmed && !ingredients.includes(trimmed)) {
      setIngredients((prev) => [...prev, trimmed]);
      setNewIngredient('');
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const userId = parseInt(localStorage.getItem("userId"));
    if (!userId) {
      setMessage("Please log in to submit a recipe.");
      return;
    }

    const recipeData = {
      ...formData,
      createdByUserId: userId,
      ingredients,
    };

    try {
      const response = await fetch("https://pantrypal-backend-218e.onrender.com/api/recipes", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(recipeData),
      });

      const text = await response.text();
      setMessage(text);
      if (response.ok) {
        setFormData({ title: '', instructions: '' });
        setIngredients([]);
      }
    } catch (err) {
      console.error("Error creating recipe:", err);
      setMessage("Something went wrong.");
    }
  };

  return (
    <div className="create-recipe-container">
      <h2>Create a Recipe</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="title"
          placeholder="Recipe Title"
          value={formData.title}
          onChange={handleChange}
          required
        />
        <textarea
          name="instructions"
          placeholder="Instructions"
          value={formData.instructions}
          onChange={handleChange}
          required
        />

        <div className="ingredient-input-group">
          <input
            type="text"
            placeholder="Add ingredient"
            value={newIngredient}
            onChange={(e) => setNewIngredient(e.target.value)}
          />
          <button type="button" onClick={handleAddIngredient}>Add</button>
        </div>

        {ingredients.length > 0 && (
          <ul className="ingredient-list">
            {ingredients.map((ing, idx) => <li key={idx}>{ing}</li>)}
          </ul>
        )}

        <button type="submit">Submit Recipe</button>
      </form>
      {message && <p className="create-recipe-message">{message}</p>}
    </div>
  );
}
