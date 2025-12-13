import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import {
    Box,
    Container,
    Paper,
    Typography,
    TextField,
    Button,
    Stack,
    Alert,
    CircularProgress,
    List,
    ListItem,
    ListItemText,
    Divider
} from "@mui/material";
import axios from "axios";

export default function GetClient() {
    const [id, setId] = useState("");
    const [name, setName] = useState("");
    const [loading, setLoading] = useState(false);
    const [clients, setClients] = useState([]);
    const [error, setError] = useState("");

    const navigate = useNavigate();

    const resetAlerts = () => {
        setError("");
        setClients([]);
    };

    const fetchClientById = async () => {
        if (!id.trim()) {
            setError("ID is required!");
            return;
        }
        resetAlerts();
        setLoading(true);
        try {
            const res = await axios.get(`/api/lawclient/get/byId/${id}`);
            setClients([res.data]); // wrap in array for uniform display
        } catch (err) {
            console.error(err);
            setError(err.response?.data?.message || err.message);
        } finally {
            setLoading(false);
        }
    };

    const fetchClientByName = async () => {
        if (!name.trim()) {
            setError("Name is required!");
            return;
        }
        resetAlerts();
        setLoading(true);
        try {
            const res = await axios.get(`/api/lawclient/get/byName?lawClientName=${encodeURIComponent(name)}`);
            setClients([res.data]); // wrap in array
        } catch (err) {
            console.error(err);
            setError(err.response?.data?.message || err.message);
        } finally {
            setLoading(false);
        }
    };

    const fetchAllClients = async () => {
        resetAlerts();
        setLoading(true);
        try {
            const res = await axios.get("/api/lawclient/get/allLawClients");
            setClients(res.data);
        } catch (err) {
            console.error(err);
            setError(err.response?.data?.message || err.message);
        } finally {
            setLoading(false);
        }
    };

    return (
        <Box sx={{ minHeight: "100vh", bgcolor: "#f3e5f5", py: 5 }}>
            <Container maxWidth="sm">
                <Paper elevation={10} sx={{ p: 5, borderRadius: 3 }}>
                    <Typography variant="h4" sx={{ fontWeight: "bold", mb: 3, textAlign: "center" }}>
                        Get LawClient
                    </Typography>

                    <Button
                        variant="outlined"
                        color="inherit"
                        onClick={() => navigate("/clients")}
                        sx={{ mb: 3 }}
                    >
                        Back to LawClient Service
                    </Button>

                    <Stack spacing={2}>
                        {/* Get by ID */}
                        <TextField
                            label="Client ID"
                            value={id}
                            onChange={(e) => setId(e.target.value)}
                            size="small"
                        />
                        <Button
                            variant="contained"
                            color="primary"
                            onClick={fetchClientById}
                            disabled={loading}
                        >
                            {loading ? <CircularProgress size={24} /> : "Get by ID"}
                        </Button>

                        {/* Get by Name */}
                        <TextField
                            label="Client Name"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            size="small"
                        />
                        <Button
                            variant="contained"
                            color="secondary"
                            onClick={fetchClientByName}
                            disabled={loading}
                        >
                            {loading ? <CircularProgress size={24} /> : "Get by Name"}
                        </Button>

                        {/* Get All Clients */}
                        <Button
                            variant="contained"
                            color="success"
                            onClick={fetchAllClients}
                            disabled={loading}
                        >
                            {loading ? <CircularProgress size={24} /> : "Get All Clients"}
                        </Button>

                        {error && <Alert severity="error">{error}</Alert>}

                        {clients.length > 0 && (
                            <List>
                                {clients.map((client, index) => (
                                    <React.Fragment key={client.id || index}>
                                        <ListItem>
                                            <ListItemText
                                                primary={`ID: ${client.id} | Name: ${client.name}`}
                                                secondary={`Law Cases: ${client.lawCaseList?.length || 0}`}
                                            />
                                        </ListItem>
                                        {index < clients.length - 1 && <Divider />}
                                    </React.Fragment>
                                ))}
                            </List>
                        )}
                    </Stack>
                </Paper>
            </Container>
        </Box>
    );
}