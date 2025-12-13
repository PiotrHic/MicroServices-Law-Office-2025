import React, { useState } from "react";
import { useNavigate } from "react-router-dom";   // <-- DODANE
import { Box, Container, Paper, Typography, Button, Stack, TextField, Alert, Card, CardContent, CircularProgress, Divider } from "@mui/material";
import axios from "axios";

export default function GetLawyer() {
    const [lawyers, setLawyers] = useState([]);
    const [singleLawyer, setSingleLawyer] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [idInput, setIdInput] = useState("");
    const [nameInput, setNameInput] = useState("");

    const navigate = useNavigate(); // <-- DODANE

    const fetchAllLawyers = async () => {
        setLoading(true); setError(""); setSingleLawyer(null);
        try {
            const res = await axios.get("/api/lawyer/get/allLawyers");
            setLawyers(res.data);
        } catch (err) {
            setError(err.response?.data?.message || err.message || "Something went wrong!");
            setLawyers([]);
        } finally {
            setLoading(false);
        }
    };

    const fetchLawyerById = async () => {
        if (!idInput.trim()) return;
        setLoading(true); setError(""); setLawyers([]); setSingleLawyer(null);
        try {
            const res = await axios.get(`/api/lawyer/get/byId/${idInput}`);
            setSingleLawyer(res.data);
        } catch (err) {
            setError(err.response?.data?.message || err.message || "Something went wrong!");
        } finally {
            setLoading(false);
        }
    };

    const fetchLawyerByName = async () => {
        if (!nameInput.trim()) return;
        setLoading(true); setError(""); setLawyers([]); setSingleLawyer(null);
        try {
            const res = await axios.get(`/api/lawyer/get/byName`, { params: { lawyerName: nameInput } });
            setSingleLawyer(res.data);
        } catch (err) {
            setError(err.response?.data?.message || err.message || "Something went wrong!");
        } finally {
            setLoading(false);
        }
    };

    return (
        <Box sx={{ minHeight: "100vh", bgcolor: "linear-gradient(135deg, #e0f7fa, #b2ebf2)", py: 5 }}>
            <Container maxWidth="md">
                <Paper elevation={12} sx={{ p: 5, borderRadius: 3, background: "rgba(255,255,255,0.95)" }}>
                    <Stack spacing={3}>

                        <Typography variant="h3" sx={{ fontWeight: "bold", textAlign: "center", color: "#00796b" }}>
                            Lawyer Finder
                        </Typography>

                        {/* 🔙 PRZYCISK POWROTU */}
                        <Button
                            variant="outlined"
                            color="inherit"
                            onClick={() => navigate("/lawyers")}
                            sx={{ width: "200px", alignSelf: "center" }}
                        >
                            Back to Lawyer Service
                        </Button>

                        {/* Inputy i przyciski */}
                        <Stack direction={{ xs: "column", sm: "row" }} spacing={2} sx={{ justifyContent: "center", flexWrap: "wrap" }}>
                            <Button variant="contained" color="primary" onClick={fetchAllLawyers}>All Lawyers</Button>

                            <TextField
                                label="Search by ID"
                                value={idInput}
                                onChange={(e) => setIdInput(e.target.value)}
                                size="small"
                                sx={{ width: 150 }}
                            />
                            <Button variant="outlined" color="secondary" onClick={fetchLawyerById}>Find by ID</Button>

                            <TextField
                                label="Search by Name"
                                value={nameInput}
                                onChange={(e) => setNameInput(e.target.value)}
                                size="small"
                                sx={{ width: 180 }}
                            />
                            <Button variant="outlined" color="secondary" onClick={fetchLawyerByName}>Find by Name</Button>
                        </Stack>

                        {loading && <CircularProgress sx={{ mt: 2, alignSelf: "center" }} />}

                        {error && <Alert severity="error" sx={{ mt: 2, fontWeight: "bold" }}>{error}</Alert>}

                        {!loading && lawyers.length > 0 && (
                            <Stack spacing={2} sx={{ mt: 2 }}>
                                {lawyers.map((lawyer) => (
                                    <Card key={lawyer.id} sx={{ background: "linear-gradient(135deg, #ffffff, #e0f7fa)", transition: "0.3s", "&:hover": { transform: "scale(1.02)" } }}>
                                        <CardContent>
                                            <Typography variant="h6">{lawyer.name}</Typography>
                                            <Typography variant="body2"><strong>ID:</strong> {lawyer.id}</Typography>
                                            <Typography variant="body2">
                                                {lawyer.lawCaseList?.length
                                                    ? `Cases: ${lawyer.lawCaseList.map(c => c.id).join(", ")}`
                                                    : "No cases assigned"}
                                            </Typography>
                                        </CardContent>
                                    </Card>
                                ))}
                            </Stack>
                        )}

                        {!loading && singleLawyer && (
                            <Card sx={{ mt: 2, p: 2, background: "linear-gradient(135deg, #b2dfdb, #80cbc4)" }}>
                                <CardContent>
                                    <Typography variant="h5" sx={{ fontWeight: "bold" }}>{singleLawyer.name}</Typography>
                                    <Typography><strong>ID:</strong> {singleLawyer.id}</Typography>
                                    <Typography>
                                        <strong>Cases:</strong>
                                        {singleLawyer.lawCaseList?.length
                                            ? singleLawyer.lawCaseList.map(c => c.id).join(", ")
                                            : "No cases"}
                                    </Typography>
                                </CardContent>
                            </Card>
                        )}
                    </Stack>
                </Paper>
            </Container>
        </Box>
    );
}
