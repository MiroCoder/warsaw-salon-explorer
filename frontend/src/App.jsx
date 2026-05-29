import { useEffect, useState } from 'react'
import './App.css'

function App() {
  const [salons, setSalons] = useState([])
  const [district, setDistrict] = useState('')
  const [selectedSalon, setSelectedSalon] = useState(null)
  const [isEditing, setIsEditing] = useState(false)
  const [editSalon, setEditSalon] = useState(null)
  const [saveStatus, setSaveStatus] = useState('')
  const [error, setError] = useState('')

  useEffect(() => {
    const url = district.trim()
      ? `http://localhost:8080/api/salons?district=${encodeURIComponent(district)}`
      : 'http://localhost:8080/api/salons'

    setError('')

    fetch(url)
      .then(response => {
        if (!response.ok) {
          throw new Error('Failed to load salons')
        }
        return response.json()
      })
      .then(data => setSalons(data))
      .catch(() => {
        setError('Could not load salons. Please check if backend is running.')
        setSalons([])
      })
  }, [district])

      function handleSelectSalon(salon) {
        setSelectedSalon(salon)
        setEditSalon({ ...salon })
        setIsEditing(false)
        setSaveStatus('')
      }

      function handleEditChange(e) {
        const { name, value } = e.target

        setEditSalon(previous => ({
          ...previous,
          [name]: value
        }))
      }

      function handleSave(e) {
        e.preventDefault()

        const salonToSave = {
          ...editSalon,
          rating: Number(editSalon.rating),
          reviewCount: Number(editSalon.reviewCount)
        }

        fetch(`http://localhost:8080/api/salons/${editSalon.id}`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(salonToSave)
        })
          .then(response => response.json())
          .then(savedSalon => {
            setSelectedSalon(savedSalon)
            setEditSalon(savedSalon)
            setSalons(salons.map(salon =>
              salon.id === savedSalon.id ? savedSalon : salon
            ))
            setSaveStatus('Saved')
            setIsEditing(false)
          })
        .catch(() => {
          setSaveStatus('Save failed. Please try again.')
        })
      }

  return (
    <main className="app">
      <h1>Warsaw Beauty Salon Explorer</h1>

      <input
        {error && <p className="error">{error}</p>}
        placeholder="Filter by district"
        value={district}
        onChange={(e) => setDistrict(e.target.value)}
      />

      {selectedSalon && (
        <section className="details">
          <h2>{selectedSalon.name}</h2>
          <p><strong>Address:</strong> {selectedSalon.address}</p>
          <p><strong>District:</strong> {selectedSalon.district}</p>
          <p><strong>Phone:</strong> {selectedSalon.phone}</p>
          <p><strong>Website:</strong> {selectedSalon.website}</p>
          <p><strong>Services:</strong> {selectedSalon.services}</p>
          <p><strong>Price:</strong> {selectedSalon.priceRange}</p>
          <p><strong>Rating:</strong> {selectedSalon.rating} ({selectedSalon.reviewCount} reviews)</p>

          <button type="button" onClick={() => setIsEditing(!isEditing)}>
            Admin edit
          </button>

          {isEditing && editSalon && (
            <form onSubmit={handleSave}>
              <h3>Edit salon data</h3>

              <input name="name" value={editSalon.name} onChange={handleEditChange} />
              <input name="phone" value={editSalon.phone} onChange={handleEditChange} />
              <input name="services" value={editSalon.services} onChange={handleEditChange} />
              <input name="priceRange" value={editSalon.priceRange} onChange={handleEditChange} />
              <input name="rating" value={editSalon.rating} onChange={handleEditChange} />
              <input name="reviewCount" value={editSalon.reviewCount} onChange={handleEditChange} />

              <button type="submit">Save changes</button>
              <p>{saveStatus}</p>
            </form>
          )}
        </section>
      )}

      <section className="list">
        {salons.map(salon => (
          <div
            className="card"
            key={salon.id}
            onClick={() => handleSelectSalon(salon)}
          >
            <h2>{salon.name}</h2>
            <p>{salon.district}</p>
            <p>⭐ {salon.rating}</p>
            <p>{salon.priceRange}</p>
            <button type="button">View details</button>
          </div>
        ))}
      </section>
    </main>
  )
}

export default App