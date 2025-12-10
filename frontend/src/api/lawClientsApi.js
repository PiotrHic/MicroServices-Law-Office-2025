import api from "./axiosClient";

export const createLawClient = (data) => api.post("/api/lawclient/create", data);

export const getLawClientById = (id) => api.get(`/api/lawclient/get/byId/${id}`);
export const getLawClientByName = (name) => api.get(`/api/lawclient/get/byName?lawclientName=${name}`);
export const getAllLawClients = () => api.get("/api/lawclient/get/allLawClients");

export const updateLawClientById = (id, data) => api.put(`/api/lawclient/update/byId/${id}`, data);
export const updateLawClientByName = (name, data) =>
    api.put(`/api/lawclient/update/byName?lawclientName=${name}`, data);

export const deleteLawClientById = (id) => api.delete(`/api/lawclient/delete/byId/${id}`);
export const deleteLawClientByName = (name) => api.delete(`/api/lawclient/delete/byName?lawclientName=${name}`);
export const deleteAllLawClients = () => api.delete(`/api/lawclient/delete/allLawClients`);