import React from "react";
import { Link, useLocation } from "react-router-dom";
import {
    AppBar,
    Toolbar,
    Typography,
    Button,
    Stack
} from "@mui/material";
import GavelIcon from "@mui/icons-material/Gavel";

export default function Navbar() {
    const location = useLocation();

    const isActive = (path) =>
        location.pathname.startsWith(path);

    return (
        <AppBar
            position="static"
            elevation={0}
            sx={{
                background:
                    "linear-gradient(135deg, #1e3c72, #2a5298)"
            }}
        >
            <Toolbar sx={{ justifyContent: "space-between" }}>
                {/* LOGO */}
                <Stack
                    direction="row"
                    spacing={1}
                    alignItems="center"
                >
                    <GavelIcon />
                    <Typography
                        variant="h6"
                        sx={{ fontWeight: "bold" }}
                    >
                        Law Office
                    </Typography>
                </Stack>

                {/* LINKS */}
                <Stack direction="row" spacing={2}>
                    <Button
                        component={Link}
                        to="/"
                        color="inherit"
                        sx={{
                            borderBottom: isActive("/")
                                ? "2px solid white"
                                : "none"
                        }}
                    >
                        Home
                    </Button>

                    <Button
                        component={Link}
                        to="/lawyers"
                        color="inherit"
                        sx={{
                            borderBottom: isActive("/lawyers")
                                ? "2px solid white"
                                : "none"
                        }}
                    >
                        Lawyers
                    </Button>

                    <Button
                        component={Link}
                        to="/clients"
                        color="inherit"
                        sx={{
                            borderBottom: isActive("/clients")
                                ? "2px solid white"
                                : "none"
                        }}
                    >
                        Clients
                    </Button>

                    <Button
                        component={Link}
                        to="/lawcases"
                        color="inherit"
                        sx={{
                            borderBottom: isActive("/lawcases")
                                ? "2px solid white"
                                : "none"
                        }}
                    >
                        LawCases
                    </Button>
                </Stack>
            </Toolbar>
        </AppBar>
    );
}