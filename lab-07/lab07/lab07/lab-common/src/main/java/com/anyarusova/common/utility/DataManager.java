package com.anyarusova.common.utility;

import com.anyarusova.common.commands.InfoCommand;
import com.anyarusova.common.data.Organization;
import com.anyarusova.common.data.User;
import com.anyarusova.common.data.OrganizationType;

public interface DataManager {

    void initialiseData();

    void addUser(User user);

    void addOrganization(Organization organization);

    boolean validateUser(String username, String password);

    boolean validateOwner(String username, int organizationId);

    boolean checkIfUsernameUnique(String username);

    boolean checkIfMax(Organization organization);

    void clearOwnedData(String username);

    String filterLessThanEmployeesCount(Long inputEmployeesCount);

    String countGreaterThanType(OrganizationType inputOrganizationType);

    String filterStartsWithName(String inputName);

    InfoCommand.InfoCommandResult getInfoAboutCollections();

    Organization getMaxByIdOrganization();

    String ascendingDataToString();

    void removeOrganizationById(int id);

    String showSortedByName();

    void updateOrganizationById(int id, Organization organization);

    void removeLowerIfOwned(Organization organization, String username);

}
