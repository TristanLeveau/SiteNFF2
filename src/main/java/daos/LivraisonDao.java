package daos;

import exceptions.NFFRuntimeException;
import pojos.Livraison;
import pojos.Semestre;

import javax.servlet.http.Part;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivraisonDao {

	public List<Livraison> listLivraisons() {
		List<Livraison> livraisons = new ArrayList<>();

		try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM Livraison WHERE supprime=false ORDER BY name")) {
			while (resultSet.next()) {
				livraisons.add(
						new Livraison(
								resultSet.getInt("id"),
								resultSet.getString("date"),
								resultSet.getString("contenu"),
								Semestre.valueOf(resultSet.getString("semestre"))
						));


			}
		} catch (SQLException e) {
			throw new NFFRuntimeException("Erreur lors de la récupération des livraisons", e);
		}

		return livraisons;
	}

	public List<Livraison> listLivraisonsVille(Semestre semestre) {
		List<Livraison> livraisons = new ArrayList<>();
		try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
			 PreparedStatement statement = connection.prepareStatement("SELECT * FROM soiree WHERE semestre = ? AND supprime=false ORDER BY name ")){
			statement.setString(1, semestre.name());
			try (ResultSet resultSet = statement.executeQuery()){
				while (resultSet.next()){
					livraisons.add(
							new Livraison(
									resultSet.getInt("id"),
									resultSet.getString("date"),
									resultSet.getString("contenu"),
									Semestre.valueOf(resultSet.getString("semestre"))
							)
					);



				}
			}
		}catch (SQLException e){
			 	throw new NFFRuntimeException("Erreur lors de l'accès a la base de données", e);
		}
		return livraisons;
	}

	public Livraison getLivraison(Integer id) {
		try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM livraison WHERE id = ? AND supprime=false")) {
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					return new Livraison(
							resultSet.getInt("id"),
							resultSet.getString("date"),
							resultSet.getString("contenu"),
							Semestre.valueOf(resultSet.getString("semestre"))
					);
				}
			}
		} catch (SQLException e) {
			throw new NFFRuntimeException("Erreur lors de la récupération des livraisons", e);
		}
		return null;
	}
	
	public void addLivraison(Livraison newLivraison, Path imagePath) {
		try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
				PreparedStatement statement = connection.prepareStatement("INSERT INTO livraison(date, contenu, semestre, image) VALUES (?, ?,?,?)")) {
			statement.setString(1, newLivraison.getDate());
			statement.setString(2, newLivraison.getContenu());
			statement.setString(3, newLivraison.getSemestre().name());
			if (imagePath!=null) {
				statement.setString(4, imagePath.toString());
			} else {
				statement.setNull(4, Types.VARCHAR);
			}
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new NFFRuntimeException("Erreur lors de la récupération des livraisons", e);
		}
	}

	public Path getImagePath (Integer id) {
		try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT image FROM livraison WHERE id = ? AND supprime=false")){
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
				throw new NFFRuntimeException("Erreur lors de la réucpération de l'image", e);
		}
		return null;
	}

	public void supprimerLivraison(Integer id) {
		try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
			 PreparedStatement statement = connection.prepareStatement("UPDATE livraison SET supprime=true WHERE id = ?")) {
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateLivraison (Livraison newLivraison, Part imagePath) {
		try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
			 PreparedStatement statement = connection.prepareStatement("UPDATE livraison SET date=?, contenu=?, semestre=? image=? WHERE id=?")) {
			statement.setString(1, newLivraison.getDate());
			statement.setString(2, newLivraison.getContenu());
			statement.setString(3, newLivraison.getSemestre().name());
			if (imagePath!=null) {
				statement.setString(5, imagePath.toString());
			} else {
				statement.setNull(5, Types.VARCHAR);
			}
			statement.setInt(6, newLivraison.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new NFFRuntimeException("Erreur lors de la récupération des livraisons", e);
		}
	}
}
