<!--
Título del PR (se convierte en el commit del squash merge):
  tipo: descripción (iss-XX)
  Ej.: refactor: extrae la seleccion de herramienta de prestar (iss-05)
-->

Closes #XX

<!-- Si este issue bloqueaba a otros (aviso "Este issue bloquea a…" del issue), al fusionar:
     comenta en cada uno avisando a su responsable, quítale el label estado:bloqueado y muévelo a "Por hacer". -->
**Desbloquea a:** <!-- #NN, #NN · o "ninguno" -->

## Técnica

<!-- Si el PR aplica una técnica. Si no aplica, escribir "No aplica". -->
- **Técnica:** N · Nombre
- **Archivo(s) y método(s):**

## Descripción

<!-- Qué problema de diseño había y cómo se resolvió. Dos o tres frases. -->

## Cambios

<!-- Un punto por paso (cada uno compila). -->
-
-

## Cómo probarlo

1. `git fetch && git switch tipo/iss-XX-descripcion`
2. Compilar desde `refactorizado/`:
   ```bash
   javac -encoding UTF-8 --release 17 -d out -sourcepath src src/gestor/prestamos/Main.java
   ```
3. Correr los casos del Anexo A (`pruebas/casos/`), sobre todo los de la opción que toca este PR:
   <!-- Ej.: CP-03-01 a CP-03-08 -->

## Resultado de las pruebas del Anexo A

<!-- Todas deben pasar igual que en el original. Si alguna cambió, el PR no se fusiona. -->
- Casos ejecutados: <!-- todos / lista -->
- Fecha de ejecución:
- Resultado: <!-- "Todos pasan" o lista de los que fallan y qué mostró el programa -->

## Criterios de aceptación

<!-- Copiados del issue. Marcar los que se cumplen. -->
- [ ]
- [ ]

## Checklist

- [ ] Las dependencias del issue ya estaban fusionadas en `develop` antes de empezar.
- [ ] La rama sale de `develop` y sigue el formato `tipo/iss-XX-descripcion`.
- [ ] El título y los commits siguen el formato `tipo: descripción (iss-XX)`.
- [ ] No se modificó nada dentro de `original/`.
- [ ] Solo se tocaron los métodos que indica el issue (§8 de `docs/convenciones.md`).
- [ ] Compila con `--release 17` sin errores ni advertencias nuevas.
- [ ] Todas las pruebas del Anexo A pasan igual que antes.
- [ ] Menú, mensajes, formato de los CSV y penalizaciones no cambiaron.
- [ ] Comentario `===== Técnica N · Nombre (ISS-XX) =====` con `Antes:` y `Cambio:` en cada lugar donde se aplicó.
- [ ] Javadoc actualizado en las firmas que cambiaron y en los métodos o clases nuevos.
- [ ] Sin código comentado ni `println` de depuración.
- [ ] Revisor asignado según la revisión cruzada de `docs/convenciones.md`.

## Notas para el revisor

<!-- Dudas, decisiones tomadas o cosas fuera de alcance. Borrar si no hay. -->
