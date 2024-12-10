import './App.css';
// import { Button, notification, Spin } from 'antd';
// import useSimulation from './hooks/useSimulation';
// import { useState } from 'react';
// import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend } from 'recharts';
import ControlPanel from './components/ControlPanel';
import Header from './components/Header';
import LogDisplay from './components/LogDisplay';



// function App() {
//   const { startSimulation, stopSimulation, getSalesData, salesData }: { salesData: SalesData[] } = useSimulation();
//   const [loading, setLoading] = useState(false);

//   const handleStartSimulation = async () => {
//     setLoading(true);
//     try {
//       await startSimulation();
//       notification.success({ message: 'Simulation Started!' });
//     } catch (error) {
//       notification.error({ message: 'Failed to start simulation.' });
//     } finally {
//       setLoading(false);
//     }
//   };

//   const handleStopSimulation = async () => {
//     setLoading(true);
//     try {
//       await stopSimulation();
//       notification.success({ message: 'Simulation Stopped!' });
//     } catch (error) {
//       notification.error({ message: 'Failed to stop simulation.' });
//     } finally {
//       setLoading(false);
//     }
//   };

//   const handleGetSalesData = async () => {
//     setLoading(true);
//     try {
//       await getSalesData();
//       notification.success({ message: 'Sales Data Fetched!' });
//     } catch (error) {
//       notification.error({ message: 'Failed to fetch sales data.' });
//     } finally {
//       setLoading(false);
//     }
//   };

//   // Format data for Recharts
//   const formattedData = salesData?.map((sale) => ({
//     timestamp: new Date(sale.timestamp).toLocaleTimeString(),
//     ticketId: sale.ticketId,
//   }));

//   return (
//     <div className="flex-col items-center justify-center">
//       <div>
//         <p className="text-2xl font-bold">Real-Time Ticketing System</p>
//       </div>
//       <div className="flex-col justify-between p-10">
//         <Button
//           className="w-56 h-12 m-10 text-lg font-bold"
//           type="primary"
//           onClick={handleStartSimulation}
//           disabled={loading}
//         >
//           Start
//         </Button>
//         <br />
//         <Button
//           className="w-56 h-12 m-10 text-lg font-bold"
//           type="primary"
//           danger
//           onClick={handleStopSimulation}
//           disabled={loading}
//         >
//           Stop
//         </Button>
//         <br />
//         <Button
//           className="w-56 h-12 m-10 text-lg font-bold"
//           type="primary"
//           ghost
//           onClick={handleGetSalesData}
//           disabled={loading}
//         >
//           Get Data
//         </Button>
//         {loading && <Spin className="m-10" />}
//       </div>
//       <div className="p-10">
//         <h2>Sales Data:</h2>
//         {salesData ? (
//           <LineChart
//             width={600}
//             height={300}
//             data={formattedData}
//             margin={{
//               top: 5,
//               right: 30,
//               left: 20,
//               bottom: 5,
//             }}
//           >
//             <CartesianGrid strokeDasharray="3 3" />
//             <XAxis dataKey="timestamp" />
//             <YAxis />
//             <Tooltip />
//             <Legend />
//             <Line type="monotone" dataKey="ticketId" stroke="#8884d8" activeDot={{ r: 8 }} />
//           </LineChart>
//         ) : (
//           <p>No sales data available yet.</p>
//         )}
//       </div>
//     </div>
//   );
// }

function App() {
  return (
    <>
          <Header />
          <ControlPanel />
          <LogDisplay />
    </>
  )
}

export default App;
