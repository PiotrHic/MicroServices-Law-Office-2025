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
    const navigate = useNavigate();

    const [lawCases, setLawCases] = useState([]);
    const [singleLawCase, setSingleLawCase] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const [lawCaseIdInput, setLawCaseIdInput] = useState("");
    const [lawCaseNameInput, setLawCaseNameInput] = useState("");
    const [lawyerIdInput, setLawyerIdInput] = useState("");
    const [lawClientIdInput, setLawClientIdInput] = useState("");

    /* =========================
       FETCH ALL LAWCASES
    ========================= */
    const fetchAllLawCases = async () => {
        setLoading(true);
        setError("");
        setSingleLawCase(null);

        try {
            const res = await axios.get("/api/lawcase/get/allLawCases");
            setLawCases(res.data);
        } catch (err) {
            setError(err.response?.data?.message || err.message);
            setLawCases([]);
        } finally {
            setLoading(false);
        }
    };

    /* =========================
       FETCH BY LAWCASE ID
    ========================= */
    const fetchLawCaseById = async () => {
        if (!lawCaseIdInput.trim()) return;

        setLoading(true);
        setError("");
        setLawCases([]);
        setSingleLawCase(null);

        try {
            const res = await axios.get(
                `/api/lawcase/get/byId/${lawCaseIdInput}`
            );
            setSingleLawCase(res.data);
        } catch (err) {
            setError(err.response?.data?.message || err.message);
        } finally {
            setLoading(false);
        }
    };

    /* =========================
       FETCH BY LAWCASE NAME
    ========================= */
    const fetchLawCaseByName = async () => {
        if (!lawCaseNameInput.trim()) return;

        setLoading(true);
        setError("");
        setLawCases([]);
        setSingleLawCase(null);

        try {
            const res = await axios.get("/api/lawcase/get/byName", {
                params: { lawCaseName: lawCaseNameInput }
            });
            setSingleLawCase(res.data);
        } catch (err) {
            setError(err.response?.data?.message || err.message);
        } finally {
            setLoading(false);
        }
    };

    /* =========================
       ATTACH LAWYER (WEBCLIENT)
    ========================= */
    const fetchLawyerToLawCasesByLawyerId = async () => {
        if (!lawyerIdInput.trim()) return;

        setLoading(true);
        setError("");
        setLawCases([]);
        setSingleLawCase(null);

        try {
            const res = await axios.get(
                `/api/lawcase/webclient/getLawyer/${lawyerIdInput}`
            );
            setLawCases(res.data); // List<LawCaseDTO>
        } catch (err) {
            setError(err.response?.data?.message || err.message);
        } finally {
            setLoading(false);
        }
    };

    /* =========================
       ATTACH LAWCLIENT (WEBCLIENT)
    ========================= */
    const fetchLawClientToLawCasesByLawClientId = async () => {
        if (!lawClientIdInput.trim()) return;

        setLoading(true);
        setError("");
        setLawCases([]);
        setSingleLawCase(null);

        try {
            const res = await axios.get(
                `/api/lawcase/webclient/getLawClient/${lawClientIdInput}`
            );
            setLawCases(res.data); // List<LawCaseDTO>
        } catch (err) {
            setError(err.response?.data?.message || err.message);
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
                <Paper elevation={12} sx={{ p: 5, borderRadius: 3 }}>
                    <Stack spacing={3}>
                        <Typography
                            variant="h3"
                            sx={{ fontWeight: "bold", textAlign: "center" }}
                        >
                            Law Case Finder
                        </Typography>

                        <Button
                            variant="outlined"
                            onClick={() => navigate("/lawcases")}
                            sx={{ width: 240, alignSelf: "center" }}
                        >
                            Back to LawCase Service
                        </Button>

                        {/* CONTROLS */}
                        <Stack
                            direction={{ xs: "column", sm: "row" }}
                            spacing={2}
                            justifyContent="center"
                            flexWrap="wrap"
                        >
                            <Button
                                variant="contained"
                                onClick={fetchAllLawCases}
                            >
                                All LawCases
                            </Button>

                            <TextField
                                label="LawCase ID"
                                size="small"
                                value={lawCaseIdInput}
                                onChange={(e) =>
                                    setLawCaseIdInput(e.target.value)
                                }
                            />
                            <Button
                                variant="outlined"
                                onClick={fetchLawCaseById}
                            >
                                Find by ID
                            </Button>

                            <TextField
                                label="LawCase Name"
                                size="small"
                                value={lawCaseNameInput}
                                onChange={(e) =>
                                    setLawCaseNameInput(e.target.value)
                                }
                            />
                            <Button
                                variant="outlined"
                                onClick={fetchLawCaseByName}
                            >
                                Find by Name
                            </Button>

                            <TextField
                                label="Lawyer ID"
                                size="small"
                                value={lawyerIdInput}
                                onChange={(e) =>
                                    setLawyerIdInput(e.target.value)
                                }
                            />
                            <Button
                                variant="outlined"
                                color="secondary"
                                onClick={fetchLawyerToLawCasesByLawyerId}
                            >
                                Fetch & Attach Lawyer
                            </Button>

                            <TextField
                                label="LawClient ID"
                                size="small"
                                value={lawClientIdInput}
                                onChange={(e) =>
                                    setLawClientIdInput(e.target.value)
                                }
                            />
                            <Button
                                variant="outlined"
                                color="secondary"
                                onClick={fetchLawClientToLawCasesByLawClientId}
                            >
                                Fetch & Attach LawClient
                            </Button>
                        </Stack>

                        {loading && (
                            <CircularProgress sx={{ alignSelf: "center" }} />
                        )}

                        {error && <Alert severity="error">{error}</Alert>}

                        {/* LIST */}
                        {!loading && lawCases.length > 0 && (
                            <Stack spacing={2}>
                                {lawCases.map((lawCase) => (
                                    <Card key={lawCase.id}>
                                        <CardContent>
                                            <Typography variant="h6">
                                                {lawCase.name}
                                            </Typography>

                                            <Typography>
                                                <strong>ID:</strong>{" "}
                                                {lawCase.id}
                                            </Typography>

                                            <Typography>
                                                <strong>Lawyer ID:</strong>{" "}
                                                {lawCase.lawyerId || "—"}
                                            </Typography>

                                            <Typography>
                                                <strong>LawClient ID:</strong>{" "}
                                                {lawCase.lawClientId || "—"}
                                            </Typography>

                                            {lawCase.lawyer && (
                                                <>
                                                    <Divider sx={{ my: 1 }} />
                                                    <Typography variant="subtitle2">
                                                        👨‍⚖️ Lawyer
                                                    </Typography>
                                                    <Typography variant="body2">
                                                        {lawCase.lawyer.name}
                                                    </Typography>
                                                </>
                                            )}

                                            {lawCase.lawClient && (
                                                <>
                                                    <Divider sx={{ my: 1 }} />
                                                    <Typography variant="subtitle2">
                                                        🧑‍💼 Law Client
                                                    </Typography>
                                                    <Typography variant="body2">
                                                        {lawCase.lawClient.name}
                                                    </Typography>
                                                </>
                                            )}
                                        </CardContent>
                                    </Card>
                                ))}
                            </Stack>
                        )}

                        {/* SINGLE */}
                        {!loading && singleLawCase && (
                            <Card>
                                <CardContent>
                                    <Typography variant="h5">
                                        {singleLawCase.name}
                                    </Typography>
                                    <Divider sx={{ my: 1 }} />
                                    <Typography>
                                        <strong>ID:</strong>{" "}
                                        {singleLawCase.id}
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