import { Button, Form, Input } from "antd";
import React from "react";
import useCallApi from "../hooks/useCallApi";

const Configuration: React.FC = () => {

    const [form] = Form.useForm();
    const {handleApiCall} = useCallApi();

    const handleSubmit = () => {
        form.validateFields()
        .then(values => {
            console.log("form values: ", values)
            handleApiCall('POST', 'http://localhost:8080/api/config', 'Configuration set successfully', "Failed to set configuration", values)
        })
        .catch(error => console.error("Validation failed: ", error))
    }

    return (
        <div className="w-1/2 p-10 bg-[#EEEEEE] shadow-lg h-fit">
            <div>
                <p className="text-lg">Configuration Form</p>
                <p className="text-sm">Adjust configuration parameters here: </p>
            </div>
            <div className="mt-8">
                <Form form={form} validateTrigger="onBlur">
                    <Form.Item name="maxTicketCapacity" rules={[{required: true, message: "Please enter max ticket capacity."}]} >
                        <Input placeholder="Max ticket capacity" />
                    </Form.Item>
                    <Form.Item name="totalTickets" rules={[{required: true, message: "Please enter total tickets amount."}]} >
                        <Input placeholder="Total Tickets" />
                    </Form.Item>
                    <Form.Item name="ticketReleaseRate" rules={[{required: true, message: "Please enter the ticket release rate."}]} >
                        <Input placeholder="Ticket release rate" />
                    </Form.Item>
                    <Form.Item name="customerRetrievalRate" rules={[{required: true, message: "Please enter the ticket retrieval rate."}]} >
                        <Input placeholder="Ticket retrieval rate" />
                    </Form.Item>
                    <Form.Item name="numberOfVendors" rules={[{required: true, message: "Please enter the number of vendors."}]} >
                        <Input placeholder="Number of vendors" />
                    </Form.Item>
                    <Form.Item name="numberOfCustomers" rules={[{required: true, message: "Please enter the number of customers."}]} >
                        <Input placeholder="Number of customers" />
                    </Form.Item>
                    <Form.Item>
                        <Button type="primary" onClick={() => handleSubmit()}>Submit</Button>
                    </Form.Item>
                </Form>
            </div>
        </div>
    )
}

export default Configuration