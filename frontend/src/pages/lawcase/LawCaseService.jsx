import React from "react";
import { Box, Container, Paper, Typography, Button, Stack } from "@mui/material";
import { useNavigate } from "react-router-dom";

export default function LawCaseService() {
    const navigate = useNavigate();

    return (
        <Box sx={{ minHeight: "100vh", bgcolor: "#f5f5f5", py: 5 }}>
            <Container maxWidth="sm">
                <Paper elevation={10} sx={{ p: 5, borderRadius: 3, textAlign: "center" }}>
                    <Typography variant="h4" sx={{ fontWeight: "bold", mb: 3 }}>
                        LawCase Service
                    </Typography>

                    {/* 🔙 PRZYCISK POWROTNY DO STRONY GŁÓWNEJ */}
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
                            onClick={() => navigate("/lawcases/add")}
                        >
                            Create LawCase
                        </Button>

                        <Button
                            variant="outlined"
                            color="secondary"
                            onClick={() => navigate("/lawcases/get")}
                        >
                            Get LawCase
                        </Button>

                        <Button
                            variant="contained"
                            color="success"
                            onClick={() => navigate("/lawcases/update")}
                        >
                            Update LawCase
                        </Button>

                        <Button
                            variant="contained"
                            color="error"
                            onClick={() => navigate("/lawcases/delete")}
                        >
                            Delete LawCase
                        </Button>
                    </Stack>
                </Paper>
            </Container>
        </Box>
    );
}
