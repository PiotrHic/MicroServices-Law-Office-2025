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

export default function UpdateClient() {
    const [idInput, setIdInput] = useState("");
    const [nameSearchInput, setNameSearchInput] = useState("");
    const [updateName, setUpdateName] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");
    const [updatedClient, setUpdatedClient] = useState(null);

    const navigate = useNavigate();

    const resetAlerts = () => {
        setError("");
        setSuccess("");
        setUpdatedClient(null);
    };

    const buildPayload = () => ({
        id: idInput.trim() || "",
        name: updateName.trim(),
        lawCaseList: []
    });

    // ✅ Update by ID
    const handleUpdateById = async () => {
        if (!idInput.trim() || !updateName.trim()) {
            setError("ID and New Name are required!");
            return;
        }

        resetAlerts();
        setLoading(true);

        try {
            const res = await axios.put(
                `/api/lawclient/update/byId/${encodeURIComponent(idInput)}`,
                buildPayload(),
                { headers: { "Content-Type": "application/json" } }
            );
            setUpdatedClient(res.data);
            setSuccess("Client updated successfully by ID!");
        } catch (err) {
            console.error(err);
            if (err.response) {
                if (err.response.status === 403) setError("Access denied (403).");
                else if (err.response.status === 404) setError("Client not found (404).");
                else setError(err.response.data?.message || "Something went wrong!");
            } else {
                setError(err.message);
            }
        } finally {
            setLoading(false);
        }
    };

    // ✅ Update by Name
    const handleUpdateByName = async () => {
        if (!nameSearchInput.trim() || !updateName.trim()) {
            setError("Current Name and New Name are required!");
            return;
        }

        resetAlerts();
        setLoading(true);

        try {
            const payload = { name: updateName.trim(), lawCaseList: [] };

            const res = await axios.put(
                "/api/lawclient/update/byName",
                payload,
                {
                    params: { lawClientName: nameSearchInput.trim() }, // query param
                    headers: { "Content-Type": "application/json" }
                }
            );

            setUpdatedClient(res.data);
            setSuccess("Client updated successfully by Name!");
        } catch (err) {
            console.error(err);
            if (err.response) {
                if (err.response.status === 403) setError("Access denied (403).");
                else if (err.response.status === 404) setError("Client not found (404).");
                else setError(err.response.data?.message || "Something went wrong!");
            } else {
                setError(err.message);
            }
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
                            Update LawClient
                        </Typography>

                        <Button
                            variant="outlined"
                            color="inherit"
                            onClick={() => navigate("/clients")}
                            sx={{ width: "200px", alignSelf: "center" }}
                        >
                            Back to LawClient Service
                        </Button>

                        {/* Update by ID */}
                        <Card sx={{ p: 3, background: "#f3e5f5" }}>
                            <Typography variant="h6" sx={{ mb: 2 }}>Update by ID</Typography>
                            <Stack direction={{ xs: "column", sm: "row" }} spacing={2}>
                                <TextField
                                    label="Client ID"
                                    value={idInput}
                                    onChange={(e) => setIdInput(e.target.value)}
                                    size="small"
                                />
                                <TextField
                                    label="New Name"
                                    value={updateName}
                                    onChange={(e) => setUpdateName(e.target.value)}
                                    size="small"
                                />
                                <Button variant="contained" color="primary" onClick={handleUpdateById}>Update</Button>
                            </Stack>
                        </Card>

                        {/* Update by Name */}
                        <Card sx={{ p: 3, background: "#ede7f6" }}>
                            <Typography variant="h6" sx={{ mb: 2 }}>Update by Name</Typography>
                            <Stack direction={{ xs: "column", sm: "row" }} spacing={2}>
                                <TextField
                                    label="Current Name"
                                    value={nameSearchInput}
                                    onChange={(e) => setNameSearchInput(e.target.value)}
                                    size="small"
                                />
                                <TextField
                                    label="New Name"
                                    value={updateName}
                                    onChange={(e) => setUpdateName(e.target.value)}
                                    size="small"
                                />
                                <Button variant="contained" color="secondary" onClick={handleUpdateByName}>Update</Button>
                            </Stack>
                        </Card>

                        {loading && <CircularProgress sx={{ mt: 2, alignSelf: "center" }} />}
                        {error && <Alert severity="error" sx={{ mt: 2 }}>{error}</Alert>}
                        {success && <Alert severity="success" sx={{ mt: 2 }}>{success}</Alert>}

                        {updatedClient && (
                            <Card sx={{ mt: 2, p: 2, background: "#d1c4e9" }}>
                                <CardContent>
                                    <Typography variant="h6" sx={{ fontWeight: "bold" }}>Updated Client:</Typography>
                                    <Divider sx={{ my: 1 }} />
                                    <Typography><strong>ID:</strong> {updatedClient.id}</Typography>
                                    <Typography><strong>Name:</strong> {updatedClient.name}</Typography>
                                    <Typography>
                                        <strong>Cases:</strong>
                                        {updatedClient.lawCaseList?.length
                                            ? updatedClient.lawCaseList.map(c => c.id).join(", ")
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