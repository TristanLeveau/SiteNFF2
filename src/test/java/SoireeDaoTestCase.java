import daos.DataSourceProvider;
import daos.SoireeDao;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import pojos.Soiree;
import pojos.Ville;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SoireeDaoTestCase extends AbstractDaoTestCase {

    private SoireeDao soireeDao = new SoireeDao();

    @Override
    public void insertDataSet(Statement statement) throws Exception {
        statement.executeUpdate("INSERT INTO soiree(id, name, summary, ville, supprime, image) VALUES(1, 'Soiree 1', 'Resumé 1', 'LI', 0, null)");
        statement.executeUpdate("INSERT INTO soiree(id, name, summary, ville, supprime, image) VALUES(2, 'Soiree 2', 'Resumé 2', 'PR', 0, 'c:/path/to/image.jpg')");
        statement.executeUpdate("INSERT INTO soiree(id, name, summary, ville, supprime, image) VALUES(3, 'Soiree 3', 'Resumé 3', 'NY', 1, null)");

    }
    @Test
    public void shouldListSoirees() throws Exception {
        // WHEN
        List<Soiree> soirees = soireeDao.listSoirees();
        // THEN
        Assertions.assertThat(soirees).hasSize(2);
        Assertions.assertThat(soirees).extracting("id", "name", "resume", "ville").containsOnly(
                Assertions.tuple(1, "Soiree 1", "Resumé 1", Ville.LI),
                Assertions.tuple(2, "Soiree 2", "Resumé 2", Ville.PR)

        );
    }
    @Test
    public void shouldListSoireesVille() throws Exception {
        // WHEN
        List<Soiree> soirees = soireeDao.listSoireesVille(Ville.LI);
        // THEN
        Assertions.assertThat(soirees).hasSize(1);
        Assertions.assertThat(soirees).extracting("id", "name", "resume", "ville").containsOnly(
                Assertions.tuple(1, "Soiree 1", "Resumé 1", Ville.LI)
        );
    }
    @Test
    public void shouldGetSoiree() throws Exception {
        // WHEN
        Soiree soiree = soireeDao.getSoiree(1);
        // THEN
        Assertions.assertThat(soiree).isNotNull();
        Assertions.assertThat(soiree.getId()).isEqualTo(1);
        Assertions.assertThat(soiree.getName()).isEqualTo("Soiree 1");
        Assertions.assertThat(soiree.getResume()).isEqualTo("Resumé 1");
        Assertions.assertThat(soiree.getVille()).isEqualTo(Ville.LI);
    }

    @Test
    public void shouldNotGetDeletedSoiree() {
        // WHEN
        Soiree soiree = soireeDao.getSoiree(3);
        // THEN
        Assertions.assertThat(soiree).isNull();
    }

    @Test
    public void shouldGetPicturePath() {
        // WHEN
        Path imagePath = soireeDao.getImagePath(2);
        // THEN
        Assertions.assertThat(imagePath).isNotNull();
        Assertions.assertThat(imagePath).isEqualTo(Paths.get("c:/path/to/image.jpg"));
    }

    @Test
    public void shouldNotGetPicturePathIfNonExistant() {
        // WHEN
        Path imagePath = soireeDao.getImagePath(1);
        // THEN
        Assertions.assertThat(imagePath).isNull();
    }

    @Test
    public void shouldAddSoiree() throws Exception {
        // GIVEN
        Soiree newSoiree = new Soiree(null, "My new soiree", "Summary for my new soiree", Ville.LI, "11/11/2018");
        // WHEN
        soireeDao.addSoiree(newSoiree, Paths.get("C:/test/to/image.jpg"));
        // THEN
        try(Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM soiree WHERE name='My new soiree'")){
            Assertions.assertThat(resultSet.next()).isTrue();
            Assertions.assertThat(resultSet.getInt("id")).isNotNull();
            Assertions.assertThat(resultSet.getString("name")).isEqualTo("My new soiree");
            Assertions.assertThat(resultSet.getString("summary")).isEqualTo("Summary for my new soiree");
            Assertions.assertThat(resultSet.getString("ville")).isEqualTo("LI");
            Assertions.assertThat(resultSet.getString("image")).isEqualTo("C:/test/to/image.jpg");
            Assertions.assertThat(resultSet.next()).isFalse();
        }
    }

    @Test
    public void shouldDeleteSoiree() throws SQLException {
        // WHEN
        soireeDao.supprimerSoiree(2);
        // THEN
        try(Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM soiree WHERE id=2")){
            Assertions.assertThat(resultSet.next()).isTrue();
            Assertions.assertThat(resultSet.getBoolean("supprime")).isTrue();
            Assertions.assertThat(resultSet.next()).isFalse();
        }
    }
}
