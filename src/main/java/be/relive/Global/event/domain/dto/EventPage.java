package be.relive.Global.event.domain.dto;

import java.io.Serializable;
import java.util.List;

public class EventPage implements Serializable {
    private int currentPage;
    private long totalItems;
    private int totalPages;

    private List<EventDto> eventList;

    public EventPage() {
    }

    private EventPage(int currentPage, long totalItems, int totalPages, List<EventDto> eventList) {
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.eventList = eventList;
    }

    public static EventPage eventPage(List<EventDto> eventList) {
        return new EventPage(0, eventList.size(), 1, eventList);
    }

    public static EventPage eventPage(int currentPage, long totalItems, int totalPages, List<EventDto> eventList) {
        return new EventPage(currentPage, totalItems, totalPages, eventList);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<EventDto> getEventList() {
        return eventList;
    }

    public void setEventList(List<EventDto> eventList) {
        this.eventList = eventList;
    }
}
