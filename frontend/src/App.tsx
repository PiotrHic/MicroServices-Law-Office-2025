import { BrowserRouter, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import LawyersPage from "./pages/LawyersPage";
import LawyerDetailsPage from "./pages/LawyerDetailsPage";
import AddLawyerPage from "./pages/AddLawyerPage";

export default function App() {
    return (
        <BrowserRouter>
            <Navbar />

            <Routes>
                <Route path="/" element={<LawyersPage />} />
                <Route path="/lawyers" element={<LawyersPage />} />
                <Route path="/lawyers/:id" element={<LawyerDetailsPage />} />
                <Route path="/lawyers/add" element={<AddLawyerPage />} />
            </Routes>
        </BrowserRouter>
    );
}