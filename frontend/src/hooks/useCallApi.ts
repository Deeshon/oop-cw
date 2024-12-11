// src/hooks/useApiCall.ts

import { useState } from 'react';
import axios from 'axios';
import { notification } from 'antd';
import { ApiMethod } from '../types';

const useCallApi = () => {
  const [loading, setLoading] = useState(false);
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  const [error, setError] = useState<any>(null);

  const handleApiCall = async (
    method: ApiMethod, 
    url: string, 
    successMessage?: string, 
    errorMessage?: string, 
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    data?: any // Optional data for POST requests
  ) => {
    setLoading(true);
    setError(null);
    try {
      let response;

      if (method === 'GET') {
        // If method is GET, use axios.get
        response = await axios.get(url);
      } else if (method === 'POST' && data) {
        // If method is POST, use axios.post and pass the data
        response = await axios.post(url, data);
      } else {
        throw new Error('Invalid API method or missing data for POST request');
      }

      if (successMessage) {
        notification.success({ message: successMessage });
      }
      return response.data;
    } catch (error) {
      setError(error);
      if (errorMessage) {
        notification.error({ message: errorMessage });
      }
      throw error;
    } finally {
      setLoading(false);
    }
  };

  return { handleApiCall, loading, error };
};

export default useCallApi;
