import java.util.Scanner;
import java.util.Stack;

public class App {

    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Bienvenido al sistema de busqueda de rutas");
        Tablero tablero = tableroMatriz();
        System.out.println(tablero);
        TableroMatriz tableroMatriz = new TableroMatriz(tablero.getTablero());
        tableroMatriz.mapaCoordenadas();
        if(tablero.isDosCasillasLibres()){
            while(!tablero.ExisteCasillaInicio()){
                try{
                    System.out.println("Ingrese la coordenada de la casilla de inicio");
                    System.out.println("Ingrese la coordenada x");
                    int x = sc.nextInt();
                    System.out.println("Ingrese la coordenada y");
                    int y = sc.nextInt();
                    tablero.setCasillaInicio(x, y);
                    tablero.getCasillaInicio().setDistanciaFinal(0);
                }catch(Exception e){
                    System.out.println("Coordenadas invalidas");
                }
            }
            while(!tablero.ExisteCasillaFinal()){
                try{
                    System.out.println("Ingrese la coordenada de la casilla de final");
                    System.out.println("Ingrese la coordenada x");
                    int x = sc.nextInt();
                    System.out.println("Ingrese la coordenada y");
                    int y = sc.nextInt();
                    tablero.setCasillaFinal(x, y);
                }catch(Exception e){
                    System.out.println("Coordenadas invalidas");
                }
            }
        }else{
            System.out.println("No hay dos casillas libres");
            return;
        }

        System.out.println(tablero);
        tableroMatriz.setVisible(false);
        MetodoDeBusqueda metodoDeBusqueda = new MetodoDeBusqueda(tablero);
        optionalSearchMethod(metodoDeBusqueda, tablero);
    }

    public static Tablero tableroMatriz(){
        int f = 0;
        int c = 0;
        int p = 0;
        while(true){
            try{
                System.out.println("Ingrese el numero de filas");
                f = sc.nextInt();
                System.out.println("Ingrese el numero de columnas");
                c = sc.nextInt();
                System.out.println("Ingrese el porcentaje de muro (0-100)");
                p = sc.nextInt();
                if(p < 1 || p > 100){
                    System.out.println("El porcentaje debe estar entre 0 y 100");
                }else{
                    break;
                }
            }catch(Exception e){
                System.out.println("Datos invalidos");
            }
        }
        return new Tablero(f, c, p);
    }

    public static void optionalSearchMethod(MetodoDeBusqueda metodoDeBusqueda, Tablero tablero){
        Stack<Casilla> F = new Stack<Casilla>();
        TableroMatriz tableroG = new TableroMatriz(tablero.getTablero());
        tableroG.initComponents();

        while(true){
            tableroG.setVisible(true);
            F.push(tablero.getCasillaInicio());
            System.out.println("Seleccione el metodo de busqueda");
            System.out.println("1. BFS");
            System.out.println("2. DFS");
            System.out.println("3. Greedy");
            System.out.println("4. A*");
            System.out.println("5. Salir");
            int opcion = sc.nextInt();

            try{
                metodoDeBusqueda.setSolucion(false);
                switch(opcion){
                    case 1:
                        metodoDeBusqueda.BFS(F);
                        F.clear();
                        metodoDeBusqueda.imprimeTableroEstrella();
                        tablero.resetTablero();
                        break;
                    case 2:
                        metodoDeBusqueda.DFS(F);
                        F.clear();
                        metodoDeBusqueda.imprimeTableroEstrella();
                        tablero.resetTablero();
                        break;
                    case 3:
                        metodoDeBusqueda.Greed(F);
                        F.clear();
                        metodoDeBusqueda.imprimeTableroEstrella();
                        tablero.resetTablero();
                        break;
                    case 4:
                        metodoDeBusqueda.Estrella(F);
                        F.clear();
                        metodoDeBusqueda.imprimeTableroEstrella();
                        tablero.resetTablero();
                        break;
                    case 5:
                        tableroG.setVisible(false);
                        return;
                    default:
                        System.out.println("Opcion invalida");
                        break;
                }
            }catch(Exception e){
                System.out.println("Ocurrio un error");
            }
        }
    }
}
