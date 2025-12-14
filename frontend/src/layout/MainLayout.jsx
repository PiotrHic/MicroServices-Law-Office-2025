import React from "react";
import { Outlet } from "react-router-dom";
import { Box } from "@mui/material";
import Navbar from "../components/Navbar";

export default function MainLayout() {
    return (
        <Box sx={{ minHeight: "100vh" }}>
            <Navbar />
            <Box sx={{ p: 2 }}>
                <Outlet />
            </Box>
        </Box>
    );
}