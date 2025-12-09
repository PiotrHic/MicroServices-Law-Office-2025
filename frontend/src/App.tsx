import { BrowserRouter, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import LawyersPage from "./pages/LawyersPage";

export default function App() {
    return (
        <BrowserRouter>
            <Navbar />

            <Routes>
                <Route path="/api/lawyer/get/allLawyers" element={<LawyersPage />} />
            </Routes>
        </BrowserRouter>
    );
}