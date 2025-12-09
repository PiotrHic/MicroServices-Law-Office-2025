import { Link } from "react-router-dom";

export default function Navbar() {
    return (
        <nav style={{ padding: 15, background: "#1a1a1a", color: "white" }}>
            <Link to="/lawyers" style={{ marginRight: 20, color: "white" }}>
                Lawyers
            </Link>
        </nav>
    );
}