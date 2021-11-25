package my_dropbox

import com.dropbox.core.DbxException
import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.v2.DbxClientV2
import com.dropbox.core.v2.files.WriteMode

import java.io.{FileInputStream, FileNotFoundException, InputStream}

object MyDropbox {

   val access_token: String = sys.env("DROPBOX_ACCESS_TOKEN")

   def create_dropbox_client(): DbxClientV2 = {
      val config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build()
      val client = new DbxClientV2(config, access_token)
      client
   }

   def get_current_account_info(client: DbxClientV2): Unit = {
      val account = client.users().getCurrentAccount()
      println(account.getName().getDisplayName())
   }

   def upload_file(file: String, client: DbxClientV2): Unit = {
      try {
         val in = new FileInputStream(file)
         client.files().uploadBuilder(s"/$file")
                       .withMode(WriteMode.OVERWRITE)
                       .uploadAndFinish(in)
      } catch {
         case e: FileNotFoundException => {
            println("File non trovato!")
         }
      }
   }
}
