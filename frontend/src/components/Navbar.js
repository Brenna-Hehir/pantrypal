import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import './Navbar.css';

export default function Navbar() {
  const location = useLocation();

  return (
    <nav className="navbar">
      <div className="navbar-logo">PantryPal</div>
      <div className="navbar-links">
        <Link to="/" className={location.pathname === '/' ? 'active' : ''}>All Recipes</Link>
        <Link to="/create" className={location.pathname === '/create' ? 'active' : ''}>Create</Link>
        <Link to="/saved" className={location.pathname === '/saved' ? 'active' : ''}>Saved</Link>
        <Link to="/signup" className={location.pathname === '/signup' ? 'active' : ''}>Sign Up</Link>
        <Link to="/login" className={location.pathname === '/login' ? 'active' : ''}>Login</Link>
      </div>
    </nav>
  );
}
