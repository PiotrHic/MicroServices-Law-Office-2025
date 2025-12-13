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

export default function UpdateLawCase() {
    const [idInput, setIdInput] = useState("");
    const [nameSearchInput, setNameSearchInput] = useState("");
    const [updateName, setUpdateName] = useState("");
    const [updateLawyerId, setUpdateLawyerId] = useState("");
    const [updateLawClientId, setUpdateLawClientId] = useState("");

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");
    const [updatedLawCase, setUpdatedLawCase] = useState(null);

    const navigate = useNavigate();

    const resetAlerts = () => {
        setError("");
        setSuccess("");
        setUpdatedLawCase(null);
    };

    const buildPayload = () => ({
        id: idInput.trim() || "",
        name: updateName.trim(),
        lawyerId: updateLawyerId.trim(),
        lawClientId: updateLawClientId.trim(),
        lawyer: null,
        lawClient: null
    });

    // ✅ UPDATE BY ID
    const handleUpdateById = async () => {
        if (!idInput.trim() || !updateName.trim()) {
            setError("ID and New Case Name are required!");
            return;
        }

        resetAlerts();
        setLoading(true);

        try {
            const res = await axios.put(
                `/api/lawcase/update/byId/${encodeURIComponent(idInput)}`,
                buildPayload(),
                { headers: { "Content-Type": "application/json" } }
            );

            setUpdatedLawCase(res.data);
            setSuccess("Law case updated successfully by ID!");
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

    // ✅ UPDATE BY NAME
    const handleUpdateByName = async () => {
        if (!nameSearchInput.trim() || !updateName.trim()) {
            setError("Current Case Name and New Name are required!");
            return;
        }

        resetAlerts();
        setLoading(true);

        try {
            const payload = {
                name: updateName.trim(),
                lawyerId: updateLawyerId.trim(),
                lawClientId: updateLawClientId.trim(),
                lawyer: null,
                lawClient: null
            };

            const res = await axios.put(
                "/api/lawcase/update/byName",
                payload,
                {
                    params: { lawCaseName: nameSearchInput.trim() },
                    headers: { "Content-Type": "application/json" }
                }
            );

            setUpdatedLawCase(res.data);
            setSuccess("Law case updated successfully by Name!");
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

    return (
        <Box sx={{ minHeight: "100vh", bgcolor: "#ede7f6", py: 5 }}>
            <Container maxWidth="md">
                <Paper elevation={12} sx={{ p: 5, borderRadius: 3 }}>
                    <Stack spacing={3}>
                        <Typography
                            variant="h4"
                            sx={{
                                fontWeight: "bold",
                                textAlign: "center",
                                color: "#512da8"
                            }}
                        >
                            Update Law Case
                        </Typography>

                        <Button
                            variant="outlined"
                            color="inherit"
                            onClick={() => navigate("/lawcases")}
                            sx={{ width: "240px", alignSelf: "center" }}
                        >
                            Back to LawCase Service
                        </Button>

                        {/* UPDATE BY ID */}
                        <Card sx={{ p: 3, background: "#ede7f6" }}>
                            <Typography variant="h6" sx={{ mb: 2 }}>
                                Update by ID
                            </Typography>

                            <Stack direction={{ xs: "column", sm: "row" }} spacing={2}>
                                <TextField
                                    label="Case ID"
                                    value={idInput}
                                    onChange={(e) => setIdInput(e.target.value)}
                                    size="small"
                                />
                                <TextField
                                    label="New Case Name"
                                    value={updateName}
                                    onChange={(e) => setUpdateName(e.target.value)}
                                    size="small"
                                />
                                <TextField
                                    label="New Lawyer ID"
                                    value={updateLawyerId}
                                    onChange={(e) => setUpdateLawyerId(e.target.value)}
                                    size="small"
                                />
                                <TextField
                                    label="New Client ID"
                                    value={updateLawClientId}
                                    onChange={(e) => setUpdateLawClientId(e.target.value)}
                                    size="small"
                                />
                                <Button
                                    variant="contained"
                                    color="primary"
                                    onClick={handleUpdateById}
                                >
                                    Update
                                </Button>
                            </Stack>
                        </Card>

                        {/* UPDATE BY NAME */}
                        <Card sx={{ p: 3, background: "#e8eaf6" }}>
                            <Typography variant="h6" sx={{ mb: 2 }}>
                                Update by Name
                            </Typography>

                            <Stack direction={{ xs: "column", sm: "row" }} spacing={2}>
                                <TextField
                                    label="Current Case Name"
                                    value={nameSearchInput}
                                    onChange={(e) => setNameSearchInput(e.target.value)}
                                    size="small"
                                />
                                <TextField
                                    label="New Case Name"
                                    value={updateName}
                                    onChange={(e) => setUpdateName(e.target.value)}
                                    size="small"
                                />
                                <TextField
                                    label="New Lawyer ID"
                                    value={updateLawyerId}
                                    onChange={(e) => setUpdateLawyerId(e.target.value)}
                                    size="small"
                                />
                                <TextField
                                    label="New Client ID"
                                    value={updateLawClientId}
                                    onChange={(e) => setUpdateLawClientId(e.target.value)}
                                    size="small"
                                />
                                <Button
                                    variant="contained"
                                    color="secondary"
                                    onClick={handleUpdateByName}
                                >
                                    Update
                                </Button>
                            </Stack>
                        </Card>

                        {loading && (
                            <CircularProgress sx={{ mt: 2, alignSelf: "center" }} />
                        )}
                        {error && <Alert severity="error">{error}</Alert>}
                        {success && <Alert severity="success">{success}</Alert>}

                        {updatedLawCase && (
                            <Card sx={{ mt: 2, p: 2, background: "#c5cae9" }}>
                                <CardContent>
                                    <Typography variant="h6" sx={{ fontWeight: "bold" }}>
                                        Updated Law Case:
                                    </Typography>
                                    <Divider sx={{ my: 1 }} />
                                    <Typography><strong>ID:</strong> {updatedLawCase.id}</Typography>
                                    <Typography><strong>Name:</strong> {updatedLawCase.name}</Typography>
                                    <Typography><strong>Lawyer ID:</strong> {updatedLawCase.lawyerId}</Typography>
                                    <Typography><strong>Client ID:</strong> {updatedLawCase.lawClientId}</Typography>
                                </CardContent>
                            </Card>
                        )}
                    </Stack>
                </Paper>
            </Container>
        </Box>
    );
}
