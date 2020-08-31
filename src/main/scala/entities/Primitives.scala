package entities

import rendy.ModelData

object Primitives {
  val Triangle: ModelData = ModelData(
    List(
      0.5f, -0.5f, 0.0f,
      -0.5f, -0.5f, 0.0f,
      0.0f, 0.5f, 0.0f,
    ),
    List(0, 1, 2),
    size = 3,
    attributes = 1
  )

  val Quad: ModelData = ModelData(
    List(
      0.5f, 0.5f, 0.0f, // top right
      0.5f, -0.5f, 0.0f, // bottom right
      -0.5f, -0.5f, 0.0f, // bottom left
      -0.5f, 0.5f, 0.0f // top left
    ),
    List(0, 1, 2, 0, 2, 3),
    size = 3,
    attributes = 1
  )

  val Cube: ModelData = ModelData(
    List( // Front face
      -1.0f, -1.0f, 1.0f,
      1.0f, -1.0f, 1.0f,
      1.0f, 1.0f, 1.0f,
      -1.0f, 1.0f, 1.0f,

      // Back face
      -1.0f, -1.0f, -1.0f,
      -1.0f, 1.0f, -1.0f,
      1.0f, 1.0f, -1.0f,
      1.0f, -1.0f, -1.0f,

      // Top face
      -1.0f, 1.0f, -1.0f,
      -1.0f, 1.0f, 1.0f,
      1.0f, 1.0f, 1.0f,
      1.0f, 1.0f, -1.0f,

      // Bottom face
      -1.0f, -1.0f, -1.0f,
      1.0f, -1.0f, -1.0f,
      1.0f, -1.0f, 1.0f,
      -1.0f, -1.0f, 1.0f,

      // Right face
      1.0f, -1.0f, -1.0f,
      1.0f, 1.0f, -1.0f,
      1.0f, 1.0f, 1.0f,
      1.0f, -1.0f, 1.0f,

      // Left face
      -1.0f, -1.0f, -1.0f,
      -1.0f, -1.0f, 1.0f,
      -1.0f, 1.0f, 1.0f,
      -1.0f, 1.0f, -1.0f
    ),
    List(
      0, 1, 2, 0, 2, 3, // front
      4, 5, 6, 4, 6, 7, // back
      8, 9, 10, 8, 10, 11, // top
      12, 13, 14, 12, 14, 15, // bottom
      16, 17, 18, 16, 18, 19, // right
      20, 21, 22, 20, 22, 23, // left
    ),
    size = 3,
    attributes = 1
  )
}