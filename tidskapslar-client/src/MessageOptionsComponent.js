import React from "react";
import { useNavigate } from "react-router-dom";
import "./MessageOptionsComponent.css";
import logo from "./asstes/Grit-Academy-logo.png";

//navigeringsmeny för användare
const MessageOptionsComponent = () => {
    const navigate = useNavigate();

    return (
        <div className="main">
            <div className="message-options-container">
                <h2>Välj ett alternativ</h2>
                <button onClick={() => navigate("/view-messages")}>
                    Visa dina meddelanden
                </button>
                <button onClick={() => navigate("/create-message")}>
                    Skapa ett meddelande
                </button>
                <button
                    onClick={() => navigate("/login")}
                    className="back-to-login"
                >
                    Tillbaka till inlogg
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

export default MessageOptionsComponent;
