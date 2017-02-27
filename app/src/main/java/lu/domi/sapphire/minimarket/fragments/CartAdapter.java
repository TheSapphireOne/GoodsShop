package lu.domi.sapphire.minimarket.fragments;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import lu.domi.sapphire.minimarket.R;
import lu.domi.sapphire.minimarket.data.CartEntry;

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG_CART_ADAPTER = CartAdapter.class.getSimpleName();

    private SparseArray<CartEntry> cartEntries;

    public CartAdapter(SparseArray<CartEntry> cartEntries) {
        this.cartEntries = cartEntries;
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

    private class ViewHolderCartEntry extends RecyclerView.ViewHolder {
        TextView productName;
        TextView quantity;

        ViewHolderCartEntry(View view) {
            super(view);
            productName = (TextView) view.findViewById(R.id.entryName_cartCard_textView);
            quantity = (TextView) view.findViewById(R.id.quantity_cartCard_textView);
//            setPlusBtnListener();
//            setNegativeBtnListener();
//            setAddToCartListener();
        }
    }

//        private void setPlusBtnListener() {
//            plusButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    increment();
//                }
//            });
//            plusButton.setOnLongClickListener(new View.OnLongClickListener() {
//                       public boolean onLongClick(View arg0) {
//                           if ((count + cartQuantity) < 999) {
//                               autoIncrement = true;
//                               repeatUpdateHandler.post(new QuantityUpdater());
//                           }
//                           return false;
//                       }
//                   }
//            );
//            plusButton.setOnTouchListener(new View.OnTouchListener() {
//                public boolean onTouch(View v, MotionEvent event) {
//                    if ((event.getAction() == MotionEvent.ACTION_UP
//                            || event.getAction() == MotionEvent.ACTION_CANCEL) && autoIncrement) {
//                        autoIncrement = false;
//                    }
//                    return false;
//                }
//            });
//        }
//
//        private void setNegativeBtnListener() {
//            negButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    decrement();
//                }
//            });
//            negButton.setOnLongClickListener(new View.OnLongClickListener() {
//                         public boolean onLongClick(View arg0) {
//                             if ((count + cartQuantity) > 0) {
//                                 autoDecrement = true;
//                                 repeatUpdateHandler.post(new QuantityUpdater());
//                             }
//                             return false;
//                         }
//                     }
//            );
//            negButton.setOnTouchListener( new View.OnTouchListener() {
//                public boolean onTouch(View v, MotionEvent event) {
//                    if ((event.getAction() == MotionEvent.ACTION_UP
//                            || event.getAction() == MotionEvent.ACTION_CANCEL) && autoDecrement) {
//                        autoDecrement = false;
//                    }
//                    return false;
//                }
//            });
//        }
//
//        private  void setAddToCartListener() {
//            addToCartBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    CartFacade.getServiceInstance(context).insertUpdate(productList.get(getAdapterPosition()), count);
//                    cartQuantity = CartFacade.getServiceInstance(context).getCartService()
//                            .getCartEntry(productList.get(getAdapterPosition()).getArtNo()).getQuanity();
//                    count = 0; // TODO remove this here and do it with count.. BUG! always one update behind
//                    quantity.setText("0");
//                    notifyItemChanged(getAdapterPosition());
//                    addToCartBtn.setVisibility(GONE);
//                    // TODO Toast or UNDO
//                }
//            });
//        }
//
//        private void increment() {
//            if ((count + cartQuantity) < 999) {
//                quantity.setText(String.valueOf(++count));
//                toggleAddToCartButton();
//            }
//        }
//
//        private void decrement() {
//            if ((count + cartQuantity) > 0) {
//                quantity.setText(String.valueOf(--count));
//                toggleAddToCartButton();
//            }
//        }
//
//        private class QuantityUpdater implements Runnable {
//            public void run() {
//                if (autoIncrement){
//                    increment();
//                    repeatUpdateHandler.postDelayed(new QuantityUpdater(), REP_DELAY);
//                } else if (autoDecrement){
//                    decrement();
//                    repeatUpdateHandler.postDelayed(new QuantityUpdater(), REP_DELAY);
//                }
//            }
//        }
//
//        private void toggleAddToCartButton() {
//            int visibility = addToCartBtn.getVisibility();
//            if (visibility != View.VISIBLE) {
//                addToCartBtn.setVisibility(View.VISIBLE);
//                if (count < 0) {
////                    addToCartBtn.setImageDrawable();
//                } else {
////                    addToCartBtn.setImageDrawable();
//                }
//            } else if (count == 0) {
//                // TODO change image of button
//                addToCartBtn.setVisibility(GONE);
//            }
//        }
//    }
}