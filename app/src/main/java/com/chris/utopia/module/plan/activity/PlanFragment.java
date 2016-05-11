package com.chris.utopia.module.plan.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chris.utopia.R;
import com.chris.utopia.common.view.BaseFragment;
import com.chris.utopia.entity.Plan;
import com.chris.utopia.module.plan.adapter.PlanAdapter;
import com.chris.utopia.module.plan.presenter.PlanPresenter;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 2015/8/15.
 */
public class PlanFragment extends BaseFragment implements View.OnClickListener, PlanActionView {

    private RecyclerView planRecyclerView;
    private FloatingActionButton addFAB;

    private PlanAdapter adapter;
    private List<Plan> planList = new ArrayList<>();

    @Inject
    private PlanPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan, container, false);
        initView(view);
        initEvent();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    public void initView(View view) {
        addFAB = (FloatingActionButton) view.findViewById(R.id.planFrm_addFad);
        planRecyclerView = (RecyclerView) view.findViewById(R.id.planFrm_recyclerView);

        adapter = new PlanAdapter(getContext(), planList);
        adapter.setOnItemClickListener(new PlanAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("PLAN", planList.get(position));
                Intent intent = new Intent(getActivity(), PlanCreateActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
            }
        });
        planRecyclerView.setAdapter(adapter);
        planRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void initData() {
        presenter.setActionView(this);
        presenter.loadPlan();
    }

    public void initEvent() {
        addFAB.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.planFrm_addFad:
                Intent intent = new Intent(getContext(), PlanCreateActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
                break;
        }
    }

    @Override
    public void loadPlan(List<Plan> plans) {
        this.planList.clear();
        this.planList.addAll(plans);
        adapter.notifyDataSetChanged();
    }
}
