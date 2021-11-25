import sys.process._
import scala.language.postfixOps

import my_spotify.MySpotify
import my_dropbox.MyDropbox

object Jalaspot {
   def main(args: Array[String]): Unit = {

      var mode = ""

      try {
         mode = args(0)
      } catch {
         case e: ArrayIndexOutOfBoundsException => {
            println("Errore!")
         }
      }

      mode match {

         case "--remove" => {

            MySpotify.currentAlbums()
            MySpotify.extractIds(".temp")
            MySpotify.removeAlbums(".temp")
         }

         case "--save" => {

            MySpotify.currentAlbums()
            MySpotify.extractIds(args(1))
         }

         case "--dropbox" => {

            val client = MyDropbox.create_dropbox_client()

            val dir = sys.env("MY_DATA_PATH_SPOTIFY")
            val file = "spotify.tar.gz"
            val exit_code = s"tar -czvf $file $dir" !

            if(exit_code == 0) {
               MyDropbox.upload_file(file, client)
            }
         }

         case _ => {

            MySpotify.currentAlbums()
            MySpotify.extractIds(".temp")
            MySpotify.removeAlbums(".temp")
            MySpotify.saveAlbums(args(0))
         }          
      }   
   }
}
