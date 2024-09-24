import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link, useNavigate } from 'react-router-dom';
import FarmLandPlanning from './FarmLandPlanning';
import CropPlanning from './CropPlanning';
import SoilObservation from './SoilObservation';
import Sensors from './Sensors';
import SensorData from './SensorData';
import Login from './Login';
import Register from './Register';
import LandingPage from './LandingPage';
import logo from './assets/logo.png'; // Ensure the logo path is correct
import './App.css';

// Layout Component for consistent design
const Layout = ({ children }) => {
  const navigate = useNavigate();

  // Handle logout
  const handleLogout = () => {
    navigate('/login');
  };

  return (
    <div className="container">
      <header className="headerContainer">
        <img src={logo} alt="Cropulse Logo" className="logo" />
        <h1 className="header">Welcome to Cropulse</h1>
        <button onClick={handleLogout} className="logoutButton">Logout</button>
      </header>
      <div className="pageContent">
        {children}
      </div>
    </div>
  );
};

const Home = () => {
  return (
    <Layout>
      <div className="buttonContainer">
        <Link to="/farm-land-planning" className="button">Farm Land Planning</Link>
        <Link to="/crop-planning" className="button">Crop Planning</Link>
        <Link to="/activity-update" className="button">Activity Update</Link>
        <Link to="/crop-observation" className="button">Crop Observation</Link>
        <Link to="/soil-observation" className="button">Soil Observation</Link>
        <Link to="/sensors" className="button">Sensors</Link>
        <Link to="/sensors-data" className="button">Sensors Data</Link>
      </div>
    </Layout>
  );
};

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/home" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/farm-land-planning" element={<Layout><FarmLandPlanning /></Layout>} />
        <Route path="/crop-planning" element={<Layout><CropPlanning /></Layout>} />
        <Route path="/soil-observation" element={<Layout><SoilObservation /></Layout>} />
        <Route path="/sensors" element={<Layout><Sensors /></Layout>} />
        <Route path="/sensors-data" element={<Layout><SensorData /></Layout>} />
      </Routes>
    </Router>
  );
};

export default App;
