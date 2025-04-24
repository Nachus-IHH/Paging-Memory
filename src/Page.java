
public class Page {
    // Attributes
    int pageId;
    SegmentType segmentType;
    Byte content;

    // Constructors
    public Page(int pageId, SegmentType segmentType, Byte content) {
        this.pageId = pageId;
        this.segmentType = segmentType;
        this.content = content;
    }

    // Methods
    public void writeContent(boolean canWrite, Byte newContent) {
        if(canWrite) {
            this.content = newContent;
        }
    }
}
