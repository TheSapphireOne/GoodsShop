package lu.domi.sapphire.minimarket.data.event;

/**
 * POJO for cart entry mutations
 */
public class CartEntryEvent {
    private final FragmentForwardingResult key;
    private final int entryArtNo;

    public CartEntryEvent(FragmentForwardingResult key, int entryArtNo) {
        this.key = key;
        this.entryArtNo = entryArtNo;
    }

    public int getEntryArtNo() {
        return entryArtNo;
    }

    public FragmentForwardingResult getKey() {
        return key;
    }
}
