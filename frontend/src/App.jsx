import React from "react";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import { Typography, Button, Stack, Box } from "@mui/material";

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
            </Routes>
        </Router>
    );
}

function Home() {
    return (
        <Box
            sx={{
                minHeight: "100vh",
                background: "linear-gradient(to right, #3b82f6, #8b5cf6)",
                display: "flex",
                flexDirection: "column",
                justifyContent: "center",
                alignItems: "center",
                color: "white",
                p: 2
            }}
        >
            <Typography variant="h2" mb={2} textAlign="center">
                Welcome to Law Office Management
            </Typography>

            <Typography variant="h5" mb={4} textAlign="center">
                Manage lawyers, cases, and clients easily with our modern application.
            </Typography>

            <Stack direction="row" spacing={2}>
                <Button
                    component={Link}
                    to="/lawyers"
                    variant="contained"
                    color="secondary"
                >
                    Lawyer Service
                </Button>

                <Button
                    component={Link}
                    to="/clients"
                    variant="contained"
                    color="secondary"
                >
                    Client Service
                </Button>

                <Button
                    component={Link}
                    to="/lawcases"
                    variant="contained"
                    color="secondary"
                >
                    LawCase Service
                </Button>
            </Stack>
        </Box>
    );
}

export default App;
