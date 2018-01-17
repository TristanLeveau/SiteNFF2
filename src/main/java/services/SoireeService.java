package services;

import daos.ParticipantDao;
import daos.SoireeDao;
import pojos.Participant;
import pojos.Soiree;
import pojos.Ville;

import javax.servlet.http.Part;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

public class SoireeService {


	private static final String CHEMIN_IMAGE = "/Users/tristanleveau/Desktop/images";

	private SoireeDao soireeDao = new SoireeDao();
	private ParticipantDao participantDao = new ParticipantDao();
	private static class SoireeServiceHolder {
		private static SoireeService instance = new SoireeService();
	}
	
	public static SoireeService getInstance() {
		return SoireeServiceHolder.instance;
	}

	private SoireeService() { }
	
	public List<Soiree> listAllSoirees(Ville villeFilter) {
		if (villeFilter == null){
			return soireeDao.listSoirees();
		}else {
			return soireeDao.listSoireesVille(villeFilter);

		}
	}
	
	public Soiree getSoiree(Integer id) {
		if(id == null) {
			throw new IllegalArgumentException("L'id de la soiree doit être renseigné.");
		}
		return soireeDao.getSoiree(id);
	}
	
	public void addSoiree(Soiree newSoiree, Part image) throws IOException {
		if(newSoiree == null){
			throw new IllegalArgumentException("Une soirée doit être renseignée.");
		}
		if(newSoiree.getName() == null || "".equals(newSoiree.getName())) {
			throw new IllegalArgumentException("Un nom doit être renseigné.");
		}
		if (newSoiree.getVille()==null|| "".equals(newSoiree.getVille())){
			throw new IllegalArgumentException("Une ville doit être spécifiée");
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
		soireeDao.addSoiree(newSoiree, imagePath);
	}

	public Path getSoireeImage(Integer id){
		Path imagePath = soireeDao.getImagePath(id);
		if (imagePath == null){
			try {
				imagePath = Paths.get(this.getClass().getClassLoader().getResource("Four-hands-holing-pints-of-beer.jpg").toURI());
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		return imagePath;
	}

	public void supprimerSoiree(Integer id){
		soireeDao.supprimerSoiree(id);
	}


	public void addParticipant(Participant participant,Integer idSoiree){
		if(participant.getNom() == null || "".equals(participant.getNom())) {
			throw new IllegalArgumentException("Un nom doit être renseigné.");
		}
		if (participant.getPrenom()==null|| "".equals(participant.getPrenom())){
			throw new IllegalArgumentException("Un prénom doit être spécifié");
		}
		if (participant.getEmail()==null|| "".equals(participant.getEmail())){
			throw new IllegalArgumentException("Un email doit être spécifié");
		}
		participantDao.addParticipant(participant,idSoiree);
	}

	public List<Participant> ListeParticipantsSoiree(Integer idSoiree){
		List participants = participantDao.ListeParticipantsSoiree(idSoiree);
		return participants;
	}

	public void updateSoiree (Soiree newSoiree, Part image, Integer id){
		soireeDao.updateSoiree(newSoiree, image);
	}

}
