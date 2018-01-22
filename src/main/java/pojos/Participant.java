package pojos;

public class Participant {

    private Integer id;
    private String nom;
    private String prenom;
    private String e-mail;
    private String motDePasse;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) { this.id = id; }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMotDePasse() {
        return motDePasse;
    }
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public Participant(Integer id, String nom, String prenom, String email, String motDePasse){
        super();
        this.email=email;
        this.id=id;
        this.nom=nom;
        this.prenom=prenom;
        this.motDePasse=motDePasse;
    }
}
