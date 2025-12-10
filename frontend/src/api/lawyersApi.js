import api from "./axiosClient";

export const createLawyer = (data) => api.post("/api/lawyer/create", data);

export const getLawyerById = (id) => api.get(`/api/lawyer/get/byId/${id}`);
export const getLawyerByName = (name) => api.get(`/api/lawyer/get/byName?lawyerName=${name}`);
export const getAllLawyers = () => api.get("/api/lawyer/get/allLawyers");

export const updateLawyerById = (id, data) => api.put(`/api/lawyer/update/byId/${id}`, data);
export const updateLawyerByName = (name, data) =>
    api.put(`/api/lawyer/update/byName?lawyerName=${name}`, data);

export const deleteLawyerById = (id) => api.delete(`/api/lawyer/delete/byId/${id}`);
export const deleteLawyerByName = (name) => api.delete(`/api/lawyer/delete/byName?lawyerName=${name}`);
export const deleteAllLawyers = () => api.delete(`/api/lawyer/delete/allLawyers`);
