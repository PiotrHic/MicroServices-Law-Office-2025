import api from "./axiosConfig";

export const getAllLawyers = async () => {
    const res = await api.get("/api/lawyer/get/allLawyers");
    return res.data;
};