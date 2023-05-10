package com.example.recyclelistcardview;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private RecyclerView  recyclerView;
    private Context ctx;
    private  CardListAdapter  myAdapter;
    private List<String>  list = new ArrayList<String>();
    private List<Integer>  iconArray = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;
        EventBus.getDefault().register(ctx);

        recyclerView = (RecyclerView)findViewById(R.id.rl_view);
        /*make data*/
        {
            list.add("Agent");
            list.add("Merchant");
            list.add("Bill");
            list.add("utility");
            list.add("balance");

            iconArray.add(R.drawable.selector_agent_foreground);
            iconArray.add(R.drawable.selector_merchant_foreground);
            iconArray.add(R.drawable.selector_agent_foreground);
            iconArray.add(R.drawable.selector_merchant_foreground);
            iconArray.add(R.drawable.selector_agent_foreground);

        }

        myAdapter = new CardListAdapter(this, recyclerView,CardListAdapter.LayoutType.GRID_LAYOUT,list,iconArray);
        recyclerView.setAdapter(myAdapter);

    }

    @Subscribe (threadMode = ThreadMode.MAIN)
    public  void refreshCardViewList(Integer eventId){
        Log.e("wzw","eventId:"+eventId);
        /*
        * remove spec view by position
        * */

        myAdapter.removeItem(eventId);
//        add
//        list.add("Billpay");
//        iconArray.add(R.drawable.selector_agent_foreground);
        myAdapter.notifyDataSetChanged();
    }
}