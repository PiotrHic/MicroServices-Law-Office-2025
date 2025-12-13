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

export default function DeleteClient() {
    const [idInput, setIdInput] = useState("");
    const [nameInput, setNameInput] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");
    const [deletedClient, setDeletedClient] = useState(null);

    const navigate = useNavigate();

    const resetAlerts = () => {
        setError("");
        setSuccess("");
        setDeletedClient(null);
    };

    // ✅ Delete by ID
    const handleDeleteById = async () => {
        if (!idInput.trim()) {
            setError("ID is required!");
            return;
        }

        resetAlerts();
        setLoading(true);

        try {
            const res = await axios.delete(`/api/lawclient/delete/deleteById/${encodeURIComponent(idInput)}`);
            setDeletedClient(res.data);
            setSuccess("Client deleted successfully by ID!");
        } catch (err) {
            console.error(err);
            if (err.response) {
                if (err.response.status === 404) setError("Client not found (404).");
                else setError(err.response.data?.message || "Something went wrong!");
            } else {
                setError(err.message);
            }
        } finally {
            setLoading(false);
        }
    };

    // ✅ Delete by Name
    const handleDeleteByName = async () => {
        if (!nameInput.trim()) {
            setError("Name is required!");
            return;
        }

        resetAlerts();
        setLoading(true);

        try {
            const res = await axios.delete("/api/lawclient/delete/byName", {
                params: { lawClientName: nameInput.trim() },
            });
            setDeletedClient(res.data);
            setSuccess("Client deleted successfully by Name!");
        } catch (err) {
            console.error(err);
            if (err.response) {
                if (err.response.status === 404) setError("Client not found (404).");
                else setError(err.response.data?.message || "Something went wrong!");
            } else {
                setError(err.message);
            }
        } finally {
            setLoading(false);
        }
    };

    // ✅ Delete all clients
    const handleDeleteAll = async () => {
        resetAlerts();
        setLoading(true);

        try {
            const res = await axios.delete("/api/lawclient/delete/allLawClients");
            setSuccess(res.data);
        } catch (err) {
            console.error(err);
            setError(err.response?.data?.message || "Something went wrong!");
        } finally {
            setLoading(false);
        }
    };

    return (
        <Box sx={{ minHeight: "100vh", bgcolor: "#f3e5f5", py: 5 }}>
            <Container maxWidth="md">
                <Paper elevation={12} sx={{ p: 5, borderRadius: 3 }}>
                    <Stack spacing={3}>
                        <Typography variant="h4" sx={{ fontWeight: "bold", textAlign: "center", color: "#6a1b9a" }}>
                            Delete LawClient
                        </Typography>

                        <Button
                            variant="outlined"
                            color="inherit"
                            onClick={() => navigate("/clients")}
                            sx={{ width: "200px", alignSelf: "center" }}
                        >
                            Back to LawClient Service
                        </Button>

                        {/* Delete by ID */}
                        <Card sx={{ p: 3, background: "#f3e5f5" }}>
                            <Typography variant="h6" sx={{ mb: 2 }}>Delete by ID</Typography>
                            <Stack direction={{ xs: "column", sm: "row" }} spacing={2}>
                                <TextField
                                    label="Client ID"
                                    value={idInput}
                                    onChange={(e) => setIdInput(e.target.value)}
                                    size="small"
                                />
                                <Button variant="contained" color="error" onClick={handleDeleteById}>Delete</Button>
                            </Stack>
                        </Card>

                        {/* Delete by Name */}
                        <Card sx={{ p: 3, background: "#ede7f6" }}>
                            <Typography variant="h6" sx={{ mb: 2 }}>Delete by Name</Typography>
                            <Stack direction={{ xs: "column", sm: "row" }} spacing={2}>
                                <TextField
                                    label="Client Name"
                                    value={nameInput}
                                    onChange={(e) => setNameInput(e.target.value)}
                                    size="small"
                                />
                                <Button variant="contained" color="error" onClick={handleDeleteByName}>Delete</Button>
                            </Stack>
                        </Card>

                        {/* Delete All */}
                        <Card sx={{ p: 3, background: "#ffcdd2" }}>
                            <Typography variant="h6" sx={{ mb: 2 }}>Delete All Clients</Typography>
                            <Button variant="contained" color="error" onClick={handleDeleteAll}>
                                Delete All
                            </Button>
                        </Card>

                        {loading && <CircularProgress sx={{ mt: 2, alignSelf: "center" }} />}
                        {error && <Alert severity="error" sx={{ mt: 2 }}>{error}</Alert>}
                        {success && <Alert severity="success" sx={{ mt: 2 }}>{success}</Alert>}

                        {deletedClient && (
                            <Card sx={{ mt: 2, p: 2, background: "#d1c4e9" }}>
                                <CardContent>
                                    <Typography variant="h6" sx={{ fontWeight: "bold" }}>Deleted Client:</Typography>
                                    <Divider sx={{ my: 1 }} />
                                    <Typography><strong>ID:</strong> {deletedClient.id}</Typography>
                                    <Typography><strong>Name:</strong> {deletedClient.name}</Typography>
                                </CardContent>
                            </Card>
                        )}
                    </Stack>
                </Paper>
            </Container>
        </Box>
    );
}