Ejecución del Proyecto

Para ejecutar correctamente el videojuego “Pizza tower fan game”, se recomienda contar con el siguiente entorno y configuraciones básicas:

Requisitos previos

Java JDK 17 o superior instalado en el sistema.

IntelliJ IDEA 2025.2.4 (o una versión equivalente compatible con Gradle o LibGDX).

Librería LibGDX correctamente configurada.

Sistema operativo compatible: Windows, Linux o macOS.
-----------------------------------------------------------------

Instrucciones de ejecución

Sigue los pasos a continuación (si ya tienes IntelliJ IDEA instalado, puedes omitir el primer paso):

Descargar e instalar IntelliJ IDEA desde su sitio oficial.

Descargar el archivo del juego GameLluvia2024.zip y descomprimirlo.

En IntelliJ, ir a File → Open, localizar la carpeta del proyecto y abrir GameLluvia2024.
Atención: Al descomprimir, puede generarse una doble carpeta (GameLluvia2024 > GameLluvia2024). Asegúrate de abrir la segunda instancia, que contiene el código fuente.

Espera unos segundos a que el IDE configure todas las dependencias.
(Inicialmente los íconos de las clases aparecerán con una taza de café; cuando cambien a círculos azules o celestes, la sincronización habrá finalizado).

Una vez sincronizado el proyecto, entra en la siguiente ruta dentro de IntelliJ:
lwjgl3 → src → main → java → puppy.code.lwjgl3

Dentro de esa carpeta, ejecuta el archivo Lwjgl3Launcher.java.
Esto abrirá la ventana principal del juego y podrás comenzar a jugar.
----------------------------------------------------------------------------

Posibles problemas al ejecutar

Error de SDK no configurado:

En IntelliJ, ve a File → Project Structure → Project Settings → Project.

Verifica que haya un SDK configurado.

Si no existe, instala Oracle OpenJDK 25.

En “Language level”, selecciona SDK default.

El juego no abre o no encuentra recursos:

Asegúrate de abrir correctamente la carpeta del proyecto (la que contiene las subcarpetas assets, core y lwjgl3).

No abras la carpeta externa vacía que se genera al descomprimir.