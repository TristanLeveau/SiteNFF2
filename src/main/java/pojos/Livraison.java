package pojos;


public class Livraison {

	private Integer id;
	private String date;
	private String contenu;
	private Semestre semestre;

	public Livraison(Integer id, String date, String contenu, Semestre semestre) {
		super();
		this.id = id;
		this.date = date;
		this.contenu = contenu;
		this.semestre = semestre;

	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getContenu() {
		return contenu;
	}
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
	public Semestre getSemestre() { return semestre; }
	public void setSemestre(Semestre semestre) { this.semestre = semestre; }
 }

