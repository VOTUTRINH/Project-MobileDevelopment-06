package com.example.oderingfood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.oderingfood.models.Food;
import com.example.oderingfood.models.GlobalVariables;
import com.example.oderingfood.models.Table;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TableListPage1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TableListPage1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TablesActivity tablesActivity;
    Context context;
    GridView gv;
    ListTablesAdapter tablesAdapter;
    List<Table> tableList = new ArrayList<Table>();

    Bottomnavigation bottomnavigation ;
    String user;
    String idRes;

    public TableListPage1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TableListPage1.
     */
    // TODO: Rename and change types and number of parameters
    public static TableListPage1 newInstance(String param1, String param2) {
        TableListPage1 fragment = new TableListPage1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        try {
            context = getActivity();
            // tablesActivity = (TablesActivity) getActivity();

            tablesActivity = new TablesActivity();
        }catch (Exception e)
        {
        }

        bottomnavigation = (Bottomnavigation) getActivity();
        user= bottomnavigation.getUser();
        idRes = bottomnavigation.getIdRes();

        tablesAdapter = new ListTablesAdapter(context,R.layout.table_layout_item, tableList);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase;

        mDatabase = database.getReference("/restaurant/"+idRes+"/BanAn");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(tableList.size() > 0)
                {
                    tableList.clear();
                }
                GlobalVariables.priority = 0;
                for(DataSnapshot postSnapShot: snapshot.getChildren())
                {
                    // Get data
                    String tenBan = postSnapShot.getKey();
                    int pri;
                    String trangThai = postSnapShot.child("TrangThai").getValue(String.class);
                    if (trangThai.equals(getString(R.string.waiting_state))) {
                        GlobalVariables.priority++;
                    }
                    try {
                        pri = postSnapShot.child("Priority").getValue(Integer.class);
                    }catch (Exception e){
                        pri = 1000;
                    }
                    // add table
                    Table table = new Table(tenBan, pri);
                    table.setState(trangThai);
                    // Get foods was ordered for table
                    DataSnapshot menuSnapShot = postSnapShot.child("Order");
                    for (DataSnapshot foodSnapShot: menuSnapShot.getChildren())
                    {
                        // Get data
                        String foodName = foodSnapShot.getKey();
                        Food food = foodSnapShot.getValue(Food.class);

                        // Add food ordered to table

                        table.AddFood(food);
                    }

                    tableList.add(table);
                }
                Collections.sort(tableList, new Comparator<Table>() {
                    @Override
                    public int compare(Table o1, Table o2) {
                        return o1.getPriority() - o2.getPriority();
                    }
                });

                tablesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout layout_page1 =(LinearLayout)inflater.inflate(R.layout.fragment_table_list_page1,null);

        gv = (GridView) layout_page1.findViewById(R.id.grid_view);
        gv.setAdapter(tablesAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                GlobalVariables.pathTable = tableList.get(i).getName();
                if(tableList.get(i).getState().equals("Empty")){
                    Intent intent=new Intent(context,MonAnActivity.class);
                    Bundle b = new Bundle();
                    b.putString("key", tableList.get(i).getName()); //Your id
                    b.putString("idRes", idRes);
                    b.putString("idUser", user);
                    intent.putExtras(b);
                    context.startActivity(intent);
                }else
                {
                    Intent intdn = new Intent(context, A2G7Activity.class); // Your nxt activity name instead of List_Activity

                    Bundle b = new Bundle();
                    b.putString("key", tableList.get(i).getName()); //Your id
                    intdn.putExtras(b); //Put your id to your next Intent
                    context.startActivity(intdn);
                }

            }
        });

        return layout_page1;
    }
}