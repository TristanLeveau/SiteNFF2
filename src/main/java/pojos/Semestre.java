package pojos;

public enum Semestre {
    S1("Semestre 1"),
    S2("Semestre 2");

    private String label;

    Semestre(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}