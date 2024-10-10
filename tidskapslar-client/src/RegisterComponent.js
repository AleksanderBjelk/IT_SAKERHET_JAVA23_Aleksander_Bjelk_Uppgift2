import React, { useState } from "react";
import { useNavigate } from "react-router-dom";  
import AuthService from "./AuthService";  
import "./RegisterComponent.css";  
import logo from "./asstes/Grit-Academy-logo.png"

const RegisterComponent = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");  // Ny state för att hantera felmeddelanden
  const navigate = useNavigate();  

  const handleRegister = (e) => {
    e.preventDefault();

    // Kolla om fälten är tomma
    if (!email || !password) {
      setErrorMessage("Vänligen fyll i både e-post och lösenord.");
      return;  // Stoppa om fälten är tomma
    }

    AuthService.register(email, password)
      .then((responseMessage) => {
        // Sätt meddelandet från response.data istället för hårdkodade meddelandet
        setMessage(responseMessage); 
        setErrorMessage("");  // Rensa felmeddelandet vid lyckad registrering
      })
      .catch(error => {
        setMessage(error.message);
      });
  };

  return (
    <div className="main">
    <div className="register-container">
      <h2>Registrera</h2>
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
        <button type="submit">Registera diit nya konto</button>
      </form>

      {/* Visa felmeddelanden om fälten är tomma */}
      {errorMessage && <div className="error-message">{errorMessage}</div>}

      {/* Visa meddelande efter registrering */}
      {message && <div className="message">{message}</div>}

      {/* Knapp för att gå till inloggning, visas alltid */}
      <button onClick={() => navigate("/login")}>Gå till inloggning</button>
      <img src={logo} alt="Grit Academy Logo" className="logo-image" />
    </div>
    </div>
  );
};

export default RegisterComponent;