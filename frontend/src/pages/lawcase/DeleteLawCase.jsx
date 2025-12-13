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
    CircularProgress,
    Card,
    CardContent,
    Divider
} from "@mui/material";
import axios from "axios";

export default function DeleteLawCase() {
    const [idInput, setIdInput] = useState("");
    const [nameInput, setNameInput] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");
    const [deletedLawCase, setDeletedLawCase] = useState(null);

    const navigate = useNavigate();

    const resetAlerts = () => {
        setError("");
        setSuccess("");
        setDeletedLawCase(null);
    };

    // ✅ DELETE BY ID
    const handleDeleteById = async () => {
        if (!idInput.trim()) {
            setError("ID is required!");
            return;
        }

        resetAlerts();
        setLoading(true);

        try {
            const res = await axios.delete(
                `/api/lawcase/delete/byId/${encodeURIComponent(idInput)}`
            );
            setDeletedLawCase(res.data);
            setSuccess("Law case deleted successfully!");
        } catch (err) {
            console.error(err);
            if (err.response) {
                if (err.response.status === 403) setError("Access denied (403).");
                else if (err.response.status === 404) setError("Law case not found (404).");
                else setError(err.response.data?.message || "Something went wrong!");
            } else {
                setError(err.message);
            }
        } finally {
            setLoading(false);
        }
    };

    // ✅ DELETE BY NAME
    const handleDeleteByName = async () => {
        if (!nameInput.trim()) {
            setError("Case name is required!");
            return;
        }

        resetAlerts();
        setLoading(true);

        try {
            const res = await axios.delete(
                "/api/lawcase/delete/byName",
                { params: { lawCaseName: nameInput.trim() } } // ✅ poprawnie
            );
            setDeletedLawCase(res.data);
            setSuccess("Law case deleted successfully!");
        } catch (err) {
            console.error(err);
            if (err.response) {
                if (err.response.status === 403) setError("Access denied (403).");
                else if (err.response.status === 404) setError("Law case not found (404).");
                else setError(err.response.data?.message || "Something went wrong!");
            } else {
                setError(err.message);
            }
        } finally {
            setLoading(false);
        }
    };

    // ✅ DELETE ALL
    const handleDeleteAll = async () => {
        resetAlerts();
        setLoading(true);

        try {
            const res = await axios.delete("/api/lawcase/delete/allLawCases");
            setSuccess(res.data || "All law cases deleted!");
        } catch (err) {
            console.error(err);
            setError(err.response?.data?.message || "Something went wrong!");
        } finally {
            setLoading(false);
        }
    };

    return (
        <Box sx={{ minHeight: "100vh", bgcolor: "#ffebee", py: 5 }}>
            <Container maxWidth="md">
                <Paper elevation={12} sx={{ p: 5, borderRadius: 3 }}>
                    <Stack spacing={3}>
                        <Typography
                            variant="h4"
                            sx={{
                                fontWeight: "bold",
                                textAlign: "center",
                                color: "#b71c1c"
                            }}
                        >
                            Delete Law Case
                        </Typography>

                        {/* 🔙 BACK */}
                        <Button
                            variant="outlined"
                            color="inherit"
                            onClick={() => navigate("/lawcases")}
                            sx={{ width: "240px", alignSelf: "center" }}
                        >
                            Back to LawCase Service
                        </Button>

                        {/* DELETE BY ID */}
                        <Card sx={{ p: 3, background: "#ffcdd2" }}>
                            <Typography variant="h6" sx={{ mb: 2 }}>
                                Delete by ID
                            </Typography>
                            <Stack direction={{ xs: "column", sm: "row" }} spacing={2}>
                                <TextField
                                    label="Law Case ID"
                                    value={idInput}
                                    onChange={(e) => setIdInput(e.target.value)}
                                    size="small"
                                />
                                <Button
                                    variant="contained"
                                    color="error"
                                    onClick={handleDeleteById}
                                >
                                    Delete
                                </Button>
                            </Stack>
                        </Card>

                        {/* DELETE BY NAME */}
                        <Card sx={{ p: 3, background: "#ef9a9a" }}>
                            <Typography variant="h6" sx={{ mb: 2 }}>
                                Delete by Name
                            </Typography>
                            <Stack direction={{ xs: "column", sm: "row" }} spacing={2}>
                                <TextField
                                    label="Case Name"
                                    value={nameInput}
                                    onChange={(e) => setNameInput(e.target.value)}
                                    size="small"
                                />
                                <Button
                                    variant="contained"
                                    color="error"
                                    onClick={handleDeleteByName}
                                >
                                    Delete
                                </Button>
                            </Stack>
                        </Card>

                        {/* DELETE ALL */}
                        <Card sx={{ p: 3, background: "#e57373" }}>
                            <Typography variant="h6" sx={{ mb: 2 }}>
                                Delete ALL Law Cases
                            </Typography>
                            <Button
                                variant="contained"
                                color="error"
                                onClick={handleDeleteAll}
                            >
                                Delete All
                            </Button>
                        </Card>

                        {loading && (
                            <CircularProgress sx={{ mt: 2, alignSelf: "center" }} />
                        )}

                        {error && <Alert severity="error">{error}</Alert>}
                        {success && <Alert severity="success">{success}</Alert>}

                        {deletedLawCase && (
                            <Card sx={{ mt: 2, p: 2, background: "#ffcdd2" }}>
                                <CardContent>
                                    <Typography variant="h6" sx={{ fontWeight: "bold" }}>
                                        Deleted Law Case:
                                    </Typography>
                                    <Divider sx={{ my: 1 }} />
                                    <Typography>
                                        <strong>ID:</strong> {deletedLawCase.id}
                                    </Typography>
                                    <Typography>
                                        <strong>Name:</strong> {deletedLawCase.name}
                                    </Typography>
                                    <Typography>
                                        <strong>Lawyer ID:</strong>{" "}
                                        {deletedLawCase.lawyerId || "—"}
                                    </Typography>
                                    <Typography>
                                        <strong>Client ID:</strong>{" "}
                                        {deletedLawCase.lawClientId || "—"}
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