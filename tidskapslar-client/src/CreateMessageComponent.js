import React, { useState } from "react";
import axios from "axios";
import AuthService from "./AuthService";
import { useNavigate } from "react-router-dom";
import "./CreateMessageComponent.css";
import logo from "./asstes/Grit-Academy-logo.png";

//skapa meddelande
const CreateMessageComponent = () => {
    const [message, setMessage] = useState("");
    const currentUser = AuthService.getCurrentUser();
    const navigate = useNavigate();

    const handleCreateMessage = (e) => {
        e.preventDefault();

        axios
            .post(
                "http://localhost:8080/messages/create",
                {
                    message: message,
                    userId: currentUser.userId,
                },
                {
                    headers: {
                        Authorization: `Bearer ${currentUser.token}`,
                    },
                }
            )
            .then((response) => {
                alert("Meddelandet har sparats och krypterats!");
            })
            .catch((error) => {
                console.error("Error creating message:", error);
            });
    };

    return (
        <div className="main">
            <div className="create-message-container">
                <h2>Skapa ett nytt meddelande</h2>
                <form onSubmit={handleCreateMessage}>
                    <div>
                        <label>Meddelande:</label>
                        <input
                            type="text"
                            value={message}
                            onChange={(e) => setMessage(e.target.value)}
                        />
                    </div>
                    <button type="submit">Skapa</button>
                    <button onClick={() => navigate("/view-messages")}>
                        Se dina skapade meddelandenn
                    </button>
                    <button onClick={() => navigate("/message-options")}>
                        Tillbaka till meddelande val
                    </button>
                </form>
                <img
                    src={logo}
                    alt="Grit Academy Logo"
                    className="logo-image"
                />
            </div>
        </div>
    );
};

export default CreateMessageComponent;
