## Aplikacja korzystająca z pozwoleń na wykład z AMSA.

Do programu została wykorzystana customowa biblioteka FotoApparat (GitHub biblioteki: https://github.com/RedApparat/Fotoapparat)
dzięki której można w łatwy sposób stworzyc instancję oraz updatować atrybuty obiektu.

## Dodanie biblioteki do naszego projektu oraz pozwolenia

Należy dodać odpowiednie dependency do `build.gratle`
```groovy
implementation 'io.fotoapparat:fotoapparat:2.7.0'
```
Oraz poprosić o odpowiednie pozwolenia dla aplikacji
```xml
<!-- Poniższy kod należy dodać do pliku AndroidManifest.xml -->
<uses-feature android:name="android.hardware.camera" android:required="true" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
```


```kotlin
//Tworzy instancję o danych atrybutach
private fun createCamera() {
        val cameraView = findViewById<CameraView>(R.id.camera_view)

        camera = Fotoapparat(
            context = this,
            view = cameraView, //gdzie ma wyświetlać obraz
            scaleType = ScaleType.CenterCrop,
            lensPosition = back() //który aparat ma wybrać (tylni to back())
        )
}

