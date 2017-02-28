package lu.domi.sapphire.minimarket;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.Locale;

import lu.domi.sapphire.minimarket.data.CartEntry;
import lu.domi.sapphire.minimarket.services.CartFacade;

public class CheckoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG_CHECKOUT_ADAPTER = CheckoutAdapter.class.getSimpleName();

    private SparseArray<CartEntry> cartEntryList;
    private Context context;

    public CheckoutAdapter(SparseArray<CartEntry> cartEntryList, Context context) {
        this.cartEntryList = cartEntryList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderCartEntry(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_entry_checkout_card, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final ViewHolderCartEntry cartEntryHolder = (ViewHolderCartEntry) holder;
        int key = cartEntryList.keyAt(position);
        CartEntry entry = cartEntryList.get(key);

        cartEntryHolder.name.setText(entry.getName()); // TODO insert special card at pos 1
        cartEntryHolder.quantity.setText(String.valueOf(entry.getQuanity()));

        String rowTotal = CartFacade.getServiceInstance(context).getCartService().getFormatedRowTotalOf(key, Locale.getDefault());
        cartEntryHolder.rowPrice.setText(rowTotal);
    }

    @Override
    public int getItemCount() {
        return cartEntryList.size();
    }

    private class ViewHolderCartEntry extends RecyclerView.ViewHolder {
        TextView name;
        TextView quantity;
        TextView rowPrice;

        ViewHolderCartEntry(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.entryName_checkoutCard_textView);
            quantity = (TextView) view.findViewById(R.id.entryQuantity_checkoutCard_textView);
            rowPrice = (TextView) view.findViewById(R.id.rowTotal_checkoutCard_textView);
        }
    }
}