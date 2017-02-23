package lu.domi.sapphire.minimarket;

import android.content.res.Resources;
import android.os.Handler;
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

public class ShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG_SHOP_ADAPTER = ShopAdapter.class.getSimpleName();

    private ArrayList<Product> goodsList;
    private Resources resources;
    private Handler repeatUpdateHandler = new Handler();

    public ShopAdapter(ArrayList<Product> goodsList, Resources resources) {
        this.resources = resources;
        this.goodsList = goodsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderProduct(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final ViewHolderProduct productHolder = (ViewHolderProduct) holder;
        Product product = goodsList.get(position);
        productHolder.title.setText(product.getName());
        productHolder.price.setText(product.getFormatedPrice());
        switch (product.getUnit()) {
            case BAG:
                productHolder.unitInfo.setText(resources.getString(R.string.q_bag));
                break;
            case BOTTLE:
                productHolder.unitInfo.setText(resources.getString(R.string.q_bottle));
                break;
            case CAN:
                productHolder.unitInfo.setText(resources.getString(R.string.q_can));
                break;
            case DOZEN:
                productHolder.unitInfo.setText(resources.getString(R.string.q_dozen));
                break;
            default:
                // Remove, if it is common usage
                Log.w(TAG_SHOP_ADAPTER, "Unit information is missing of product: " + product.getName());
        }
        productHolder.productImg.setImageDrawable(product.getProductImg());
    }

    @Override
    public int getItemCount() {
        return goodsList.size();
    }

    private class ViewHolderProduct extends RecyclerView.ViewHolder {
        private final static int REP_DELAY = 50;
        private boolean autoIncrement = false;
        private boolean autoDecrement = false;
        private int count = 0; // TODO check after reloading app ->  state of value
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
            ImageButton plusButton = (ImageButton) view.findViewById(R.id.plus_card_imgButton);
            ImageButton negButton = (ImageButton) view.findViewById(R.id.neg_card_imgButton);
            setPlusBtnListener(plusButton);
            setNegativeBtnListener(negButton);
        }

        private void setPlusBtnListener(ImageButton btn) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    increment();
                }
            });
            btn.setOnLongClickListener(new View.OnLongClickListener() {
                       public boolean onLongClick(View arg0) {
                           if (count < 1000) {
                               autoIncrement = true;
                               repeatUpdateHandler.post(new QuantityUpdater());
                           }
                           return false;
                       }
                   }
            );
            btn.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    if ((event.getAction() == MotionEvent.ACTION_UP
                            || event.getAction() == MotionEvent.ACTION_CANCEL) && autoIncrement) {
                        autoIncrement = false;
                    }
                    return false;
                }
            });
        }

        private void setNegativeBtnListener(ImageButton btn) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    decrement();
                }
            });
            btn.setOnLongClickListener(new View.OnLongClickListener() {
                         public boolean onLongClick(View arg0) {
                             if (count > 0) {
                                 autoDecrement = true;
                                 repeatUpdateHandler.post(new QuantityUpdater());
                             }
                             return false;
                         }
                     }
            );
            btn.setOnTouchListener( new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    if ((event.getAction() == MotionEvent.ACTION_UP
                            || event.getAction() == MotionEvent.ACTION_CANCEL) && autoDecrement ){
                        autoDecrement = false;
                    }
                    return false;
                }
            });
        }

        private void increment() {
            if (count < 1000) {
                quantity.setText(String.valueOf(++count));
                toggleAddToCartButton();
            }
        }

        private void decrement() {
            if (count > 0) {
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
            int visability = addToCartBtn.getVisibility();
            if (count > 0) {
                if (visability != View.VISIBLE) {
                    addToCartBtn.setVisibility(View.VISIBLE);
                }
            } else if (visability == View.VISIBLE) {
                addToCartBtn.setVisibility(View.GONE);
            }
        }
    }
}