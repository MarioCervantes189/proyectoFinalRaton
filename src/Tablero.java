public class Tablero {
    private Casilla[][] tablero;
    private int filas;
    private int columnas;

    private Casilla casillaInicio;
    private Casilla casillaFinal;

    public Tablero(int filas, int columnas, int porcentajeDeMuros) {
        this.filas = filas;
        this.columnas = columnas;
        this.tablero = new Casilla[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                tablero[i][j] = new Casilla(i, j);
            }
        }

        generarMuros(porcentajeDeMuros);
    }

    public void generarMuros(int porcentajeDeMuros) {
        int cantidadDeMuros = (int) ((filas * columnas) * (porcentajeDeMuros / 100.0));
        int murosGenerados = 0;
        while (murosGenerados < cantidadDeMuros) {
            int x = (int) (Math.random() * filas);
            int y = (int) (Math.random() * columnas);
            if (tablero[x][y].getEstado().equals(Estado.NOVISITADO.toString())) {
                tablero[x][y].setEstado(Estado.MURO);
                murosGenerados++;
            }
        }
    }

    public void setCasillaInicio(int x, int y) {
        try{
            if (tablero[x][y].esMeta()){
                System.out.println("La casilla de inicio no puede ser una meta");
                return;
            }else if(tablero[x][y].getEstado().equals(Estado.MURO.toString())){
                System.out.println("La casilla de inicio no puede ser un muro");
                return;
            }else{
                this.casillaInicio = tablero[x][y];
                this.casillaInicio.setEstado(Estado.INICIO);
            }
        }catch (Exception e){
            System.out.println("La casilla de inicio no puede estar fuera del tablero");
        }
    }

    public void setCasillaFinal(int x, int y) {
        try{
            if(tablero[x][y].getEstado().equals(Estado.INICIO.toString())){
                System.out.println("La casilla de final no puede ser la misma que la de inicio");
                return;
        }else if (tablero[x][y].getEstado().equals(Estado.MURO.toString())){
            System.out.println("La casilla de final no puede ser un muro");
            return;
        }else{
            this.casillaFinal = tablero[x][y];
            this.casillaFinal.setEsMeta(true);
        }
    }catch (Exception e){
        System.out.println("La casilla de final no puede estar fuera del tablero");
    }
  }

    public boolean ExisteCasillaFinal(){
        return casillaFinal != null;
    }

    public boolean ExisteCasillaInicio(){
        return casillaInicio != null;
    }

    public boolean isDosCasillasLibres(){
        int contador = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (tablero[i][j].getEstado().equals(Estado.NOVISITADO.toString())){
                    contador++;
                    if(contador == 2){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void resetTablero(){
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                tablero[i][j].setPadre(null);
                if(tablero[i][j].getEstado().equals(Estado.CAMINO.toString())||
                tablero[i][j].getEstado().equals(Estado.VISITADO.toString())){
                    tablero[i][j].setEstado(Estado.NOVISITADO);   
                }
                tablero[i][j].setDistanciaFinal(0);
            }
        }
        tablero[casillaInicio.getX()][casillaInicio.getY()].setEstado(Estado.INICIO);
        tablero[casillaInicio.getX()][casillaInicio.getY()].setDistanciaFinal(0);
    }

    public boolean EsCasillaFinal(Casilla casilla){
        return casilla.getX() == casillaFinal.getX() && casilla.getY() == casillaFinal.getY();
    }
    public boolean EsCasillaInicio(Casilla casilla){
        return casilla.getX() == casillaInicio.getX() && casilla.getY() == casillaInicio.getY();
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public Casilla getCasilla(int x, int y) {
        return tablero[x][y];
    }

    public Casilla getCasillaInicio() {
        return casillaInicio;
    }

    public Casilla getCasillaFinal() {
        return casillaFinal;
    }

    public Casilla[][] getTablero() {
        return tablero;
    }

    public double[][] getMatrizDeDistancias(){
        double[][] matriz = new double[this.filas][this.columnas];
        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < this.columnas; j++) {
                if(this.tablero[i][j].getEstado().equals(Estado.MURO.toString())){
                    matriz[i][j] = -1;
                }else{
                    matriz[i][j] = this.tablero[i][j].getDistanciaFinal();
                }
            }
        }
        return matriz;
    }

        public String toString() {
            String tableroString = "   ";
            for (int i = 0; i < columnas; i++) {
                if (i < 10) {
                    tableroString += "0"+i + " ";
                } else {
                    tableroString += i + " ";
                }
            }
            tableroString += "\n"+"";

            for(int i = 0; i < columnas; i++){
                tableroString += "___";
            }
            tableroString += "\n";

            for (int i = 0; i < filas; i++) {
                if (i < 10) {
                    tableroString += " "+i + "| ";
                } else {
                    tableroString += i + "| ";
                }
                for (int j = 0; j < columnas; j++) {
                    switch (tablero[i][j].getEstado()) {
                        case "NOVISITADO":
                            if(tablero[i][j].esMeta()){
                                tableroString += "M ";
                                casillaFinal = tablero[i][j];
                                break;    
                            }
                            tableroString += "N  ";
                            break;
                        case "VISITADO":
                            tableroString += "V  ";
                            break;
                        case "MURO":
                            tableroString += "X  ";
                            break;
                        case "RECORRIDO":
                            tableroString += "R  ";
                            break;
                        case "INICIO":
                            tableroString += "I  ";
                            break;
                        case "ESTADOACTUAL":
                            tableroString += "E  ";
                            tablero[i][j].setEstado(Estado.VISITADO);
                            break;
                        case "CAMINO":
                            tableroString += "C  ";
                            break;
                    }
                    tableroString += "";
                }
                tableroString += "\n";
            }
            return tableroString;
        }
} 