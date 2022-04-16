package com.anyarusova.client.utility;

import com.anyarusova.client.data.Organization;

import java.time.ZonedDateTime;
import java.util.PriorityQueue;

public class CollectionManager {
    private PriorityQueue<Organization> mainData = new PriorityQueue<>();
    private final ZonedDateTime creationDate = ZonedDateTime.now();

    public void initialiseData(PriorityQueue<Organization> priorityQueue) {
        this.mainData = priorityQueue;
    }


    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public PriorityQueue<Organization> getMainData() {
        return mainData;
    }

    public int getMaxId() {
        int maxId = 0;
        for (Organization organization : mainData) {
            if (organization.getId() > maxId) {
                maxId = organization.getId();
            }
        }
        return maxId;
    }
}
