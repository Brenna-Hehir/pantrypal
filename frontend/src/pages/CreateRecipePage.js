import React, { useState } from 'react';
import './CreateRecipePage.css';

export default function CreateRecipePage() {
  const [formData, setFormData] = useState({
    title: '',
    instructions: '',
  });

  const [message, setMessage] = useState('');

  const handleChange = (e) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const recipe = {
      title: formData.title,
      instructions: formData.instructions,
      createdBy: { userId: 1 }, // Replace this with the logged-in user ID in the future
    };

    try {
      const response = await fetch('http://localhost:8080/api/recipes', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(recipe),
      });

      const result = await response.text();
      setMessage(result);
      setFormData({ title: '', instructions: '' });
    } catch (err) {
      console.error('Error creating recipe:', err);
      setMessage('Failed to create recipe.');
    }
  };

  return (
    <div className="create-recipe-container">
      <h2>Create a New Recipe</h2>
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
        <button type="submit">Submit Recipe</button>
      </form>
      {message && <p className="create-recipe-message">{message}</p>}
    </div>
  );
}
