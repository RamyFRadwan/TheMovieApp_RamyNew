//package com.ramyfradwan.ramy.themovieapp_tmdb.ui;
//
//import android.app.Fragment;
//import android.content.res.Configuration;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.nightonke.jellytogglebutton.JellyToggleButton;
//import com.nightonke.jellytogglebutton.State;
//import com.ramyfradwan.ramy.themovieapp_tmdb.R;
//import com.ramyfradwan.ramy.themovieapp_tmdb.adapters.MoviesAdapter;
//import com.ramyfradwan.ramy.themovieapp_tmdb.model.Movie;
//import com.ramyfradwan.ramy.themovieapp_tmdb.model.MoviesResponse;
//import com.ramyfradwan.ramy.themovieapp_tmdb.presenters.MoviesPresenter;
//import com.ramyfradwan.ramy.themovieapp_tmdb.presenters.MoviesPresenterListener;
//import com.ramyfradwan.ramy.themovieapp_tmdb.utils.Constants;
//import com.ramyfradwan.ramy.themovieapp_tmdb.utils.network.ConnectionStatus;
//import com.rockerhieu.rvadapter.endless.EndlessRecyclerViewAdapter;
//
//import java.util.List;
//
//public class MoviesMainFragment extends Fragment
//        implements MoviesPresenterListener, EndlessRecyclerViewAdapter.RequestToLoadMoreListener {
//
//    private MoviesPresenter presenter;
//    private LinearLayoutManager mStaggeredLayoutManager;
//    private RecyclerView movieRV;
//    private JellyToggleButton jellyToggleButton;
//    private String sortType = Constants.GET_POP_MOVIES;
//    private int pageIndex = 1;
//    private MoviesAdapter moviesAdapter;
//    private EndlessRecyclerViewAdapter endlessRecyclerViewAdapter;
//    private int pageCount = 0;
//    private ConnectionStatus connectionStatus = new ConnectionStatus();
//
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        final View view = inflater.inflate(R.layout.activity_main, container, false);
//
//        initUI();
//
//        Configuration config = getResources().getConfiguration();
//        if (config.smallestScreenWidthDp >= 600
//                && (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)) {
//            mStaggeredLayoutManager = new GridLayoutManager(getActivity(), 1);
//        } else if (config.smallestScreenWidthDp < 600
//                && (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)) {
//            mStaggeredLayoutManager = new GridLayoutManager(getActivity(), 3);
//        } else {
//            mStaggeredLayoutManager = new GridLayoutManager(getActivity(), 2);
//        }
//
//        movieRV.setLayoutManager(mStaggeredLayoutManager);
//
//        movieRV.setHasFixedSize(true);
//
//        connectionStatus.initConnectionStatus((AppCompatActivity) getActivity());
//        moviesAdapter =
//                new MoviesAdapter((AppCompatActivity) getActivity());
//
//        //Load first page
//        presenter.getPopularMovies(MoviesMainFragment.class.getSimpleName(), pageIndex);
//
//
//        if (null != jellyToggleButton) {
//            jellyToggleButton.setOnStateChangeListener(new JellyToggleButton.OnStateChangeListener() {
//                @Override
//                public void onStateChange(float process, State state, JellyToggleButton jtb) {
//                    if (state.name().equalsIgnoreCase(getString(R.string.right))) {
//                        //reset the recyclerViewAdapter
//                        moviesAdapter.clear();
//
//                        //reset the counters before the call
//                        pageIndex = 1;
//                        pageCount = 0;
//
//                        //Change SortType
//                        sortType = Constants.GET_TOP_MOVIES;
//
//                        /// /Make a new call
//                        presenter.getTopRatedMovies(
//                                MoviesMainFragment.class.getSimpleName()
//                                , pageIndex);
//                    }
//                    if (state.name().equalsIgnoreCase(getString(R.string.left))) {
//                        //reset the recyclerViewAdapter
//                        moviesAdapter.clear();
//
//                        //reset the counters before the call
//                        pageCount = 0;
//                        pageIndex = 1;
//
//                        //Change SortType
//                        sortType = Constants.GET_POP_MOVIES;
//                        /// /Make a new call
//                        presenter.getPopularMovies(MoviesMainFragment.class.getSimpleName(), pageIndex);
//                    }
//                }
//            });
//
//        }
//
//
//
//        return view;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//        }
//
//
//    private void initUI() {
//        jellyToggleButton = getActivity().findViewById(R.id.moviesTypeToggle);
//        movieRV = getActivity().findViewById(R.id.moviesList);
//
//    }
//
//    @Override
//    public void handleError(String errorMessage, String tag) {
//
//    }
//
//    @Override
//    public void onConnectionError() {
//
//    }
//
//    @Override
//    public void getMoviesResponse(MoviesResponse moviesResponse) {
//
//    }
//
//    @Override
//    public void getMovies(List<Movie> movies, int pageCount) {
//        this.pageCount = pageCount;
//        if (pageIndex > 1) {
//
//            moviesAdapter.appendItems(movies);
//            movieRV.setAdapter(endlessRecyclerViewAdapter);
//
//        } else {
//            moviesAdapter =
//                    new MoviesAdapter(this.getActivity(),this.getActivity().getBaseContext(), movies,true);
//            endlessRecyclerViewAdapter = new EndlessRecyclerViewAdapter(moviesAdapter, this);
//            movieRV.setAdapter(endlessRecyclerViewAdapter);
//
//        }
//
//
//    }
//
//    @Override
//    public void onLoadMoreRequested() {
//        if (pageIndex <= pageCount) {
//            pageIndex++;
//            presenter.loadMorePages(MoviesMainFragment.class.getSimpleName(), pageIndex, sortType);
//
//        }
//    }
//
//
//    /**
//     *
//     * A callback interface that all activities containing this fragment must
//     * implement. This mechanism allows activities to be notified of item
//     * selections.
//     */
//    public interface Callback {
//        /**
//         * callback for when an item has been selected.
//         */
//        void onItemSelected(Movie movie, View view);
//    }
//}
