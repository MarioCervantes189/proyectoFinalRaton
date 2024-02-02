import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Stack;

public class MetodoDeBusqueda {
    private Tablero tablero;
    private Casilla EA;
    private boolean solucion = false;

    public MetodoDeBusqueda(Tablero tablero) {
        this.tablero = tablero;
    }

    public void BFS(Stack<Casilla> F){
        if(F.isEmpty()){
            System.out.println("No hay solucion");
            return;
        }else{
            this.EA = F.pop();
            if(GoalTest(EA)){
                System.out.println("Solucion encontrada");
                solucion = true;
                F.clear();
                return;
            }else{
                Stack<Casilla> OS = Expand(EA);
                removeRepeate(OS,F);
                F = insert(F,OS);
                BFS(F);
                
            }
        }
    }
    public void DFS(Stack<Casilla> F){
        if(F.isEmpty()){
            System.out.println("No hay solucion");
            return;
        }else{
            this.EA = F.pop();
            if(GoalTest(EA)){
                System.out.println("Solucion encontrada");
                solucion = true;
                F.clear();
                return;
            }else{
                Stack<Casilla> OS = Expand(EA);
                removeRepeate(OS,F);
                F = insert(OS,F);
                DFS(F);
                
            }
        }
    }
    public void Greed(Stack<Casilla> F){
        if(F.isEmpty()){
            System.out.println("No hay solucion");
            return;
        }else{
            this.EA = F.pop();
            if(GoalTest(EA)){
                System.out.println("Solucion encontrada");
                solucion = true;
                F.clear();
                return;
            }else{
                Stack<Casilla> OS = Expand(EA);
                OS = EvaluateGreed(OS);
                if(!OS.isEmpty()){
                    OS = sort(OS);
                    F.push(OS.pop());
                }
                Greed(F);
                
            }
        }
    }

    public void Estrella(Stack<Casilla> F){
        if(F.isEmpty()){
            System.out.println("No hay solucion");
            return;
        }else{
            this.EA = F.pop();
            if(GoalTest(EA)){
                System.out.println("Solucion encontrada");
                solucion = true;
                return;
            }else{
                Stack<Casilla> OS = Expand(EA);
                 OS = EvaluateH(OS);
                F = insert(F,OS);
                F = sort(F);
                Estrella(F);
                
            }
        }
    }

    private Stack<Casilla> EvaluateH(Stack<Casilla> OS){
        for(int i=0; i<OS.size(); i++){
            Casilla casillaEvaluada = OS.get(i);

            double recorrido = getDistanciaLineal(this.tablero.getCasillaInicio(), casillaEvaluada);
            double valorH = getDistanciaRecorrida(casillaEvaluada, this.tablero.getCasillaFinal());
            

            casillaEvaluada.setDistanciaFinal((recorrido+ valorH));
            OS.set(i, casillaEvaluada);
        }
        return OS;
    }

    public void imprimeTableroEstrella(){
        TableroMatriz tableroG = new TableroMatriz(this.tablero.getTablero());
        if(solucion){
            this.retroceder(this.tablero.getCasillaFinal());
        }
        tableroG.initComponents();
        tableroG.setVisible(true);
        Scanner sc = new Scanner (System.in);
        sc.nextLine();
        tableroG.setVisible(false);
    }

     private Stack<Casilla> sort(Stack<Casilla> OS) {

        ArrayList<Casilla> Ordenar = new ArrayList<Casilla> ();
        while(!OS.isEmpty()){
            Ordenar.add(OS.pop());
        }

        // Ordenar el ArrayList utilizando un Comparator
        Collections.sort(Ordenar, new Comparator<Casilla>() {
            @Override
            public int compare(Casilla casilla2, Casilla casilla1) {
                // Comparar las distancias de menor a mayor
                return Double.compare(casilla1.getDistanciaFinal(), casilla2.getDistanciaFinal());
            }
        });

        for(Casilla casilla : Ordenar){
            OS.push(casilla);
        }
     
        return OS;
    }

