import React, { useState } from "react";
import { useNavigate } from "react-router-dom";  // Importera useNavigate för navigering
import AuthService from "./AuthService";  // Justera vägen till AuthService om det behövs

const RegisterComponent = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate();  // Använd useNavigate för att hantera navigering

  const handleRegister = (e) => {
    e.preventDefault();

    AuthService.register(email, password)
      .then(() => {
        setMessage("Användaren har registrerats! Du omdirigeras till inloggning.");
        setTimeout(() => {
          navigate("/login");  // Omdirigera till inloggningssidan efter registrering
        }, 2000);  // Vänta 2 sekunder innan omdirigering
      })
      .catch(error => {
        setMessage(error.message);
      });
  };

  return (
    <div>
      <form onSubmit={handleRegister}>
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
        <button type="submit">Register</button>
      </form>

      {message && <div>{message}</div>}
    </div>
  );
};

export default RegisterComponent;