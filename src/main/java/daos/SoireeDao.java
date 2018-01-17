package daos;

import exceptions.PayeTaSoireeRuntimeException;
import pojos.Soiree;
import pojos.Ville;

import javax.servlet.http.Part;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SoireeDao {

	public List<Soiree> listSoirees() {
		List<Soiree> soirees = new ArrayList<>();

		try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM soiree WHERE supprime=false ORDER BY name")) {
			while (resultSet.next()) {
				soirees.add(
						new Soiree(
								resultSet.getInt("id"),
								resultSet.getString("name"),
								resultSet.getString("summary"),
								Ville.valueOf(resultSet.getString("ville")),
								resultSet.getString("dateSoiree")));


			}
		} catch (SQLException e) {
			throw new PayeTaSoireeRuntimeException("Erreur lors de la récupération des soirées", e);
		}

		return soirees;
	}

	public List<Soiree> listSoireesVille(Ville ville) {
		List<Soiree> soirees = new ArrayList<>();
		try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
			 PreparedStatement statement = connection.prepareStatement("SELECT * FROM soiree WHERE ville = ? AND supprime=false ORDER BY name ")){
			statement.setString(1,ville.name());
			try (ResultSet resultSet = statement.executeQuery()){
				while (resultSet.next()){
					soirees.add(
							new Soiree(
									resultSet.getInt("id"),
									resultSet.getString("name"),
									resultSet.getString("summary"),
									Ville.valueOf(resultSet.getString("ville")),
									resultSet.getString("dateSoiree")
							)
					);



				}
			}
		}catch (SQLException e){
			 	throw new PayeTaSoireeRuntimeException("Erreur lors de l'accès a la base de données", e);
		}
		return soirees;
	}

	public Soiree getSoiree(Integer id) {
		try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM soiree WHERE id = ? AND supprime=false")) {
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					return new Soiree(
							resultSet.getInt("id"),
							resultSet.getString("name"),
							resultSet.getString("summary"),
							Ville.valueOf(resultSet.getString("ville")),
							resultSet.getString("dateSoiree"));
				}
			}
		} catch (SQLException e) {
			throw new PayeTaSoireeRuntimeException("Erreur lors de la récupération des soirées", e);
		}
		return null;
	}
	
	public void addSoiree(Soiree newSoiree, Path imagePath) {
		try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
				PreparedStatement statement = connection.prepareStatement("INSERT INTO soiree(name, summary, ville,dateSoiree, image) VALUES (?, ?,?,?,?)")) {
			statement.setString(1, newSoiree.getName());
			statement.setString(2, newSoiree.getResume());
			statement.setString(3,newSoiree.getVille().name());
			statement.setString(4, newSoiree.getDateSoiree());
			if (imagePath!=null) {
				statement.setString(5, imagePath.toString());
			} else {
				statement.setNull(5, Types.VARCHAR);
			}
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new PayeTaSoireeRuntimeException("Erreur lors de la récupération des soirées", e);
		}
	}

	public Path getImagePath (Integer id) {
		try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT image FROM soiree WHERE id = ? AND supprime=false")){
				statement.setInt(1, id);
				try (ResultSet resultSet = statement.executeQuery()){
					if (resultSet.next()){
						String imagePath= resultSet.getString("image");
						if (imagePath!=null){
							return Paths.get(imagePath);
						}
					}
				}
			} catch (SQLException e){
				throw new PayeTaSoireeRuntimeException("Erreur lors de la réucpération de l'image", e);
		}
		return null;
	}

	public void supprimerSoiree(Integer id) {
		try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
			 PreparedStatement statement = connection.prepareStatement("UPDATE soiree SET supprime=true WHERE id = ?")) {
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateSoiree (Soiree newSoiree, Part imagePath) {
		try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
			 PreparedStatement statement = connection.prepareStatement("UPDATE soiree SET name=?, summary=?, ville=?,dateSoiree=?, image=? WHERE id=?")) {
			statement.setString(1, newSoiree.getName());
			statement.setString(2, newSoiree.getResume());
			statement.setString(3,newSoiree.getVille().name());
			statement.setString(4, newSoiree.getDateSoiree());
			if (imagePath!=null) {
				statement.setString(5, imagePath.toString());
			} else {
				statement.setNull(5, Types.VARCHAR);
			}
			statement.setInt(6,newSoiree.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new PayeTaSoireeRuntimeException("Erreur lors de la récupération des soirées", e);
		}
	}
}