    private Stack<Casilla> EvaluateGreed(Stack<Casilla> OS){
        for(int i=0; i<OS.size(); i++){
            Casilla casillaEvaluada = OS.get(i);
            
            double valorH = getDistanciaLineal(casillaEvaluada, this.tablero.getCasillaFinal());

            casillaEvaluada.setDistanciaFinal(valorH);
            OS.set(i, casillaEvaluada);
        }
        return OS;
    }

    private  Stack<Casilla> insert(Stack<Casilla> F, Stack<Casilla> OS){
        while(!OS.isEmpty()){
            Casilla ciudadActual = OS.pop();
            F.add(0,ciudadActual);
            }
        return F;
    }

    private  Stack<Casilla> Expand(Casilla EA){
        Stack<Casilla> OS = new Stack<Casilla>();
        for(int i=-1;i<=1;i+=2){
            try{
                if(this.tablero.getCasilla(EA.getX()+i, EA.getY()).getEstado().equals(Estado.NOVISITADO.toString())){
                    OS.add(this.tablero.getCasilla(EA.getX()+i, EA.getY()));
                }
            }
            catch(ArrayIndexOutOfBoundsException e){

            }
        }

        for(int i=-1;i<=1;i+=2){
            try{
                if(this.tablero.getCasilla(EA.getX(), EA.getY()+i).getEstado().equals(Estado.NOVISITADO.toString())){
                    OS.add(tablero.getCasilla(EA.getX(), EA.getY()+i));
                }
            }
            catch(ArrayIndexOutOfBoundsException e){

            }
        }

        for(int i=0;i<OS.size();i++){
            OS.get(i).setPadre(EA);
        }

        return OS;
    }

    private  boolean GoalTest(Casilla EA){
        EA.setEstado(Estado.VISITADO);
        return this.tablero.EsCasillaFinal(EA);
    }

    private Stack<Casilla> removeRepeate(Stack<Casilla> OS, Stack<Casilla> F){
        Stack<Casilla> aux = new Stack<Casilla>();
        while(!OS.isEmpty()){
            Casilla casillaActual = OS.pop();
            if(!isCasillaRepeat(F, casillaActual)){
                aux.add(casillaActual);
            }
        }

        while(!aux.isEmpty()){
            OS.push(aux.pop());
        }
        return OS;
    }

    private boolean isCasillaRepeat(Stack<Casilla> F, Casilla casillaActual){
        for(Casilla casilla : F){
            if(casilla.getX() == casillaActual.getX() && casilla.getY() == casillaActual.getY()){
                return true;
            }
        }
        return false;
    }

    private double getDistanciaLineal(Casilla casillaActual, Casilla casillaFinal){
        return Math.sqrt(Math.pow(casillaActual.getX()-casillaFinal.getX(),2)+Math.pow(casillaActual.getY()-casillaFinal.getY(),2));
        //return Math.abs(casillaActual.getPosX()-casillaFinal.getPosX())+Math.abs(casillaActual.getPosY()-casillaFinal.getPosY());
       
    }

    private double getDistanciaRecorrida(Casilla casillaActual, Casilla casillaFinal){
        //return Math.sqrt(Math.pow(casillaActual.getPosX()-casillaFinal.getPosX(),2)+Math.pow(casillaActual.getPosY()-casillaFinal.getPosY(),2));
        return Math.abs(casillaActual.getX()-casillaFinal.getX())+Math.abs(casillaActual.getY()-casillaFinal.getY());
       
    }
    

    public void retroceder(Casilla actual){
        if(actual.getPadre() == null){
            System.out.println("No hay solucion");
            return;
        }else{
            actual.setEstado(Estado.CAMINO);
            actual = actual.getPadre();
            if(this.tablero.EsCasillaInicio(actual)){
                actual.setEstado(Estado.INICIO);
                System.out.println("Solucion encontrada");
                return;
            }else{
                retroceder(actual);
            }
        } 
        
    }

    public void setSolucion(boolean solucion) {
        this.solucion = solucion; 
    }


}



