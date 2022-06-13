package com.anyarusova.server.data;

import com.anyarusova.common.data.Coordinates;
import com.anyarusova.common.data.Address;
import com.anyarusova.common.data.OrganizationType;
import com.anyarusova.common.data.Organization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.PriorityQueue;

public class OrganizationTable implements Table<Organization> {
    private static final int PARAMETER_NAME = 1;
    private static final int PARAMETER_COORDINATES_X = 2;
    private static final int PARAMETER_COORDINATES_Y = 3;
    private static final int PARAMETER_TIMESTAMP = 4;
    private static final int PARAMETER_ANNUAL_TURNOVER = 5;
    private static final int PARAMETER_EMPLOYEES_COUNT = 6;
    private static final int PARAMETER_ORGANIZATION_TYPE = 7;
    private static final int PARAMETER_POSTAL_ADDRESS_STREET = 8;
    private static final int PARAMETER_POSTAL_ADDRESS_ZIPCODE = 9;
    private static final int PARAMETER_OWNER = 10;
    private final Connection connection;


    public OrganizationTable(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void init() throws SQLException {
        try (
                Statement statement = connection.createStatement()
        ) {
            statement.execute("CREATE TABLE IF NOT EXISTS organizations ("
                    + "id serial PRIMARY KEY,"
                    + "name varchar(100) NOT NULL,"
                    + "coordinates_x int NOT NULL,"
                    + "coordinates_y integer NOT NULL,"
                    + "creation_date TIMESTAMP NOT NULL,"
                    + "annual_turnover bigint,"
                    + "employees_count bigint NOT NULL,"
                    + "organization_type varchar(100) NOT NULL,"
                    + "postal_address_street varchar(100) NOT NULL,"
                    + "postal_address_zipcode varchar(100) NOT NULL,"
                    + "owner_username varchar (100) NOT NULL)");
        }
    }

    @Override
    public Organization mapRowToObject(ResultSet resultSet) throws SQLException {
        final Organization organization = new Organization(
                resultSet.getString("name"),
                new Coordinates(
                        resultSet.getInt("coordinates_x"),
                        resultSet.getInt("coordinates_y")
                ),
                resultSet.getLong("annual_turnover"),
                resultSet.getLong("employees_count"),
                OrganizationType.valueOf(resultSet.getString("organization_type")),
                new Address(
                        resultSet.getString("postal_address_street"),
                        resultSet.getString("postal_address_zipcode")
                ),
                resultSet.getTimestamp("creation_date").toLocalDateTime().toLocalDate(),
                resultSet.getString("owner_username")
        );

        organization.setId(resultSet.getInt("id"));

        return organization;
    }

    @Override
    public int add(Organization element) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO organizations VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id"
        )) {
            makePreparedStatementOfOrganization(preparedStatement, element);
            try (
                    ResultSet resultSet = preparedStatement.executeQuery()
            ) {
                resultSet.next();
                return resultSet.getInt("id");
            }
        }
    }

    private void makePreparedStatementOfOrganization(PreparedStatement preparedStatement, Organization organization) throws SQLException {
        preparedStatement.setString(PARAMETER_NAME, organization.getName());
        preparedStatement.setInt(PARAMETER_COORDINATES_X, organization.getCoordinates().getX());
        preparedStatement.setInt(PARAMETER_COORDINATES_Y, organization.getCoordinates().getY());
        preparedStatement.setTimestamp(PARAMETER_TIMESTAMP, Timestamp.valueOf(organization.getCreationDate().atStartOfDay()));
        preparedStatement.setLong(PARAMETER_ANNUAL_TURNOVER, organization.getAnnualTurnover());
        if (organization.getEmployeesCount() != null) {
            preparedStatement.setLong(PARAMETER_EMPLOYEES_COUNT, organization.getEmployeesCount());
        } else {
            preparedStatement.setNull(PARAMETER_EMPLOYEES_COUNT, Types.BIGINT);
        }
        preparedStatement.setString(PARAMETER_ORGANIZATION_TYPE, organization.getOrganizationType().toString());
        preparedStatement.setString(PARAMETER_POSTAL_ADDRESS_STREET, organization.getPostalAddress().getStreet());
        preparedStatement.setString(PARAMETER_POSTAL_ADDRESS_ZIPCODE, organization.getPostalAddress().getZipCode());
        preparedStatement.setString(PARAMETER_OWNER, organization.getOwner());
    }

    @Override
    public PriorityQueue<Organization> getCollection() throws SQLException {
        final PriorityQueue<Organization> newCollection = new PriorityQueue<>();

        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM organizations")
        ) {
            while (resultSet.next()) {
                Organization organization = mapRowToObject(resultSet);
                newCollection.add(organization);
            }

        }

        return newCollection;
    }

    public void clearOwnedData(String username) throws SQLException {

        String query = "DELETE FROM organizations WHERE owner_username=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.execute();
        }

    }

    public void removeById(int id) throws SQLException {
        try (
                Statement statement = connection.createStatement()
        ) {
            statement.execute("DELETE FROM organizations WHERE id=" + id);

        }
    }

    public void updateById(int id, Organization organization) throws SQLException {

        String query = "UPDATE organizations SET "
                + "name=?"
                + ",coordinates_x=?"
                + ",coordinates_y=?"
                + ",creation_date=?"
                + ",annual_turnover=?"
                + ",employees_count=?"
                + ",organization_type=?"
                + ",postal_address_street=?"
                + ",postal_address_zipcode=?"
                + ",owner_username=? "
                + "WHERE id =" + id;

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            makePreparedStatementOfOrganization(preparedStatement, organization);
            preparedStatement.execute();
        }


    }

}

