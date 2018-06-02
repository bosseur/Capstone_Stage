package br.bosseur.beachvolleytour.ui.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.bosseur.beachvolleytour.R;
import br.bosseur.beachvolleytour.model.BeachRound;
import br.bosseur.beachvolleytour.ui.adapters.RoundsAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoundsFragment extends Fragment {

  public static final String BEACH_ROUNDS_KEY = "BEACH_ROUNDS_KEY";

  private ArrayList<BeachRound> mBeachRounds = new ArrayList<>();
  private RoundsAdapter mRoundsAdapter;

  @BindView(R.id.rounds_view)
  RecyclerView mRoundsView;

  public RoundsFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null && getArguments().containsKey(BEACH_ROUNDS_KEY)) {
      mBeachRounds = getArguments().getParcelableArrayList(BEACH_ROUNDS_KEY);
    }
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_rounds, container, false);
    ButterKnife.bind(this, view);

    mRoundsAdapter = new RoundsAdapter(mBeachRounds);
    mRoundsView.setAdapter(mRoundsAdapter);
    LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
    mRoundsView.setLayoutManager(layoutManager);

    return view;
  }

}
