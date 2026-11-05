<div align="center">

# Per Tour

### Descubre el Perú, vive tu próxima aventura

Aplicación Android para explorar destinos turísticos, registrar reservas y consultar puntos de encuentro.

<p>
  <img alt="Android" src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" />
  <img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" />
  <img alt="Android Studio" src="https://img.shields.io/badge/Android%20Studio-3DDC84?style=for-the-badge&logo=androidstudio&logoColor=white" />
  <img alt="XML" src="https://img.shields.io/badge/XML-555555?style=for-the-badge" />
</p>

</div>

---

## Acerca de Per Tour

**Per Tour** es una aplicación móvil de turismo desarrollada con **Kotlin y XML** en Android Studio. Permite descubrir lugares de Cusco, registrar reservas de viajes y consultar sus puntos de encuentro en un mapa.

## Funcionalidades

| Módulo | Descripción |
|---|---|
| Inicio de sesión y registro | Permite crear una cuenta e ingresar a Per Tour. |
| Destinos turísticos | Muestra fotografías, ubicaciones y descripciones de lugares turísticos. |
| Registro de reservas | Permite elegir el destino, la fecha, la cantidad de personas y el punto de encuentro. |
| Mis reservas | Muestra las reservas registradas y permite consultar sus detalles y actualizar su estado. |
| Mapas | Visualiza ubicaciones con OpenStreetMap y permite abrirlas en Google Maps. |
| Mi ubicación | Solicita permiso para mostrar la ubicación del dispositivo. |
| Nosotros y Contactos | Presenta la empresa y permite preparar un mensaje de contacto. |
| Calculadora de salarios | Calcula un sueldo estimado según el puesto y las horas trabajadas. |

## Destinos disponibles

| Machu Picchu | Laguna Humantay | Montaña de Siete Colores |
|:---:|:---:|:---:|
| Ciudadela inca | Laguna de aguas turquesas | Montaña Vinicunca |

## Estructura del proyecto

```text
PerTour-Android/
├── app/
│   └── src/
│       └── main/
│           ├── java/com/example/parcialapp/
│           │   ├── MainActivity.kt
│           │   ├── LoginActivity.kt
│           │   ├── RegisterActivity.kt
│           │   ├── HomeActivity.kt
│           │   ├── RegistroReservaActivity.kt
│           │   ├── DetalleReservaActivity.kt
│           │   ├── MapaActivity.kt
│           │   ├── SalarioActivity.kt
│           │   ├── HomeFragment.kt
│           │   ├── NosotrosFragment.kt
│           │   ├── ContactosFragment.kt
│           │   ├── DestinosFragment.kt
│           │   ├── ReservasFragment.kt
│           │   ├── Destino.kt
│           │   ├── Reserva.kt
│           │   ├── Usuario.kt
│           │   ├── DestinoAdapter.kt
│           │   ├── ReservaAdapter.kt
│           │   ├── PuntoEncuentro.kt
│           │   ├── ReservasTemporales.kt
│           │   └── UsuariosTemporales.kt
│           ├── res/
│           │   ├── layout/       (pantallas y elementos visuales)
│           │   ├── drawable/     (imágenes, logos e iconos)
│           │   ├── menu/         (opciones del Navigation Drawer)
│           │   ├── mipmap/       (icono de la aplicación)
│           │   └── values/       (colores, textos y estilos)
│           └── AndroidManifest.xml
├── gradle/
├── README.md
└── settings.gradle.kts
```

## Tecnologías utilizadas

**Kotlin**, **XML**, **Android Studio**, **Material Design**, **Activities**, **Fragments**, **RecyclerView** y **osmdroid (OpenStreetMap)**.

## Requisitos e instalación

1. Tener **Android Studio** y un SDK de Android configurado.
2. Clonar este repositorio o descargarlo como archivo ZIP.
3. Abrir la carpeta del proyecto en Android Studio.
4. Esperar la sincronización de **Gradle**.

## Ejecución y flujo de prueba

1. Ejecutar `app` en un emulador o dispositivo Android.
2. Crear una cuenta o iniciar sesión.
3. Explorar los destinos turísticos.
4. Registrar una reserva seleccionando destino, fecha y punto de encuentro.
5. Consultar la reserva y cambiar su estado.
6. Abrir la ubicación en el mapa y, si se desea, en Google Maps.
7. Acceder a Contactos y a la calculadora de salarios desde la aplicación.
