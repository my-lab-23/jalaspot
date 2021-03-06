package authorization.authorization_code;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class AuthorizationCodeRefreshExample {
  private static final String clientId = System.getenv("SPOTIFY_CLIENT_ID");
  private static final String clientSecret = System.getenv("SPOTIFY_CLIENT_SECRET");
  private static final String refreshToken = System.getenv("SPOTIFY_REFRESH_TOKEN");

  private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
    .setClientId(clientId)
    .setClientSecret(clientSecret)
    .setRefreshToken(refreshToken)
    .build();
  private static final AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest = spotifyApi.authorizationCodeRefresh()
    .build();

  public /*static*/ String authorizationCodeRefresh_Sync() {
    try {
      final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();

      // Set access and refresh token for further "spotifyApi" object usage
      spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());

      System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
      return authorizationCodeCredentials.getAccessToken();
    } catch (IOException | SpotifyWebApiException | ParseException e) {
      System.out.println("Error: " + e.getMessage());
      return "Error.";
    }
  }

  public static void authorizationCodeRefresh_Async() {
    try {
      final CompletableFuture<AuthorizationCodeCredentials> authorizationCodeCredentialsFuture = authorizationCodeRefreshRequest.executeAsync();

      // Thread free to do other tasks...

      // Example Only. Never block in production code.
      final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeCredentialsFuture.join();

      // Set access token for further "spotifyApi" object usage
      spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());

      System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
    } catch (CompletionException e) {
      System.out.println("Error: " + e.getCause().getMessage());
    } catch (CancellationException e) {
      System.out.println("Async operation cancelled.");
    }
  }

  //public static void main(String[] args) {
  //  authorizationCodeRefresh_Sync();
  //  authorizationCodeRefresh_Async();
  //}
}
