package com.anyarusova.server.data;

import java.sql.Connection;
import java.sql.SQLException;

public class Database {
    private final UsersTable usersTable;
    private final OrganizationTable organizationTable;

    public Database(Connection connection) {
        this.organizationTable = new OrganizationTable(connection);
        this.usersTable = new UsersTable(connection);

        try {
            initTables();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initTables() throws SQLException {
        organizationTable.init();
        usersTable.init();
    }

    public OrganizationTable getOrganizationTable() {
        return organizationTable;
    }

    public UsersTable getUsersTable() {
        return usersTable;
    }
}
