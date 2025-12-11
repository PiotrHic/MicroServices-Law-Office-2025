import { useEffect, useState } from "react";

function App() {
    const [lawyers, setLawyers] = useState([]);

    useEffect(() => {
        fetch("/api/lawyer/get/allLawyers")   // NIE używamy localhost! Proxy to załatwia!
            .then(res => res.json())
            .then(data => setLawyers(data))
            .catch(err => console.error(err));
    }, []);

    return (
        <div>
            <h1>Lista prawników</h1>
            <ul>
                {lawyers.map(l => (
                    <li key={l.id}>{l.name}</li>
                ))}
            </ul>
        </div>
    );
}

export default App;
