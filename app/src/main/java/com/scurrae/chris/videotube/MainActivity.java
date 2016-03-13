package com.scurrae.chris.videotube;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;


public class MainActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Initialize player object
        youTubePlayerView = (YouTubePlayerView)findViewById(R.id.youtube_view);
        youTubePlayerView.initialize(Config.YOUTUBE_API_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        // If initialization succeeds
        if (!b){
            //Start Sia - Alive
            youTubePlayer.loadPlaylist("PLbxg9IbgCVkS9uTUQgno-twAlpqrsVpO-");
        }
    }

    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        // If initialization fails get error dialogs
        if(youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else{
            String error = String.format(getString(R.string.player_error), youTubeInitializationResult.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RECOVERY_REQUEST){
            // Retry initialization if user tries recovery
            getYouTubePlayerProvider().initialize(Config.YOUTUBE_API_KEY, this);
        }
    }
    protected Provider getYouTubePlayerProvider(){
        // Function to return youtube player view
        return youTubePlayerView;
    }
    private void showMessage(String message){
        // Function in which toast is passed into parameters
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    private final class MyPlaybackEventListener implements YouTubePlayer.PlaybackEventListener{
        @Override
        public void onPlaying() {
            // Function runs on video play
            showMessage("Playing");
        }

        @Override
        public void onPaused() {
            // Function runs on video pause
            showMessage("Paused");
        }

        @Override
        public void onStopped() {
            // Function runs on video stop
            showMessage("Stopped");
        }

        @Override
        public void onBuffering(boolean b) {
            // Function runs on video buffer start or stop

        }

        @Override
        public void onSeekTo(int i) {
            // Function runs on video change in position

        }
    }
    private final class MyPlayerStateChangeListener implements YouTubePlayer.PlayerStateChangeListener{
        @Override
        public void onLoading() {
            // Video is loading, player can't play or pause
        }

        @Override
        public void onLoaded(String s) {
            // Video has loaded and can play, pause or seek position
        }

        @Override
        public void onAdStarted() {
            // Advertisement starts
        }

        @Override
        public void onVideoStarted() {
            // Video playback has began
        }

        @Override
        public void onVideoEnded() {
            // Video playback has stopped
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {
            // Called when errors occur
        }
    }
}
