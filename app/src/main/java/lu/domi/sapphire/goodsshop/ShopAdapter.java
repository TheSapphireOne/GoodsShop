package lu.domi.sapphire.goodsshop;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import lu.domi.sapphire.goodsshop.data.Product;

public class ShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG_SHOP_ADAPTER = ShopAdapter.class.getSimpleName();

    private ArrayList<Product> goodsList;
    private Resources resources;

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
        TextView title;
        TextView price;
        TextView unitInfo;
        ImageView productImg;

        ViewHolderProduct(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.productTitle_card_textView);
            price = (TextView) view.findViewById(R.id.productPrice_card_textView);
            unitInfo = (TextView) view.findViewById(R.id.productInfo_card_textView);
            productImg = (ImageView) view.findViewById(R.id.product_card_imgView);
        }
    }
}