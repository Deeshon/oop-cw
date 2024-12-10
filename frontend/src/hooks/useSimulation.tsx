import { useState } from 'react';
import axios from 'axios';
import { notification } from 'antd';
import { SalesData } from '../types';

const useSimulation = () => {
    const [salesData, setSalesData] = useState<SalesData[]>(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const handleApiCall = async (url, successMessage) => {
        setLoading(true);
        setError(null);
        try {
            const response = await axios.get(url);
            notification.success({ message: successMessage });
            return response.data;
        } catch (error) {
            setError(error);
            notification.error({ message: `Error: ${error.message}` });
            throw error; // Rethrow to let caller handle if needed
        } finally {
            setLoading(false);
        }
    };

    const startSimulation = async () => {
        await handleApiCall(
            'http://localhost:8080/api/simulation/start',
            'Simulation started successfully!'
        );
    };

    const stopSimulation = async () => {
        await handleApiCall(
            'http://localhost:8080/api/simulation/stop',
            'Simulation stopped successfully!'
        );
    };

    const getSalesData = async () => {
        const data: SalesData[] = await handleApiCall(
            'http://localhost:8080/api/sales',
            'Sales data fetched successfully!'
        );
        setSalesData(data);
    };

    return {
        startSimulation,
        stopSimulation,
        getSalesData,
        salesData,
        loading,
        error
    };
};

export default useSimulation;
