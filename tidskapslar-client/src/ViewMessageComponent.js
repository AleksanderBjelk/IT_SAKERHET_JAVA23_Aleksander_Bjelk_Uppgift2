import React, { useState, useEffect } from "react";
import axios from "axios";
import AuthService from "./AuthService";
import "./ViewMessageComponent.css";
import logo from "./asstes/Grit-Academy-logo.png";
import { useNavigate } from "react-router-dom";

//hämtar och visar meddelanden
const ViewMessageComponent = () => {
    const [messages, setMessages] = useState([]);
    const currentUser = AuthService.getCurrentUser();
    const navigate = useNavigate();

    useEffect(() => {
        axios
            .get(`http://localhost:8080/messages/user/${currentUser.userId}`, {
                headers: {
                    Authorization: `Bearer ${currentUser.token}`,
                },
            })
            .then((response) => {
                setMessages(response.data);
            })
            .catch((error) => {
                console.error("Error fetching messages:", error);
            });
    }, [currentUser]);

    return (
        <div className="main">
            <div className="view-message-container">
                <h2>Dina meddelanden</h2>
                <ul>
                    {messages.length > 0 ? (
                        messages.map((message, index) => (
                            <li key={index}>{message}</li>
                        ))
                    ) : (
                        <p>Inga meddelanden hittades.</p>
                    )}
                </ul>
                <button onClick={() => navigate("/create-message")}>
                    Skapa nytt meddelande
                </button>
                <button onClick={() => navigate("/message-options")}>
                    Tillbaka till meddelande val
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

export default ViewMessageComponent;
