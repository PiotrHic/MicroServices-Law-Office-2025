import React from "react";
import { Link } from "react-router-dom";

export default function Navbar() {
    return (
        <nav style={{ padding: "10px", background: "#3b82f6", color: "#fff", display: "flex", justifyContent: "space-around" }}>
            <Link to="/lawyers" style={{ color: "#fff" }}>Lawyers</Link>
            <Link to="/lawcases" style={{ color: "#fff" }}>LawCases</Link>
            <Link to="/lawclients" style={{ color: "#fff" }}>LawClients</Link>
        </nav>
    );
}