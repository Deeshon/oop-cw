import React, { useEffect, useState } from "react";
import Configuration from "./Configuration";
import { Button } from "antd";
import useSimulation from "../hooks/useSimulation";
import useCallApi from "../hooks/useCallApi";
import { useAppContext } from "../AppContext";
import { IoMdPause } from "react-icons/io";
import { RiAddFill } from "react-icons/ri";



const ControlPanel: React.FC = () => {
  const [open, setOpen] = useState(true);
  const [vendors, setVendors] = useState<string[]>([]);
  const [customers, setCustomers] = useState<string[]>([])
  const { startSimulation, stopSimulation } = useSimulation();
  const { handleApiCall } = useCallApi();
  const { isSimulation } = useAppContext();

  const fetchVendors = async () => {
    const data = await handleApiCall(
      "GET",
      "http://localhost:8080/api/simulation/vendor/all"
    );
    setVendors(data);
  };

  const addVendors = async () => {
    await handleApiCall(
      "GET",
      "http://localhost:8080/api/simulation/vendor/add",
      "New vendor added successfully!",
      "Failed to add new vendor!"
    );
    fetchVendors();
  };

  const removeVendor = async (vendorId: string) => {
    await handleApiCall(
      "POST",
      "http://localhost:8080/api/simulation/vendor/discontinue",
      `Vendor ${vendorId} removed successfully!`,
      `Failed to remove vendor ${vendorId}`,
      {vendorId: vendorId}
    );
    fetchVendors();
  };

  const fetchCustomers = async () => {
    const data = await handleApiCall(
      "GET",
      "http://localhost:8080/api/simulation/customers/all"
    );
    setCustomers(data);
  };

  const addCustomer = async () => {
    await handleApiCall(
      "GET",
      "http://localhost:8080/api/simulation/customers/add",
      "New customer added successfully!",
      "Failed to add new customer!"
    );
    fetchCustomers();
  };

  const removeCustomer = async (customerId: string) => {
    await handleApiCall(
      "POST",
      "http://localhost:8080/api/simulation/customers/discontinue",
      `Customer ${customerId} removed successfully!`,
      `Failed to remove customer ${customerId}`,
      {customerId: customerId}
    );
    fetchCustomers();
  };

  useEffect(() => {
    if (isSimulation) {
      fetchVendors()
      fetchCustomers()
    }
  }, [isSimulation])

  useEffect(() => {
    fetchVendors();
  }, []);

  return (
    <div className="m-10 bg-white">
      <div className="flex items-center justify-between p-4 bg-white shadow-lg">
        <div className="text-xl font-bold">Control Panel</div>
        <Button
          className="bg-[#FAF9F6] border-[#FAF9F6] rounded-full text-xl"
          onClick={() => setOpen(!open)}
        >
          {open ? "--" : "+"}
        </Button>
      </div>
      <div className={open ? "bg-white" : "hidden"}>
        <div className="flex p-10">
          <Configuration />
          <div className="w-1/2 ml-8">
            <div className="flex justify-between">
              <Button
                className="w-64 h-12 mr-4"
                type="primary"
                onClick={startSimulation}
              >
                Start
              </Button>
              <Button
                className="w-64 h-12"
                type="primary"
                danger
                onClick={stopSimulation}
              >
                Stop
              </Button>
            </div>
            {/* Vendors Section */}
            <div className="pt-10 grid-container">
              {vendors.map((vendor) => (
                <div
                  key={vendor}
                  className="grid-item flex items-center justify-between w-[180px] h-14 p-8 m-3 bg-[#EEEEEE] rounded-lg"
                >
                  <div>{vendor}</div>
                  <Button
                    id={vendor}
                    className="rounded-full"
                    danger
                    onClick={() => removeVendor(vendor)}
                  >
                    <IoMdPause />
                  </Button>
                </div>
              ))}
              {vendors.length > 0 && (
                <div className="flex items-center justify-between w-[200px] h-14 p-4 m-3 rounded-lg">
                  <Button
                    className="rounded-full"
                    ghost
                    type="primary"
                    onClick={addVendors}
                  >
                    <RiAddFill />
                  </Button>
                </div>
              )}
            </div>
            {/* Customers Section (Placeholder for future expansion) */}
            <div className="pt-10 mt-5 grid-container">
              {customers.map((customer) => (
                <div
                  key={customer}
                  className="grid-item flex items-center justify-between w-[180px] h-14 p-8 m-3 bg-[#EEEEEE] rounded-lg"
                >
                  <div>{customer}</div>
                  <Button
                    id={customer}
                    className="rounded-full"
                    danger
                    onClick={() => removeCustomer(customer)}
                  >
                    <IoMdPause />
                  </Button>
                </div>
              ))}
              {customers.length > 0 && (
                <div className="flex items-center justify-between w-[200px] h-14 p-4 m-3 rounded-lg">
                  <Button
                    className="rounded-full"
                    ghost
                    type="primary"
                    onClick={addCustomer}
                  >
                    <RiAddFill />
                  </Button>
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ControlPanel;
