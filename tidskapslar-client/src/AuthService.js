import axios from 'axios';

const API_URL = "http://localhost:8080"; 

//registrera en ny användare
const register = (email, password) => {
  return axios.post(`${API_URL}/auth/register`, {
    email: email,
    password: password
  })
  .then(response => {
    console.log("User registration successful:", response.data);
    return response.data;
  })
  .catch(error => {
    console.error("Error during registration:", error);
    throw new Error(error.response ? error.response.data : 'Registration failed');
  });
};

const login = (email, password) => {
  return axios.post(`${API_URL}/auth/login`, {
    email: email,
    password: password
  })
  .then(response => {
    if (response.data.token) {
      localStorage.setItem("user", JSON.stringify(response.data)); //spara token och userId i localStorage
    }
    return response.data;
  })
  .catch((error) => {
    throw new Error(error.response ? error.response.data.error : 'Login failed');
  });
};

const logout = () => {
  localStorage.removeItem("user");
};

const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem("user"));
};

export default {
  register,
  login,
  logout,
  getCurrentUser
};