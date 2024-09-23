import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import logo from './assets/logo.png';

const Register = () => {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [userName, setUserName] = useState('');
  const [email, setEmail] = useState('');
  const [role, setRole] = useState(''); // Dropdown for admin and user roles
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false); // State for toggling password visibility
  const [showConfirmPassword, setShowConfirmPassword] = useState(false); // State for toggling confirm password visibility
  const [showAlert, setShowAlert] = useState(false); // State for showing alert
  const [alertMessage, setAlertMessage] = useState('');
  const navigate = useNavigate(); // Use for redirecting

  const handleRegister = (e) => {
    e.preventDefault();
    
    // Check if all required fields are filled
    if (!firstName || !lastName || !userName || !email || !role || !password || !confirmPassword) {
      setAlertMessage('All fields are required');
      setShowAlert(true);
      return;
    }

    // Check if the password is alphanumeric
    const alphanumericRegex = /^[a-z0-9]+$/i;
    if (!alphanumericRegex.test(password)) {
      setAlertMessage('Password must be alphanumeric');
      setShowAlert(true);
      return;
    }

    // Check if passwords match
    if (password !== confirmPassword) {
      setAlertMessage('Passwords do not match');
      setShowAlert(true);
      return;
    }

    // Handle registration logic here
    setAlertMessage('Registered successfully!');
    setShowAlert(true);
    setTimeout(() => {
      navigate('/home'); // After successful registration, navigate to the home page
    }, 2000);
  };

  const closeAlert = () => {
    setShowAlert(false); // Close alert on clicking OK
  };

  return (
    <div style={styles.formContainer}>
      <img src={logo} alt="Cropulse Logo" style={styles.logo} />
      <h2 style={styles.title}>Register</h2>
      <form onSubmit={handleRegister}>
        <input
          type="text"
          placeholder="First Name"
          value={firstName}
          onChange={(e) => setFirstName(e.target.value)}
          style={styles.input}
          required // Makes the field required
        />
        <input
          type="text"
          placeholder="Last Name"
          value={lastName}
          onChange={(e) => setLastName(e.target.value)}
          style={styles.input}
          required
        />
        <input
          type="text"
          placeholder="Username"
          value={userName}
          onChange={(e) => setUserName(e.target.value)}
          style={styles.input}
          required
        />
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          style={styles.input}
          required
        />
        <select
          value={role}
          onChange={(e) => setRole(e.target.value)}
          style={styles.input}
          required
        >
          <option value="">Select Role</option>
          <option value="Admin">Admin</option>
          <option value="User">User</option>
        </select>
        <div style={styles.passwordContainer}>
          <input
            type={showPassword ? 'text' : 'password'}
            placeholder="Password (Alphanumeric)"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            style={styles.passwordInput}
            required
          />
          <span onClick={() => setShowPassword(!showPassword)} style={styles.eyeIcon}>
            {showPassword ? '🙈' : '👁️'}
          </span>
        </div>
        <div style={styles.passwordContainer}>
          <input
            type={showConfirmPassword ? 'text' : 'password'}
            placeholder="Confirm Password"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            style={styles.passwordInput}
            required
          />
          <span onClick={() => setShowConfirmPassword(!showConfirmPassword)} style={styles.eyeIcon}>
            {showConfirmPassword ? '🙈' : '👁️'}
          </span>
        </div>
        <button type="submit" style={styles.submitButton}>Register</button>
      </form>

      {/* Custom Alert */}
      {showAlert && (
        <div style={styles.alertOverlay}>
          <div style={styles.alertBox}>
            <p>{alertMessage}</p>
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
  passwordContainer: {
    position: 'relative',
    display: 'flex',
    alignItems: 'center',
    marginBottom: '20px',
  },
  passwordInput: {
    flex: 1,
    padding: '10px',
    fontSize: '16px',
    borderRadius: '5px',
    border: '1px solid #ccc',
  },
  eyeIcon: {
    position: 'absolute',
    right: '10px',
    cursor: 'pointer',
    fontSize: '18px',
  },
  submitButton: {
    padding: '10px 20px',
    backgroundColor: '#4CAF50',
    color: '#fff',
    border: 'none',
    borderRadius: '5px',
    cursor: 'pointer',
  },
  alertOverlay: {
    position: 'fixed',
    top: 0,
    left: 0,
    width: '100vw',
    height: '100vh',
    backgroundColor: 'rgba(0, 0, 0, 0.7)', // Darkened background
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    zIndex: 1000,
    backdropFilter: 'blur(5px)', // Blur background
  },
  alertBox: {
    backgroundColor: '#fff',
    padding: '30px', // Make the alert box bigger
    borderRadius: '10px',
    border: '2px solid #4CAF50', // Add green border
    textAlign: 'center',
    boxShadow: '0 4px 12px rgba(0,0,0,0.3)',
    fontSize: '18px',
    maxWidth: '300px',
  },
  okButton: {
    padding: '10px 20px',
    backgroundColor: '#4CAF50',
    color: '#fff',
    border: 'none',
    borderRadius: '5px',
    cursor: 'pointer',
    fontSize: '16px',
  },
};

export default Register;
