package com.nuts.my.drawnuts.app.lobby;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nuts.my.drawnuts.R;
import com.nuts.my.drawnuts.domain.game.Game;

class GameEntryViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.game_lobby_list_item_text)
  TextView textView;

  public GameEntryViewHolder(CardView view) {
    super(view);
    ButterKnife.bind(this, view);
  }

  public void bind(Game game) {
    textView.setText(game.getName());
  }
}
