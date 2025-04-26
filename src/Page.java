
public class Page {
    // Attributes
    int pageId;
    // Pensar cuando una Page pueda tener diferentes SegmentType
    SegmentType segmentType;
    Byte content;

    // Constructors
    public Page(int pageId, Byte content) {
        this.pageId = pageId;
        this.content = content;
    }

    // Methods
    public void writeContent(boolean canWrite, Byte newContent) {
        if(canWrite) {
            this.content = newContent;
        }
    }
}
