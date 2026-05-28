import { useEffect, useState } from 'react'
import './App.css'

function App() {
  const [salons, setSalons] = useState([])
  const [district, setDistrict] = useState('')
  const [selectedSalon, setSelectedSalon] = useState(null)

  useEffect(() => {
    const url = district.trim()
      ? `http://localhost:8080/api/salons?district=${encodeURIComponent(district)}`
      : 'http://localhost:8080/api/salons'

    fetch(url)
      .then(response => response.json())
      .then(data => setSalons(data))
  }, [district])

  return (
    <main className="app">
      <h1>Warsaw Beauty Salon Explorer</h1>

      <input
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
        </section>
      )}

      <section className="list">
        {salons.map(salon => (
          <div
            className="card"
            key={salon.id}
            onClick={() => setSelectedSalon(salon)}
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