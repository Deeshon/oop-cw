package org.example;

import org.example.utils.Utils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TicketPool {
    private final Queue<Ticket> queue;
    private final int capacity;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public TicketPool(int capacity) {
        this.queue = new LinkedList<>();
        this.capacity = capacity;
    }

    public int getTicketCount() {
        return queue.size();
    }

    public boolean addTickets(Ticket ticket) {
        lock.lock();
        try {
            while (queue.size() == capacity) {
                Utils.log("TicketPool at maximum capacity. Vendor is waiting to add tickets.");
                notFull.await(); // Wait until there is space to add more tickets
            }
            queue.add(ticket);
            Utils.log("Vendor "
                    + ticket.getVendorId()
                    + " added a ticket."
                    + "\nTotal tickets available: "
                    + queue.size(), Utils.CYAN);
            notEmpty.signalAll(); // Notify customers that tickets are available
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } finally {
            lock.unlock();
        }
    }

//    public synchronized boolean addTickets(Ticket ticket) {
//        while (queue.size() == capacity) {
//            try {
//                Utils.log("TicketPool at maximum capacity. Vendor is waiting to add tickets.");
//                wait();
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//                return false;
//            }
//        }
//        queue.add(ticket);
//        notifyAll();
//        return true;
//    }

    public Ticket removeTicket(String customerId) throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                Utils.log("Ticket pool is empty. Customer is waiting for tickets.");
                notEmpty.await(); // Wait until tickets are available
            }
            Ticket ticket = queue.poll();
            if (ticket != null) {
                ticket.setCustomerId(customerId);
                Utils.log(customerId
                        + " purchased ticket "
                        + ticket.getTicketId()
                        + ". \nTotal tickets available: "
                        + queue.size(), Utils.YELLOW);
            }
            notFull.signalAll(); // Notify vendors that space is available
            return ticket;
        } finally {
            lock.unlock();
        }
    }

//    public synchronized Ticket removeTicket(String customerId) throws InterruptedException {
//        while (queue.isEmpty()) {
//            Utils.log("Ticket pool is empty. Customer is waiting for tickets.");
//            wait();
//        }
//
//        Ticket ticket = queue.poll();
//        if (ticket != null) {
//            ticket.setCustomerId(customerId);
//        }
//        notifyAll(); // Notify vendors if pool has space
//        return ticket;
//    }
}
