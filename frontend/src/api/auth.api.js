import axiosInstance from "./axiosConfig";

export const login = (username, password) => {
  const formData = new URLSearchParams();
  formData.append("username", username);
  formData.append("password", password);

  return axiosInstance.post("/auth/login", formData, {
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
  });
};

export const me = () =>
  axiosInstance.get("/auth/me");

export const logout = () =>
  axiosInstance.post("/auth/logout");
