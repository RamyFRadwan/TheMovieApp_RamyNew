package com.ramyfradwan.ramy.themovieapp_tmdb.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nightonke.jellytogglebutton.JellyToggleButton;
import com.nightonke.jellytogglebutton.State;
import com.ramyfradwan.ramy.themovieapp_tmdb.R;
import com.ramyfradwan.ramy.themovieapp_tmdb.adapters.MoviesAdapter;
import com.ramyfradwan.ramy.themovieapp_tmdb.base.BaseActivity;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.Movie;
import com.ramyfradwan.ramy.themovieapp_tmdb.model.MoviesResponse;
import com.ramyfradwan.ramy.themovieapp_tmdb.presenters.MoviesPresenter;
import com.ramyfradwan.ramy.themovieapp_tmdb.presenters.MoviesPresenterListener;
import com.ramyfradwan.ramy.themovieapp_tmdb.utils.Constants;
import com.ramyfradwan.ramy.themovieapp_tmdb.utils.network.ConnectionStatus;
import com.rockerhieu.rvadapter.endless.EndlessRecyclerViewAdapter;

import java.util.List;

/**
 * An activity representing a list of Films. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link FilmDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class FilmListActivity extends BaseActivity<MoviesPresenter>
        implements
        MoviesPresenterListener,
        EndlessRecyclerViewAdapter.RequestToLoadMoreListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private LinearLayoutManager mStaggeredLayoutManager;
    private boolean mTwoPane;
    private RecyclerView movieRV;
    private JellyToggleButton jellyToggleButton;
    private String sortType = Constants.GET_POP_MOVIES;
    private int pageIndex = 1;
    private MoviesAdapter moviesAdapter;
    private EndlessRecyclerViewAdapter endlessRecyclerViewAdapter;
    private int pageCount = 0;
    private ConnectionStatus connectionStatus = new ConnectionStatus();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_list);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle(getTitle());

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        if (findViewById(R.id.film_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }


        initUI();

        Configuration config = getResources().getConfiguration();
        if (config.smallestScreenWidthDp >= 600
                && (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)) {
            mStaggeredLayoutManager = new GridLayoutManager(this, 1);
        } else if (config.smallestScreenWidthDp < 600
                && (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)) {
            mStaggeredLayoutManager = new GridLayoutManager(this, 3);
        } else {
            mStaggeredLayoutManager = new GridLayoutManager(this, 2);
        }

        movieRV.setLayoutManager(mStaggeredLayoutManager);

        movieRV.setHasFixedSize(true);

        connectionStatus.initConnectionStatus(this);
        moviesAdapter =
                new MoviesAdapter( this);

        //Load first page
        presenter.getPopularMovies(getClassName(), pageIndex);


        if (null != jellyToggleButton) {
            jellyToggleButton.setOnStateChangeListener(new JellyToggleButton.OnStateChangeListener() {
                @Override
                public void onStateChange(float process, State state, JellyToggleButton jtb) {
                    if (state.name().equalsIgnoreCase(getString(R.string.right))) {
                        //reset the recyclerViewAdapter
                        moviesAdapter.clear();

                        //reset the counters before the call
                        pageIndex = 1;
                        pageCount = 0;

                        //Change SortType
                        sortType = Constants.GET_TOP_MOVIES;

                        /// /Make a new call
                        presenter.getTopRatedMovies(
                                getClassName()
                                , pageIndex);
                    }
                    if (state.name().equalsIgnoreCase(getString(R.string.left))) {
                        //reset the recyclerViewAdapter
                        moviesAdapter.clear();

                        //reset the counters before the call
                        pageCount = 0;
                        pageIndex = 1;

                        //Change SortType
                        sortType = Constants.GET_POP_MOVIES;
                        /// /Make a new call
                        presenter.getPopularMovies(getClassName(), pageIndex);
                    }
                }
            });

        }

    }

    private void initUI() {
        jellyToggleButton = this.findViewById(R.id.moviesTypeToggle);
        movieRV = this.findViewById(R.id.moviesList);

    }


    @Override
    protected MoviesPresenter setupPresenter() {
        return new MoviesPresenter(this);
    }
//
//    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
//        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, mTwoPane));
//    }

    @Override
    public void getMoviesResponse(MoviesResponse moviesResponse) {

    }

    @Override
    public void getMovies(List<Movie> movies, int totalPages) {
        this.pageCount = pageCount;
        if (pageIndex > 1) {

            moviesAdapter.appendItems(movies);
            movieRV.setAdapter(endlessRecyclerViewAdapter);

        } else {
            moviesAdapter =
                    new MoviesAdapter(this,this, movies, mTwoPane);
            endlessRecyclerViewAdapter = new EndlessRecyclerViewAdapter(moviesAdapter, this);
            movieRV.setAdapter(endlessRecyclerViewAdapter);

        }

    }

    @Override
    public void handleError(String errorMessage, String tag) {

    }

    @Override
    public void onConnectionError() {

    }

    @Override
    public void onLoadMoreRequested() {
        if (pageIndex <= pageCount) {
            pageIndex++;
            presenter.loadMorePages(getClassName(), pageIndex, sortType);

        }

    }
//
//    public static class SimpleItemRecyclerViewAdapter
//            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {
//
//        private final ItemListActivity mParentActivity;
//        private final List<DummyContent.DummyItem> mValues;
//        private final boolean mTwoPane;
//        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
//                if (mTwoPane) {
//                    Bundle arguments = new Bundle();
//                    arguments.putString(FilmDetailFragment.ARG_ITEM_ID, item.id);
//                    FilmDetailFragment fragment = new FilmDetailFragment();
//                    fragment.setArguments(arguments);
//                    mParentActivity.getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.film_detail_container, fragment)
//                            .commit();
//                } else {
//                    Context context = view.getContext();
//                    Intent intent = new Intent(context, FilmDetailActivity.class);
//                    intent.putExtra(FilmDetailFragment.ARG_ITEM_ID, item.id);
//
//                    context.startActivity(intent);
//                }
//            }
//        };
//
//        SimpleItemRecyclerViewAdapter(FilmListActivity parent,
//                                      List<DummyContent.DummyItem> items,
//                                      boolean twoPane) {
//            mValues = items;
//            mParentActivity = parent;
//            mTwoPane = twoPane;
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.film_list_content, parent, false);
//            return new ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(final ViewHolder holder, int position) {
//            holder.mIdView.setText(mValues.get(position).id);
//            holder.mContentView.setText(mValues.get(position).content);
//
//            holder.itemView.setTag(mValues.get(position));
//            holder.itemView.setOnClickListener(mOnClickListener);
//        }
//
//        @Override
//        public int getItemCount() {
//            return mValues.size();
//        }
//
//        class ViewHolder extends RecyclerView.ViewHolder {
//            final TextView mIdView;
//            final TextView mContentView;
//
//            ViewHolder(View view) {
//                super(view);
//                mIdView = (TextView) view.findViewById(R.id.id_text);
//                mContentView = (TextView) view.findViewById(R.id.content);
//            }
//        }
//    }
}
