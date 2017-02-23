package lu.domi.sapphire.minimarket.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class ContentTextView extends TextView {

    public ContentTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ContentTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "SourceSansPro-Regular.ttf");
        setTypeface(myTypeface);
    }
}