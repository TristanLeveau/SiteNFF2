package pojos;


public class Soiree {

	private Integer id;
	private String name;
	private String resume;
	private Ville ville;
	private String dateSoiree;

	public Soiree(Integer id, String name, String resume, Ville ville, String dateSoiree) {
		super();
		this.id = id;
		this.name = name;
		this.resume = resume;
		this.ville = ville;
		this.dateSoiree = dateSoiree;

	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getResume() {
		return resume;
	}
	public void setResume(String summary) {
		this.resume = summary;
	}
	public Ville getVille() { return ville; }
	public void setVille(Ville ville) { this.ville = ville; }
	public String getDateSoiree() { return dateSoiree; }
	public void setDateSoiree(String date) { this.dateSoiree = date; }
}
