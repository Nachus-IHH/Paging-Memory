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
    static int SIZE_PAGE = 4096;
    static final int NO_FRAMES = 8;
    static final int NO_SWAP_SPACE = 12;
    // Memorias
    //  RAM
    static Page[] frames = new Page[NO_FRAMES];
    //  Disco
    static Page[] swapSpace = new Page[NO_SWAP_SPACE];


    public static void main(String[] args) throws Exception {
        // Procesos
        ArrayList<ProcessDefinition> pendingProcesses = new ArrayList<>();
        ArrayList<PCB> runningProcesses = new ArrayList<>();

        // SIZE_PAGE & SIZE_FRAME is 4096 bytes = 4KiB
        int processCounter = 0;

        PCB pcb1 = new PCB(processCounter++, "word.exe");
        //String p1 = ".code0.data4096.stack9000";
        String p1 = ".code0.data6144.heap8192.stack12288";
        createPTBR(p1, pcb1);
    }

    public static void createPTBR(String process, PCB pcb) {
        String regex = "\\.code|\\.data|\\.heap|\\.stack";
        String[] parts = process.split(regex);

        int index = 1;
        int code = Integer.parseInt(parts[index++]);
        int data = Integer.parseInt(parts[index++]);
        boolean hasHeap = parts.length == 5;
        
        int heap = (hasHeap)
            ? Integer.parseInt(parts[index++])
            : 0;
        
        int stack = Integer.parseInt(parts[index]);
        SegmentType[] types = hasHeap
            ? new SegmentType[]{SegmentType.CODE, SegmentType.DATA, SegmentType.HEAP, SegmentType.STACK}
            : new SegmentType[]{SegmentType.CODE, SegmentType.DATA, SegmentType.STACK};
    
        int[] sizes = hasHeap
            ? new int[]{code, data, heap, stack}
            : new int[]{code, data, stack};

        // Creación de la PTBR
        int logicalAddress = 0;
        boolean hasModule = false;
        boolean hasCompleteSegment;
        for (int i = 0; i < sizes.length-1; i++) {
            // high limit
            int size = sizes[i+1];
            int pages = 0;
            if(hasModule) {
                hasCompleteSegment = true;
                hasModule = size%SIZE_PAGE != 0;
                pages = hasModule
                    ? (int) Math.ceil((double)size + (size%SIZE_PAGE)/SIZE_PAGE)
                    : (size - logicalAddress + (size%SIZE_PAGE))/SIZE_PAGE;
            }
            else {
                hasCompleteSegment = false;
                hasModule = size%SIZE_PAGE != 0;
                pages = hasModule
                    ? (int) Math.ceil((double)(size - logicalAddress)/SIZE_PAGE)
                    : (size - logicalAddress) / SIZE_PAGE;
            }
            
            for (int p = 0; p < pages; p++) {
                int addr = logicalAddress + (p * SIZE_PAGE);
                boolean canWrite = types[i] != SegmentType.CODE;
                if(p==0 && hasCompleteSegment) {
                    addr = sizes[i];
                }
                pcb.addRowPTBR(addr, new PageTable((byte)0, (byte)0, 0, types[i], canWrite));
            }    
            logicalAddress += hasModule
                ? (pages - 1) * SIZE_PAGE
                : pages * SIZE_PAGE;
        }        
        pcb.addRowPTBR(stack, new PageTable((byte)0, (byte)0, 0, SegmentType.STACK, true));
        
    }    
    
    public static int anyFreeFrames() {
        int framesFree = 0;
        for (int i = 0; i < frames.length; i++) {
            if(frames[i] == null) {
                return i;
            }
        }
        return -1;
    }


    public static void seeALLPD(PCB pcb){

    }

    public static void seeALLPD(ArrayList<PCB> pcbs){
        for (PCB pcb : pcbs) {
            seeALLPD(pcb);
        }
    }

    public static int getPhysicalAddres(PCB pcb, int logicalAddres) {
        int offset = logicalAddres % SIZE_PAGE;
        // El proceso esta en RAM
        if(pcb.PTBR.get(logicalAddres).presenceBit == 1) {
            return (pcb.PTBR.get(logicalAddres).frame * SIZE_PAGE) + offset;
        }
        // El proceso esta en disco
        return (pcb.PTBR.get(logicalAddres).swapAddres * SIZE_PAGE) + offset;
    }
}
