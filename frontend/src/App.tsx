import "./App.css";
import Header from "./components/Header";
import ControlPanel from "./components/ControlPanel";
import LogDisplay from "./components/LogDisplay";
import AnalysisDisplay from "./components/AnalysisDisplay";
import { AppProvider } from "./AppContext";

function App() {
  return (
    <AppProvider>
      <Header />
      <ControlPanel />
      <LogDisplay />
      <AnalysisDisplay />
    </AppProvider>
  );
}

export default App;
