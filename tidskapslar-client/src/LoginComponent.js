import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import AuthService from "./AuthService";  
import "./LoginComponent.css"
import logo from "./asstes/Grit-Academy-logo.png"


//sätter inloggning från användare
const LoginComponent = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate();  

  const handleLogin = (e) => {
    e.preventDefault();

    if (!email || !password) {
      setMessage("Vänligen ange både email och lösenord.");  
      return;
    }

    AuthService.login(email, password)
      .then(() => {
        navigate("/message-options");  
      })
      .catch(error => {
        setMessage(error.message);
      });
  };

  return (
    <div className="main">
    <div className="login-container">
      <h2>Logga in för att se och skapa meddelanden</h2>
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
        <button type="submit">Logga in</button>
      </form>

      {message && <div className="message">{message}</div>}

      <div className="register-link">
        <p>Har du inget konto? <button onClick={() => navigate("/register")}>Registrera dig här</button></p>
      </div>
    <img src={logo} alt="Grit Academy Logo" className="logo-image" />
    </div>
    </div>
  );
};

export default LoginComponent;