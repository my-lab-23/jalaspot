package data.library;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.requests.data.library.RemoveAlbumsForCurrentUserRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class RemoveAlbumsForCurrentUserExample {
  private static String accessToken;
  private static String[] ids = new String[]{""};
  private static SpotifyApi spotifyApi;
  private static RemoveAlbumsForCurrentUserRequest removeAlbumsForCurrentUserRequest;

  public void set(String token, String[] ids_array) {
    accessToken = token;
    ids = ids_array;

    spotifyApi = new SpotifyApi.Builder()
      .setAccessToken(accessToken)
      .build();

    removeAlbumsForCurrentUserRequest = spotifyApi
      .removeAlbumsForCurrentUser(ids)
      .build();
  }

  //

  public /*static*/ void removeAlbumsForCurrentUser_Sync() {
    try {
      final String string = removeAlbumsForCurrentUserRequest.execute();

      System.out.println("Null: " + string);
    } catch (IOException | SpotifyWebApiException | ParseException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  public static void removeAlbumsForCurrentUser_Async() {
    try {
      final CompletableFuture<String> stringFuture = removeAlbumsForCurrentUserRequest.executeAsync();

      // Thread free to do other tasks...

      // Example Only. Never block in production code.
      final String string = stringFuture.join();

      System.out.println("Null: " + string);
    } catch (CompletionException e) {
      System.out.println("Error: " + e.getCause().getMessage());
    } catch (CancellationException e) {
      System.out.println("Async operation cancelled.");
    }
  }

  //public static void main(String[] args) {
  //  removeAlbumsForCurrentUser_Sync();
  //  removeAlbumsForCurrentUser_Async();
  //}
}
