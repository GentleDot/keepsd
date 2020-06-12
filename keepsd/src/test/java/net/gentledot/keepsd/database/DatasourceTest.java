package net.gentledot.keepsd.database;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DatasourceTest {

    @Autowired
    private DataSource ds;

    @Test
    void testConnection() {
        try (Connection con = ds.getConnection()) {
            System.out.println(con);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
