import axios from "axios";

const API_URL = "http://localhost:8080";

//registrering verifering
const register = (email, password) => {
    return axios
        .post(`${API_URL}/auth/register`, {
            email: email,
            password: password,
        })
        .then((response) => {
            console.log("User registration successful:", response.data);
            return response.data;
        })
        .catch((error) => {
            console.error("Error during registration:", error);
            throw new Error(
                error.response ? error.response.data : "Registration failed"
            );
        });
};

//inloggning verifiering
const login = (email, password) => {
    return axios
        .post(`${API_URL}/auth/login`, {
            email: email,
            password: password,
        })
        .then((response) => {
            if (response.data.token) {
                localStorage.setItem("user", JSON.stringify(response.data));
            } else {
                throw new Error("Invalid credentials");
            }
            return response.data;
        })
        .catch((error) => {
            throw new Error(
                error.response ? error.response.data.error : "Login failed"
            );
        });
};

//utloggning
const logout = () => {
    localStorage.removeItem("user");
};

//kollar vem som är inloggad
const getCurrentUser = () => {
    const user = localStorage.getItem("user");
    return user ? JSON.parse(user) : null;
};

export default {
    register,
    login,
    logout,
    getCurrentUser,
};
