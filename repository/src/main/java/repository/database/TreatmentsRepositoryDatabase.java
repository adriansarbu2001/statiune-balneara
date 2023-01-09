package repository.database;

import model.Treatment;
import repository.TreatmentsRepository;
import repository.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TreatmentsRepositoryDatabase implements TreatmentsRepository {
    private JdbcUtils dbUtils;

    /**
     * Constructor for repository using Spring beans
     * @param jdbcProps properties for database connection
     */
    public TreatmentsRepositoryDatabase(Properties jdbcProps) {
        dbUtils = new JdbcUtils(jdbcProps);
    }

    @Override
    public Treatment add(Treatment e) {
        return null;
    }

    @Override
    public Treatment delete(Treatment e) {
        return null;
    }

    @Override
    public Treatment find(int id) throws Exception {
        Connection con = dbUtils.getConnection();
        Treatment treatment = null;
        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM Tratamente WHERE idt = ?");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String name = result.getString("nume");
                int cost = result.getInt("cost");
                int duration_minutes = result.getInt("durata_minute");
                treatment = new Treatment(id, name, cost, duration_minutes);
            }
            result.close();
        } catch (SQLException ex) {
            throw new Exception("Error finding treatment!");
        }
        return treatment;
    }

    @Override
    public Treatment update(Treatment e) {
        return null;
    }

    @Override
    public List<Treatment> getAll() throws Exception {
        Connection con = dbUtils.getConnection();
        List<Treatment> treatments = new ArrayList<>();
        try {
            PreparedStatement statement = con.prepareStatement
                    ("SELECT * FROM Tratamente");

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("nume");
                int cost = result.getInt("cost");
                int duration_minutes = result.getInt("durata_minute");

                Treatment treatment = new Treatment(id, name, cost, duration_minutes);
                treatments.add(treatment);
            }
            result.close();
        } catch (SQLException ex) {
            throw new Exception("Error getting all treatments!");
        }
        return treatments;
    }

    @Override
    public int size() {
        return -1;
    }
}
