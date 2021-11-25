# jalaspot

Programmino che sostituisce gli album nella libreria di Spotify con quelli il cui ID è presente in un file dato.

La parte in Java è adattata partendo dagli esempi presenti qui: [Spotify Web API Java](https://github.com/spotify-web-api-java/spotify-web-api-java)
```bash
export SPOTIFY_CLIENT_ID=""
export SPOTIFY_CLIENT_SECRET=""
export SPOTIFY_REFRESH_TOKEN=""
export MY_DATA_PATH_SPOTIFY="~/example_data_path"
export DROPBOX_ACCESS_TOKEN=""
```
Mettere gli ID in un file tipo 'haydn.txt' o 'mozart.txt':
```bash
sbt "run haydn" 
```
Per rimuovere tutti gli album:
```bash
sbt "run --remove"
```
Per salvare gli ID correnti con backup su Dropbox:
```bash
sbt "run --save haydn"
sbt "run --dropbox"
```
