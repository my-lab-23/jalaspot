package data.library;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.SavedAlbum;
import se.michaelthelin.spotify.requests.data.library.GetCurrentUsersSavedAlbumsRequest;
import org.apache.hc.core5.http.ParseException;
import com.neovisionaries.i18n.CountryCode;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class GetCurrentUsersSavedAlbumsExample {
  private static String accessToken;
  private static SpotifyApi spotifyApi;
  private static GetCurrentUsersSavedAlbumsRequest getCurrentUsersSavedAlbumsRequest;

  public void set(String token) {
    accessToken = token;

    spotifyApi = new SpotifyApi.Builder()
      .setAccessToken(accessToken)
      .build();

    getCurrentUsersSavedAlbumsRequest = spotifyApi
      .getCurrentUsersSavedAlbums()
//    .limit(10)
      .market(CountryCode.IT)
//    .offset(0)
      .build();
  }

  //

  public /*static*/ SavedAlbum[] getCurrentUsersSavedAlbums_Sync() {
    SavedAlbum[] sap = null;
    try {
      final Paging<SavedAlbum> savedAlbumPaging = getCurrentUsersSavedAlbumsRequest.execute();
      System.out.println("Total: " + savedAlbumPaging.getTotal());
      sap = savedAlbumPaging.getItems();
    } catch (IOException | SpotifyWebApiException | ParseException e) {
      System.out.println("Error: " + e.getMessage());
    }
    return sap;
  }

  public static void getCurrentUsersSavedAlbums_Async() {
    try {
      final CompletableFuture<Paging<SavedAlbum>> pagingFuture = getCurrentUsersSavedAlbumsRequest.executeAsync();

      // Thread free to do other tasks...

      // Example Only. Never block in production code.
      final Paging<SavedAlbum> savedAlbumPaging = pagingFuture.join();

      System.out.println("Total: " + savedAlbumPaging.getTotal());
    } catch (CompletionException e) {
      System.out.println("Error: " + e.getCause().getMessage());
    } catch (CancellationException e) {
      System.out.println("Async operation cancelled.");
    }
  }

  //public static void main(String[] args) {
  //  getCurrentUsersSavedAlbums_Sync();
  //  getCurrentUsersSavedAlbums_Async();
  //}
}
