import api from "./axiosClient";

export const createLawCase = (data) => api.post("/api/lawcase/create", data);

export const getLawCases = () => api.get("/api/lawcase/get/allLawCases");
export const getLawCaseById = (id) => api.get(`/api/lawcase/get/byId/${id}`);
export const getLawCaseByName = (name) => api.get(`/api/lawcase/get/byName?lawcaseName=${name}`);

export const updateLawCaseById = (id, data) => api.put(`/api/lawcase/update/byId/${id}`, data);
export const updateLawCaseByName = (name, data) =>
    api.put(`/api/lawcase/update/byName?lawcaseName=${name}`, data);

export const deleteLawCaseById = (id) => api.delete(`/api/lawcase/delete/byId/${id}`);
export const deleteLawCaseByName = (name) =>
    api.delete(`/api/lawcase/delete/byName?lawcaseName==${name}`);
export const deleteAllLawCases = () => api.delete(`/api/lawcase/delete/allLawCases`);

