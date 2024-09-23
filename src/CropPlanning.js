import React, { useState, useEffect } from 'react';
import axios from 'axios'; // Import axios for making API calls
import { FaEdit, FaTrash, FaPlus } from 'react-icons/fa';

const CropPlanning = () => {
  const [cropPlans, setCropPlans] = useState([]);
  const [selectedPlan, setSelectedPlan] = useState(null);
  const [plot, setPlot] = useState('');
  const [cropType, setCropType] = useState('');
  const [sowingDate, setSowingDate] = useState('');
  const [expectedYield, setExpectedYield] = useState('');
  const [seedsUsed, setSeedsUsed] = useState('');
  const [expectedRevenue, setExpectedRevenue] = useState('');
  const [cultivation, setCultivation] = useState('');

   // Fetch crop plans from the backend
   useEffect(() => {
    const fetchCropPlans = async () => {
      try {
        const response = await axios.get('http://localhost:8080/crop-planning'); // Fetch from backend
        setCropPlans(response.data);
      } catch (error) {
        console.error('Error fetching crop plans:', error);
      }
    };
    fetchCropPlans();
  }, []);

  const handleAddOrUpdatePlan = async () => {
    const cropPlanning = { plot, cropType, sowingDate, yield: expectedYield, seedsUsed, revenue: expectedRevenue, cultivation };
    if (selectedPlan !== null) {
      // Update existing plan
      const updatedPlans = cropPlans.map((plan, index) =>
        index === selectedPlan ? cropPlanning : plan
      );
      setCropPlans(updatedPlans);
      setSelectedPlan(null);
    } else {
      // Add new plan
      try {
        const response = await axios.post('http://localhost:8080/crop-planning', cropPlanning); // Send to backend
        setCropPlans([...cropPlans, response.data]); // Update state with new plan
      } catch (error) {
        console.error('Error adding crop plan:', error);
      }
    }
    clearForm();
  };

  const handleEdit = (index) => {
    const plan = cropPlans[index];
    setSelectedPlan(index);
    setPlot(plan.plot);
    setCropType(plan.cropType);
    setSowingDate(plan.sowingDate);
    setExpectedYield(plan.expectedYield);
    setSeedsUsed(plan.seedsUsed);
    setExpectedRevenue(plan.expectedRevenue);
    setCultivation(plan.cultivation);
  };

  const handleDelete = (index) => {
    const updatedPlans = cropPlans.filter((_, i) => i !== index);
    setCropPlans(updatedPlans);
  };

  const clearForm = () => {
    setPlot('');
    setCropType('');
    setSowingDate('');
    setExpectedYield('');
    setSeedsUsed('');
    setExpectedRevenue('');
    setCultivation('');
  };

  return (
    <div style={styles.container}>
      <h2 style={styles.header}>Crop Planning</h2>
      
      <div style={styles.formContainer}>
        {/* Left side: Form */}
        <div style={styles.form}>
          <select style={styles.input} value={plot} onChange={(e) => setPlot(e.target.value)}>
            <option value="">Select Plot</option>
            {/* Add more plot options as required */}
            <option value="Plot 1">Plot 1</option>
            <option value="Plot 2">Plot 2</option>
          </select>

          <select style={styles.input} value={cropType} onChange={(e) => setCropType(e.target.value)}>
            <option value="">Select Crop Type</option>
            {/* Add more crop options as required */}
            <option value="Wheat">Wheat</option>
            <option value="Rice">Rice</option>
          </select>

          <input
            type="date"
            style={styles.input}
            placeholder="Sowing Date"
            value={sowingDate}
            onChange={(e) => setSowingDate(e.target.value)}
          />

          <input
            type="text"
            style={styles.input}
            placeholder="Expected Yield"
            value={expectedYield}
            onChange={(e) => setExpectedYield(e.target.value)}
          />

          <select style={styles.input} value={seedsUsed} onChange={(e) => setSeedsUsed(e.target.value)}>
            <option value="">Select Seeds</option>
            {/* Add more seed options as required */}
            <option value="Seed 1">Seed 1</option>
            <option value="Seed 2">Seed 2</option>
          </select>

          <input
            type="text"
            style={styles.input}
            placeholder="Expected Revenue"
            value={expectedRevenue}
            onChange={(e) => setExpectedRevenue(e.target.value)}
          />

          <select style={styles.input} value={cultivation} onChange={(e) => setCultivation(e.target.value)}>
            <option value="">Select Cultivation</option>
            {/* Add more cultivation options as required */}
            <option value="Organic">Organic</option>
            <option value="Inorganic">Inorganic</option>
          </select>

          <button onClick={handleAddOrUpdatePlan} style={styles.addButton}>
            {selectedPlan !== null ? 'Update' : 'Add Plan'} <FaPlus />
          </button>
        </div>

        {/* Right side: List of crop plans */}
        <div style={styles.planList}>
          <h3>Existing Crop Plans</h3>
          {cropPlans.map((plan, index) => (
            <div key={index} style={styles.planItem}>
              <div>
                <p><strong>Plan {index + 1}:</strong> {plan.plot}</p>
              </div>
              <div>
                <button onClick={() => handleEdit(index)} style={styles.editButton}><FaEdit /> Edit</button>
                <button onClick={() => handleDelete(index)} style={styles.deleteButton}><FaTrash /> Delete</button>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

const styles = {
  container: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    fontFamily: 'Arial, sans-serif',
    backgroundColor: '#f7f7f7',
    minHeight: '100vh',
    padding: '20px',
  },
  header: {
    fontSize: '24px',
    color: '#4CAF50',
    marginBottom: '20px',
  },
  formContainer: {
    display: 'flex',
    justifyContent: 'space-between',
    width: '100%',
    maxWidth: '1200px',
  },
  form: {
    flex: '1',
    padding: '20px',
    backgroundColor: '#fff',
    borderRadius: '10px',
    boxShadow: '0 4px 8px rgba(0,0,0,0.1)',
  },
  input: {
    width: '100%',
    padding: '10px',
    margin: '10px 0',
    borderRadius: '4px',
    border: '1px solid #ccc',
  },
  addButton: {
    backgroundColor: '#28a745',
    color: '#fff',
    padding: '10px 20px',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    gap: '5px',
  },
  planList: {
    flex: '1',
    marginLeft: '20px',
    padding: '20px',
    backgroundColor: '#fff',
    borderRadius: '10px',
    boxShadow: '0 4px 8px rgba(0,0,0,0.1)',
  },
  planItem: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: '10px',
    borderBottom: '1px solid #ccc',
  },
  editButton: {
    backgroundColor: '#ffc107',
    color: '#fff',
    padding: '5px 10px',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
    marginRight: '10px',
  },
  deleteButton: {
    backgroundColor: '#dc3545',
    color: '#fff',
    padding: '5px 10px',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
  },
};

export default CropPlanning;
