import java.util.HashMap;

public class PCB {
    //Attributes
    int PID;
    int PC;
    // state = START | READY | RUNNING | WAITING | TERMINATION
    String state;
    String ejecutable;
    // LogicalAddress;      presenceBit, dirtyBit, frame, PERMISSION_WRITE, swapAddres
    HashMap<Integer, PageTable> PTBR;

    // Constructors
    public PCB(int PID, String ejecutable) {
        this.PID = PID;
        this.PC = 0;
        this.state = "START";
        this.ejecutable = ejecutable;
        PTBR = new HashMap<>();
    }

    // Methods
    public int PTLR() {
        return PTBR.values().size();
    }

    public void addRowPTBR(Integer pageId, PageTable pageTable) {
        PTBR.put(pageId, pageTable);
    }
}


class PageTable {
    // Attributes
    // IF presenceBit=1 then Page is in frames; else is in swapSpace
    byte presenceBit;
    // IF dirtyBit=1 copy Page from frames to swapSpace
    byte dirtyBit;
    int frame;
    // PERMISSION_WRITE=1 then READ & WRITE; else ONLY_READ
    boolean PERMISSION_WRITE;
    int swapAddres;

    // Constructors
    public PageTable(byte presenceBit, byte dirtyBit, int frame, boolean PERMISSION_WRITE) {
        this.presenceBit = presenceBit;
        this.dirtyBit = dirtyBit;
        this.frame = frame;
        this.PERMISSION_WRITE = PERMISSION_WRITE;
    }

    // Methods
    public void setSwapAddres(int swapAddres) {
        this.swapAddres = swapAddres;
    }
}