import { Button, notification } from "antd";
import React, { useEffect, useState } from "react";
import useCallApi from "../hooks/useCallApi";
import { useAppContext } from "../AppContext";

const SystemLogs: React.FC = () => {
    const [systemLogs, setSystemLogs] = useState<string[]>([]);
    const [open, setOpen] = useState(true);
    const { handleApiCall } = useCallApi();
    const { isSimulation } = useAppContext();

    // Helper function to determine log style based on content
    const getLogStyle = (log: string) => {
        if (log.includes("New")) {
            return "bg-green-200 text-green-800"; // New system entry
        } else if (log.includes("added a ticket")) {
            return "bg-blue-200 text-blue-800"; // Ticket added
        } else if (log.includes("purchased ticket")) {
            return "bg-yellow-200 text-yellow-800"; // Ticket purchased
        } else if (log.includes("finished selling") || log.includes("removed")) {
            return "bg-red-200 text-red-800"; // Ticket capacity update
        } else {
            return "bg-gray-200 text-gray-800"; // Default log
        }
    };

    const fetchSystemLogs = async () => {
        try {
            const data = await handleApiCall(
                "GET",
                "http://localhost:8080/api/config/logs"
            );
            setSystemLogs(data);
        } catch (error) {
            console.error("Error fetching system logs: ", error);
        }
    };

    useEffect(() => {
        let interval = null;

        if (isSimulation) {
            fetchSystemLogs();
            notification.success({ message: "System logs fetched successfully!" });
            interval = setInterval(fetchSystemLogs, 5000);
        }

        return () => {
            if (interval) {
                clearInterval(interval);
            }
        };
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [isSimulation]);

    return (
        <div className="m-10 bg-white">
            <div className="flex justify-between p-4 text-xl font-bold shadow-lg">
                <p>System Logs</p>
                <div>
                    <Button
                        className="mr-5"
                        type="primary"
                        onClick={fetchSystemLogs}
                    >
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
                className={open ? "p-8 bg-900 max-h-[400px] overflow-y-auto" : "hidden"}
            >
                {systemLogs?.map((log, index) => {
                    const logStyle = getLogStyle(log);
                    return (
                        <p key={index} className={`p-2 rounded my-2 ${logStyle}`}>
                            {log}
                        </p>
                    );
                })}
            </div>
        </div>
    );
};

export default SystemLogs;
