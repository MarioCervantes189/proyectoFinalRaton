public enum Estado {
    NOVISITADO ("NOVISITADO"),
    VISITADO("VISITADO"),
    CAMINO("CAMINO"),
    MURO("MURO"),
    INICIO("INICIO"),
    ESTADOACTUAL("ESTADOACTUAL"),;

    private String estado;

    private Estado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }
}
