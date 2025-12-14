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

export default function GetLawyer() {
    const navigate = useNavigate();

    const [lawyers, setLawyers] = useState([]);
    const [singleLawyer, setSingleLawyer] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const [lawyerIdInput, setLawyerIdInput] = useState("");
    const [lawyerNameInput, setLawyerNameInput] = useState("");
    const [attachLawCasesLawyerId, setAttachLawCasesLawyerId] = useState("");

    /* =========================
       FETCH ALL LAWYERS
    ========================= */
    const fetchAllLawyers = async () => {
        setLoading(true);
        setError("");
        setSingleLawyer(null);

        try {
            const res = await axios.get("/api/lawyer/get/allLawyers");
            setLawyers(res.data);
        } catch (err) {
            setError(err.response?.data?.message || err.message);
            setLawyers([]);
        } finally {
            setLoading(false);
        }
    };

    /* =========================
       FETCH BY ID
    ========================= */
    const fetchLawyerById = async () => {
        if (!lawyerIdInput.trim()) return;

        setLoading(true);
        setError("");
        setLawyers([]);
        setSingleLawyer(null);

        try {
            const res = await axios.get(
                `/api/lawyer/get/byId/${lawyerIdInput}`
            );
            setSingleLawyer(res.data);
        } catch (err) {
            setError(err.response?.data?.message || err.message);
        } finally {
            setLoading(false);
        }
    };

    /* =========================
       FETCH BY NAME
    ========================= */
    const fetchLawyerByName = async () => {
        if (!lawyerNameInput.trim()) return;

        setLoading(true);
        setError("");
        setLawyers([]);
        setSingleLawyer(null);

        try {
            const res = await axios.get("/api/lawyer/get/byName", {
                params: { lawyerName: lawyerNameInput }
            });
            setSingleLawyer(res.data);
        } catch (err) {
            setError(err.response?.data?.message || err.message);
        } finally {
            setLoading(false);
        }
    };

    /* =========================
       ATTACH LAW CASES (WEBCLIENT)
    ========================= */
    const fetchAndAttachLawCasesToLawyer = async () => {
        if (!attachLawCasesLawyerId.trim()) return;

        setLoading(true);
        setError("");
        setLawyers([]);
        setSingleLawyer(null);

        try {
            const res = await axios.get(
                `/api/lawyer/webclient/getLawCases/${attachLawCasesLawyerId}`
            );
            setSingleLawyer(res.data); // LawyerDTO z lawCaseList
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
                background: "linear-gradient(135deg, #e0f7fa, #b2ebf2)",
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
                            Lawyer Finder
                        </Typography>

                        <Button
                            variant="outlined"
                            onClick={() => navigate("/lawyers")}
                            sx={{ width: 220, alignSelf: "center" }}
                        >
                            Back to Lawyer Service
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
                                onClick={fetchAllLawyers}
                            >
                                All Lawyers
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
                                onClick={fetchLawyerById}
                            >
                                Find by ID
                            </Button>

                            <TextField
                                label="Lawyer Name"
                                size="small"
                                value={lawyerNameInput}
                                onChange={(e) =>
                                    setLawyerNameInput(e.target.value)
                                }
                            />
                            <Button
                                variant="outlined"
                                onClick={fetchLawyerByName}
                            >
                                Find by Name
                            </Button>

                            <TextField
                                label="Attach LawCases by Lawyer ID"
                                size="small"
                                value={attachLawCasesLawyerId}
                                onChange={(e) =>
                                    setAttachLawCasesLawyerId(e.target.value)
                                }
                            />
                            <Button
                                variant="outlined"
                                color="secondary"
                                onClick={fetchAndAttachLawCasesToLawyer}
                            >
                                Fetch & Attach LawCases
                            </Button>
                        </Stack>

                        {loading && (
                            <CircularProgress sx={{ alignSelf: "center" }} />
                        )}

                        {error && <Alert severity="error">{error}</Alert>}

                        {/* LIST */}
                        {!loading && lawyers.length > 0 && (
                            <Stack spacing={2}>
                                {lawyers.map((lawyer) => (
                                    <Card key={lawyer.id}>
                                        <CardContent>
                                            <Typography variant="h6">
                                                {lawyer.name}
                                            </Typography>
                                            <Typography>
                                                <strong>ID:</strong> {lawyer.id}
                                            </Typography>
                                            <Typography>
                                                <strong>Cases:</strong>{" "}
                                                {lawyer.lawCaseList?.length
                                                    ? lawyer.lawCaseList
                                                        .map((c) => c.id)
                                                        .join(", ")
                                                    : "—"}
                                            </Typography>
                                        </CardContent>
                                    </Card>
                                ))}
                            </Stack>
                        )}

                        {/* SINGLE */}
                        {!loading && singleLawyer && (
                            <Card>
                                <CardContent>
                                    <Typography variant="h5">
                                        {singleLawyer.name}
                                    </Typography>
                                    <Divider sx={{ my: 1 }} />
                                    <Typography>
                                        <strong>ID:</strong>{" "}
                                        {singleLawyer.id}
                                    </Typography>

                                    <Typography variant="subtitle2" sx={{ mt: 1 }}>
                                        📁 Law Cases
                                    </Typography>

                                    {singleLawyer.lawCaseList?.length ? (
                                        singleLawyer.lawCaseList.map(
                                            (lawCase) => (
                                                <Typography
                                                    key={lawCase.id}
                                                    variant="body2"
                                                >
                                                    • {lawCase.name} (ID:{" "}
                                                    {lawCase.id})
                                                </Typography>
                                            )
                                        )
                                    ) : (
                                        <Typography variant="body2">
                                            No cases assigned
                                        </Typography>
                                    )}
                                </CardContent>
                            </Card>
                        )}
                    </Stack>
                </Paper>
            </Container>
        </Box>
    );
}