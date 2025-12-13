import React from "react";
import { Box, Container, Paper, Typography, Button, Stack } from "@mui/material";
import { useNavigate } from "react-router-dom";

export default function LawClientService() {
    const navigate = useNavigate();

    return (
        <Box sx={{ minHeight: "100vh", bgcolor: "#f5f5f5", py: 5 }}>
            <Container maxWidth="sm">
                <Paper elevation={10} sx={{ p: 5, borderRadius: 3, textAlign: "center" }}>
                    <Typography variant="h4" sx={{ fontWeight: "bold", mb: 3 }}>
                        LawClient Service
                    </Typography>

                    <Button
                        variant="outlined"
                        color="inherit"
                        onClick={() => navigate("/")}
                        sx={{ mb: 3 }}
                    >
                        Back to Home
                    </Button>

                    <Stack spacing={2}>
                        <Button
                            variant="contained"
                            color="primary"
                            onClick={() => navigate("/clients/add")}
                        >
                            Add Client
                        </Button>

                        <Button
                            variant="outlined"
                            color="secondary"
                            onClick={() => navigate("/clients/get")}
                        >
                            Get Client
                        </Button>

                        <Button
                            variant="contained"
                            color="success"
                            onClick={() => navigate("/clients/update")}
                        >
                            Update Client
                        </Button>

                        <Button
                            variant="contained"
                            color="error"
                            onClick={() => navigate("/clients/delete")}
                        >
                            Delete Client
                        </Button>
                    </Stack>
                </Paper>
            </Container>
        </Box>
    );
}