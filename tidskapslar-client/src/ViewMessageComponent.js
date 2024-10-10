import React, { useState, useEffect } from "react";
import axios from "axios";
import AuthService from "./AuthService";

const ViewMessageComponent = () => {
  const [messages, setMessages] = useState([]);
  const currentUser = AuthService.getCurrentUser();

  useEffect(() => {
    axios.get(`http://localhost:8080/messages/user/${currentUser.userId}`, {
      headers: {
        Authorization: `Bearer ${currentUser.token}`,
      },
    })
    .then(response => {
      setMessages(response.data);
    })
    .catch(error => {
      console.error("Error fetching messages:", error);
    });
  }, [currentUser]);

  return (
    <div>
      <h2>Dina meddelanden</h2>
      <ul>
        {messages.length > 0 ? messages.map((message, index) => (
          <li key={index}>{message}</li>
        )) : <p>Inga meddelanden hittades.</p>}
      </ul>
    </div>
  );
};

export default ViewMessageComponent;