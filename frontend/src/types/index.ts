export type SalesData = {
    id: number;
    vendorId: string;
    customerId: string;
    ticketId: string;
    ticketsAvailable: number;
    timestamp: string;
  };

export type FieldType = {
    username?: string;
    password?: string;
    remember?: string;
  };

export type ApiMethod = "GET" | "POST" | "UPDATE"