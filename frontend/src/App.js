import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import SignupPage from './pages/SignupPage';
import AllRecipesPage from './pages/AllRecipesPage';
import RecipeDetailPage from './pages/RecipeDetailPage';
import CreateRecipePage from './pages/CreateRecipePage';
import SavedRecipesPage from './pages/SavedRecipesPage';
import Navbar from './components/Navbar';

function App() {
  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/" element={<AllRecipesPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/signup" element={<SignupPage />} />
        <Route path="/recipes/:id" element={<RecipeDetailPage />} />
        <Route path="/create" element={<CreateRecipePage />} />
        <Route path="/saved" element={<SavedRecipesPage />} />
      </Routes>
    </Router>
  );
}

export default App;
