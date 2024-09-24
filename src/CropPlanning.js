import React, { useState, useEffect } from 'react';
import axios from 'axios';
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

  const [plots, setPlots] = useState([]);
  const [cropTypes, setCropTypes] = useState([]);
  const [seeds, setSeeds] = useState([]);
  const [cultivations, setCultivations] = useState([]);

  // Fetch crop plans from the backend API
  useEffect(() => {
    const fetchCropPlans = async () => {
      try {
        const response = await axios.get('http://localhost:8081/api/crop_planning');
        setCropPlans(response.data);
      } catch (error) {
        console.error('Error fetching crop plans:', error);
      }
    };
    fetchCropPlans();
  }, []);

  // Fetch plots
  useEffect(() => {
    const fetchPlots = async () => {
      try {
        const response = await axios.get('http://localhost:8081/api/plots');
        setPlots(response.data);
      } catch (error) {
        console.error('Error fetching plots:', error);
      }
    };
    fetchPlots();
  }, []);

  // Fetch crop types
  useEffect(() => {
    const fetchCropTypes = async () => {
      try {
        const response = await axios.get('http://localhost:8081/api/cropTypes');
        setCropTypes(response.data);
      } catch (error) {
        console.error('Error fetching crop types:', error);
      }
    };
    fetchCropTypes();
  }, []);

  // Fetch seeds
  useEffect(() => {
    const fetchSeeds = async () => {
      try {
        const response = await axios.get('http://localhost:8081/api/seeds');
        setSeeds(response.data);
      } catch (error) {
        console.error('Error fetching seeds:', error);
      }
    };
    fetchSeeds();
  }, []);

  // Fetch cultivation methods
  useEffect(() => {
    const fetchCultivations = async () => {
      try {
        const response = await axios.get('http://localhost:8081/api/cultivations');
        setCultivations(response.data);
      } catch (error) {
        console.error('Error fetching cultivations:', error);
      }
    };
    fetchCultivations();
  }, []);

  // Function to handle adding or updating crop plans
  const handleAddOrUpdatePlan = async () => {
    const cropPlanning = {
      plot: plot.plotName,             // Send plot name
      cropType: cropType.cropName,     // Send crop type name
      sowingDate,
      yield: expectedYield,
      seedsUsed: seedsUsed.seedName,   // Send seeds used name
      revenue: expectedRevenue,
      cultivation: cultivation.cultivationName, // Send cultivation name
    };

    if (selectedPlan !== null) {
      // Update existing plan logic
      try {
        await axios.put(`http://localhost:8081/api/crop_planning/${selectedPlan.id}`, cropPlanning);
        const updatedPlans = cropPlans.map((plan) =>
          plan.id === selectedPlan.id ? { ...plan, ...cropPlanning } : plan
        );
        setCropPlans(updatedPlans);
        setSelectedPlan(null);
      } catch (error) {
        console.error('Error updating crop plan:', error);
      }
    } else {
      // Add new plan
      try {
        const response = await axios.post('http://localhost:8081/api/crop_planning', cropPlanning);
        setCropPlans([...cropPlans, response.data]);
      } catch (error) {
        console.error('Error adding crop plan:', error);
      }
    }
    clearForm();
  };

  // Edit plan functionality
  const handleEdit = (plan) => {
    setSelectedPlan(plan);
    setPlot(plan.plot);
    setCropType(plan.cropType);
    setSowingDate(plan.sowingDate);
    setExpectedYield(plan.yield);
    setSeedsUsed(plan.seedsUsed);
    setExpectedRevenue(plan.revenue);
    setCultivation(plan.cultivation);
  };

  // Delete plan functionality
  const handleDelete = async (id) => {
    try {
      await axios.delete(`http://localhost:8081/api/crop_planning/${id}`);
      const updatedPlans = cropPlans.filter((plan) => plan.id !== id);
      setCropPlans(updatedPlans);
    } catch (error) {
      console.error('Error deleting crop plan:', error);
    }
  };

  // Clear form fields
  const clearForm = () => {
    setPlot('');
    setCropType('');
    setSowingDate('');
    setExpectedYield('');
    setSeedsUsed('');
    setExpectedRevenue('');
    setCultivation('');
    setSelectedPlan(null);
  };

  return (
    <div style={styles.container}>
      <h2 style={styles.header}>Crop Planning</h2>

      <div style={styles.formContainer}>
        <div style={styles.form}>
          <select style={styles.input} value={plot} onChange={(e) => setPlot(JSON.parse(e.target.value))}>
            <option value="">Select Plot</option>
            {plots.map((plot) => (
              <option key={plot.plotId} value={JSON.stringify(plot)}>
                {plot.plotName}
              </option>
            ))}
          </select>

          <select style={styles.input} value={cropType} onChange={(e) => setCropType(JSON.parse(e.target.value))}>
            <option value="">Select Crop Type</option>
            {cropTypes.map((crop) => (
              <option key={crop.cropTypeId} value={JSON.stringify(crop)}>
                {crop.cropName}
              </option>
            ))}
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

          <select style={styles.input} value={seedsUsed} onChange={(e) => setSeedsUsed(JSON.parse(e.target.value))}>
            <option value="">Select Seeds</option>
            {seeds.map((seed) => (
              <option key={seed.seedId} value={JSON.stringify(seed)}>
                {seed.seedName}
              </option>
            ))}
          </select>

          <input
            type="text"
            style={styles.input}
            placeholder="Expected Revenue"
            value={expectedRevenue}
            onChange={(e) => setExpectedRevenue(e.target.value)}
          />

          <select style={styles.input} value={cultivation} onChange={(e) => setCultivation(JSON.parse(e.target.value))}>
            <option value="">Select Cultivation</option>
            {cultivations.map((cult) => (
              <option key={cult.cultivationId} value={JSON.stringify(cult)}>
                {cult.cultivationName}
              </option>
            ))}
          </select>

          <button onClick={handleAddOrUpdatePlan} style={styles.addButton}>
            {selectedPlan ? 'Update Plan' : 'Add Plan'} <FaPlus />
          </button>
        </div>

        <div style={styles.planList}>
          <h3>Existing Crop Plans</h3>
          {cropPlans.map((plan) => (
            <div key={plan.id} style={styles.planItem}>
              <div>
                <p><strong>Plot:</strong> {plan.plot}</p>
                <p><strong>Crop Type:</strong> {plan.cropType}</p>
                <p><strong>Sowing Date:</strong> {new Date(plan.sowingDate).toLocaleDateString()}</p>
                <p><strong>Yield:</strong> {plan.yield}</p>
                <p><strong>Seeds Used:</strong> {plan.seedsUsed}</p>
                <p><strong>Revenue:</strong> {plan.revenue}</p>
                <p><strong>Cultivation:</strong> {plan.cultivation}</p>
              </div>
              <div>
                <button onClick={() => handleEdit(plan)} style={styles.editButton}>
                  <FaEdit /> Edit
                </button>
                <button onClick={() => handleDelete(plan.id)} style={styles.deleteButton}>
                  <FaTrash /> Delete
                </button>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

// Styles for the component
const styles = {
  container: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    padding: '20px',
    backgroundColor: '#f4f4f4',
    minHeight: '100vh',
  },
  header: {
    fontSize: '24px',
    marginBottom: '20px',
  },
  formContainer: {
    display: 'flex',
    width: '100%',
    maxWidth: '1200px',
  },
  form: {
    display: 'flex',
    flexDirection: 'column',
    width: '40%',
  },
  input: {
    marginBottom: '10px',
    padding: '10px',
    fontSize: '16px',
    borderRadius: '5px',
    border: '1px solid #ccc',
  },
  addButton: {
    backgroundColor: '#28a745',
    color: '#fff',
    padding: '10px 20px',
    fontSize: '16px',
    borderRadius: '5px',
    border: 'none',
    cursor: 'pointer',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  planList: {
    flex: '1',
    marginLeft: '20px',
  },
  planItem: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: '10px',
    backgroundColor: '#fff',
    marginBottom: '10px',
    borderRadius: '5px',
    boxShadow: '0 4px 8px rgba(0,0,0,0.1)',
  },
  editButton: {
    backgroundColor: '#ffc107',
    border: 'none',
    color: '#fff',
    padding: '5px 10px',
    borderRadius: '3px',
    cursor: 'pointer',
  },
  deleteButton: {
    backgroundColor: '#dc3545',
    border: 'none',
    color: '#fff',
    padding: '5px 10px',
    borderRadius: '3px',
    cursor: 'pointer',
  },
};

export default CropPlanning;
