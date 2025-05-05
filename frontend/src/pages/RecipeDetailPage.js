// src/pages/RecipeDetailPage.js
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import './RecipeDetailPage.css';

export default function RecipeDetailPage() {
  const { id } = useParams();
  const [recipe, setRecipe] = useState(null);
  const [isSaved, setIsSaved] = useState(false);
  const [message, setMessage] = useState('');
  const [comments, setComments] = useState([]);
  const [newComment, setNewComment] = useState('');

  const userId = parseInt(localStorage.getItem("userId"));

  useEffect(() => {
    // Fetch recipe details
    fetch(`http://localhost:8080/api/recipes/${id}`)
      .then(res => res.json())
      .then(data => setRecipe(data))
      .catch(err => console.error("Error fetching recipe:", err));

    // Fetch saved status
    if (userId) {
      fetch(`http://localhost:8080/api/saved/user/${userId}`)
        .then(res => res.json())
        .then(data => {
          const found = data.some(r => r.recipe.recipeId === parseInt(id));
          setIsSaved(found);
        })
        .catch(err => console.error("Error checking saved status:", err));
    }

    // Fetch comments
    fetch(`http://localhost:8080/api/comments/recipe/${id}`)
      .then(res => res.json())
      .then(data => setComments(data))
      .catch(err => console.error("Error fetching comments:", err));
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

  const handleCommentSubmit = async (e) => {
    e.preventDefault();

    if (!newComment.trim()) return;

    const body = {
      userId,
      recipeId: parseInt(id),
      content: newComment
    };

    try {
      const response = await fetch("http://localhost:8080/api/comments", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body)
      });

      const text = await response.text();
      setMessage(text);
      setNewComment('');

      // Re-fetch comments
      const updated = await fetch(`http://localhost:8080/api/comments/recipe/${id}`).then(res => res.json());
      setComments(updated);
    } catch (err) {
      console.error("Comment submission failed:", err);
      setMessage("Could not post comment.");
    }
  };

  if (!recipe) return <div className="detail-container">Loading...</div>;

  return (
    <div className="detail-container">
      <h2>{recipe.title}</h2>
      <p><strong>Instructions:</strong></p>
      <p>{recipe.instructions}</p>
      <p><strong>Created by:</strong> {recipe.createdByUsername}</p>
      <p className="timestamp"><strong>Created at:</strong> {new Date(recipe.createdAt).toLocaleString()}</p>

      {recipe.ingredients?.length > 0 && (
        <>
          <p><strong>Ingredients:</strong></p>
          <ul>
            {recipe.ingredients.map((ing, i) => (
              <li key={i}>{ing}</li>
            ))}
          </ul>
        </>
      )}

      {userId ? (
        <>
          <button onClick={handleSaveToggle}>
            {isSaved ? 'Unsave Recipe' : 'Save Recipe'}
          </button>

          <form onSubmit={handleCommentSubmit} className="comment-form">
            <textarea
              value={newComment}
              onChange={(e) => setNewComment(e.target.value)}
              placeholder="Write a comment..."
              rows="3"
              required
            />
            <button type="submit">Post Comment</button>
          </form>
        </>
      ) : (
        <p style={{ color: 'red' }}>Log in to save and comment on this recipe.</p>
      )}

      {message && <p className="save-message">{message}</p>}

      <div className="comments-section">
        <h3>Comments</h3>
        {comments.length === 0 ? (
          <p>No comments yet.</p>
        ) : (
          <ul className="comment-list">
            {comments.map((c, idx) => (
              <li key={idx}>
                <strong>{c.user.username}</strong>: {c.content}
              </li>
            ))}
          </ul>
        )}
      </div>
    </div>
  );
}
