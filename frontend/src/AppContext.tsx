import React, { createContext, ReactNode, useContext, useState } from "react";

// define the context value type
type AppContextProps = {
    isSimulation: boolean;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    setSimulation: any;
}

// Create the context
const AppContext = createContext<AppContextProps | undefined>(undefined);

// Create the context provider
export const AppProvider: React.FC<{children: ReactNode}> = ({children}) => {
    // Read the initial value from localStorage if available, otherwise default to false
    const savedSimulation = localStorage.getItem("isSimulation") === "true";
    const [isSimulation, setSimulationState] = useState(savedSimulation || false);

    // Update localStorage whenever `isSimulation` changes
    const setSimulation = (value: boolean) => {
        setSimulationState(value);
        localStorage.setItem("isSimulation", value.toString());
    };

    return (
        <AppContext.Provider value={{ isSimulation, setSimulation }}>
            {children}
        </AppContext.Provider>
    );
}

// Create a custom hook to use the context
export const useAppContext = (): AppContextProps => {
    const context = useContext(AppContext);
    if (!context) {
      throw new Error("useAppContext must be used within an AppProvider");
    }
    return context;
};
