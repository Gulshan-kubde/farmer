import React from 'react';
import { Link } from 'react-router-dom';
import logo from './assets/logo.png';
import agriculture from './assets/premium.png'; // You can replace it with the new image

const LandingPage = () => {
  return (
    <div style={styles.container}>
      {/* Logo */}
      <div style={styles.logoContainer}>
        <img src={logo} alt="Cropulse Logo" style={styles.logo} />
      </div>

      {/* Landing content */}
      <div style={styles.textContainer}>
        <h1>Welcome to Cropulse</h1>
        <p>Plan, Manage, and Monitor Your Agriculture Activities</p>
        <div>
          <Link to="/login" style={styles.button}>Login</Link>
          <Link to="/register" style={styles.button}>Register</Link>
        </div>
      </div>

      {/* Blurred background image */}
      <div style={styles.backgroundImageContainer}>
        <img src={agriculture} alt="Agriculture" style={styles.backgroundImage} />
      </div>
    </div>
  );
};

const styles = {
  container: {
    position: 'relative',
    height: '100vh',
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#f0f4f7',
    overflow: 'hidden',
  },
  logoContainer: {
    position: 'absolute',
    top: '20px',
    left: '20px',
    zIndex: 2, // Ensure it stays on top of the background
    mixBlendMode: 'multiply', // Blend mode to make logo blend with the background
  },
  logo: {
    width: '150px',
    height: 'auto',
    opacity: 0.8, // Optional: Reduce opacity to further integrate with background
  },
  textContainer: {
    zIndex: 1,
    color: '#fff',
    textAlign: 'center',
    fontWeight: 'bold',
    textShadow: '2px 2px 10px rgba(0, 0, 0, 0.7)', // Keeps the text readable on the background
  },
  button: {
    display: 'inline-block',
    padding: '12px 24px',
    margin: '10px',
    backgroundColor: '#4CAF50',
    color: '#fff',
    textDecoration: 'none',
    borderRadius: '5px',
    fontSize: '18px',
    transition: 'background-color 0.3s ease',
    boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
  },
  backgroundImageContainer: {
    position: 'absolute',
    top: 0,
    left: 0,
    width: '100%',
    height: '100%',
    zIndex: 0,
    overflow: 'hidden',
  },
  backgroundImage: {
    width: '100%',
    height: '100%',
    objectFit: 'cover',
    filter: 'blur(2px)',  // Subtle blur
    opacity: 0.9,  // Slight opacity to make text clearer
  },
};

export default LandingPage;
