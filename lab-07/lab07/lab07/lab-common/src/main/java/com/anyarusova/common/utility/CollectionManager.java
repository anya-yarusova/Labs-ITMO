package com.anyarusova.common.utility;

import com.anyarusova.common.data.Organization;

import java.time.ZonedDateTime;
import java.util.PriorityQueue;

public interface CollectionManager {

    void initialiseData(PriorityQueue<Organization> priorityQueue);

    ZonedDateTime getCreationDate();

    PriorityQueue<Organization> getMainData();

    int getMaxId();
}
