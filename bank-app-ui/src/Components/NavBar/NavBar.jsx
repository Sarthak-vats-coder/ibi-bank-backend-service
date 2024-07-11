import React from "react";
import { FaRegUser } from "react-icons/fa";
import { IoHomeOutline } from "react-icons/io5";
import { MdWorkOutline, MdOutlineAccountBalance } from "react-icons/md";
import { GrLogin } from "react-icons/gr";
import { BiSupport } from "react-icons/bi";

import("./NavBar.css");

const NavBar = () => {
  return (
    <div className="navBar">
      <ul className="navBarItems flexbox-Column ">
        <li className="navBarLogo flexbox-left">
          <a className="navBarItemsInner flexbox-left">
            <div className="navBarItemsInnerWrrapper flexbox">
              <FaRegUser className="icon" />
            </div>
            <span className="text">picture</span>
          </a>
        </li>
        <li className="navBaritems flexbox-left">
          <a className="navBarItemsInner flexbox-left" href="/update">
            <div className="navBarItemsInnerWrrapper flexbox">
              {" "}
              <FaRegUser className="icon" />
            </div>
            <span className="text">Profile</span>
          </a>
        </li>
        <li className="navBaritems flexbox-left">
          <a className="navBarItemsInner flexbox-left" href="/dashboard">
            <div className="navBarItemsInnerWrrapper flexbox">
              <IoHomeOutline />
            </div>
            <span className="text">Home</span>
          </a>
        </li>
        <li className="navBaritems flexbox-left">
          <a className="navBarItemsInner flexbox-left">
            <div className="navBarItemsInnerWrrapper flexbox">
              <MdWorkOutline />
            </div>
            <span className="text">Services</span>
          </a>
        </li>
        <li className="navBaritems flexbox-left">
          <a className="navBarItemsInner flexbox-left">
            <div className="navBarItemsInnerWrrapper flexbox">
              <MdOutlineAccountBalance />
            </div>
            <span className="text">Account</span>
          </a>
        </li>
        <li className="navBaritems flexbox-left">
          <a className="navBarItemsInner flexbox-left" href="/login">
            <div className="navBarItemsInnerWrrapper flexbox">
              <GrLogin />
            </div>
            <span className="text">Login</span>
          </a>
        </li>
        <li className="navBaritems flexbox-left">
          <a className="navBarItemsInner flexbox-left">
            <div className="navBarItemsInnerWrrapper flexbox">
              <BiSupport />
            </div>
            <span className="text">Support</span>
          </a>
        </li>
      </ul>
    </div>
  );
};

export default NavBar;
