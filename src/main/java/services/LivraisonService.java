package services;

import daos.ParticipantDao;
import daos.LivraisonDao;
import pojos.Livraison;
import pojos.Participant;
import pojos.Semestre;

import javax.servlet.http.Part;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LivraisonService {


	private static final String CHEMIN_IMAGE = "/Users/tristanleveau/Desktop/images";

	private LivraisonDao livraisonDao = new LivraisonDao();
	private ParticipantDao participantDao = new ParticipantDao();
	private static class LivraisonServiceHolder {
		private static LivraisonService instance = new LivraisonService();
	}
	
	public static LivraisonService getInstance() {
		return LivraisonServiceHolder.instance;
	}

	private LivraisonService() { }
	
	public List<Livraison> listAllLivraisons(Semestre semestreFilter) {
		if (semestreFilter == null){
			return livraisonDao.listLivraisons();
		}else {
			return livraisonDao.listLivraisonsVille(semestreFilter);

		}
	}
	
	public Livraison getLivraison(Integer id) {
		if(id == null) {
			throw new IllegalArgumentException("L'id de la livraison doit être renseigné.");
		}
		return livraisonDao.getLivraison(id);
	}
	public void addLivraison(Livraison newLivraison, Part image) throws IOException {
		if(newLivraison == null){
			throw new IllegalArgumentException("Une livraison doit être renseignée.");
		}
		if(newLivraison.getDate() == null || "".equals(newLivraison.getDate())) {
			throw new IllegalArgumentException("Une date doit être renseignée.");
		}
		if (newLivraison.getSemestre()==null|| "".equals(newLivraison.getSemestre())){
			throw new IllegalArgumentException("Un semestre doit être spécifié");
		}
		Path imagePath = null;
		if (image !=null) {
			//String filename = UUID.randomUUID().toString().substring(0,8) + "-" + image.getSubmittedFileName();
			//imagePath = Paths.get(CHEMIN_IMAGE, filename);
			try{
				Files.copy(image.getInputStream(),imagePath);
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}
		livraisonDao.addLivraison(newLivraison, imagePath);
	}

	public Path getLivraisonImage(Integer id){
		Path imagePath = livraisonDao.getImagePath(id);
		if (imagePath == null){
			try {
				imagePath = Paths.get(this.getClass().getClassLoader().getResource("fruits-et-légumes1.jpg").toURI());
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		return imagePath;
	}

	public void supprimerLivraison(Integer id){
		livraisonDao.supprimerLivraison(id);
	}


	public void addParticipant(Participant participant,Integer idLivraison){
		if(participant.getNom() == null || "".equals(participant.getNom())) {
			throw new IllegalArgumentException("Un nom doit être renseigné.");
		}
		if (participant.getPrenom()==null|| "".equals(participant.getPrenom())){
			throw new IllegalArgumentException("Un prénom doit être spécifié");
		}
		if (participant.getEmail()==null|| "".equals(participant.getEmail())){
			throw new IllegalArgumentException("Un email doit être spécifié");
		}
		participantDao.addParticipant(participant,idLivraison);
	}

	public List<Participant> ListeParticipantsLivraison(Integer idLivraison){
		List participants = participantDao.ListeParticipantsLivraison(idLivraison);
		return participants;
	}

	public void updateLivraison(Livraison newLivraison, Part image, Integer id){
		livraisonDao.updateLivraison(newLivraison, image);
	}

}
