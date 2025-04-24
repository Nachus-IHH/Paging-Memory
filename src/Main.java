/* Cosas a realizar
 * 
 * MMU
 *  mapea las direcciones lógicas a direcciones físicas (traduce pues)
 */

 /*
  * Flujo a seguir
  1. ProcessDefinition (en cola de pendientes)
  2. OS decide ejecutarla
  3. Creacion del PCB y estructuras asociadas (tabla de páginas, asignacion de marcos)
  4. PCB en cola de listas
  */
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        // Procesos
        ArrayList<ProcessDefinition> pendingProcesses = new ArrayList<>();
        ArrayList<PCB> runningProcesses = new ArrayList<>();

        // Memorias
        //  RAM
        Page[] frames = new Page[8];
        //  Disco
        Page[] swapSpace = new Page[12];

        // SIZE_PAGE & SIZE_FRAME is 4096 bytes = 4KiB
        int SIZE_PAGE = 4096;
        int processCounter = 0;

    }

    public static void processParseToPages(String process, PCB pcbOfProcess) {
        String regex = "\\.text|\\.data|\\.heap|\\.stack";
        String[] parts = process.split(regex);
        int code = Integer.parseInt(parts[1]);
        int data = Integer.parseInt(parts[2]);
        int heap=0, stack;

        if(process.matches("\\.heap")){
            // Lógica para cuando hay heap
            heap = Integer.parseInt(parts[3]);
            stack = Integer.parseInt(parts[4]);
        }
        else {
            // Lógica para cuando no hay heap
            stack = Integer.parseInt(parts[3]);
        }

        if(heap > 0) {

        }
        

        System.out.println(".text " + code);
        System.out.println(".data " + data);
        //System.out.println("heap " + heap);
        //System.out.println("stack " + stack);
        // Lógica para dividir los segmentos en páginas y asignarles una dirección lógica
    }

    public static void seeALLPD(PCB pcb){

    }

    public static void seeALLPD(ArrayList<PCB> pcbs){
        for (PCB pcb : pcbs) {
            seeALLPD(pcb);
        }
    }
}
