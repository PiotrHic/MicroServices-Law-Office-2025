import { useEffect, useState } from "react";
import { getClients, deleteClient } from "../api/clientsApi";

export default function ClientList() {
    const [clients, setClients] = useState([]);

    const load = () => {
        getClients().then(res => setClients(res.data));
    };

    useEffect(() => {
        load();
    }, []);

    return (
        <div>
            <h2>Lista klientów</h2>

            <table border="1" cellPadding="6">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Imię</th>
                    <th>Nazwisko</th>
                    <th>Akcje</th>
                </tr>
                </thead>

                <tbody>
                {clients.map(c => (
                    <tr key={c.id}>
                        <td>{c.id}</td>
                        <td>{c.firstName}</td>
                        <td>{c.lastName}</td>
                        <td>
                            <button onClick={() => deleteClient(c.id).then(load)}>
                                Usuń
                            </button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>

        </div>
    );
}