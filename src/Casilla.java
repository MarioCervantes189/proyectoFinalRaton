public class Casilla {
    private int x;
    private int y;
    private Estado estado = Estado.NOVISITADO;
    private Casilla padre = null;
    private double distanciaFinal;
    private boolean esMeta = false;

    public Casilla(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean esMeta() {
        return esMeta;
    }

    public void setEsMeta(boolean esMeta) {
        this.esMeta = esMeta;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado.toString();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPadre(Casilla padre) {
        this.padre = padre;
    }

    public Casilla getPadre() {
        return padre;
    }

    public void setDistanciaFinal(double distanciaFinal) {
        this.distanciaFinal = Math.abs(distanciaFinal);
    }

    public double getDistanciaFinal() {
        return distanciaFinal;
    }

    public String toString() {
        return "Casilla(" + x + ", " + y + " )\n";
    }
}
