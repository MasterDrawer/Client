package com.nuts.my.drawnuts.app.lobby;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.nuts.my.drawnuts.R;
import com.nuts.my.drawnuts.domain.game.Game;
import java.util.ArrayList;
import java.util.List;

class GamesAdapter extends RecyclerView.Adapter<GameEntryViewHolder> {

  private final List<Game> games;

  public GamesAdapter() {
    games = new ArrayList<>();
  }

  @Override
  public GameEntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    //return new GameEntryViewHolder();
    CardView itemCardView = (CardView) LayoutInflater.from(parent.getContext())
        .inflate(R.layout.game_lobby_list_item, parent, false);
    return new GameEntryViewHolder(itemCardView);
  }

  @Override
  public void onBindViewHolder(GameEntryViewHolder holder, int position) {
    holder.bind(games.get(position));
  }

  @Override
  public int getItemCount() {
    return games.size();
  }

  public void addGame(Game game) {
    games.add(game);
    //notifyItemInserted(games.size() - 1);
    notifyDataSetChanged();
  }
}
