package lu.domi.sapphire.minimarket.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import lu.domi.sapphire.minimarket.R;
import lu.domi.sapphire.minimarket.data.CartEntry;
import lu.domi.sapphire.minimarket.data.event.CartEntryEvent;

import static lu.domi.sapphire.minimarket.data.event.FragmentForwardingResult.CART_EMPTY;
import static lu.domi.sapphire.minimarket.data.event.FragmentForwardingResult.CART_ENTRY_REMOVED;

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG_CART_ADAPTER = CartAdapter.class.getSimpleName();

    private SparseArray<CartEntry> cartEntries;
    private Context context;

    public CartAdapter(SparseArray<CartEntry> cartEntries, Context context) {
        this.cartEntries = cartEntries;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderCartEntry(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_entry_card, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final ViewHolderCartEntry productHolder = (ViewHolderCartEntry) holder;
        CartEntry entry = cartEntries.valueAt(position);
        productHolder.productName.setText(entry.getName());
        productHolder.quantity.setText(String.valueOf(entry.getQuanity()));
    }

    @Override
    public int getItemCount() {
        return cartEntries.size();
    }

    private void removeEntry(int index) { // TODO udate price
        CartEntryEvent eventPayload;
        if (cartEntries.size() > 1) {
            eventPayload = new CartEntryEvent(CART_ENTRY_REMOVED, cartEntries.keyAt(index));
        } else {
            eventPayload = new CartEntryEvent(CART_EMPTY, cartEntries.keyAt(index));
        }
        cartEntries.removeAt(index);
        notifyItemRemoved(index);
        notifyItemRangeChanged(index, cartEntries.size());
        EventBus.getDefault().post(eventPayload);
    }

    private class ViewHolderCartEntry extends RecyclerView.ViewHolder {
        TextView productName;
        TextView quantity;
        ImageButton deleteBtn;

        ViewHolderCartEntry(View view) {
            super(view);
            productName = (TextView) view.findViewById(R.id.entryName_cartCard_textView);
            quantity = (TextView) view.findViewById(R.id.quantity_cartCard_textView);
            deleteBtn = (ImageButton) view.findViewById(R.id.removeEntry_cartCard_imgButton);
            setDeleteBtnListener();
        }

        private void setDeleteBtnListener() {
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = getAdapterPosition();
                    removeEntry(index);
                }
            });
        }
    }
}