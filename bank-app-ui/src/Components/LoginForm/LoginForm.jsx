import React, { useState } from "react";
import "./LoginForm.css";
import { FaRegUser, FaLock } from "react-icons/fa";
import axios from "axios";

const LoginForm = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  var body = {
    username: username,
    password: password,
  };

  const submitLoginRequest = (event) => {
    axios({
      method: "post",
      url: "banking-service/user-service/user/signIn",
      data: body,
    }).then(
      (data) => console.log(data),
      (error) => console.log(error)
    );
    event.preventDefault();
  };
  return (
    <div className="main-LoginForm">
      <form onSubmit={submitLoginRequest} target="#">
        <h1>LogIn</h1>
        <div className="userInput">
          <FaRegUser className="icon" />
          <input
            type="text"
            placeholder="Username"
            value={username}
            onChange={(event) => setUsername(event.target.value)}
            required
          />
        </div>
        <div className="userInput">
          <FaLock className="icon" />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(event) => setPassword(event.target.value)}
            required
          />
        </div>

        <div className="forgotPassword">
          <label>
            <a href="#"> Forgot Password</a>
          </label>
        </div>
        <button type="submit">Login</button>

        <div className="register">
          Don't have an account <a href="/register"> Register</a>
        </div>
      </form>
    </div>
  );
};

export default LoginForm;
