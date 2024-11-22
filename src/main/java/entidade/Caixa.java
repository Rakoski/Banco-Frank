package entidade;

public class Caixa {
    private String item;

    private void colocar(String item) {
        this.item = item;
    }

    public String pegar() {
        return this.item;
    }
}
