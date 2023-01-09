package repository.database;

import model.Location;
import model.MaxPatient;
import repository.LocationsRepository;
import repository.MaxPatientsRepository;
import repository.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MaxPatientsRepositoryDatabase implements MaxPatientsRepository {
    private JdbcUtils dbUtils;

    /**
     * Constructor for repository using Spring beans
     * @param jdbcProps properties for database connection
     */
    public MaxPatientsRepositoryDatabase(Properties jdbcProps) {
        dbUtils = new JdbcUtils(jdbcProps);
    }

    @Override
    public MaxPatient add(MaxPatient e) {
        return null;
    }

    @Override
    public MaxPatient delete(MaxPatient e) {
        return null;
    }

    @Override
    public MaxPatient find(int id) throws Exception {
        return null;
    }
    @Override
    public MaxPatient findBy(int idl, int idt) throws Exception {
        Connection con = dbUtils.getConnection();
        MaxPatient maxPatient = null;
        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM MaximPacienti WHERE idl = ? AND idt = ?");
            statement.setInt(1, idl);
            statement.setInt(2, idt);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int max = result.getInt("max");
                maxPatient = new MaxPatient(idl, idt, max);
            }
            result.close();
        } catch (SQLException ex) {
            throw new Exception("Error finding max!");
        }
        return maxPatient;
    }

    @Override
    public MaxPatient update(MaxPatient e) {
        return null;
    }

    @Override
    public List<MaxPatient> getAll() throws Exception {
        return null;
    }

    @Override
    public int size() {
        return -1;
    }
}
