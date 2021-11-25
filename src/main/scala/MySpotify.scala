package my_spotify

import authorization.authorization_code.AuthorizationCodeRefreshExample
import data.library.RemoveAlbumsForCurrentUserExample
import data.library.GetCurrentUsersSavedAlbumsExample
import data.library.SaveAlbumsForCurrentUserExample

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

import java.io._

object MySpotify {

   val data_path = sys.env.get("MY_DATA_PATH_SPOTIFY").get
   
   def currentAlbums(): Unit = {

      val acre = new AuthorizationCodeRefreshExample
      val token = acre.authorizationCodeRefresh_Sync()

      val gcusae = new GetCurrentUsersSavedAlbumsExample
      gcusae.set(token)
      val result = gcusae.getCurrentUsersSavedAlbums_Sync()

      val pw = new PrintWriter(new FileOutputStream(new File(s"$data_path/spotify.txt"), false))
      for(r <- result) { pw.write(s"$r\n") }

      pw.close()
   }

   //

   def extractIds(lib: String): Unit = {

      val source = Source.fromFile(s"$data_path/spotify.txt")
      var index = List[Int]()
      var id = ""
      val pw = new PrintWriter(new FileOutputStream(new File(s"$data_path/$lib.txt"), false))

      for(l <- source.getLines()) {
         index = """spotify=https://open.spotify.com/album/""".r.findFirstMatchIn(l).map(_.start).toList
         id = l.substring(index(0)+39, index(0)+61)
         pw.write(s"$id\n")
      }

      pw.close()
   }

   //

   def removeAlbums(lib: String): Unit = {

      val source = Source.fromFile(s"$data_path/$lib.txt")
      var ids = ArrayBuffer[String]()

      for(line <- source.getLines()) {
         ids += line
      }

      val acre = new AuthorizationCodeRefreshExample
      val token = acre.authorizationCodeRefresh_Sync()

      val rafcue = new RemoveAlbumsForCurrentUserExample()
      val ids_array = ids.toArray
      
      if(!(ids_array.isEmpty)) {
         rafcue.set(token, ids_array)
         rafcue.removeAlbumsForCurrentUser_Sync()
      }
   }

   //

   def saveAlbums(lib: String): Unit = {

      val source = Source.fromFile(s"$data_path/$lib.txt")
      var ids = ArrayBuffer[String]()

      for(line <- source.getLines()) {
         ids += line
      }

      val acre = new AuthorizationCodeRefreshExample
      val token = acre.authorizationCodeRefresh_Sync()

      val safce = new SaveAlbumsForCurrentUserExample()
      val ids_array = ids.toArray
      safce.set(token, ids_array)

      safce.saveAlbumsForCurrentUser_Sync()
   }   
}
