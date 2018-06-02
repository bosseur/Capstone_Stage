package br.bosseur.beachvolleytour.ui.fragments;

import static br.bosseur.beachvolleytour.ui.BeachTournamentsActivity.BEACH_TOURNAMENT_KEY;
import static br.bosseur.beachvolleytour.ui.fragments.RoundsFragment.BEACH_ROUNDS_KEY;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.bosseur.beachvolleytour.R;
import br.bosseur.beachvolleytour.listeners.ErrorCallBack;
import br.bosseur.beachvolleytour.listeners.FivbCallBack;
import br.bosseur.beachvolleytour.listeners.MatchesLoaderCallBack;
import br.bosseur.beachvolleytour.loaders.BeachRoundsLoader;
import br.bosseur.beachvolleytour.loaders.MatchesLoader;
import br.bosseur.beachvolleytour.model.BeachRound;
import br.bosseur.beachvolleytour.model.BeachTournament;
import br.bosseur.beachvolleytour.model.TournamentMatch;
import br.bosseur.beachvolleytour.receivers.ExceptionReceiver;
import br.bosseur.beachvolleytour.services.FivbIntentService;
import br.bosseur.beachvolleytour.ui.adapters.TournamentGenderPagerAdapter;
import br.bosseur.beachvolleytour.utils.DataUtil;
import br.bosseur.beachvolleytour.utils.LoaderUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class MatchesFragment extends Fragment implements FivbCallBack<List<BeachRound>>, MatchesLoaderCallBack, ErrorCallBack {
  public static final String TOURNAMENT_PARAM = "TOURNAMENT_PARAM";
  public static final String TAB_POSITION_PARAM = "TAB_POSITION_PARAM";

  private static final int ID_ROUNDS_LOADER = 35;
  private static final int ID_MATCHES_LOADER = 36;

  private BeachRoundsLoader mRoundsLoader;
  private MatchesLoader mMatchesLoaderCallBack;
  private BeachTournament mTournament;
  private TournamentGenderPagerAdapter mGenderTabsAdapter;
  private List<BeachRound> mBeachRounds;
  private List<TournamentMatch> mMatches;

  private int mTabPosition;

  private Spinner yearsSpinner;

  @BindView(R.id.tabs_genders)
  TabLayout mTabsGenders;

  @BindView(R.id.pager_gender)
  ViewPager mGendersView;

  @BindView(R.id.progress_bar)
  ProgressBar mProgressBar;
  private ExceptionReceiver mExceptionReceiver;

  public MatchesFragment() {
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    if (savedInstanceState != null && savedInstanceState.containsKey(TAB_POSITION_PARAM)) {
      mTabPosition = savedInstanceState.getInt(TAB_POSITION_PARAM);
    }
    mRoundsLoader = new BeachRoundsLoader(this, this.getContext());
    mMatchesLoaderCallBack = new MatchesLoader(this, this.getContext());
    if (getArguments() != null) {
      mTournament = getArguments().getParcelable(BEACH_TOURNAMENT_KEY);
    }

    mGenderTabsAdapter = new TournamentGenderPagerAdapter(getChildFragmentManager());
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_matches, container, false);
    ButterKnife.bind(this, view);

    AppCompatActivity activity = (AppCompatActivity) getActivity();
    if (activity != null) {
      View rightFragment = activity.findViewById(R.id.fragment_matches);
      ActionBar actionBar = activity.getSupportActionBar();
      if (actionBar != null) {
        actionBar.setElevation(getResources().getDimension(R.dimen.no_elevation));
        actionBar.setTitle(mTournament.getTile());
        if (rightFragment == null ) {
          actionBar.setDisplayHomeAsUpEnabled(true);
          actionBar.setDisplayShowHomeEnabled(true);
        }
      }
      yearsSpinner = activity.findViewById(R.id.spinner_years);

      // In wide screen (+600dp) maintain spinner for year selection

      if (rightFragment == null) {
        yearsSpinner.setVisibility(View.GONE);
      }

      mGendersView.setAdapter(mGenderTabsAdapter);
      mTabsGenders.setupWithViewPager(mGendersView);
      loadData();
    }

    return view;
  }

  @Override
  public void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(TAB_POSITION_PARAM, mTabsGenders.getSelectedTabPosition());
  }

  @Override
  public void onResume() {
    super.onResume();
    mTabsGenders.setVisibility(View.VISIBLE);
    mExceptionReceiver = new ExceptionReceiver(this, getContext());
    if (getActivity() != null) {
      getActivity().registerReceiver(mExceptionReceiver, new IntentFilter(getString(R.string.broadcast_request_error)));
    }
  }

  @Override
  public void onPause() {
    super.onPause();
    mTabsGenders.setVisibility(View.GONE);
    FragmentActivity activity = getActivity();
    if (activity != null) {
      LoaderManager supportLoaderManager = activity.getSupportLoaderManager();
      supportLoaderManager.destroyLoader(ID_MATCHES_LOADER);
      supportLoaderManager.destroyLoader(ID_ROUNDS_LOADER);
      activity.unregisterReceiver(mExceptionReceiver);
    }
  }


  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menu_matches, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    switch (id) {
      case R.id.refresh:
        showLoading();
        this.mTabPosition = mTabsGenders.getSelectedTabPosition();
        mGenderTabsAdapter.reset();
        FivbIntentService.startFivbMatchService(getContext(), mTournament);
        return true;
      default:
        break;
    }

    return false;
  }

  private void loadData() {
    showLoading();
    this.mBeachRounds = null;
    this.mMatches = null;
    mGenderTabsAdapter.reset();
    Bundle bundle = new Bundle();
    bundle.putParcelable(TOURNAMENT_PARAM, mTournament);

    LoaderManager supportLoaderManager = getActivity().getSupportLoaderManager();

    LoaderUtil.startLoader(bundle, supportLoaderManager, ID_ROUNDS_LOADER, mRoundsLoader);

    LoaderUtil.startLoader(bundle, supportLoaderManager, ID_MATCHES_LOADER, mMatchesLoaderCallBack);

  }


  private void showLoading() {
    mGendersView.setVisibility(View.GONE);
    mProgressBar.setVisibility(View.VISIBLE);
  }

  private void hideLoading() {
    mGendersView.setVisibility(View.VISIBLE);
    mProgressBar.setVisibility(View.GONE);
  }

  @Override
  public void success(List<BeachRound> beachRounds) {
    mBeachRounds = beachRounds;
    showRounds();
  }

  @Override
  public void matchesLoaded(List<TournamentMatch> matches) {
    hideLoading();
    this.mMatches = matches;
    showRounds();
  }

  private void showRounds() {
    if (this.mMatches != null && this.mBeachRounds != null) {
      Map<String, ArrayList<BeachRound>> roundsPerGender = DataUtil.getRoundsPerGender(mTournament, mBeachRounds, mMatches, getContext());
      mGenderTabsAdapter.reset();
      for (String gender : roundsPerGender.keySet()) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(BEACH_ROUNDS_KEY, roundsPerGender.get(gender));
        RoundsFragment roundsFragment = new RoundsFragment();
        roundsFragment.setArguments(bundle);
        mGenderTabsAdapter.addFragment(roundsFragment, gender);
      }
      mGendersView.setCurrentItem(mTabPosition);
    }
  }


  @Override
  public void error(String message) {
    hideLoading();
    Snackbar snackbar = Snackbar.make(mGendersView, message, Snackbar.LENGTH_LONG);
    snackbar.setAction(R.string.retry, view -> loadData());
    snackbar.show();
  }
}
