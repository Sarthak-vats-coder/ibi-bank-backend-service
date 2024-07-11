import axios from "axios";
import React, { useState } from "react";
import("./UpdateForm.css");

const UpdateForm = () => {
  const [username, setUsername] = useState(localStorage.getItem("username"));
  const [userId, setUserId] = useState(localStorage.getItem("userID"));
  const [fname, setFname] = useState("");
  const [lname, setLname] = useState("");
  const [email, setEmail] = useState("");
  const [age, setAge] = useState("");
  const [password, setPassword] = useState(localStorage.getItem("password"));

  var body = {
    userId: userId,
    username: username,
    fname: fname,
    lname: lname,
    email: email,
    age: age,
    password: password,
  };

  const submitUpdateRequest = (event) => {
    event.preventDefault();
    axios({
      headers: {
        username: localStorage.getItem("username"),
        password: localStorage.getItem("password"),
      },
      method: "post",
      url: "http://localhost:1025/banking-service/user-service/user/updateUser",
      data: body,
    }).then(
      (data) => console.log(data),
      (error) => console.log(error)
    );
  };

  return (
    <div className="updateForm">
      <h1>Update Form</h1>
      <form onSubmit={submitUpdateRequest} target="#">
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
        <button type="submit">Update</button>
      </form>
    </div>
  );
};

export default UpdateForm;
