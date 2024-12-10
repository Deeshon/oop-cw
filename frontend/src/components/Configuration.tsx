import { Button, Form, Input } from "antd";
import axios from "axios";
import React from "react";

const Configuration: React.FC = () => {

    const [form] = Form.useForm();

    const handleSubmit = () => {
        form.validateFields()
        .then(values => {
            console.log("form values: ", values)
            axios.post("http://localhost:8080/api/config", values)
                .then(response => console.log(response))
                .catch(error => console.error(error))
        })
        .catch(error => console.error("Validation failed: ", error))
    }

    return (
        <div className="w-1/2 p-10 bg-white shadow-lg h-fit">
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