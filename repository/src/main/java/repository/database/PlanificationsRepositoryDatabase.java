package repository.database;

import model.Planification;
import org.springframework.stereotype.Component;
import repository.PlanificationsRepository;
import repository.utils.JdbcUtils;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Database containing users
 */
@Component
public class PlanificationsRepositoryDatabase implements PlanificationsRepository {
    private JdbcUtils dbUtils;

    /**
     * Constructor for repository using Spring beans
     * @param jdbcProps properties for database connection
     */
    public PlanificationsRepositoryDatabase(Properties jdbcProps) {
        dbUtils = new JdbcUtils(jdbcProps);
    }

    @Override
    public Planification add(Planification e) throws Exception {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preSmt = con.prepareStatement(
            "insert into Planificari (nume, cnp, data, idl, idt, data_tratament, ora_tratament) values (?, ?, ?, ?, ?, ?, ?)"
        )) {
            preSmt.setString(1, e.getName());
            preSmt.setString(2, e.getCnp());
            preSmt.setDate(3, Date.valueOf(e.getDate()));
            preSmt.setInt(4, e.getIdl());
            preSmt.setInt(5, e.getIdt());
            preSmt.setDate(6, Date.valueOf(e.getTreatmentDate()));
            preSmt.setTime(7, Time.valueOf(e.getTreatmentTime()));
            int result = preSmt.executeUpdate();

            try (ResultSet generatedKeys = preSmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    e.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating planification failed, no ID obtained.");
                }
            }
            return e;
        } catch (SQLException ex) {
            throw new Exception("Error adding planification!");
        }
    }

    @Override
    public Planification delete(Planification e) {
        return null;
    }

    @Override
    public Planification find(int id) throws Exception {
        Connection con = dbUtils.getConnection();
        Planification planification = null;
        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM Planificari WHERE idpf = ?");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                // int id = result.getInt("idpf");
                String name = result.getString("nume");
                String cnp = result.getString("cnp");
                LocalDate date = result.getDate("data").toLocalDate();
                int idl = result.getInt("idl");
                int idt = result.getInt("idt");
                LocalDate treatmentDate = result.getDate("data_tratament").toLocalDate();
                LocalTime treatmentTime = result.getTime("ora_tratament").toLocalTime();
                planification = new Planification(id, name, cnp, date, idl, idt, treatmentDate, treatmentTime);
            }
            result.close();
        } catch (SQLException ex) {
            throw new Exception("Error finding planification!");
        }
        return planification;
    }

    @Override
    public Planification update(Planification e) {
        return null;
    }

    @Override
    public List<Planification> getAll() throws Exception {
        Connection con = dbUtils.getConnection();
        List<Planification> planifications = new ArrayList<>();
        try {
            PreparedStatement statement = con.prepareStatement
                    ("SELECT * FROM Plati");

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int id = result.getInt("idpf");
                String name = result.getString("nume");
                String cnp = result.getString("cnp");
                LocalDate date = result.getDate("data").toLocalDate();
                int idl = result.getInt("idl");
                int idt = result.getInt("idt");
                LocalDate treatmentDate = result.getDate("data_tratament").toLocalDate();
                LocalTime treatmentTime = result.getTime("ora_tratament").toLocalTime();
                Planification planification = new Planification(id, name, cnp, date, idl, idt, treatmentDate, treatmentTime);
                planifications.add(planification);
            }
            result.close();
        } catch (SQLException ex) {
            throw new Exception("Error getting all planifications!");
        }
        return planifications;
    }

    @Override
    public int size() {
        return -1;
    }
}
