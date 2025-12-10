import { useState } from "react";
import { createClient } from "../api/clientsApi";

export default function ClientForm({ onAdded }) {
    const [form, setForm] = useState({ firstName: "", lastName: "" });

    const submit = (e) => {
        e.preventDefault();
        createClient(form).then(() => {
            onAdded();
            setForm({ firstName: "", lastName: "" });
        });
    };

    return (
        <form onSubmit={submit}>
            <h3>Dodaj klienta</h3>

            <input
                placeholder="Imię"
                value={form.firstName}
                onChange={e => setForm({ ...form, firstName: e.target.value })}
            />

            <input
                placeholder="Nazwisko"
                value={form.lastName}
                onChange={e => setForm({ ...form, lastName: e.target.value })}
            />

            <button type="submit">Dodaj</button>
        </form>
    );
}