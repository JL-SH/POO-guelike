# 🎮 POO-guelike 🎮

POO-guelike es un **videojuego estilo Rogue-Like basado en consola**, desarrollado en **Kotlin**. Utiliza **Programación Orientada a Objetos (POO)**, sigue los **principios SOLID** para asi tener código modular y mantenible. Además, utiliza la **arquitectura MVC** para separar la lógica del juego de la interfaz e implementa **librerías externas** para mejorar la funcionalidad.

---

## 🛠️ Especificaciones Técnicas

| Característica              | Descripción |
|----------------------------|-------------|
| 🖥️ **Lenguaje**          | Kotlin |
| 🏗️ **Arquitectura**      | MVC (Modelo-Vista-Controlador) |
| 🧩 **Paradigma**         | Programación Orientada a Objetos (POO) |
| 📏 **Principios**        | SOLID |
| 📚 **Librerías**        | Uso de librerías externas |
| 💾 **Guardado**         | Archivos de guardado de partidas |
| 🎲 **Generación**       | Mazmorras y enemigos generados proceduralmente |
| ⚔️ **Combate**          | Basado en turnos |

---

## 🎮 Mecánica del Juego

🔹 Al iniciar el juego, se muestra una **animacion** y aparece un **menú principal** con las siguientes opciones:

✅ **Nueva partida** – Inicia una aventura desde cero.  
🔄 **Cargar partida** – Continúa desde el último guardado.  
📜 **Instrucciones** – Muestra las reglas del juego.  
❌ **Salir** – Cierra la aplicación.

### 🏹 Elección de Personaje
Al empezar una **nueva partida**, el jugador elige entre **diferentes clases de personajes**, cada una con atributos únicos como:

- **Fuerza** 💪
- **Velocidad** ⚡
- **Resistencia** 🛡️
- **Habilidades especiales** ✨

---

## 🏰 Exploración y Combate

El jugador es transportado a una **mazmorra con 5 salas**:

1️⃣ Cada sala contiene **entre 2 y 4 monstruos** que deben ser derrotados.  
2️⃣ El combate es **por turnos**, donde puedes **atacar, defenderte, esquivar o usar objetos**.  
3️⃣ Tras ganar, se activa el **sistema de looteo**, obteniendo **objetos consumibles, armas, armaduras y recursos**.  
4️⃣ Puedes **continuar a la siguiente sala, guardar la partida o salir**.  
5️⃣ La última sala tiene un **enemigo mucho más fuerte** que funciona como jefe final.

---

## 🏆 Progresión y Puntuación

🔹 Al finalizar la mazmorra, puedes:
- **Retirarte** y recibir una **puntuación basada en los recursos obtenidos**.
- **Continuar explorando** mazmorras más difíciles para conseguir mejores recompensas.

### 📊 Cálculo de la puntuación:
✔️ **Enemigos derrotados**  
⏳ **Tiempo total de juego**  
💰 **Recursos recolectados**  
⚔️ **Eficiencia en combate**

---

## 🌟 Características Adicionales

✅ **Sistema de guardado** para continuar la partida más tarde.  
🆙 **Progresión de personaje** con mejoras en atributos.  
🎲 **Generación aleatoria** de mazmorras y enemigos.  
📺 **Interfaz de consola optimizada** para mejor navegación y combate.