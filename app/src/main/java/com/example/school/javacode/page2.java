package com.example.school.javacode;

import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.school.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link page2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class page2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public page2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment page2.
     */
    // TODO: Rename and change types and number of parameters
    public static page2 newInstance(String param1, String param2) {
        page2 fragment = new page2();
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
    }

    ArrayAdapter<String> adapter;
    AlertDialog.Builder builder;

    FirebaseDatabase database;
    DatabaseReference usersRef;
    DatabaseReference itemsRef;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();

    boolean isAdmin;

    ListView listView;
    TextView oneTextView;
    List<String> itemsName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page2, container, false);

        listView = view.findViewById(R.id.homeworkListView);
        oneTextView = view.findViewById(R.id.oneTextView);

        database = FirebaseDatabase.getInstance();
        usersRef = database.getReference().child("users");
        itemsRef = database.getReference().child("items");

        oneTextView.setPaintFlags(
                oneTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        itemsName = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, itemsName);
        listView.setAdapter(adapter);

        builder = new AlertDialog.Builder(getActivity(),
                R.style.AlertDialogCustom);

        builder.setPositiveButton("Ок", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        usersRef.child(user.getUid()).child("admin").get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.getResult().getValue().equals("true")) {
                            builder.setNeutralButton("Удалить", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                        }
                    }
                });

        ChildEventListener itemsChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Item item = snapshot.getValue(Item.class);
                itemsName.add(item.getSelectedItem());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        itemsRef.addChildEventListener(itemsChildEventListener);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s = (String) listView.getItemAtPosition(i);

                switch (s) {
                    case "Английский язык":
                        showText(s, builder, i);
                        break;
                    case "Литература":
                        showText(s, builder, i);
                        break;
                    case "Русский язык":
                        showText(s, builder, i);
                        break;
                    case "Алгебра":
                        showText(s, builder, i);
                        break;
                    case "Геометрия":
                        showText(s, builder, i);
                        break;
                    case "Биология":
                        showText(s, builder, i);
                        break;
                    case "Физика":
                        showText(s, builder, i);
                        break;
                    case "Химия":
                        showText(s, builder, i);
                        break;
                    case "География":
                        showText(s, builder, i);
                        break;
                    case "Общество":
                        showText(s, builder, i);
                        break;
                    case "История":
                        showText(s, builder, i);
                        break;
                    case "ОБЖ":
                        showText(s, builder, i);
                        break;
                }

            }
        });

        return view;
    }

    private void showText(String s, AlertDialog.Builder builder, int position) {

        TimeLessonsActivity.itemsRef.child(s).child("text").get().addOnCompleteListener(
                new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                builder.setMessage(s + ": " + task.getResult().getValue());

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                try {
                    alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(
                            new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (itemsName.size() == 1) {
                                Toast.makeText(getActivity(), "Последнюю Д/З удалить нельзя!",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }

                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity(),
                                    R.style.AlertDialogCustom);
                            builder1.setMessage("Удалить?");
                            builder1.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    TimeLessonsActivity.itemsRef.child(s).removeValue();
                                    itemsName.remove(position);
                                    adapter.notifyDataSetChanged();
                                    alertDialog.dismiss();
                                }
                            });
                            builder1.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            builder1.create().show();
                        }
                    });
                } catch (Exception e) {
                }
            }
        });

    }
}