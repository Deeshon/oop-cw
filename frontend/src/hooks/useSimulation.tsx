import { useState } from 'react';
import { SalesData } from '../types';
import useCallApi from './useCallApi';
import { useAppContext } from '../AppContext';

const useSimulation = () => {
    const [salesData, setSalesData] = useState<SalesData[]>([]);

    const {error, handleApiCall, loading} = useCallApi();
    const {setSimulation} = useAppContext();


    const startSimulation = async () => {
        setSimulation(true)
        await handleApiCall(
            'GET',
            'http://localhost:8080/api/simulation/start',
            'Simulation started successfully!',
            'Error occured. Failed to start simulation'
        );
    };

    const stopSimulation = async () => {
        setSimulation(false)
        await handleApiCall(
            'GET',
            'http://localhost:8080/api/simulation/stop',
            'Simulation stopped successfully!',
            'Error occured. Failed to stop simulation'
        );
    };

    const getSalesData = async () => {
        const data: SalesData[] = await handleApiCall(
            'GET',
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
