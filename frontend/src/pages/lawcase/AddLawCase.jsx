import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import {
    Box,
    Container,
    Paper,
    Typography,
    TextField,
    Button,
    Alert,
    Stack
} from "@mui/material";
import axios from "axios";

export default function AddLawCase() {
    const [name, setName] = useState("");
    const [lawyerId, setLawyerId] = useState("");
    const [lawClientId, setLawClientId] = useState("");

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
            const response = await axios.post("/api/lawcase/create", {
                id: null,
                name: name.trim(),
                lawyerId: lawyerId.trim(),
                lawClientId: lawClientId.trim(),
                lawyer: null,
                lawClient: null
            });

            if (response.status === 201 || response.status === 200) {
                setSuccess("Law case created successfully!");
                setTimeout(() => navigate("/lawcases"), 1500);
            }
        } catch (err) {
            console.error(err);

            let errorMsg = "Something went wrong!";
            if (err.response) {
                if (typeof err.response.data === "string") {
                    errorMsg = err.response.data;
                } else if (err.response.data?.message) {
                    errorMsg = err.response.data.message;
                } else {
                    errorMsg = JSON.stringify(err.response.data, null, 2);
                }
            } else if (err.message) {
                errorMsg = err.message;
            }

            setError(errorMsg);
        }
    };

    return (
        <Box
            sx={{
                minHeight: "100vh",
                background: "linear-gradient(to right, #6366f1, #8b5cf6)",
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                p: 3
            }}
        >
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
                    <Typography
                        variant="h3"
                        gutterBottom
                        sx={{ fontWeight: "bold", color: "white" }}
                    >
                        Add New Law Case
                    </Typography>

                    <form onSubmit={handleSubmit}>
                        <Stack spacing={3} sx={{ mt: 4 }}>
                            <TextField
                                label="Case Name"
                                variant="filled"
                                value={name}
                                onChange={(e) => setName(e.target.value)}
                                fullWidth
                                InputProps={{
                                    sx: {
                                        backgroundColor: "rgba(255,255,255,0.2)",
                                        color: "white"
                                    }
                                }}
                                InputLabelProps={{ sx: { color: "white" } }}
                            />

                            <TextField
                                label="Lawyer ID"
                                variant="filled"
                                value={lawyerId}
                                onChange={(e) => setLawyerId(e.target.value)}
                                fullWidth
                                InputProps={{
                                    sx: {
                                        backgroundColor: "rgba(255,255,255,0.2)",
                                        color: "white"
                                    }
                                }}
                                InputLabelProps={{ sx: { color: "white" } }}
                            />

                            <TextField
                                label="Law Client ID"
                                variant="filled"
                                value={lawClientId}
                                onChange={(e) => setLawClientId(e.target.value)}
                                fullWidth
                                InputProps={{
                                    sx: {
                                        backgroundColor: "rgba(255,255,255,0.2)",
                                        color: "white"
                                    }
                                }}
                                InputLabelProps={{ sx: { color: "white" } }}
                            />

                            <Button
                                type="submit"
                                variant="contained"
                                color="secondary"
                                size="large"
                            >
                                Create Law Case
                            </Button>

                            <Button
                                variant="outlined"
                                color="inherit"
                                onClick={() => navigate("/lawcases")}
                            >
                                Back to LawCase Service
                            </Button>

                            {error && (
                                <Alert severity="error" sx={{ whiteSpace: "pre-line" }}>
                                    {error}
                                </Alert>
                            )}

                            {success && (
                                <Alert severity="success">
                                    {success}
                                </Alert>
                            )}
                        </Stack>
                    </form>
                </Paper>
            </Container>
        </Box>
    );
}
