import React, { useState } from "react";
import { useNavigate } from "react-router-dom"; // <-- NOWY IMPORT
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

export default function DeleteLawyer() {
    const [idInput, setIdInput] = useState("");
    const [nameInput, setNameInput] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");
    const [deletedLawyer, setDeletedLawyer] = useState(null);

    const navigate = useNavigate(); // <-- UŻYWAMY

    const resetAlerts = () => {
        setError("");
        setSuccess("");
        setDeletedLawyer(null);
    };

    const handleDeleteById = async () => {
        if (!idInput.trim()) {
            setError("ID is required!");
            return;
        }

        resetAlerts();
        setLoading(true);

        try {
            const res = await axios.delete(
                `/api/lawyer/delete/byId/${encodeURIComponent(idInput)}`
            );
            setDeletedLawyer(res.data);
            setSuccess("Lawyer deleted successfully!");
        } catch (err) {
            console.error(err);
            if (err.response) {
                if (err.response.status === 403) setError("Access denied (403).");
                else if (err.response.status === 404) setError("Lawyer not found (404).");
                else setError(err.response.data?.message || "Something went wrong!");
            } else {
                setError(err.message);
            }
        } finally {
            setLoading(false);
        }
    };

    const handleDeleteByName = async () => {
        if (!nameInput.trim()) {
            setError("Name is required!");
            return;
        }

        resetAlerts();
        setLoading(true);

        try {
            const res = await axios.delete(`/api/lawyer/delete/byName`, {
                params: { lawyerName: nameInput.trim() }
            });
            setDeletedLawyer(res.data);
            setSuccess("Lawyer deleted successfully!");
        } catch (err) {
            console.error(err);
            if (err.response) {
                if (err.response.status === 403) setError("Access denied (403).");
                else if (err.response.status === 404) setError("Lawyer not found (404).");
                else setError(err.response.data?.message || "Something went wrong!");
            } else {
                setError(err.message);
            }
        } finally {
            setLoading(false);
        }
    };

    const handleDeleteAll = async () => {
        resetAlerts();
        setLoading(true);

        try {
            const res = await axios.delete(`/api/lawyer/delete/allLawyers`);
            setSuccess(res.data || "All lawyers deleted!");
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
                            sx={{ fontWeight: "bold", textAlign: "center", color: "#b71c1c" }}
                        >
                            Delete Lawyer
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

                        {/* Delete by ID */}
                        <Card sx={{ p: 3, background: "#ffcdd2" }}>
                            <Typography variant="h6" sx={{ mb: 2 }}>Delete by ID</Typography>
                            <Stack direction={{ xs: "column", sm: "row" }} spacing={2}>
                                <TextField
                                    label="Lawyer ID"
                                    value={idInput}
                                    onChange={(e) => setIdInput(e.target.value)}
                                    size="small"
                                />
                                <Button variant="contained" color="error" onClick={handleDeleteById}>
                                    Delete
                                </Button>
                            </Stack>
                        </Card>

                        {/* Delete by Name */}
                        <Card sx={{ p: 3, background: "#ef9a9a" }}>
                            <Typography variant="h6" sx={{ mb: 2 }}>Delete by Name</Typography>
                            <Stack direction={{ xs: "column", sm: "row" }} spacing={2}>
                                <TextField
                                    label="Lawyer Name"
                                    value={nameInput}
                                    onChange={(e) => setNameInput(e.target.value)}
                                    size="small"
                                />
                                <Button variant="contained" color="error" onClick={handleDeleteByName}>
                                    Delete
                                </Button>
                            </Stack>
                        </Card>

                        {/* Delete all */}
                        <Card sx={{ p: 3, background: "#e57373" }}>
                            <Typography variant="h6" sx={{ mb: 2 }}>Delete ALL Lawyers</Typography>
                            <Button variant="contained" color="error" onClick={handleDeleteAll}>
                                Delete All
                            </Button>
                        </Card>

                        {loading && <CircularProgress sx={{ mt: 2, alignSelf: "center" }} />}

                        {error && <Alert severity="error" sx={{ mt: 2 }}>{error}</Alert>}
                        {success && <Alert severity="success" sx={{ mt: 2 }}>{success}</Alert>}

                        {deletedLawyer && (
                            <Card sx={{ mt: 2, p: 2, background: "#ffcdd2" }}>
                                <CardContent>
                                    <Typography variant="h6" sx={{ fontWeight: "bold" }}>
                                        Deleted Lawyer:
                                    </Typography>
                                    <Divider sx={{ my: 1 }} />
                                    <Typography><strong>ID:</strong> {deletedLawyer.id}</Typography>
                                    <Typography><strong>Name:</strong> {deletedLawyer.name}</Typography>
                                    <Typography>
                                        <strong>Cases:</strong>{" "}
                                        {deletedLawyer.lawCaseList?.length
                                            ? deletedLawyer.lawCaseList.map(c => c.id).join(", ")
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
