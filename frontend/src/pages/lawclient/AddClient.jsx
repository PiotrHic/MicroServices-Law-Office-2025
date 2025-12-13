import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Box, Container, Paper, Typography, TextField, Button, Stack, Alert, CircularProgress } from "@mui/material";
import axios from "axios";

export default function AddClient() {
    const [name, setName] = useState("");
    const [loading, setLoading] = useState(false);
    const [success, setSuccess] = useState("");
    const [error, setError] = useState("");

    const navigate = useNavigate();

    const resetAlerts = () => {
        setError("");
        setSuccess("");
    };

    const handleAddClient = async () => {
        if (!name.trim()) {
            setError("Name is required!");
            return;
        }

        resetAlerts();
        setLoading(true);

        try {
            const payload = {
                name: name.trim(),
                lawCaseList: [] // domyślnie pusty array, możesz później rozszerzyć
            };

            const res = await axios.post("/api/lawclient/create", payload, {
                headers: { "Content-Type": "application/json" }
            });

            setSuccess(`Client "${res.data.name}" created successfully!`);
            setName("");
        } catch (err) {
            console.error(err);
            if (err.response) {
                setError(err.response.data?.message || "Something went wrong!");
            } else {
                setError(err.message);
            }
        } finally {
            setLoading(false);
        }
    };

    return (
        <Box sx={{ minHeight: "100vh", bgcolor: "#f3e5f5", py: 5 }}>
            <Container maxWidth="sm">
                <Paper elevation={10} sx={{ p: 5, borderRadius: 3 }}>
                    <Typography variant="h4" sx={{ fontWeight: "bold", mb: 3, textAlign: "center" }}>
                        Add LawClient
                    </Typography>

                    {/* 🔙 POWRÓT */}
                    <Button
                        variant="outlined"
                        color="inherit"
                        onClick={() => navigate("/clients")}
                        sx={{ mb: 3 }}
                    >
                        Back to LawClient Service
                    </Button>

                    <Stack spacing={2}>
                        <TextField
                            label="Name"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            size="small"
                        />

                        <Button
                            variant="contained"
                            color="primary"
                            onClick={handleAddClient}
                            disabled={loading}
                        >
                            {loading ? <CircularProgress size={24} /> : "Add Client"}
                        </Button>

                        {success && <Alert severity="success">{success}</Alert>}
                        {error && <Alert severity="error">{error}</Alert>}
                    </Stack>
                </Paper>
            </Container>
        </Box>
    );
}
