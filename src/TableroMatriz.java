import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

public class TableroMatriz extends JFrame {
    private int filas = 8;
    private int columnas = 8;
    private Casilla[][] tablero;

    public TableroMatriz(Casilla[][] tablero) {
        this.tablero = tablero;
        this.filas = tablero.length;
        this.columnas = tablero[0].length;
    }

    public void mapaCoordenadas(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tablero");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(filas, columnas));

        Color colorMuro = Color.BLACK;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Color color = Color.WHITE;
                String coordenada = "(" + j + ", " + i + ")";
                switch(tablero[i][j].getEstado()){
                    case "MURO":
                        color = colorMuro;
                        coordenada = "";
                        break;
                    default:
                        break;                   
                }
                CasillaPanel cuadro = new CasillaPanel(coordenada);
                cuadro.setBackground(color);

                panel.add(cuadro);
            }
        }

        add(panel);

        Font font = new Font("Arial", Font.BOLD, 20);
        setFont(font);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void initComponents(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tablero");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(filas, columnas));

        Color colorMuro = Color.BLACK;
        Color colorInicio = Color.GREEN;
        Color colorFinal = Color.RED;
        Color colorCamino = Color.YELLOW;
        Color colorVisitado = Color.BLUE;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Color color = Color.WHITE;
                String coordenada = "";
                switch(tablero[i][j].getEstado()){
                    case "MURO":
                        color = colorMuro;
                        break;
                    case "INICIO":
                        color = colorInicio;
                        coordenada = "(" + i + ", " + j + ")";
                        break;
                    case "CAMINO":
                        color = colorCamino;
                        break;
                    case "VISITADO":
                        color = colorVisitado;
                        break;
                    default:
                        break;                   
                }
                if(tablero[i][j].esMeta()){
                    color = Color.RED;
                    coordenada = "(" + i + ", " + j + ")";
                }

                CasillaPanel cuadro = new CasillaPanel(coordenada);
                cuadro.setBackground(color);

                panel.add(cuadro);
            }
        }

        add(panel);

        Font font = new Font("Arial", Font.BOLD, 20);
        setFont(font);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private static class CasillaPanel extends JPanel{
        private String valor;

        public CasillaPanel(String valor){
            this.valor = valor;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawString(String.valueOf(valor), getWidth()/5, getHeight()/2);
        }
    }
}
