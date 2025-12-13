import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Box, Container, Paper, Typography, TextField, Button, Alert, Stack } from "@mui/material";
import axios from "axios";

export default function AddLawyer() {
    const [name, setName] = useState("");
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");
        setSuccess("");

        if (name.trim().length < 4) {
            setError("Name must have at least 4 characters!");
            return;
        }

        try {
            // Relative URL dzięki proxy Vite
            const response = await axios.post("/api/lawyer/create", {
                id: null,
                name: name.trim(),
                lawCaseList: []
            });

            if (response.status === 201) {
                setSuccess("Lawyer created successfully!");
                setTimeout(() => navigate("/lawyers"), 1500);
            }
        } catch (err) {
            console.error(err);

            // Pobranie pełnego błędu z backendu
            let errorMsg = "Something went wrong!";
            if (err.response) {
                if (err.response.data) {
                    if (typeof err.response.data === "string") {
                        errorMsg = err.response.data;
                    } else if (err.response.data.message) {
                        errorMsg = err.response.data.message;
                    } else {
                        errorMsg = JSON.stringify(err.response.data, null, 2);
                    }
                } else {
                    errorMsg = `Error: ${err.response.status} ${err.response.statusText}`;
                }
            } else if (err.message) {
                errorMsg = err.message;
            }

            setError(errorMsg);
            setTimeout(() => navigate("/lawyers"), 4000);
        }
    };

    return (
        <Box sx={{
            minHeight: "100vh",
            bgcolor: "linear-gradient(to right, #4f46e5, #3b82f6)",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            p: 3
        }}>
            <Container maxWidth="sm">
                <Paper
                    elevation={10}
                    sx={{
                        p: 6,
                        textAlign: "center",
                        borderRadius: 4,
                        bgcolor: "rgba(255, 255, 255, 0.1)",
                        backdropFilter: "blur(10px)"
                    }}
                >
                    <Typography variant="h3" gutterBottom sx={{ fontWeight: "bold", color: "white" }}>
                        Add New Lawyer
                    </Typography>

                    <form onSubmit={handleSubmit}>
                        <Stack spacing={3} sx={{ mt: 4 }}>
                            <TextField
                                label="Name"
                                variant="filled"
                                value={name}
                                onChange={(e) => setName(e.target.value)}
                                fullWidth
                                InputProps={{ sx: { backgroundColor: "rgba(255,255,255,0.2)", color: "white" } }}
                                InputLabelProps={{ sx: { color: "white" } }}
                            />
                            <Button type="submit" variant="contained" color="secondary" size="large">
                                Create Lawyer
                            </Button>

                            {/* 🔙 PRZYCISK POWROTU */}
                            <Button
                                variant="outlined"
                                color="inherit"
                                onClick={() => navigate("/lawyers")}
                            >
                                Back to Lawyer Service
                            </Button>


                            {error && <Alert severity="error" sx={{ whiteSpace: "pre-line" }}>{error}</Alert>}
                            {success && <Alert severity="success">{success}</Alert>}
                        </Stack>
                    </form>
                </Paper>
            </Container>
        </Box>
    );
}

