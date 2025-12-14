import React from "react";
import { Typography, Button, Stack, Box } from "@mui/material";
import { Link } from "react-router-dom";

export default function Home() {
    return (
        <Box
            sx={{
                minHeight: "calc(100vh - 64px)",
                background: "linear-gradient(135deg, #667eea, #764ba2)",
                display: "flex",
                flexDirection: "column",
                justifyContent: "center",
                alignItems: "center",
                color: "white",
                textAlign: "center",
                px: 2
            }}
        >
            <Typography
                variant="h2"
                sx={{ fontWeight: "bold", mb: 2 }}
            >
                ⚖️ Law Office Management
            </Typography>

            <Typography
                variant="h5"
                sx={{ mb: 4, maxWidth: 600 }}
            >
                Modern microservices-based system for managing
                lawyers, clients and law cases
            </Typography>

            <Stack
                direction={{ xs: "column", sm: "row" }}
                spacing={2}
            >
                <Button
                    component={Link}
                    to="/lawyers"
                    variant="contained"
                    color="secondary"
                    size="large"
                >
                    Lawyers
                </Button>

                <Button
                    component={Link}
                    to="/clients"
                    variant="contained"
                    color="secondary"
                    size="large"
                >
                    Clients
                </Button>

                <Button
                    component={Link}
                    to="/lawcases"
                    variant="contained"
                    color="secondary"
                    size="large"
                >
                    Law Cases
                </Button>
            </Stack>
        </Box>
    );
}