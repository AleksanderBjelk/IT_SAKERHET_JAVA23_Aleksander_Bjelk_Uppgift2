import React, { useState } from "react";
import { useNavigate } from "react-router-dom";  // Importera useNavigate för navigering
import AuthService from "./AuthService";  // Justera vägen till AuthService om det behövs

const LoginComponent = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate();  // Använd useNavigate för att hantera navigering

  const handleLogin = (e) => {
    e.preventDefault();

    AuthService.login(email, password)
      .then(() => {
        navigate("/profile");  // Navigera till profilsidan efter lyckad inloggning
      })
      .catch(error => {
        setMessage(error.message);
      });
  };

  return (
    <div>
      <form onSubmit={handleLogin}>
        <div>
          <label>Email</label>
          <input
            type="text"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </div>
        <div>
          <label>Password</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
        <button type="submit">Login</button>
      </form>

      {message && <div>{message}</div>}

      {/* Lägg till knapp för att gå till registreringssidan */}
      <div>
        <p>Har du inget konto? <button onClick={() => navigate("/register")}>Registrera dig här</button></p>
      </div>
    </div>
  );
};

export default LoginComponent;