import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, useNavigate } from 'react-router-dom';
import './App.css';
import Login from './components/login/login';
import Admin from './components/admin/admin';
import ProjectHomePage from './components/developer/projecthomepage';
import BugDetailsPage from './components/developer/bug/bugdetailspage';
import NavBar from './components/navbar/navbar';
import TokenService from './services/auth/tokenservice';

function App() {
  const tokenService = new TokenService();
  const [isAuthenticated, setIsAuthenticated] = useState(() => {
    const token = tokenService.getAccessToken();
    return !!token; // Only check if token exists, not if it's expired
  });

  useEffect(() => {
    const handleStorageChange = () => {
      const token = localStorage.getItem('access_token');
      setIsAuthenticated(!!token); // Only check if token exists, not if it's expired
    };

    window.addEventListener('storage', handleStorageChange);

    return () => {
      window.removeEventListener('storage', handleStorageChange);
    };
  }, [tokenService]);

  const onLogin = () => {
    setIsAuthenticated(true);
  };

  return (
    <Router>
      <div>
        {isAuthenticated && <NavBar />}
        <Routes>
          <Route path="/" element={<Login onLogin={onLogin} />} />
          <Route path="/login" element={<Login onLogin={onLogin} />} />
          <Route path="/admin" element={<Admin />} />
          <Route path="/project_home_page/:userId" element={<ProjectHomePage />} />
          <Route path="/bugs/:bugId/:projectId" element={<BugDetailsPage />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
