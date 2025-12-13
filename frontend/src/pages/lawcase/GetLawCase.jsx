import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import {
    Box,
    Container,
    Paper,
    Typography,
    Button,
    Stack,
    TextField,
    Alert,
    Card,
    CardContent,
    CircularProgress,
    Divider
} from "@mui/material";
import axios from "axios";

export default function GetLawCase() {
    const [lawCases, setLawCases] = useState([]);
    const [singleLawCase, setSingleLawCase] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [idInput, setIdInput] = useState("");
    const [nameInput, setNameInput] = useState("");

    const navigate = useNavigate();

    const fetchAllLawCases = async () => {
        setLoading(true);
        setError("");
        setSingleLawCase(null);

        try {
            const res = await axios.get("/api/lawcase/get/allLawCases");
            setLawCases(res.data);
        } catch (err) {
            setError(err.response?.data?.message || err.message || "Something went wrong!");
            setLawCases([]);
        } finally {
            setLoading(false);
        }
    };

    const fetchLawCaseById = async () => {
        if (!idInput.trim()) return;

        setLoading(true);
        setError("");
        setLawCases([]);
        setSingleLawCase(null);

        try {
            const res = await axios.get(`/api/lawcase/get/byId/${idInput}`);
            setSingleLawCase(res.data);
        } catch (err) {
            setError(err.response?.data?.message || err.message || "Something went wrong!");
        } finally {
            setLoading(false);
        }
    };

    const fetchLawCaseByName = async () => {
        if (!nameInput.trim()) return;

        setLoading(true);
        setError("");
        setLawCases([]);
        setSingleLawCase(null);

        try {
            const res = await axios.get("/api/lawcase/get/byName", {
                params: { lawCaseName: nameInput } // <- dokładnie tak jak w backendzie
            });
            setSingleLawCase(res.data); // pamiętaj, żeby ustawić wynik!
        } catch (err) {
            setError(err.response?.data?.message || err.message || "Something went wrong!");
        } finally {
            setLoading(false);
        }
    };

    return (
        <Box
            sx={{
                minHeight: "100vh",
                background: "linear-gradient(135deg, #ede7f6, #d1c4e9)",
                py: 5
            }}
        >
            <Container maxWidth="md">
                <Paper
                    elevation={12}
                    sx={{
                        p: 5,
                        borderRadius: 3,
                        background: "rgba(255,255,255,0.95)"
                    }}
                >
                    <Stack spacing={3}>
                        <Typography
                            variant="h3"
                            sx={{
                                fontWeight: "bold",
                                textAlign: "center",
                                color: "#512da8"
                            }}
                        >
                            Law Case Finder
                        </Typography>

                        {/* 🔙 BACK */}
                        <Button
                            variant="outlined"
                            color="inherit"
                            onClick={() => navigate("/lawcases")}
                            sx={{ width: "220px", alignSelf: "center" }}
                        >
                            Back to LawCase Service
                        </Button>

                        {/* Controls */}
                        <Stack
                            direction={{ xs: "column", sm: "row" }}
                            spacing={2}
                            sx={{ justifyContent: "center", flexWrap: "wrap" }}
                        >
                            <Button
                                variant="contained"
                                color="primary"
                                onClick={fetchAllLawCases}
                            >
                                All Law Cases
                            </Button>

                            <TextField
                                label="Search by ID"
                                value={idInput}
                                onChange={(e) => setIdInput(e.target.value)}
                                size="small"
                                sx={{ width: 160 }}
                            />
                            <Button
                                variant="outlined"
                                color="secondary"
                                onClick={fetchLawCaseById}
                            >
                                Find by ID
                            </Button>

                            <TextField
                                label="Search by Name"
                                value={nameInput}
                                onChange={(e) => setNameInput(e.target.value)}
                                size="small"
                                sx={{ width: 200 }}
                            />
                            <Button
                                variant="outlined"
                                color="secondary"
                                onClick={fetchLawCaseByName}
                            >
                                Find by Name
                            </Button>
                        </Stack>

                        {loading && (
                            <CircularProgress sx={{ mt: 2, alignSelf: "center" }} />
                        )}

                        {error && (
                            <Alert severity="error" sx={{ mt: 2, fontWeight: "bold" }}>
                                {error}
                            </Alert>
                        )}

                        {/* LISTA */}
                        {!loading && lawCases.length > 0 && (
                            <Stack spacing={2} sx={{ mt: 2 }}>
                                {lawCases.map((lawCase) => (
                                    <Card
                                        key={lawCase.id}
                                        sx={{
                                            background: "linear-gradient(135deg, #ffffff, #ede7f6)",
                                            transition: "0.3s",
                                            "&:hover": { transform: "scale(1.02)" }
                                        }}
                                    >
                                        <CardContent>
                                            <Typography variant="h6">
                                                {lawCase.name}
                                            </Typography>
                                            <Typography variant="body2">
                                                <strong>ID:</strong> {lawCase.id}
                                            </Typography>
                                            <Divider sx={{ my: 1 }} />
                                            <Typography variant="body2">
                                                <strong>Lawyer ID:</strong>{" "}
                                                {lawCase.lawyerId || "—"}
                                            </Typography>
                                            <Typography variant="body2">
                                                <strong>Client ID:</strong>{" "}
                                                {lawCase.lawClientId || "—"}
                                            </Typography>
                                        </CardContent>
                                    </Card>
                                ))}
                            </Stack>
                        )}

                        {/* SINGLE */}
                        {!loading && singleLawCase && (
                            <Card
                                sx={{
                                    mt: 2,
                                    p: 2,
                                    background: "linear-gradient(135deg, #c5cae9, #9fa8da)"
                                }}
                            >
                                <CardContent>
                                    <Typography variant="h5" sx={{ fontWeight: "bold" }}>
                                        {singleLawCase.name}
                                    </Typography>
                                    <Divider sx={{ my: 1 }} />
                                    <Typography>
                                        <strong>ID:</strong> {singleLawCase.id}
                                    </Typography>
                                    <Typography>
                                        <strong>Lawyer ID:</strong>{" "}
                                        {singleLawCase.lawyerId || "—"}
                                    </Typography>
                                    <Typography>
                                        <strong>Client ID:</strong>{" "}
                                        {singleLawCase.lawClientId || "—"}
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
