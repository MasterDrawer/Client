package com.nuts.my.drawnuts.app.lobby;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nuts.my.drawnuts.R;
import com.nuts.my.drawnuts.app.ObjectCreator;
import com.nuts.my.drawnuts.domain.Game;
import com.nuts.my.drawnuts.domain.GamesRepository;
import java.util.ArrayList;
import java.util.List;

public class LobbyFragment extends Fragment {

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

    gamesRecyclerView.setAdapter(new GamesAdapter());

    return inflate;
  }

  private static class GamesAdapter extends RecyclerView.Adapter<GameEntryViewHolder> {

    private final List<Game> games;

    public GamesAdapter() {
      games = new ArrayList<>();

    }

    @Override
    public GameEntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      return null;
    }

    @Override
    public void onBindViewHolder(GameEntryViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
      return 0;
    }
  }
}
