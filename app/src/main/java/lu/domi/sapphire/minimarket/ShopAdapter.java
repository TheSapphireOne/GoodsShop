package lu.domi.sapphire.minimarket;

import android.content.Context;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import lu.domi.sapphire.minimarket.data.Product;
import lu.domi.sapphire.minimarket.services.CartFacade;

import static android.view.View.GONE;

public class ShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG_SHOP_ADAPTER = ShopAdapter.class.getSimpleName();

    private ArrayList<Product> productList;
    private Context context;
    private int red;
    private int green;
    private int grey;
    private Handler repeatUpdateHandler = new Handler();

    public ShopAdapter(ArrayList<Product> productList, Context context) {
        this.context = context;
        this.productList = productList;
        red = ContextCompat.getColor(context, R.color.redDark);
        green = ContextCompat.getColor(context, R.color.colorPrimary);
        grey = ContextCompat.getColor(context, R.color.grey);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderProduct(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final ViewHolderProduct productHolder = (ViewHolderProduct) holder;
        Product product = productList.get(position);
        productHolder.title.setText(product.getName());
        productHolder.price.setText(product.getFormatedPrice());
        switch (product.getUnit()) {
            case BAG:
                productHolder.unitInfo.setText(context.getResources().getString(R.string.q_bag));
                break;
            case BOTTLE:
                productHolder.unitInfo.setText(context.getResources().getString(R.string.q_bottle));
                break;
            case CAN:
                productHolder.unitInfo.setText(context.getResources().getString(R.string.q_can));
                break;
            case DOZEN:
                productHolder.unitInfo.setText(context.getResources().getString(R.string.q_dozen));
                break;
            default:
                // Remove, if it is common usage
                Log.w(TAG_SHOP_ADAPTER, "Unit information is missing of product: " + product.getName());
        }
        productHolder.productImg.setImageDrawable(product.getProductImg());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    private class ViewHolderProduct extends RecyclerView.ViewHolder {
        private final static int REP_DELAY = 50;
        private boolean autoIncrement = false;
        private boolean autoDecrement = false;
        private int count = 0;
        private int cartQuantity;
        private ImageButton negButton;
        private ImageButton plusButton;
        TextView title;
        TextView price;
        TextView unitInfo;
        ImageView productImg;
        TextView quantity;
        ImageButton addToCartBtn;

        ViewHolderProduct(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.productTitle_card_textView);
            price = (TextView) view.findViewById(R.id.productPrice_card_textView);
            unitInfo = (TextView) view.findViewById(R.id.productInfo_card_textView);
            productImg = (ImageView) view.findViewById(R.id.product_card_imgView);
            quantity = (TextView) view.findViewById(R.id.quantity_card_editText);
            addToCartBtn = (ImageButton) view.findViewById(R.id.addToCart_card_button);
            plusButton = (ImageButton) view.findViewById(R.id.plus_card_imgButton);
            negButton = (ImageButton) view.findViewById(R.id.neg_card_imgButton);
            setPlusBtnListener();
            setNegativeBtnListener();
            setAddToCartListener();
        }

        private void setPlusBtnListener() {
            plusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    increment();
                }
            });
            plusButton.setOnLongClickListener(new View.OnLongClickListener() {
                       public boolean onLongClick(View arg0) {
                           if ((count + cartQuantity) < 999) {
                               autoIncrement = true;
                               repeatUpdateHandler.post(new QuantityUpdater());
                           }
                           return false;
                       }
                   }
            );
            plusButton.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    if ((event.getAction() == MotionEvent.ACTION_UP
                            || event.getAction() == MotionEvent.ACTION_CANCEL) && autoIncrement) {
                        autoIncrement = false;
                    }
                    return false;
                }
            });
        }

        private void setNegativeBtnListener() {
            negButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    decrement();
                }
            });
            negButton.setOnLongClickListener(new View.OnLongClickListener() {
                         public boolean onLongClick(View arg0) {
                             if ((count + cartQuantity) > 0) {
                                 autoDecrement = true;
                                 repeatUpdateHandler.post(new QuantityUpdater());
                             }
                             return false;
                         }
                     }
            );
            negButton.setOnTouchListener( new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    if ((event.getAction() == MotionEvent.ACTION_UP
                            || event.getAction() == MotionEvent.ACTION_CANCEL) && autoDecrement) {
                        autoDecrement = false;
                    }
                    return false;
                }
            });
        }

        private  void setAddToCartListener() {
            addToCartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartFacade.getServiceInstance(context).insertUpdate(productList.get(getAdapterPosition()), count);
                    cartQuantity = CartFacade.getServiceInstance(context).getCartService()
                            .getCartEntry(productList.get(getAdapterPosition()).getArtNo()).getQuanity();
                    count = 0; // TODO remove this here and do it with count.. BUG! always one update behind
                    quantity.setText("0");
                    notifyItemChanged(getAdapterPosition());
                    addToCartBtn.setVisibility(GONE);
                    // TODO Toast or UNDO
                }
            });
        }

        private void increment() {
            if ((count + cartQuantity) < 999) {
                quantity.setText(String.valueOf(++count));
                toggleAddToCartButton();
            }
        }

        private void decrement() {
            if ((count + cartQuantity) > 0) {
                quantity.setText(String.valueOf(--count));
                toggleAddToCartButton();
            }
        }

        private class QuantityUpdater implements Runnable {
            public void run() {
                if (autoIncrement){
                    increment();
                    repeatUpdateHandler.postDelayed(new QuantityUpdater(), REP_DELAY);
                } else if (autoDecrement){
                    decrement();
                    repeatUpdateHandler.postDelayed(new QuantityUpdater(), REP_DELAY);
                }
            }
        }

        private void toggleAddToCartButton() {
            int visibility = addToCartBtn.getVisibility();
            if (visibility != View.VISIBLE) {
                addToCartBtn.setVisibility(View.VISIBLE);
                if (count < 0) {
//                    addToCartBtn.setImageDrawable();
                } else {
//                    addToCartBtn.setImageDrawable();
                }
            } else if (count == 0) {
                // TODO change image of button
                addToCartBtn.setVisibility(GONE);
            }
        }
    }
}