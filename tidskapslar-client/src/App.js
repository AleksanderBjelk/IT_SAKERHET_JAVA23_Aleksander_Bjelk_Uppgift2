import React from "react";
import {
    BrowserRouter as Router,
    Route,
    Routes,
    Navigate,
} from "react-router-dom";
import LoginComponent from "./LoginComponent";
import RegisterComponent from "./RegisterComponent";
import ViewMessageComponent from "./ViewMessageComponent";
import CreateMessageComponent from "./CreateMessageComponent";
import MessageOptionsComponent from "./MessageOptionsComponent";
import AuthService from "./AuthService";

const PrivateRoute = ({ children }) => {
    const currentUser = AuthService.getCurrentUser();
    return currentUser ? children : <Navigate to="/login" />;
};

function App() {
    return (
        <Router>
            <div>
                <Routes>
                    <Route path="/" element={<Navigate to="/login" />} />
                    <Route path="/login" element={<LoginComponent />} />
                    <Route path="/register" element={<RegisterComponent />} />
                    <Route
                        path="/message-options"
                        element={
                            <PrivateRoute>
                                <MessageOptionsComponent />
                            </PrivateRoute>
                        }
                    />
                    <Route
                        path="/view-messages"
                        element={
                            <PrivateRoute>
                                <ViewMessageComponent />
                            </PrivateRoute>
                        }
                    />
                    <Route
                        path="/create-message"
                        element={
                            <PrivateRoute>
                                <CreateMessageComponent />
                            </PrivateRoute>
                        }
                    />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
