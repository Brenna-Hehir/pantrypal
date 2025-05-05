import React, { useState } from 'react';
import './LoginPage.css';

export default function LoginPage() {
  const [formData, setFormData] = useState({
    username: '',
    password: '',
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
  
    try {
      const response = await fetch("http://localhost:8080/api/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          username: formData.username,
          passwordHash: formData.password,
        }),
      });
  
      if (response.ok) {
        const data = await response.json();
        localStorage.setItem("userId", data.userId);
        localStorage.setItem("username", data.username);
        setMessage(data.message); // "Login successful"
      } else {
        const errorMsg = await response.text();
        setMessage(errorMsg);
      }
    } catch (error) {
      console.error("Login failed:", error);
      setMessage("An error occurred. Please try again.");
    }
  };  

  return (
    <div className="login-container">
      <h2>Login</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="username"
          placeholder="Username"
          value={formData.username}
          onChange={handleChange}
          required
        />
        <input
          type="password"
          name="password"
          placeholder="Password"
          value={formData.password}
          onChange={handleChange}
          required
        />
        <button type="submit">Login</button>
      </form>
      {message && <p className="login-message">{message}</p>}
    </div>
  );
}
