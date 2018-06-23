package br.bosseur.beachvolleytour.ui.fragments;


import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.bosseur.beachvolleytour.R;
import br.bosseur.beachvolleytour.listeners.ErrorCallBack;
import br.bosseur.beachvolleytour.listeners.FivbCallBack;
import br.bosseur.beachvolleytour.listeners.OnItemClickListener;
import br.bosseur.beachvolleytour.loaders.TournamentsLoader;
import br.bosseur.beachvolleytour.model.BeachTournament;
import br.bosseur.beachvolleytour.receivers.ExceptionReceiver;
import br.bosseur.beachvolleytour.ui.adapters.TournamentsAdapter;
import br.bosseur.beachvolleytour.utils.LoaderUtil;
import br.bosseur.beachvolleytour.utils.PreferenceUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BeachTournamentsFragment extends Fragment implements FivbCallBack<List<BeachTournament>>, ErrorCallBack {

  public static final String YEAR_PARAM = "YEAR_PARAM";

  private static final int ID_TOURNAMENTS_LOADER = 45;
  private static final String LIST_STATE_KEY = "LIST_STATE_KEY";
  private static final String TOURNAMENTS_LIST_KEY = "TOURNAMENTS_LIST_KEY";

  private ExceptionReceiver mExceptionReceiver;

  private TournamentsAdapter mTournamentsAdapter;
  private List<BeachTournament> mTournamentList = new ArrayList<>();
  private TournamentsLoader mLoader;
  private Spinner mYearSpinner;
  private RelativeLayout mainLayout;


  @BindView(R.id.tournaments_view)
  RecyclerView mTournamentView;

  @BindView(R.id.progress_bar)
  ProgressBar mProgressBar;


  public BeachTournamentsFragment() {
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mLoader = new TournamentsLoader(this, getContext());
    mTournamentList = null;
  }

  @Override
  public void onResume() {
    super.onResume();
    mExceptionReceiver = new ExceptionReceiver(this, getContext());
    getActivity().registerReceiver(mExceptionReceiver, new IntentFilter(getString(R.string.broadcast_request_error)));
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_beach_tournaments, container, false);
    ButterKnife.bind(this, view);

    mainLayout = getActivity().findViewById(R.id.main);
    mYearSpinner = getActivity().findViewById(R.id.spinner_years);
    mTournamentsAdapter = new TournamentsAdapter((OnItemClickListener<BeachTournament>) getActivity());
    mTournamentView.setAdapter(mTournamentsAdapter);

    LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);

    /* setLayoutManager associates the LayoutManager we created above with our RecyclerView */
    mTournamentView.setLayoutManager(layoutManager);

    AppCompatActivity activity = (AppCompatActivity) getActivity();
    if (activity != null) {
      ActionBar actionBar = activity.getSupportActionBar();
      if (actionBar != null) {
        actionBar.setTitle(getString(R.string.app_name));
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setElevation(getResources().getDimension(R.dimen.action_bar_elevation));
      }
    }
    loadSpinner();

    return view;
  }

  @Override
  public void onPause() {
    super.onPause();
    LoaderManager supportLoaderManager = getActivity().getSupportLoaderManager();
    supportLoaderManager.destroyLoader(ID_TOURNAMENTS_LOADER);
    getActivity().unregisterReceiver(mExceptionReceiver);
  }

  @Override
  public void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    if (mTournamentList != null) {
      outState.putParcelable(LIST_STATE_KEY, mTournamentView.getLayoutManager().onSaveInstanceState());
      outState.putParcelableArrayList(TOURNAMENTS_LIST_KEY, (ArrayList<BeachTournament>) mTournamentList);
    }
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
    Snackbar.make(mainLayout, getString(R.string.tournaments_loaded), Snackbar.LENGTH_SHORT).show();
    mTournamentList = tournaments;
    final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.animation_bottom_up);

    mTournamentView.setLayoutAnimation(controller);
    mTournamentView.scheduleLayoutAnimation();
    mTournamentsAdapter.setTournaments(tournaments);

    getActivity().getSupportLoaderManager().destroyLoader(ID_TOURNAMENTS_LOADER);

  }

  @Override
  public void error(String message) {
    hideLoading();
    Snackbar.make(mTournamentView, message, Snackbar.LENGTH_SHORT).show();
  }

  private void loadData(String requestYear) {
    showLoading();
    this.mTournamentList = new ArrayList<>();
    mTournamentsAdapter.setTournaments(mTournamentList);
    Bundle bundle = new Bundle();
    bundle.putString(YEAR_PARAM, requestYear);
    LoaderManager supportLoaderManager = getActivity().getSupportLoaderManager();
    LoaderUtil.startLoader(bundle,supportLoaderManager, ID_TOURNAMENTS_LOADER, mLoader );

  }


  private void loadSpinner() {
    Integer startYear = getResources().getInteger(R.integer.start_year);
    Integer endYear = Calendar.getInstance().get(Calendar.YEAR);
    String requestYear = PreferenceUtils.getPreferredYear(this.getContext());
    int position = 0;
    final Integer[] years = new Integer[endYear - startYear + 1];
    for (int i = 0; i <= endYear - startYear; i++) {
      if (String.valueOf(endYear - i).equals(requestYear)) {
        position = i;
      }
      years[i] = endYear - i;
    }
    SpinnerAdapter spinnerAdapter = new ArrayAdapter<>(this.getContext(), R.layout.spinner_item, years);

    mYearSpinner.setVisibility(View.VISIBLE);
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
