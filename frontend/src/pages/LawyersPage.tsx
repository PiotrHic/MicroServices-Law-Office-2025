import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getAllLawyers } from "../api/lawyersApi";

export default function LawyersPage() {
    const [lawyers, setLawyers] = useState<any[]>([]);

    useEffect(() => {
        getAllLawyers().then(setLawyers);
    }, []);

    return (
        <div style={{ padding: 20 }}>
            <h1>Lawyers</h1>

            <ul>
                {lawyers.map((lawyer) => (
                    <li key={lawyer.id}>
                        <Link to={`/lawyers/${lawyer.id}`}>
                            {lawyer.name}
                        </Link>
                    </li>
                ))}
            </ul>
        </div>
    );
}