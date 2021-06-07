package com.example.schedulegenerator.Fragments;

import android.os.Bundle;
import android.os.SystemClock;
import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.schedulegenerator.Model.Project;
import com.example.schedulegenerator.R;
import com.example.schedulegenerator.Utils.Constants;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ProjectSearchFragment extends Fragment {

    private static final String GROUP_1_LABEL = "initiation";
    private static final String GROUP_2_LABEL = "planning";
    private static final String GROUP_3_LABEL = "execution";
    private static final String GROUP_4_LABEL = "closure";
    private static final int GROUPS = 4;
    private static final int MAX_X_VALUE = 3;
    private static final float BAR_SPACE = 0.05f;
    private static final float BAR_WIDTH = 0.2f;
    private static final String[] VALUES = {"small", "medium", "large"};

    private FirebaseFirestore firestore;
    private BarChart chart;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_project_search, container, false);
        chart = mainView.findViewById(R.id.project_search_barchart);
        firestore = FirebaseFirestore.getInstance();

        BarData data = createBarChartData();
        configureChartAppearance();
        prepareChartData(data);
        return mainView;
    }

    private void configureChartAppearance()
    {
        chart.getDescription().setEnabled(false);
        chart.setDrawValueAboveBar(false);

        /*chart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                System.out.println((int) value);
                return VALUES[(int) value];
            }
        });*/

        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(VALUES));
        chart.getXAxis().setCenterAxisLabels(true);


        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisLeft().setAxisMinimum(0);
        chart.getAxisLeft().setGranularity(1f);

        chart.getAxisRight().setEnabled(false);
    }


    private BarData createBarChartData()  {
        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();
        ArrayList<BarEntry> values3 = new ArrayList<>();
        ArrayList<BarEntry> values4 = new ArrayList<>();

        //int[] countArray = {0,0,0,0,0,0,0,0,0,0,0,0};

        /*firestore.collection(Constants.PROJECT).get().addOnCompleteListener
                (new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot each : task.getResult())
                    {
                        Project eachProject = each.toObject(Project.class);
                        if (eachProject.getCapacity() <= 5)
                        {
                            switch (eachProject.getStatus())
                            {
                                case "initiation":
                                    countArray[0] += 1;
                                    break;
                                case "planning":
                                    countArray[1] += 1;
                                    break;
                                case "execution":
                                    countArray[2] += 1;
                                    break;
                                case "closure":
                                    countArray[3] += 1;
                                    break;
                                default:
                                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (eachProject.getCapacity() > 5 && eachProject.getCapacity() <= 10)
                        {
                            switch (eachProject.getStatus())
                            {
                                case "initiation":
                                    countArray[4] += 1;
                                    break;
                                case "planning":
                                    countArray[5] += 1;
                                    break;
                                case "execution":
                                    countArray[6] += 1;
                                    break;
                                case "closure":
                                    countArray[7] += 1;
                                    break;
                                default:
                                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (eachProject.getCapacity() >= 10)
                        {
                            switch (eachProject.getStatus())
                            {
                                case "initiation":
                                    countArray[8] += 1;
                                    break;
                                case "planning":
                                    countArray[9] += 1;
                                    break;
                                case "execution":
                                    countArray[10] += 1;
                                    break;
                                case "closure":
                                    countArray[11] += 1;
                                    break;
                                default:
                                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        int[] countArray = {4, 5, 6, 5, 3, 0, 0, 0, 3, 4, 5, 6};


        for (int i = 0; i < MAX_X_VALUE; i++)
        {
            values1.add(new BarEntry(i, countArray[4*i]));
            values2.add(new BarEntry(i, countArray[4*i + 1]));
            values3.add(new BarEntry(i, countArray[4*i + 2]));
            values4.add(new BarEntry(i, countArray[4*i + 3]));
        }

        BarDataSet set1 = new BarDataSet(values1, GROUP_1_LABEL);
        BarDataSet set2 = new BarDataSet(values2, GROUP_2_LABEL);
        BarDataSet set3 = new BarDataSet(values3, GROUP_3_LABEL);
        BarDataSet set4 = new BarDataSet(values4, GROUP_4_LABEL);

        set1.setColor(ColorTemplate.MATERIAL_COLORS[0]);
        set2.setColor(ColorTemplate.MATERIAL_COLORS[1]);
        set3.setColor(ColorTemplate.MATERIAL_COLORS[2]);
        set4.setColor(ColorTemplate.MATERIAL_COLORS[3]);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);
        dataSets.add(set3);
        dataSets.add(set4);

        BarData data = new BarData(dataSets);

        return data;
    }

    private void prepareChartData(BarData data)
    {
        data.setBarWidth(BAR_WIDTH);
        chart.setData(data);


        float groupSpace = 1f - ((BAR_SPACE + BAR_WIDTH) * GROUPS);
        chart.groupBars(0, groupSpace, BAR_SPACE);

        chart.invalidate();
    }
}
