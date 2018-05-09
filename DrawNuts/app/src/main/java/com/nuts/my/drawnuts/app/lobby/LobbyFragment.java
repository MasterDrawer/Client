package com.nuts.my.drawnuts.app.lobby;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.firebase.ui.common.ChangeEventType;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.nuts.my.drawnuts.R;
import com.nuts.my.drawnuts.domain.game.Game;
import java.util.Random;

public class LobbyFragment extends Fragment {

    private static final int GAME_TO_PRESENT_LIMIT = 100;
    private static final String FIREBASE_GAMES_REFERENCE_NAME = "games";
    private Random mRandom;

    private FirebaseRecyclerAdapter<Game, GameEntryViewHolder> mFirebaseRecyclerAdapter;

    @BindView(R.id.lobby_recycler_view)
    RecyclerView gamesRecyclerView;

    public LobbyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_lobby, container, false);
        ButterKnife.bind(this, inflate);

        initializeRecyclerViewLayout();

        connectRecyclerWithFirebase();

        return inflate;
    }

    @OnClick(R.id.lobby_fab)
    public void onFabClicked() {
        addGame(generateGame());
    }

    private void initializeRecyclerViewLayout() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        gamesRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration =
            new DividerItemDecoration(gamesRecyclerView.getContext(),
                                      layoutManager.getOrientation());
        gamesRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void connectRecyclerWithFirebase() {
        Query gamesQuery = FirebaseDatabase.getInstance()
            .getReference(FIREBASE_GAMES_REFERENCE_NAME)
            .limitToLast(GAME_TO_PRESENT_LIMIT);

        FirebaseRecyclerOptions<Game> options =
            new FirebaseRecyclerOptions.Builder<Game>().setLifecycleOwner(this)
                .setQuery(gamesQuery, Game.class)
                .build();

        mFirebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Game, GameEntryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull GameEntryViewHolder holder,
                int position,
                @NonNull Game game) {
                holder.bind(game);
            }

            @NonNull
            @Override
            public GameEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                CardView view = (CardView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.game_lobby_list_item, parent, false);
                return new GameEntryViewHolder(view);
            }

            @Override
            public void onChildChanged(@NonNull ChangeEventType type,
                @NonNull DataSnapshot snapshot,
                int newIndex,
                int oldIndex) {
                super.onChildChanged(type, snapshot, newIndex, oldIndex);
                scrollToNewItems(type, newIndex);
            }

            private void scrollToNewItems(@NonNull ChangeEventType type, int newIndex) {
                if (type == ChangeEventType.ADDED) {
                    gamesRecyclerView.scrollToPosition(newIndex);
                }
            }
        };
        gamesRecyclerView.setAdapter(mFirebaseRecyclerAdapter);
    }

    private void addGame(Game game) {
        FirebaseDatabase.getInstance()
            .getReference("games")
            .push()
            .setValue(game);
    }

    private Game generateGame() {
        mRandom = new Random();
        return new Game("Game " + mRandom.nextInt(10000));
    }
}
