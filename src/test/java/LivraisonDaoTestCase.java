import daos.DataSourceProvider;
import daos.LivraisonDao;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import pojos.Livraison;
import pojos.Semestre;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class LivraisonDaoTestCase extends AbstractDaoTestCase {

    private LivraisonDao livraisonDao = new LivraisonDao();

    @Override
    public void insertDataSet(Statement statement) throws Exception {
        statement.executeUpdate("INSERT INTO livraison(id, name, summary, ville, supprime, image) VALUES(1, Livraison, 'Resumé 1', 'LI', 0, null)");
        statement.executeUpdate("INSERT INTO livraison(id, name, summary, ville, supprime, image) VALUES(2, 'Soiree 2', 'Resumé 2', 'PR', 0, 'c:/path/to/image.jpg')");
        statement.executeUpdate("INSERT INTO livraison(id, name, summary, ville, supprime, image) VALUES(3, Livraison, 'Resumé 3', 'NY', 1, null)");

    }
    @Test
    public void shouldListSoirees() throws Exception {
        // WHEN
        List<Livraison> livraisons = livraisonDao.listLivraisons();
        // THEN
        Assertions.assertThat(livraisons).hasSize(2);
        Assertions.assertThat(livraisons).extracting("id", "name", "resume", "ville").containsOnly(
                Assertions.tuple(1, "SLivraison1", "Resumé 1", Semestre.S1),
                Assertions.tuple(2, "SLivraison2", "Resumé 2", Semestre.S2)

        );
    }
    @Test
    public void shouldListSoireesVille() throws Exception {
        // WHEN
        List<Livraison> livraisons = livraisonDao.listLivraisonsVille(Semestre.S1);
        // THEN
        Assertions.assertThat(livraisons).hasSize(1);
        Assertions.assertThat(livraisons).extracting("id", "name", "resume", "ville").containsOnly(
                Assertions.tuple(1, "SLivraison1", "Resumé 1", Semestre.S1)
        );
    }
    @Test
    public void shouldGetSoiree() throws Exception {
        // WHEN
        Livraison livraison = livraisonDao.getLivraison(1);
        // THEN
        Assertions.assertThat(livraison).isNotNull();
        Assertions.assertThat(livraison.getId()).isEqualTo(1);
        Assertions.assertThat(livraison.getDate()).isEqualTo("SLivraison1");
        Assertions.assertThat(livraison.getContenu()).isEqualTo("Resumé 1");
        Assertions.assertThat(livraison.getSemestre()).isEqualTo(Semestre.S1);
    }

    @Test
    public void shouldNotGetDeletedSoiree() {
        // WHEN
        Livraison livraison = livraisonDao.getLivraison(3);
        // THEN
        Assertions.assertThat(livraison).isNull();
    }

    @Test
    public void shouldGetPicturePath() {
        // WHEN
        Path imagePath = livraisonDao.getImagePath(2);
        // THEN
        Assertions.assertThat(imagePath).isNotNull();
        Assertions.assertThat(imagePath).isEqualTo(Paths.get("c:/path/to/image.jpg"));
    }

    @Test
    public void shouldNotGetPicturePathIfNonExistant() {
        // WHEN
        Path imagePath = livraisonDao.getImagePath(1);
        // THEN
        Assertions.assertThat(imagePath).isNull();
    }

    @Test
    public void shouldAddSoiree() throws Exception {
        // GIVEN
        Livraison newLivraison = new Livraison(null, "11/11/1111", "Test", Semestre.S1);
        // WHEN
        livraisonDao.addLivraison(newLivraison, Paths.get("C:/test/to/image.jpg"));
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
        livraisonDao.supprimerLivraison(2);
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
