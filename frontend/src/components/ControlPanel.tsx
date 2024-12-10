import React, { useState } from "react";
import Configuration from "./Configuration";
import { Button } from "antd";

const ControlPanel: React.FC = () => {
  const [open, setOpen] = useState(true);

  return (
    <div className="bg-white m-10">
      <div className={"shadow-lg bg-white flex justify-between h-14 p-6 items-center"}>
        <div className="text-xl font-bold">
            Control Panel
        </div>
        <Button className="bg-[#FAF9F6] border-[#FAF9F6] rounded-full text-xl" onClick={() => setOpen(!open)}> 
            {open ? "--" : "+"}
        </Button>
      </div>
      <div className={open ? "bg-white" : "hidden"}>
        <div className="flex p-10">
          <Configuration />
          <div className="flex items-center justify-center w-1/2">
            <div className="flex flex-col">
              <Button className="w-64 h-12 m-10" type="primary">
                Start
              </Button>
              <Button className="w-64 h-12 m-10" type="primary" danger>
                Stop
              </Button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ControlPanel;
