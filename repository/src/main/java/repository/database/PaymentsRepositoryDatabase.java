package repository.database;

import model.Payment;
import org.springframework.stereotype.Component;
import repository.PaymentsRepository;
import repository.utils.JdbcUtils;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Database containing users
 */
@Component
public class PaymentsRepositoryDatabase implements PaymentsRepository {
    private JdbcUtils dbUtils;

    /**
     * Constructor for repository using Spring beans
     * @param jdbcProps properties for database connection
     */
    public PaymentsRepositoryDatabase(Properties jdbcProps) {
        dbUtils = new JdbcUtils(jdbcProps);
    }

    @Override
    public Payment add(Payment e) throws Exception {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preSmt = con.prepareStatement(
            "insert into Plati (cnp, data, suma) values (?, ?, ?)"
        )) {

            preSmt.setString(1, e.getCnp());
            preSmt.setDate(2, Date.valueOf(e.getDate()));
            preSmt.setFloat(3, e.getSum());
            int result = preSmt.executeUpdate();

            try (ResultSet generatedKeys = preSmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    e.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating payment failed, no ID obtained.");
                }
            }
            return e;
        } catch (SQLException ex) {
            throw new Exception("Error adding payment!");
        }
    }

    @Override
    public Payment delete(Payment e) {
        return null;
    }

    @Override
    public Payment find(int id) throws Exception {
        Connection con = dbUtils.getConnection();
        Payment payment = null;
        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM Plati WHERE idp = ?");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String cnp = result.getString("cnp");
                LocalDate date = result.getDate("data").toLocalDate();
                int sum = result.getInt("suma");
                payment = new Payment(id, cnp, date, sum);
            }
            result.close();
        } catch (SQLException ex) {
            throw new Exception("Error finding payment!");
        }
        return payment;
    }

    @Override
    public Payment update(Payment e) {
        return null;
    }

    @Override
    public List<Payment> getAll() throws Exception {
        Connection con = dbUtils.getConnection();
        List<Payment> payments = new ArrayList<>();
        try {
            PreparedStatement statement = con.prepareStatement
                    ("SELECT * FROM Plati");

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int id = result.getInt("idp");
                String cnp = result.getString("cnp");
                LocalDate date = result.getDate("data").toLocalDate();
                int sum = result.getInt("suma");
                Payment payment = new Payment(id, cnp, date, sum);
                payments.add(payment);
            }
            result.close();
        } catch (SQLException ex) {
            throw new Exception("Error getting all payments!");
        }
        return payments;
    }

    @Override
    public int size() {
        return -1;
    }
}
