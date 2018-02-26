package com.nuts.my.drawnuts.app.lobby;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.nuts.my.drawnuts.R;
import com.nuts.my.drawnuts.app.ObjectCreator;
import com.nuts.my.drawnuts.domain.game.Game;
import com.nuts.my.drawnuts.domain.game.GamesRepository;
import java.util.Random;

public class LobbyFragment extends Fragment {

  private GamesAdapter gamesAdapter;
  private Random mRandom;

  public static LobbyFragment newInstance(String param1, String param2) {
    LobbyFragment fragment = new LobbyFragment();
    Bundle args = new Bundle();
    //args.putString(ARG_PARAM1, param1);
    //args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

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

    gamesAdapter = new GamesAdapter();
    ObjectCreator.getGamesRepository().addListener(new GamesRepository.Listener() {
      @Override
      public void onAdded(Game game) {
        gamesAdapter.addGame(game);
      }
    });
    gamesRecyclerView.setAdapter(gamesAdapter);

    return inflate;
  }

  @OnClick(R.id.lobby_fab)
  public void onFabClicked() {
    ObjectCreator.getGamesRepository().addGame(generateGame());
    gamesRecyclerView.smoothScrollToPosition(gamesAdapter.getItemCount() - 1);
  }

  private Game generateGame() {
    mRandom = new Random();
    return new Game("Game " + mRandom.nextInt(10000));
  }

  private void initializeRecyclerViewLayout() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    gamesRecyclerView.setLayoutManager(layoutManager);
    DividerItemDecoration dividerItemDecoration =
        new DividerItemDecoration(gamesRecyclerView.getContext(), layoutManager.getOrientation());
    gamesRecyclerView.addItemDecoration(dividerItemDecoration);
  }
}
