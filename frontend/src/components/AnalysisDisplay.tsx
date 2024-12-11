/* eslint-disable @typescript-eslint/no-explicit-any */
import { Button, Spin } from "antd";
import React, { useState, useEffect } from "react";
import {
  BarChart,
  Bar,
  ResponsiveContainer,
  Tooltip,
  Legend,
  XAxis,
  YAxis,
} from "recharts";
import useCallApi from "../hooks/useCallApi";
import { SalesData } from "../types";
import { useAppContext } from "../AppContext";

// Function to calculate statistics from the sales data
function calculateSalesData(data: SalesData[]) {
  const vendorSales: Record<string, number> = {};
  const customerIds = new Set<string>();
  const ticketsAvailable = data[data.length - 1]?.ticketsAvailable || 0;

  data?.forEach((sale: SalesData) => {
    const vendorId = sale.vendorId;
    const customerId = sale.customerId;

    if (!vendorSales[vendorId]) {
      vendorSales[vendorId] = 0;
    }
    vendorSales[vendorId]++;

    customerIds.add(customerId);
  });

  const vendorCount = Object.keys(vendorSales).length;
  const totalSales = data?.length || 0;
  const uniqueCustomers = customerIds.size;

  return {
    vendorSales,
    vendorCount,
    totalSales,
    ticketsAvailable,
    uniqueCustomers,
  };
}

const AnalysisDisplay: React.FC = () => {
  const [open, setOpen] = useState(true);
  const [salesData, setSalesData] = useState<SalesData[]>([]);
  const [loading, setLoading] = useState(false);

  const {isSimulation} = useAppContext();

  const { handleApiCall } = useCallApi();

  const fetchSaleLogs = async () => {
    setLoading(true);
    try {
      const data = await handleApiCall(
        "GET",
        "http://localhost:8080/api/sales"
      );
      setSalesData(data || []);
    } catch (error) {
      console.error("Error fetching sale logs:", error);
    } finally {
      setLoading(false);
    }
  };

  // Fetch data every 2 seconds
  useEffect(() => {
    let interval = null;
    if (isSimulation) {
        fetchSaleLogs(); // Fetch immediately on mount
        interval = setInterval(fetchSaleLogs, 2000); // Fetch every 2 seconds
    }
    return () => {
        if (interval) {
            clearInterval(interval)
        }
    }; // Cleanup interval on unmount
  }, [isSimulation]);

  // Get the calculated sales data
  const {
    vendorSales,
    vendorCount,
    totalSales,
    uniqueCustomers,
    ticketsAvailable,
  } = calculateSalesData(salesData);

  // Data for the BarChart
  const vendorChartData = Object.entries(vendorSales).map(
    ([vendorId, sales]) => ({
      vendorId,
      sales,
    })
  );

  return (
    <div className="m-10 bg-white">
      <div className="flex justify-between p-4 text-xl font-bold shadow-lg">
        <p>Analysis board</p>
        <div>
          <Button className="mr-5" type="primary" onClick={fetchSaleLogs}>
            Get Data
          </Button>
          <Button
            className="bg-[#FAF9F6] border-[#FAF9F6] rounded-full text-xl"
            onClick={() => setOpen(!open)}
          >
            {open ? "--" : "+"}
          </Button>
        </div>
      </div>
      <div className={open ? "h-fit p-8" : "hidden"}>
        {loading && (
          <div className="flex items-center justify-center">
            <Spin size="large" />
          </div>
        )}
        <div className={loading ? "blur-sm" : ""}>
          {/* Vendor Sales Bar Chart */}
          <div className="bg-[#EEEEEE] w-full mb-6 p-4 rounded-md shadow-lg">
            <span className="text-2xl font-semibold">Vendor Sales Summary</span>
            <ResponsiveContainer width="100%" height={300}>
              <BarChart data={vendorChartData}>
                <XAxis dataKey="vendorId" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Bar dataKey="sales" fill="#8884d8" />
              </BarChart>
            </ResponsiveContainer>
            <div className="mt-4">
              {Object.entries(vendorSales).map(([vendorId, sales]) => (
                <div key={vendorId} className="flex justify-between mt-2">
                  <span className="text-xl font-medium">{`Vendor ${vendorId}:`}</span>
                  <span className="text-xl">{sales}</span>
                </div>
              ))}
            </div>
          </div>

          {/* General Stats */}
          <div className="bg-[#EEEEEE] w-full mb-6 p-4 rounded-md shadow-lg">
            <span className="text-2xl font-semibold">General Sales Stats</span>
            <ResponsiveContainer width="100%" height={300}>
              <BarChart
                data={[
                  { label: "Produced", value: ticketsAvailable + totalSales },
                  { label: "Sold", value: totalSales },
                  { label: "Available", value: ticketsAvailable },
                ]}
              >
                <XAxis dataKey="label" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Bar dataKey="value" fill="#82ca9d" />
              </BarChart>
            </ResponsiveContainer>
            <div className="mt-4">
              <div className="flex justify-between">
                <span className="text-xl font-medium">
                  Total Tickets Produced:
                </span>
                <span className="text-xl">{ticketsAvailable + totalSales}</span>
              </div>
              <div className="flex justify-between mt-2">
                <span className="text-xl font-medium">Total Tickets Sold:</span>
                <span className="text-xl">{totalSales}</span>
              </div>
              <div className="flex justify-between mt-2">
                <span className="text-xl font-medium">
                  Total Tickets Available:
                </span>
                <span className="text-xl">{ticketsAvailable}</span>
              </div>
              <div className="flex justify-between mt-10">
                <span className="text-xl font-medium">Unique Customers:</span>
                <span className="text-xl">{uniqueCustomers}</span>
              </div>
              <div className="flex justify-between mt-2">
                <span className="text-xl font-medium">Total Vendors:</span>
                <span className="text-xl">{vendorCount}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AnalysisDisplay;
