import React, { useState } from 'react';

const Sensors = () => {
  const [sensorId, setSensorId] = useState('');
  const [cropType, setCropType] = useState(''); // New state for crop type
  const [capturedData, setCapturedData] = useState(null);

  // Simulate capturing sensor data
  const captureData = () => {
    if (!sensorId || !cropType) {
      alert('Please select a Sensor ID and Crop Type');
      return;
    }

    // Simulating the captured data
    const data = {
      sensorId: sensorId,
      cropType: cropType, // Include crop type in the captured data
      moistureLevel: Math.floor(Math.random() * 100) + 1, // random value between 1-100
      timestamp: new Date().toLocaleString(),
    };

    setCapturedData(data);
  };

  return (
    <div style={styles.container}>
      <h2 style={styles.header}>Moisture Sensor</h2>

      <div style={styles.sensorForm}>
        <select
          value={sensorId}
          onChange={(e) => setSensorId(e.target.value)}
          style={styles.input}
        >
          <option value="">Sensor ID</option>
          <option value="Sensor 1">Sensor 1</option>
          <option value="Sensor 2">Sensor 2</option>
          <option value="Sensor 3">Sensor 3</option>
        </select>

        <select
          value={cropType}
          onChange={(e) => setCropType(e.target.value)}
          style={styles.input}
        >
          <option value="">Crop Type</option>
          <option value="Rice">Rice</option>
          <option value="Wheat">Wheat</option>
          <option value="Maize">Maize</option>
          <option value="Soybean">Soybean</option>
        </select>

        <button onClick={captureData} style={styles.captureButton}>
          Capture
        </button>
      </div>

      {/* Display captured data */}
      {capturedData && (
        <div style={styles.capturedData}>
          <h3>Captured Data</h3>
          <p><strong>Sensor ID:</strong> {capturedData.sensorId}</p>
          <p><strong>Crop Type:</strong> {capturedData.cropType}</p>
          <p><strong>Moisture Level:</strong> {capturedData.moistureLevel}%</p>
          <p><strong>Timestamp:</strong> {capturedData.timestamp}</p>
        </div>
      )}
    </div>
  );
};

const styles = {
  container: {
    padding: '20px',
    maxWidth: '400px',
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
  sensorForm: {
    display: 'flex',
    flexDirection: 'column',
    gap: '10px',
    marginBottom: '20px',
  },
  input: {
    padding: '10px',
    fontSize: '16px',
    borderRadius: '4px',
    border: '1px solid #ccc',
    width: '100%',
  },
  captureButton: {
    padding: '10px',
    backgroundColor: '#4CAF50',
    color: '#fff',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
    fontSize: '16px',
  },
  capturedData: {
    marginTop: '20px',
    backgroundColor: '#fff',
    padding: '15px',
    borderRadius: '10px',
    boxShadow: '0 4px 12px rgba(0,0,0,0.1)',
  },
};

export default Sensors;
