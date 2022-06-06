package com.anyarusova.server.data;

import com.anyarusova.common.commands.InfoCommand;
import com.anyarusova.common.data.Organization;
import com.anyarusova.common.data.OrganizationType;
import com.anyarusova.common.data.User;
import com.anyarusova.common.utility.DataManager;
import com.anyarusova.common.utility.Encryptor;

import java.sql.SQLException;
import java.util.PriorityQueue;
import java.util.StringJoiner;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class DataManagerImpl implements DataManager {

    private final Database database;
    private PriorityQueue<Organization> mainData = new PriorityQueue<>();
    private PriorityQueue<User> users = new PriorityQueue<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock(true);

    public DataManagerImpl(Database database) {
        this.database = database;
    }


    @Override
    public void addUser(User user) {
        Lock writeLock = lock.writeLock();
        try {
            writeLock.lock();
            final User encryptedUser = new User(user.getId(), Encryptor.encryptThisString(user.getPassword()), user.getName());

            final int generatedId;

            try {
                generatedId = database.getUsersTable().add(encryptedUser);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            encryptedUser.setId(generatedId);
            users.add(encryptedUser);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void addOrganization(Organization organization) {
        Lock writeLock = lock.writeLock();
        try {
            writeLock.lock();
            final int generatedId = database.getOrganizationTable().add(organization);
            organization.setId(generatedId);
            mainData.add(organization);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            writeLock.unlock();
        }

    }

    @Override
    public boolean validateUser(String username, String password) {
        Lock readLock = lock.readLock();
        try {
            readLock.lock();
            return users.stream().anyMatch(it -> it.getName().equals(username) && it.getPassword().equals(Encryptor.encryptThisString(password)));
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean checkIfUsernameUnique(String username) {
        Lock readLock = lock.readLock();
        try {
            readLock.lock();
            return users.stream().noneMatch(it -> it.getName().matches(username));
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean checkIfMax(Organization organization) {
        Lock readLock = lock.readLock();
        try {
            readLock.lock();
            return mainData.isEmpty() || organization.compareTo(mainData.peek()) < 0;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void clearOwnedData(String username) {
        Lock writeLock = lock.writeLock();
        try {
            writeLock.lock();
            database.getOrganizationTable().clearOwnedData(username);
            mainData.removeIf(it -> it.getOwner().equals(username));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            writeLock.unlock();
        }

    }

    @Override
    public String filterLessThanEmployeesCount(Long inputEmployeesCount) {
        Lock readLock = lock.readLock();
        try {
            readLock.lock();
            StringJoiner output = new StringJoiner("\n\n");
            mainData.stream().filter(it -> it.getEmployeesCount().compareTo(inputEmployeesCount) < 0).forEach(it -> output.add(it.toString()));
            return output.toString();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public String countGreaterThanType(OrganizationType inputOrganizationType) {
        Lock readLock = lock.readLock();
        try {
            readLock.lock();
            StringJoiner output = new StringJoiner("\n\n");
            mainData.stream().filter(it -> it.getOrganizationType().compareTo(inputOrganizationType) > 0).count();
            return output.toString();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public String filterStartsWithName(String inputName) {
        Lock readLock = lock.readLock();
        try {
            readLock.lock();
            StringJoiner output = new StringJoiner("\n\n");
            mainData.stream().filter(it -> it.getName().startsWith(inputName)).
                    forEach(it -> output.add(it.toString()));
            return output.toString();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public InfoCommand.InfoCommandResult getInfoAboutCollections() {
        Lock readLock = lock.readLock();
        try {
            readLock.lock();
            if (mainData.isEmpty()) {
                return new InfoCommand.InfoCommandResult(
                        0,
                        0
                );
            }
            return new InfoCommand.InfoCommandResult(
                    mainData.size(),
                    mainData.peek().getAnnualTurnover()
            );
        } finally {
            readLock.unlock();
        }
    }


    @Override
    public Organization getMaxByIdOrganization() {
        Lock readLock = lock.readLock();
        try {
            readLock.lock();
            return mainData.stream().max(Comparator.comparingInt(Organization::getId)).orElse(null);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public String ascendingDataToString() {
        Lock readLock = lock.readLock();
        try {
            readLock.lock();
            return mainData.toString();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void removeOrganizationById(int id) {
        Lock writeLock = lock.writeLock();
        try {
            writeLock.lock();
            database.getOrganizationTable().removeById(id);
            mainData.removeIf(it -> it.getId() == id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public String showSortedByName() {
        Lock readLock = lock.readLock();
        try {
            readLock.lock();
            return mainData
                    .stream()
                    .sorted(Comparator.comparing(Organization::getName))
                    .collect(Collectors.toList()).toString();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void updateOrganizationById(int id, Organization organization) {
        Lock writeLock = lock.writeLock();
        try {
            writeLock.lock();
            database.getOrganizationTable().updateById(id, organization);
            mainData.removeIf(it -> it.getId() == id);
            mainData.add(organization);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void removeLowerIfOwned(Organization organization, String username) {
        Lock writeLock = lock.writeLock();
        try {
            writeLock.lock();
            TreeSet<Organization> tailSet = new TreeSet<>();
            for (Organization organization1 : mainData) {
                if (organization1.compareTo(organization) < 0) {
                    tailSet.add(organization1);
                }
            }
            final Set<Integer> idsToRemove =
                    tailSet
                            .stream()
                            .filter(it -> it.getOwner().equals(username))
                            .map(Organization::getId)
                            .collect(Collectors.toSet());
            for (int id : idsToRemove) {
                database.getOrganizationTable().removeById(id);
                mainData.removeIf(it -> idsToRemove.contains(it.getId()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public boolean validateOwner(String username, int organizationId) {
        Lock readLock = lock.readLock();
        try {
            readLock.lock();
            return mainData.stream().anyMatch(it -> it.getId() == organizationId && it.getOwner().equals(username));
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void initialiseData() {
        try {
            this.mainData = database.getOrganizationTable().getCollection();
            this.users = database.getUsersTable().getCollection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
