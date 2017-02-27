package lu.domi.sapphire.minimarket.fragments;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import lu.domi.sapphire.minimarket.R;
import lu.domi.sapphire.minimarket.data.CartEntry;
import lu.domi.sapphire.minimarket.services.CartFacade;

public class CartDialogFragment extends DialogFragment {

    private CartAdapter adapter;
    private SparseArray<CartEntry> cartEntries;

    @Override
    @NonNull
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        @SuppressLint("InflateParams") final View dialogView = inflater.inflate(R.layout.cart_dialog_view, null);

        Button checkoutBtn =  (Button) dialogView.findViewById(R.id.checkout_cart_button);
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO green robot
                getDialog().dismiss();
            }
        });

        cartEntries = CartFacade.getServiceInstance(getContext()).getCartService().getCartEntries();
        adapter = new CartAdapter(cartEntries);

        RecyclerView recyclerView = (RecyclerView) dialogView.findViewById(R.id.entries_cart_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        builder.setView(dialogView);
        return builder.create();
    }
}