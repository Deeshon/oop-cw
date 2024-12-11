import { Button, notification } from "antd";
import React, { useState, useEffect, useRef } from "react";
import { SalesData } from "../types";
import { useAppContext } from "../AppContext";
import useCallApi from "../hooks/useCallApi";

const SaleLogs: React.FC = () => {
  const [saleLogs, setSaleLogs] = useState<SalesData[] | null>(null);
  const [open, setOpen] = useState(true);

  const scrollableDivRef = useRef<HTMLDivElement | null>(null);
  const { handleApiCall } = useCallApi();
  const { isSimulation } = useAppContext();

  const fetchSaleLogs = async () => {
    try {
      const data = await handleApiCall(
        "GET",
        "http://localhost:8080/api/sales"
      );
      setSaleLogs(data);
    } catch (error) {
      console.error("Error fetching sale logs:", error);
    }
  };

  // Scroll to the bottom when saleLogs updates
  useEffect(() => {
    if (scrollableDivRef.current) {
      scrollableDivRef.current.scrollTop = scrollableDivRef.current.scrollHeight;
    }
  }, [saleLogs]);

  useEffect(() => {
    let interval = null;

    if (isSimulation) {
      fetchSaleLogs();
      notification.success({ message: "Sales data fetched successfully!" });
      interval = setInterval(fetchSaleLogs, 5000); // Fetch every 5 seconds
    }

    return () => {
      if (interval) {
        clearInterval(interval); // Clear interval on component unmount or when isSimulation changes
      }
    };
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isSimulation]);

  useEffect(() => {
    fetchSaleLogs()
  }, [])

  const formatSaleLog = (log: SalesData) => {
    const date = new Date(log.timestamp);
    return `[${date.toLocaleString()}] Customer ${
      log.customerId
    } purchased ticket from Vendor ${log.vendorId}`;
  };

  return (
    <div className="m-10 bg-white">
      <div className="flex justify-between p-4 text-xl font-bold shadow-lg">
        <p>Sale Logs</p>
        <div>
          <Button className="mr-5" type="primary" onClick={fetchSaleLogs}>
            Get Logs
          </Button>
          <Button
            className="bg-[#FAF9F6] border-[#FAF9F6] rounded-full text-xl"
            onClick={() => setOpen(!open)}
          >
            {open ? "--" : "+"}
          </Button>
        </div>
      </div>
      <div
        ref={scrollableDivRef}
        className={
          open ? "p-4 bg-900 max-h-[400px] overflow-y-auto" : "hidden"
        }
      >
        <div>
          {saleLogs?.map((log, index) => (
            <p key={index} className="p-2 my-2 text-gray-700 rounded">
              {formatSaleLog(log)}
            </p>
          ))}
        </div>
      </div>
    </div>
  );
};

export default SaleLogs;
