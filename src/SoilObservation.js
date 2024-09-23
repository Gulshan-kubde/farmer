import React, { useState } from 'react';

const SoilObservation = () => {
  const [isGpsEnabled, setIsGpsEnabled] = useState(false); // Simulating GPS state
  const [showGpsModal, setShowGpsModal] = useState(false);
  
  const [plot, setPlot] = useState('');
  const [rain, setRain] = useState('');
  const [soilColor, setSoilColor] = useState('');
  const [worms, setWorms] = useState('');
  const [nitrogen, setNitrogen] = useState('');
  const [submittedDetails, setSubmittedDetails] = useState([]);
  const [showDetails, setShowDetails] = useState(false);

  // Function to handle GPS status check
  const checkGpsStatus = () => {
    if (!isGpsEnabled) {
      setShowGpsModal(true);
    } else {
      // Proceed with form submission
      submitForm();
    }
  };

  // Function to simulate form submission
  const submitForm = () => {
    const newDetails = { plot, rain, soilColor, worms, nitrogen };
    setSubmittedDetails([...submittedDetails, newDetails]);
    alert('Form submitted successfully');
    clearForm();
  };

  // Handling modal close
  const handleCloseModal = () => {
    setShowGpsModal(false);
  };

  // Toggle GPS status
  const toggleGpsStatus = () => {
    setIsGpsEnabled(!isGpsEnabled);
    setShowGpsModal(false);
  };

  // Function to clear the form after submission
  const clearForm = () => {
    setPlot('');
    setRain('');
    setSoilColor('');
    setWorms('');
    setNitrogen('');
  };

  return (
    <div style={styles.container}>
      <h2 style={styles.header}>Soil Observation</h2>

      <div style={styles.form}>
        <select style={styles.input} value={plot} onChange={(e) => setPlot(e.target.value)}>
          <option value="">Select Plot</option>
          <option value="Plot 1">Plot 1</option>
          <option value="Plot 2">Plot 2</option>
        </select>

        <input
          type="text"
          style={styles.input}
          placeholder="Rain (mm)"
          value={rain}
          onChange={(e) => setRain(e.target.value)}
        />

        <select style={styles.input} value={soilColor} onChange={(e) => setSoilColor(e.target.value)}>
          <option value="">Select Soil Color</option>
          <option value="Black">Black</option>
          <option value="Brown">Brown</option>
        </select>

        <select style={styles.input} value={worms} onChange={(e) => setWorms(e.target.value)}>
          <option value="">Worms</option>
          <option value="Yes">Yes</option>
          <option value="No">No</option>
        </select>

        <input
          type="text"
          style={styles.input}
          placeholder="Nitrogen (%)"
          value={nitrogen}
          onChange={(e) => setNitrogen(e.target.value)}
        />

        <button onClick={checkGpsStatus} style={styles.submitButton}>
          Submit
        </button>
        <button onClick={() => setShowDetails(!showDetails)} style={styles.showButton}>
          {showDetails ? 'Hide Details' : 'Show Details'}
        </button>
      </div>

      {/* GPS Settings Modal */}
      {showGpsModal && (
        <div style={styles.modalOverlay}>
          <div style={styles.modalContent}>
            <h3>GPS Settings</h3>
            <p>GPS is not enabled. Do you want to enable GPS?</p>
            <div style={styles.modalButtons}>
              <button onClick={handleCloseModal} style={styles.cancelButton}>
                Cancel
              </button>
              <button onClick={toggleGpsStatus} style={styles.settingsButton}>
                {isGpsEnabled ? 'Disable GPS' : 'Enable GPS'}
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Show Details Section */}
      {showDetails && submittedDetails.length > 0 && (
        <div style={styles.detailsContainer}>
          <h3>Submitted Details</h3>
          {submittedDetails.map((detail, index) => (
            <div key={index} style={styles.detailItem}>
              <p>Plot: {detail.plot}</p>
              <p>Rain: {detail.rain} mm</p>
              <p>Soil Color: {detail.soilColor}</p>
              <p>Worms: {detail.worms}</p>
              <p>Nitrogen: {detail.nitrogen}%</p>
              <hr />
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

const styles = {
  container: {
    padding: '20px',
    maxWidth: '500px',
    margin: '0 auto',
    fontFamily: 'Arial, sans-serif',
    backgroundColor: '#f9f9f9',
    borderRadius: '10px',
    boxShadow: '0 4px 12px rgba(0,0,0,0.1)',
  },
  header: {
    textAlign: 'center',
    marginBottom: '20px',
    fontSize: '24px',
    color: '#4CAF50',
  },
  form: {
    display: 'flex',
    flexDirection: 'column',
    gap: '15px',
  },
  input: {
    padding: '10px',
    fontSize: '16px',
    borderRadius: '4px',
    border: '1px solid #ccc',
    width: '100%',
  },
  submitButton: {
    padding: '10px 15px',
    backgroundColor: '#4CAF50',
    color: '#fff',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
    fontSize: '16px',
  },
  showButton: {
    padding: '10px 15px',
    backgroundColor: '#2196F3',
    color: '#fff',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
    fontSize: '16px',
    marginTop: '10px',
  },
  modalOverlay: {
    position: 'fixed',
    top: '0',
    left: '0',
    right: '0',
    bottom: '0',
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
  },
  modalContent: {
    backgroundColor: '#fff',
    padding: '20px',
    borderRadius: '10px',
    textAlign: 'center',
    width: '300px',
  },
  modalButtons: {
    display: 'flex',
    justifyContent: 'space-between',
    marginTop: '20px',
  },
  cancelButton: {
    backgroundColor: '#ccc',
    border: 'none',
    padding: '10px 20px',
    borderRadius: '5px',
    cursor: 'pointer',
  },
  settingsButton: {
    backgroundColor: '#4CAF50',
    color: '#fff',
    border: 'none',
    padding: '10px 20px',
    borderRadius: '5px',
    cursor: 'pointer',
  },
  detailsContainer: {
    marginTop: '20px',
    backgroundColor: '#fff',
    borderRadius: '10px',
    padding: '15px',
    boxShadow: '0 4px 12px rgba(0,0,0,0.1)',
  },
  detailItem: {
    marginBottom: '10px',
  },
};

export default SoilObservation;
