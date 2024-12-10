import React from "react";
import Configuration from "./Configuration";
import { Button } from "antd";

const ControlPanel: React.FC = () => {
    return (
        <div className="flex m-10">
            <Configuration />
            <div className="flex items-center justify-center w-1/2">
            <div className="flex flex-col">
                <Button className="w-64 h-12 m-10" type="primary">Start</Button>
                <Button className="w-64 h-12 m-10" type="primary" danger>Stop</Button>
            </div>
            </div>
        </div>
    )
}

export default ControlPanel;