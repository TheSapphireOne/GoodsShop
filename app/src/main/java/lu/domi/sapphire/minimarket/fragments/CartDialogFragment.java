package lu.domi.sapphire.minimarket.fragments;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import lu.domi.sapphire.minimarket.R;
import lu.domi.sapphire.minimarket.data.CartEntry;
import lu.domi.sapphire.minimarket.services.CartFacade;

import static lu.domi.sapphire.minimarket.data.event.FragmentForwardingResult.CHECKOUT;

public class CartDialogFragment extends DialogFragment {
    private TextView subtotalTextView;

    @Override
    @NonNull
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        @SuppressLint("InflateParams") final View dialogView = inflater.inflate(R.layout.cart_dialog_view, null);

        builder.setView(dialogView)
                .setPositiveButton(R.string.btn_checkout, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EventBus.getDefault().post(CHECKOUT);
                        getDialog().dismiss();
                    }
                }).setNeutralButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int id) {
                    CartDialogFragment.this.getDialog().cancel();
                }
            });

        SparseArray<CartEntry> cartEntries = CartFacade.getServiceInstance(getContext()).getCartService().getCartEntries();
        CartAdapter adapter = new CartAdapter(cartEntries);

        RecyclerView recyclerView = (RecyclerView) dialogView.findViewById(R.id.entries_cart_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        subtotalTextView = (TextView)dialogView.findViewById(R.id.subtotal_cart_textView);
        subtotalTextView.setText(CartFacade.getServiceInstance(getContext()).getCartTotal());

        builder.setView(dialogView);
        return builder.create();
    }

    public void updateSubtotal() {
        subtotalTextView.setText(CartFacade.getServiceInstance(getContext()).getCartTotal());
    }

    @Override
    public void onStart() {
        super.onStart();

        AlertDialog dialog = (AlertDialog) getDialog();
        if (dialog != null) {
            Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            positiveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        }
    }
}