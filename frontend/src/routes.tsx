import { createBrowserRouter } from "react-router";
import { Layout } from "./layout/Layout";
import { Login } from "./features/auth/pages/Login";
import { Register } from "./features/auth/pages/Register";
import { AdminDashboard } from "./features/dashboard/pages/AdminDashboard";
import { Settings } from "./features/dashboard/Settings";
export const router = createBrowserRouter([
  {
    path: "/login",
    Component: Login,
  },
  {
    path: "/register",
    Component: Register,
  },
  {
    path: "/",
    Component: Layout,
    children: [
      { index: true, Component: AdminDashboard },
      { path: "admin", Component: AdminDashboard },
      { path: "settings", Component: Settings },
    ],
  },
]);
