import axios from "axios";
import React from "react";
import "./UserDashboard.css";

const UserDashboard = () => {
  const GetAllUser = (event) => {
    axios.get("/banking-service/user-service/user/GetAllUser").then(
      (data) => console.log(data),
      (error) => console.log(error)
    );

    event.preventDefault();
  };
  return (
    <form onSubmit={GetAllUser}>
      <div className="main-dashboard">
        {" "}
        <button type="submit">Login</button>
      </div>
    </form>
  );
};

export default UserDashboard;
