import axios from "axios";
import React, { useState } from "react";
import("./RegisterForm.css");

const RegisterForm = () => {
  const [username, setUsername] = useState("");
  const [fname, setFname] = useState("");
  const [lname, setLname] = useState("");
  const [email, setEmail] = useState("");
  const [age, setAge] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("ROLE_CUSTOMER");
  const [confirmPassword, setConfirmPassword] = useState("");

  var body = {
    username: username,
    fname: fname,
    lname: lname,
    email: email,
    age: age,
    password: password,
    role: role,
  };

  const submitRegisterRequest = (event) => {
    event.preventDefault();
    axios({
      method: "post",
      url: "http://localhost:1025/banking-service/user-service/user/createUser",
      data: body,
    }).then(
      (data) => console.log(data),
      (error) => console.log(error)
    );
  };

  return (
    <div className="main-registerform">
      <h1>Registration Form</h1>
      <form onSubmit={submitRegisterRequest} target="#">
        <div className="userInput">
          <input
            type="text"
            placeholder="Username"
            value={username}
            onChange={(event) => setUsername(event.target.value)}
            required
          />
        </div>
        <div className="userInput">
          <input
            type="text"
            placeholder="First Name"
            value={fname}
            onChange={(event) => setFname(event.target.value)}
            required
          />
        </div>
        <div className="userInput">
          <input
            type="text"
            placeholder="Last Name"
            value={lname}
            onChange={(event) => setLname(event.target.value)}
            required
          />
        </div>
        <div className="userInput">
          <input
            type="text"
            placeholder="Email Adderess"
            value={email}
            onChange={(event) => setEmail(event.target.value)}
            required
          />
        </div>
        <div className="userInput">
          <input
            type="text"
            placeholder="Age"
            value={age}
            onChange={(event) => setAge(event.target.value)}
            required
          />
        </div>
        <div className="userInput">
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(event) => setPassword(event.target.value)}
            required
          />
        </div>
        <div className="userInput">
          <input
            type="password"
            placeholder="Confirm Password"
            value={confirmPassword}
            onChange={(event) => setConfirmPassword(event.target.value)}
            required
          />
        </div>
        <div className="userInput">
          <label>Role:</label>
          <select
            value={role}
            onChange={(event) => setRole(event.target.value)}
          >
            <option value="ROLE_CUSTOMER">User</option>
            <option value="ROLE_ADMIN">Admin</option>
          </select>
        </div>

        <button type="submit">Login</button>

        <div className="login">
          Already have an account <a href="/login"> Login</a>
        </div>
      </form>
    </div>
  );
};

export default RegisterForm;
