import React from "react";
import SystemLogs from "./SystemLogs";
import SaleLogs from "./SaleLogs";

const LogDisplay: React.FC = () => {
    return (
        <>
            <SystemLogs />
            <SaleLogs />
        </>
    )
};

export default LogDisplay;
