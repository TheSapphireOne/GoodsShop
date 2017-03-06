package lu.domi.sapphire.minimarket;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.math.BigDecimal;

import lu.domi.sapphire.minimarket.data.CartEntry;
import lu.domi.sapphire.minimarket.services.CartFacade;

public class CheckoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final SparseArray<CartEntry> cartEntryList;
    private final Context context;
    private BigDecimal currencyRate;

    public CheckoutAdapter(SparseArray<CartEntry> cartEntryList, Context context) {
        this.cartEntryList = cartEntryList;
        this.context = context;
        currencyRate = CartFacade.getServiceInstance(context).getCurrencyRate();
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

        cartEntryHolder.name.setText(entry.getName());
        cartEntryHolder.quantity.setText(String.valueOf(entry.getQuantity()));

        String rowTotal = CartFacade.getServiceInstance(context).getCartService().getFormattedRowTotalOf(key, currencyRate);
        cartEntryHolder.rowPrice.setText(rowTotal);
    }

    public void notifyPricesHaveChanged() {
        currencyRate = CartFacade.getServiceInstance(context).getCurrencyRate();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return cartEntryList.size();
    }

    private class ViewHolderCartEntry extends RecyclerView.ViewHolder {
        final TextView name;
        final TextView quantity;
        final TextView rowPrice;

        ViewHolderCartEntry(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.entryName_checkoutCard_textView);
            quantity = (TextView) view.findViewById(R.id.entryQuantity_checkoutCard_textView);
            rowPrice = (TextView) view.findViewById(R.id.rowTotal_checkoutCard_textView);
        }
    }
}