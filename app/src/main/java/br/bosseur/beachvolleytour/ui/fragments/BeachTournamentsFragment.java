package br.bosseur.beachvolleytour.ui.fragments;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.bosseur.beachvolleytour.R;
import br.bosseur.beachvolleytour.listeners.FivbCallBack;
import br.bosseur.beachvolleytour.listeners.OnItemClickListener;
import br.bosseur.beachvolleytour.loaders.TournamentsLoader;
import br.bosseur.beachvolleytour.model.BeachTournament;
import br.bosseur.beachvolleytour.services.FivbIntentService;
import br.bosseur.beachvolleytour.ui.adapters.TournamentsAdapter;
import br.bosseur.beachvolleytour.utils.NetworkUtil;
import br.bosseur.beachvolleytour.utils.PreferenceUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BeachTournamentsFragment extends Fragment implements FivbCallBack<List<BeachTournament>> {

  public static final String YEAR_PARAM = "YEAR_PARAM";

  private static final int ID_TOURNAMENTS_LOADER = 45;
  private static final String LIST_STATE_KEY = "LIST_STATE_KEY";
  private static final String TOURNAMENTS_LIST_KEY = "TOURNAMENTS_LIST_KEY";

  private TournamentsAdapter mTournamentsAdapter;
  private List<BeachTournament> mTournamentList;
  private Parcelable mState;
  private Toolbar mToolbar;
  private TournamentsLoader mLoader;

  @BindView(R.id.tournaments_view)
  RecyclerView mTournamentView;

  @BindView(R.id.progress_bar)
  ProgressBar mProgressBar;

  @BindView(R.id.spinner_years)
  Spinner mYearSpinner;

  public BeachTournamentsFragment() {
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mLoader = new TournamentsLoader(this, getContext());
    if (savedInstanceState != null && savedInstanceState.get(LIST_STATE_KEY) != null) {
      mState = savedInstanceState.getParcelable(LIST_STATE_KEY);
      mTournamentList = savedInstanceState.getParcelableArrayList(TOURNAMENTS_LIST_KEY);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view =  inflater.inflate(R.layout.fragment_beach_tournaments, container, false);
    ButterKnife.bind(this, view);

    mTournamentsAdapter = new TournamentsAdapter((OnItemClickListener<BeachTournament>)getActivity());
    mTournamentView.setAdapter(mTournamentsAdapter);

    LinearLayoutManager layoutManager =
        new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);

    /* setLayoutManager associates the LayoutManager we created above with our RecyclerView */
    mTournamentView.setLayoutManager(layoutManager);

    if (mTournamentList != null && !mTournamentList.isEmpty()) {
      mTournamentsAdapter.setTournaments(mTournamentList);
      mTournamentView.getLayoutManager().onRestoreInstanceState(mState);
    }

    loadSpinner();

    mToolbar = view.findViewById(R.id.toolbar);

    AppCompatActivity activity = (AppCompatActivity) getActivity();
    if( activity != null ) {
      mToolbar.setTitle(getString(R.string.app_name));
      activity.setSupportActionBar(mToolbar);
    }


    return view;
  }

  @Override
  public void onStart() {
    super.onStart();
    String requestYear = PreferenceUtils.getPreferredYear(this.getContext());


    if (mTournamentList == null || mTournamentList.isEmpty()) {
      loadData(requestYear);
    }

  }


  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putParcelable(LIST_STATE_KEY, mTournamentView.getLayoutManager().onSaveInstanceState());
    outState.putParcelableArrayList(TOURNAMENTS_LIST_KEY, (ArrayList<BeachTournament>) mTournamentList);
  }

  private void showLoading() {
    mTournamentView.setVisibility(View.GONE);
    mProgressBar.setVisibility(View.VISIBLE);
  }

  private void hideLoading() {
    mTournamentView.setVisibility(View.VISIBLE);
    mProgressBar.setVisibility(View.GONE);
  }

  @Override
  public void success(List<BeachTournament> tournaments) {
    hideLoading();
    Snackbar.make(mTournamentView, getString(R.string.tournaments_loaded),
        Snackbar.LENGTH_SHORT).show();
    mTournamentList = tournaments;
    final LayoutAnimationController controller =
        AnimationUtils.loadLayoutAnimation(getContext(), R.anim.animation_bottom_up);

    mTournamentView.setLayoutAnimation(controller);
    mTournamentsAdapter.setTournaments(tournaments);;
    mTournamentView.scheduleLayoutAnimation();

  }

  @Override
  public void error(String message) {
    hideLoading();
  }

  private void loadData(String requestYear) {
    mTournamentsAdapter.setTournaments(null);
    showLoading();
    Bundle bundle = new Bundle();
    bundle.putString(YEAR_PARAM, requestYear);

    LoaderManager supportLoaderManager = getActivity().getSupportLoaderManager();
    Loader loader = supportLoaderManager.getLoader(ID_TOURNAMENTS_LOADER);
    if (loader == null) {
      supportLoaderManager.initLoader(ID_TOURNAMENTS_LOADER, bundle, mLoader);
    } else {
      supportLoaderManager.restartLoader(ID_TOURNAMENTS_LOADER, bundle, mLoader);
    }

    if (NetworkUtil.isOnline(this.getContext())) {
      FivbIntentService.startFivbService(getContext(), requestYear);
    }
  }


  private void loadSpinner() {
    Integer startYear = getResources().getInteger(R.integer.start_year);
    Integer endYear = Calendar.getInstance().get(Calendar.YEAR);
    String requestYear = PreferenceUtils.getPreferredYear(this.getContext());
    int position = 0;
    final Integer[] years = new Integer[endYear-startYear + 1];
    for(int i = 0; i <= endYear - startYear; i++){
      if(String.valueOf(endYear - i).equals(requestYear)){
        position = i;
      }
      years[i] = endYear - i;
    }
    SpinnerAdapter spinnerAdapter = new ArrayAdapter<Integer>(this.getContext(), R.layout.spinner_item, years);

    mYearSpinner.setAdapter(spinnerAdapter);
    mYearSpinner.setSelection(position);
    mYearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Integer year = years[position];
        loadData(String.valueOf(year));
        PreferenceUtils.savePreferredYear(getContext(), year.toString());
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

  }
}
