import React, { useState } from "react";
import LoginForm from "./Components/LoginForm/LoginForm";
import NavBar from "./Components/NavBar/NavBar";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import RegisterForm from "./Components/RegisterForm/RegisterForm";
import UpdateForm from "./Components/UpdateForm/UpdateForm";
import UserDashboard from "./Components/UserDashboard/UserDashboard";
import axios from "axios";

const App = () => {
  axios.default.withCredentials = true;
  axios.defaults.baseURL = "http://localhost:1025";
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  return (
    <div>
      <LoginForm />
      {/* <NavBar /> */}
      <UserDashboard />
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<LoginForm></LoginForm>}></Route>

          <Route path="/register" element={<RegisterForm></RegisterForm>}></Route>

          <Route path="/update" element={<UpdateForm />}></Route>
          <Route path="/dashboard" element={<UserDashboard />}></Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
};

export default App;
