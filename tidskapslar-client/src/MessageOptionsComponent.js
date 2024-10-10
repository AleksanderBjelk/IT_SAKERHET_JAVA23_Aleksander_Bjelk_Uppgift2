import React from 'react';
import { useNavigate } from 'react-router-dom';

const MessageOptionsComponent = () => {
  const navigate = useNavigate();

  return (
    <div>
      <h2>Välj ett alternativ</h2>
      <button onClick={() => navigate('/view-messages')}>Visa dina meddelanden</button>
      <button onClick={() => navigate('/create-message')}>Skapa ett meddelande</button>
    </div>
  );
};

export default MessageOptionsComponent;
