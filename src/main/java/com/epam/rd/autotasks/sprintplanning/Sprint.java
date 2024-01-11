package com.epam.rd.autotasks.sprintplanning;

import com.epam.rd.autotasks.sprintplanning.tickets.Bug;
import com.epam.rd.autotasks.sprintplanning.tickets.Ticket;
import com.epam.rd.autotasks.sprintplanning.tickets.UserStory;

public class Sprint {
    private final int capacity;
    private int numberOfTickets;
    private int totalEstimate;
    private final int ticketsLimit;
    private final Ticket[] tickets;

    public Sprint(int capacity, int ticketsLimit) {
        this.capacity = capacity;
        this.ticketsLimit = ticketsLimit;
        tickets = new Ticket[ticketsLimit];
        numberOfTickets = 0;
        totalEstimate = 0;
    }

    private boolean isNotAccepted(Ticket ticket) {
        return ticket == null ||
                ticket.isCompleted() ||
                totalEstimate + ticket.getEstimate() > capacity ||
                numberOfTickets >= ticketsLimit;
    }

    private void addTicket(Ticket ticket) {
        totalEstimate += ticket.getEstimate();
        tickets[numberOfTickets++] = ticket;
    }

    public boolean addUserStory(UserStory userStory) {
        if(isNotAccepted(userStory)) return false;
        int counter;
        for(UserStory dependency : userStory.getDependencies()){
            counter = 0;
            for (Ticket ticket : tickets) if (ticket == dependency) counter++;
            if(counter == 0) return false;
        }
        addTicket(userStory);
        return true;
    }

    public boolean addBug(Bug bugReport) {
        if(isNotAccepted(bugReport)) return false;
        addTicket(bugReport);
        return true;
    }

    public Ticket[] getTickets() {
        Ticket[] defensiveTickets = new Ticket[numberOfTickets];
        System.arraycopy(tickets, 0, defensiveTickets, 0, numberOfTickets);
        return defensiveTickets;
    }

    public int getTotalEstimate() {
        return totalEstimate;
    }
}
