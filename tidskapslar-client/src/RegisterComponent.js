import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import AuthService from "./AuthService";
import "./RegisterComponent.css";
import logo from "./asstes/Grit-Academy-logo.png";

const RegisterComponent = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [message, setMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("");
    const navigate = useNavigate();

    const handleRegister = (e) => {
        e.preventDefault();

        //kollar om fälten är tomma
        if (!email || !password) {
            setErrorMessage("Vänligen fyll i både e-post och lösenord.");
            return;
        }

        AuthService.register(email, password)
            .then((responseMessage) => {
                //sätter responemeddelandet så användaren vet exakt vad som gäller (backenden sköter detta)
                setMessage(responseMessage);
                setErrorMessage("");
            })
            .catch((error) => {
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

                {errorMessage && (
                    <div className="error-message">{errorMessage}</div>
                )}

                {message && <div className="message">{message}</div>}

                <button onClick={() => navigate("/login")}>
                    Gå till inloggning
                </button>
                <img
                    src={logo}
                    alt="Grit Academy Logo"
                    className="logo-image"
                />
            </div>
        </div>
    );
};

export default RegisterComponent;
