import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import FarmLandPlanning from './FarmLandPlanning';
import CropPlanning from './CropPlanning'; 
import SoilObservation from './SoilObservation';
import Sensors from './Sensors';
import SensorData from './SensorData';
import Login from './Login';
import Register from './Register';
import LandingPage from './LandingPage';
import logo from './assets/logo.png'; // Logo you already have

// Layout Component for consistent elements across pages
const Layout = ({ children }) => {
  return (
    <div style={styles.container}>
      <div style={styles.headerContainer}>
        <img src={logo} alt="Cropulse Logo" style={styles.logo} />
        <h1 style={styles.header}>Welcome to the Cropulse</h1>
      </div>
      <div style={styles.pageContent}>
        {children}
      </div>
    </div>
  );
};

const Home = () => {
  return (
    <Layout>
      <div style={styles.buttonContainer}>
        <Link to="/farm-land-planning" style={styles.button}>Farm Land Planning</Link>
        <Link to="/crop-planning" style={styles.button}>Crop Planning</Link>
        <Link to="/activity-update" style={styles.button}>Activity Update</Link>
        <Link to="/crop-observation" style={styles.button}>Crop Observation</Link>
        <Link to="/soil-observation" style={styles.button}>Soil Observation</Link>
        <Link to="/sensors" style={styles.button}>Sensors</Link>
        <Link to="/sensors-data" style={styles.button}>Sensors Data</Link>
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

const styles = {
  container: {
    fontFamily: 'Arial, sans-serif',
    backgroundColor: '#f0f4f7',
    padding: '20px',
    minHeight: '100vh',
  },
  headerContainer: {
    textAlign: 'center',
    marginBottom: '40px',
    borderBottom: '2px solid #4CAF50',
    paddingBottom: '20px',
  },
  logo: {
    width: '150px',
    height: 'auto',
    display: 'block',
    margin: '0 auto',
  },
  header: {
    color: '#4CAF50',
    fontSize: '32px',
    fontWeight: 'bold',
    marginTop: '10px',
  },
  buttonContainer: {
    display: 'grid',
    gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))',
    gridGap: '20px',
    justifyItems: 'center',
    padding: '0 20px',
    maxWidth: '800px',
    margin: '0 auto',
  },
  button: {
    display: 'inline-block',
    padding: '20px 30px',
    backgroundColor: '#e0f7fa',
    color: '#00796b',
    textAlign: 'center',
    borderRadius: '8px',
    border: '2px solid #4CAF50',
    textDecoration: 'none',
    fontSize: '18px',
    boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
    transition: 'background-color 0.3s ease',
    textTransform: 'uppercase',
    width: '220px',
    height: '100px',
    cursor: 'pointer',
  },
  pageContent: {
    border: '2px solid #4CAF50',
    borderRadius: '10px',
    padding: '20px',
    marginTop: '20px',
    backgroundColor: '#fff',
    boxShadow: '0 4px 12px rgba(0,0,0,0.1)',
  },
};

export default App;
