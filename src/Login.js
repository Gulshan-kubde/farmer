import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import logo from './assets/logo.png';

const Login = () => {
  const [userID, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [showAlert, setShowAlert] = useState(false); // State for showing alert
  const navigate = useNavigate(); // Use for redirecting

  const handleLogin = async (e) => {
    e.preventDefault();

    // Create the login payload (adjusted for your backend)
    const loginData = {
      username: userID,   // Your backend uses 'username'
      password: password, // Your backend uses 'password'
    };

    try {
      // Make the POST request to the login API
      const response = await fetch('http://localhost:8081/auth/api/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(loginData),
      });

      if (response.ok) {
        // If the login is successful, navigate to the home page
        navigate('/home');
      } else {
        // If login fails, show alert
        setShowAlert(true);
      }
    } catch (error) {
      console.error('Login failed:', error);
      setShowAlert(true); // Show alert if there's an error
    }
  };

  const closeAlert = () => {
    setShowAlert(false); // Close alert on clicking OK
  };

  return (
    <div style={styles.formContainer}>
      <img src={logo} alt="Cropulse Logo" style={styles.logo} />
      <h2 style={styles.title}>Login</h2>
      <form onSubmit={handleLogin}>
        <input
          type="text"
          placeholder="Username"
          value={userID}
          onChange={(e) => setUsername(e.target.value)}
          style={styles.input}
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          style={styles.input}
        />
        <button type="submit" style={styles.submitButton}>Login</button>
      </form>
      <p>Don't have an account? <Link to="/register">Register here</Link></p>
      
      {/* Custom Alert */}
      {showAlert && (
        <div style={styles.alertContainer}>
          <div style={styles.alertBox}>
            <p>Please enter valid credentials</p>
            <button onClick={closeAlert} style={styles.okButton}>OK</button>
          </div>
        </div>
      )}
    </div>
  );
};

const styles = {
  formContainer: {
    maxWidth: '400px',
    margin: '150px auto',
    padding: '20px',
    textAlign: 'center',
    backgroundColor: '#fff',
    borderRadius: '10px',
    boxShadow: '0 4px 12px rgba(0,0,0,0.1)',
  },
  logo: {
    width: '100px',
    marginBottom: '20px',
  },
  title: {
    fontSize: '24px',
    marginBottom: '20px',
    fontWeight: 'bold',
    color: '#4CAF50',
  },
  input: {
    display: 'block',
    width: '100%',
    padding: '10px',
    marginBottom: '20px',
    fontSize: '16px',
    borderRadius: '5px',
    border: '1px solid #ccc',
  },
  submitButton: {
    padding: '10px 20px',
    backgroundColor: '#4CAF50',
    color: '#fff',
    border: 'none',
    borderRadius: '5px',
    cursor: 'pointer',
  },
  alertContainer: {
    position: 'fixed',
    top: 0,
    left: 0,
    width: '100vw',
    height: '100vh',
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    zIndex: 1000,
  },
  alertBox: {
    backgroundColor: '#fff',
    padding: '20px',
    borderRadius: '10px',
    textAlign: 'center',
    boxShadow: '0 4px 12px rgba(0,0,0,0.1)',
  },
  okButton: {
    padding: '10px 20px',
    backgroundColor: '#4CAF50',
    color: '#fff',
    border: 'none',
    borderRadius: '5px',
    cursor: 'pointer',
  },
};

export default Login;
