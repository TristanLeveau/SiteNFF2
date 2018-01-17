package pojos;

public enum Ville {
    PR("Paris"),
    LI("Lille"),
    NY("New-York");

    private String label;

    Ville(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}