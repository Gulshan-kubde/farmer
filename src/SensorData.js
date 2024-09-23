import React, { useState, useEffect } from 'react';

const SensorData = () => {
  const [sensorData, setSensorData] = useState([]);

  // Simulating fetching sensor data
  useEffect(() => {
    // Replace this with actual API call if necessary
    const fetchData = async () => {
      const data = [
        { id: 1, sensorId: 'Sensor 1', value: '35%', type: 'Moisture' },
        { id: 2, sensorId: 'Sensor 2', value: '45%', type: 'Moisture' },
        { id: 3, sensorId: 'Sensor 3', value: '65%', type: 'Temperature' },
      ];
      setSensorData(data);
    };
    fetchData();
  }, []);

  return (
    <div style={styles.container}>
      <h2 style={styles.header}>Sensor Data</h2>
      {sensorData.length > 0 ? (
        <table style={styles.table}>
          <thead>
            <tr>
              <th>Sensor ID</th>
              <th>Value</th>
              <th>Type</th>
            </tr>
          </thead>
          <tbody>
            {sensorData.map((sensor) => (
              <tr key={sensor.id}>
                <td>{sensor.sensorId}</td>
                <td>{sensor.value}</td>
                <td>{sensor.type}</td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p>No sensor data available.</p>
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
  table: {
    width: '100%',
    borderCollapse: 'collapse',
  },
  th: {
    borderBottom: '1px solid #ccc',
    padding: '10px',
    textAlign: 'left',
  },
  td: {
    borderBottom: '1px solid #ccc',
    padding: '10px',
    textAlign: 'left',
  },
};

export default SensorData;
