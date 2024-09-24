import React, { useState, useEffect } from 'react';
import { FaPlus, FaEdit, FaTrash } from 'react-icons/fa';
import axios from 'axios';

const FarmLandPlanning = () => {
  const [showForm, setShowForm] = useState(false); // Initially hide the form
  const [approxAcre, setAcre] = useState('');
  const [cents, setCents] = useState('');
  const [name, setFarmName] = useState('');
  const [farms, setFarms] = useState([]);
  const [isEditing, setIsEditing] = useState(false);
  const [editIndex, setEditIndex] = useState(null);

  useEffect(() => {
    // Fetch farms from backend
    axios.get('http://localhost:8081/api/farmlands')
      .then(response => {
        setFarms(response.data);
      })
      .catch(error => {
        console.error('Error fetching farms:', error);
      });
  }, []);

  const toggleForm = () => {
    setShowForm(!showForm);
  };

  const addOrEditFarm = () => {
    if (approxAcre && cents && name) {
      const newFarm = { approxAcre, cents, name };
  
      if (isEditing) {
        // Update existing farm
        axios.put(`http://localhost:8081/api/farmlands/${farms[editIndex].farmerId}`, newFarm)
          .then(response => {
            const updatedFarm = response.data;
            const updatedFarms = [...farms];
            updatedFarms[editIndex] = { ...updatedFarm, farmerId: farms[editIndex].farmerId }; // Retain the farmerId
            setFarms(updatedFarms);
            resetForm();
          })
          .catch(error => {
            console.error('Error editing farm:', error);
          });
      } else {
        // Add new farm
        axios.post('http://localhost:8081/api/farmlands', newFarm)
          .then(response => {
            const savedFarm = response.data;
            setFarms([...farms, savedFarm]); // Add new farm to the state
            resetForm();
          })
          .catch(error => {
            console.error('Error adding farm:', error);
          });
      }
    }
  };
  
  const resetForm = () => {
    setAcre('');
    setCents('');
    setFarmName('');
    setShowForm(false);
    setIsEditing(false);
    setEditIndex(null);
  };
  

  const deleteFarm = (index) => {
    const farmToDelete = farms[index];
    axios.delete(`http://localhost:8081/api/farmlands/${farmToDelete.farmerId}`)
      .then(() => {
        setFarms(farms.filter((_, i) => i !== index));
      })
      .catch(error => {
        console.error('Error deleting farm:', error);
      });
  };

  const editFarm = (index) => {
    const farmToEdit = farms[index];
    setAcre(farmToEdit.approxAcre);
    setCents(farmToEdit.cents);
    setFarmName(farmToEdit.name);
    setIsEditing(true);
    setEditIndex(index);
    setShowForm(true); // Show form with pre-filled values for editing
  };

  return (
    <div style={styles.container}>
      <h2 style={styles.header}>Farm Land Planning</h2>

      {/* Add Button - Initially Visible */}
      {!showForm && (
        <button onClick={toggleForm} style={styles.addButton}>
          Add <FaPlus />
        </button>
      )}

      {/* Form - Visible only when showForm is true */}
      {showForm && (
        <form style={styles.form}>
          <input
            type="text"
            placeholder="Approx Acre"
            value={approxAcre}
            onChange={(e) => setAcre(e.target.value)}
            style={styles.input}
          />
          <input
            type="text"
            placeholder="Cents"
            value={cents}
            onChange={(e) => setCents(e.target.value)}
            style={styles.input}
          />
          <input
            type="text"
            placeholder="Name"
            value={name}
            onChange={(e) => setFarmName(e.target.value)}
            style={styles.input}
          />
          <div style={styles.buttonGroup}>
            <button
              style={styles.doneButton}
              onClick={(e) => {
                e.preventDefault();
                setShowForm(false);
              }}
            >
              Done
            </button>
            <button
              onClick={(e) => {
                e.preventDefault();
                addOrEditFarm();
              }}
              style={styles.addButton}
            >
              {isEditing ? 'Edit' : 'Add'} <FaPlus />
            </button>
          </div>
        </form>
      )}

      {/* Display Farms in Cards */}
      {farms.length > 0 && (
        <div style={styles.cardContainer}>
          {farms.map((farm, index) => (
            <div key={farm.farmerId} style={styles.card}>
              <p><strong>Name:</strong> {farm.name}</p>
              <p><strong>Approx Acre:</strong> {farm.approxAcre}</p>
              <p><strong>Cents:</strong> {farm.cents}</p>
              <div style={styles.buttonGroup}>
                <button
                  onClick={() => editFarm(index)}
                  style={styles.editButton}
                >
                  Edit <FaEdit />
                </button>
                <button
                  onClick={() => deleteFarm(index)}
                  style={styles.deleteButton}
                >
                  Delete <FaTrash />
                </button>
              </div>
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
    boxShadow: '0 4px 8px rgba(0,0,0,0.1)',
  },
  header: {
    textAlign: 'center',
    marginBottom: '20px',
    fontSize: '24px',
    fontWeight: 'bold',
  },
  form: {
    display: 'flex',
    flexDirection: 'column',
    gap: '10px',
    marginBottom: '20px',
    border: '1px solid #ccc',
    padding: '15px',
    borderRadius: '10px',
    backgroundColor: '#ffffff',
  },
  input: {
    padding: '10px',
    fontSize: '16px',
    borderRadius: '4px',
    border: '1px solid #ccc',
    width: '100%',
  },
  buttonGroup: {
    display: 'flex',
    justifyContent: 'space-between',
  },
  addButton: {
    backgroundColor: '#28a745',
    color: '#fff',
    border: 'none',
    padding: '10px 20px',
    borderRadius: '4px',
    cursor: 'pointer',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    gap: '5px',
  },
  doneButton: {
    backgroundColor: '#007bff',
    color: '#fff',
    border: 'none',
    padding: '10px 20px',
    borderRadius: '4px',
    cursor: 'pointer',
  },
  cardContainer: {
    display: 'flex',
    flexDirection: 'column',
    gap: '15px',
  },
  card: {
    padding: '15px',
    backgroundColor: '#f0f4f7',
    borderRadius: '8px',
    boxShadow: '0 2px 4px rgba(0,0,0,0.1)',
  },
  deleteButton: {
    backgroundColor: '#dc3545',
    color: '#fff',
    border: 'none',
    padding: '5px 10px',
    borderRadius: '4px',
    cursor: 'pointer',
  },
  editButton: {
    backgroundColor: '#ffc107',
    color: '#fff',
    border: 'none',
    padding: '5px 10px',
    borderRadius: '4px',
    cursor: 'pointer',
    marginRight: '10px',
  },
};

export default FarmLandPlanning;
