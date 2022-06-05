package com.anyarusova.server.utility;

import com.anyarusova.common.data.Organization;
import com.anyarusova.common.utility.CollectionManager;

import java.time.ZonedDateTime;
import java.util.PriorityQueue;

public class PriorityQueueManager implements CollectionManager {

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
