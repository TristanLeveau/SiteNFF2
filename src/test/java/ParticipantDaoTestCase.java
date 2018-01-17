import daos.DataSourceProvider;
import daos.ParticipantDao;
import org.junit.Test;
import pojos.Participant;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class ParticipantDaoTestCase extends AbstractDaoTestCase {

    ParticipantDao participantDao = new ParticipantDao();

    @Override
    public void insertDataSet(Statement statement) throws Exception {
        statement.executeUpdate("INSERT INTO soiree(id, name, summary,ville,dateSoiree) VALUES(1, 'Soiree 1', 'Résumé 1','Lille','11/11/2018')");
        statement.executeUpdate("INSERT INTO soiree(id, name, summary,ville,dateSoiree) VALUES(2, 'Soiree 2', 'Résumé 2', 'Paris','11/11/2018')");
        statement.executeUpdate("INSERT INTO participant(id, nom, prenom, email, soiree) VALUES (1, 'Tristan', 'LEVEAU', 'tristan.leveau@hei.yncrea.fr', 1)");
        statement.executeUpdate("INSERT INTO participant(id, nom, prenom, email, soiree) VALUES (2, 'John', 'Jackson', 'John.Jackson@hei.yncrea.fr', 2)");
        statement.executeUpdate("INSERT INTO participant(id, nom, prenom, email, soiree) VALUES (3, 'Jack', 'Johnson', 'Jack.Johnson@hei.yncrea.fr', 1)");

    }

    @Test
    public void shouldListParticipantBySoiree() {
        // WHEN
        List<Participant> participants = participantDao.ListeParticipantsSoiree(1);
        // THEN
        assertThat(participants).hasSize(2);
        assertThat(participants).extracting("id", "nom", "prenom", "email").containsExactly(
                tuple(1, "Tristan","LEVEAU" , "tristan.leveau@hei.yncrea.fr"),
                tuple(3, "Jack", "Johnson", "Jack.Johnson@hei.yncrea.fr")
        );
    }

    @Test
    public void shouldAddParticipant() throws SQLException {
        // GIVEN
        Participant newParticipant = new Participant(null, "Jacques","Chirac", "jacques.chirac@elysee.com","chirac123");
        // WHEN
        participantDao.addParticipant(newParticipant, 1);
        // THEN
        try(Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM participant WHERE nom='Jacques'")){
            assertThat(resultSet.next()).isTrue();
            assertThat(resultSet.getInt("id")).isNotNull();
            assertThat(resultSet.getString("nom")).isEqualTo("Jacques");
            assertThat(resultSet.getString("prenom")).isEqualTo("Chirac");
            assertThat(resultSet.getString("email")).isEqualTo("jacques.chirac@elysee.com");
            assertThat(resultSet.getString("motDePasse")).isEqualTo("chirac123");
            assertThat(resultSet.next()).isFalse();
        }
    }

}
