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

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.nuts.my.drawnuts.R;
import com.nuts.my.drawnuts.app.ObjectCreator;
import com.nuts.my.drawnuts.domain.Game;
import com.nuts.my.drawnuts.domain.GamesRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Random;

public class LobbyFragment extends Fragment {

  private GamesAdapter gamesAdapter;
  private Random mRandom;
	
	private Socket mSocket;
	{
		try {
			mSocket = IO.socket("http://10.0.0.4:1234");
		} catch (URISyntaxException e) {
		}
	}
	
  public static LobbyFragment newInstance(String param1, String param2) {
    LobbyFragment fragment = new LobbyFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @BindView(R.id.lobby_recycler_view)
  RecyclerView gamesRecyclerView;

	
  public LobbyFragment() {
    mSocket.connect();
    mSocket.emit("GetGames");
    mSocket.on("GetGames2",onNewGame);
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
 
	
    private Emitter.Listener onNewGame = new Emitter.Listener() {
      @Override
      public void call(final Object... args) {
        getActivity().runOnUiThread(new Runnable() {
          @Override
          public void run() {
            JSONObject data = (JSONObject) args[0];
            String title;
            String state;
            try {
              title = data.getString("title");
              state = data.getString("state");
            } catch (JSONException e) {
              return;
            }
          
            // add the message to view
            addGame(title, state);
          }
  
          
        });
      }
    };
  
    private void addGame(String title, String state) {
	    this.gamesAdapter.addGame(new Game(title,state));
    }


    

  @OnClick(R.id.lobby_fab)
  public void onFabClicked() {
    ObjectCreator.getGamesRepository().addGame(generateGame());
    gamesRecyclerView.smoothScrollToPosition(gamesAdapter.getItemCount() - 1);
  }

  private Game generateGame() {
    mRandom = new Random();
    return new Game("Game " + mRandom.nextInt(10000),"Open");
  }

  private void initializeRecyclerViewLayout() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    gamesRecyclerView.setLayoutManager(layoutManager);
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(gamesRecyclerView.getContext(), layoutManager.getOrientation());
    gamesRecyclerView.addItemDecoration(dividerItemDecoration);
  }
}
