package repository.database;

import model.Location;
import repository.LocationsRepository;
import repository.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class LocationsRepositoryDatabase implements LocationsRepository {
    private JdbcUtils dbUtils;

    /**
     * Constructor for repository using Spring beans
     * @param jdbcProps properties for database connection
     */
    public LocationsRepositoryDatabase(Properties jdbcProps) {
        dbUtils = new JdbcUtils(jdbcProps);
    }

    @Override
    public Location add(Location e) {
        return null;
    }

    @Override
    public Location delete(Location e) {
        return null;
    }

    @Override
    public Location find(int id) throws Exception {
        Connection con = dbUtils.getConnection();
        Location location = null;
        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM Locatii WHERE idl = ?");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String name = result.getString("nume");
                location = new Location(id, name);
            }
            result.close();
        } catch (SQLException ex) {
            throw new Exception("Error finding location!");
        }
        return location;
    }
    //method to get a random location
    public Location findRandomLocation() throws Exception {
        Connection con = dbUtils.getConnection();
        Location location = null;
        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM Locatii ORDER BY RAND() LIMIT 1");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int id = result.getInt("idl");
                String name = result.getString("nume");
                location = new Location(id, name);
            }
            result.close();
        } catch (SQLException ex) {
            throw new Exception("Error finding location!");
        }
        return location;
    }

    @Override
    public Location update(Location e) {
        return null;
    }

    @Override
    public List<Location> getAll() throws Exception {
        Connection con = dbUtils.getConnection();
        List<Location> locations = new ArrayList<>();
        try {
            PreparedStatement statement = con.prepareStatement
                    ("SELECT * FROM Locatii");

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int id = result.getInt("idl");
                String name = result.getString("nume");

                Location location = new Location(id, name);
                locations.add(location);
            }
            result.close();
        } catch (SQLException ex) {
            throw new Exception("Error getting all locations!");
        }
        return locations;
    }

    @Override
    public int size() {
        return -1;
    }
}
