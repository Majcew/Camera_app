## Aplikacja korzystająca z pozwoleń na wykład z AMSA.

Do programu została wykorzystana customowa biblioteka FotoApparat (GitHub biblioteki: https://github.com/RedApparat/Fotoapparat)
dzięki której można w łatwy sposób stworzyc instancję oraz updatować atrybuty obiektu.

## Dodanie biblioteki do naszego projektu oraz pozwolenia

Należy dodać odpowiedni dependency do `build.gratle`
```groovy
implementation 'io.fotoapparat:fotoapparat:2.7.0'
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

