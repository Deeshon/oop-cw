import React from "react";
import { DiWebplatform } from "react-icons/di";


const Header: React.FC = () => {
    return (
        <div className="flex items-center p-4 bg-white shadow-lg justify-centerh-16">
            <DiWebplatform size={40} color="#667eea" />
            <p className="ml-4 text-2xl text-[#764ba2] montserrat-header">Tickify</p>
        </div>
    )
}

export default Header