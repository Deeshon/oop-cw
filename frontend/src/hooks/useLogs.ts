import { useState } from "react";
import useCallApi from "./useCallApi";

const useLogs = () => {

    const [systemLogs, setSystemLogs] = useState<[]>();
    const [saleLogs, setSaleLogs] = useState<[]>();
    const {handleApiCall} = useCallApi();

    const getSystemLogs = async () => {
        const data = await handleApiCall(
            'GET',
            'http://localhost:8080/api/config/logs',
            'System logs fetched successfully!',
            'Failed to fetch system logs!'
        );
        setSystemLogs(data)
    }

    const getSaleLogs = async () => {
        const data = await handleApiCall(
            'GET',
            'http://localhost:8080/api/sales',
            'Sale logs fetched successfully!',
            'Failed to fetch sale logs!'
        )
        setSaleLogs(data)
    }

    return {
        getSystemLogs,
        systemLogs,
        getSaleLogs,
        saleLogs
    }

}

export default useLogs;