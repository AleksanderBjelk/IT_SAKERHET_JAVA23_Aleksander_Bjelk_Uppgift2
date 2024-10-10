import React, { useState } from "react";
import axios from "axios";
import AuthService from "./AuthService";

const CreateMessageComponent = () => {
  const [message, setMessage] = useState("");
  const currentUser = AuthService.getCurrentUser();

  const handleCreateMessage = (e) => {
    e.preventDefault();

    axios.post("http://localhost:8080/messages/create", {
      message: message,
      userId: currentUser.userId
    }, {
      headers: {
        Authorization: `Bearer ${currentUser.token}`,
      }
    })
    .then(response => {
      alert("Meddelandet har sparats och krypterats!");
    })
    .catch(error => {
      console.error("Error creating message:", error);
    });
  };

  return (
    <div>
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
      </form>
    </div>
  );
};

export default CreateMessageComponent;