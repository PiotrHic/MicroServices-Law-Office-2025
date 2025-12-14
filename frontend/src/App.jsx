import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import MainLayout from "./layout/MainLayout";

/* HOME */
import Home from "./Home";

/* LAWYER */
import LawyerService from "./pages/lawyer/LawyerService.jsx";
import AddLawyer from "./pages/lawyer/AddLawyer.jsx";
import GetLawyer from "./pages/lawyer/GetLawyer.jsx";
import UpdateLawyer from "./pages/lawyer/UpdateLawyer.jsx";
import DeleteLawyer from "./pages/lawyer/DeleteLawyer.jsx";

/* CLIENT */
import LawClientService from "./pages/lawclient/LawClientService.jsx";
import AddClient from "./pages/lawclient/AddClient.jsx";
import GetClient from "./pages/lawclient/GetClient.jsx";
import UpdateClient from "./pages/lawclient/UpdateClient.jsx";
import DeleteClient from "./pages/lawclient/DeleteClient.jsx";

/* LAW CASE */
import LawCaseService from "./pages/lawcase/LawCaseService.jsx";
import AddLawCase from "./pages/lawcase/AddLawCase.jsx";
import GetLawCase from "./pages/lawcase/GetLawCase.jsx";
import UpdateLawCase from "./pages/lawcase/UpdateLawCase.jsx";
import DeleteLawCase from "./pages/lawcase/DeleteLawCase.jsx";

function App() {
    return (
        <Router>
            <Routes>
                {/* LAYOUT */}
                <Route element={<MainLayout />}>
                    <Route path="/" element={<Home />} />

                    {/* LAWYERS */}
                    <Route path="/lawyers" element={<LawyerService />} />
                    <Route path="/lawyers/add" element={<AddLawyer />} />
                    <Route path="/lawyers/get" element={<GetLawyer />} />
                    <Route path="/lawyers/update" element={<UpdateLawyer />} />
                    <Route path="/lawyers/delete" element={<DeleteLawyer />} />

                    {/* CLIENTS */}
                    <Route path="/clients" element={<LawClientService />} />
                    <Route path="/clients/add" element={<AddClient />} />
                    <Route path="/clients/get" element={<GetClient />} />
                    <Route path="/clients/update" element={<UpdateClient />} />
                    <Route path="/clients/delete" element={<DeleteClient />} />

                    {/* LAW CASES */}
                    <Route path="/lawcases" element={<LawCaseService />} />
                    <Route path="/lawcases/add" element={<AddLawCase />} />
                    <Route path="/lawcases/get" element={<GetLawCase />} />
                    <Route path="/lawcases/update" element={<UpdateLawCase />} />
                    <Route path="/lawcases/delete" element={<DeleteLawCase />} />
                </Route>
            </Routes>
        </Router>
    );
}

export default App;