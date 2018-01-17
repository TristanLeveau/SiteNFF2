import daos.DataSourceProvider;
import org.junit.Before;

import java.sql.Connection;
import java.sql.Statement;

public abstract class AbstractDaoTestCase {

	@Before
	public void initDatabase() throws Exception {
		try(
	        Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
	        Statement statement = connection.createStatement()){
			statement.executeUpdate("DELETE FROM participant");
			statement.executeUpdate("DELETE FROM soiree");

	        this.insertDataSet(statement);
		}
	}
	
	public abstract void insertDataSet(Statement statement) throws Exception;
}
