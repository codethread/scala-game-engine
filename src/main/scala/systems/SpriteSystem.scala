package systems

import components.{Camera, CameraActive, Sprite, Transform}
import org.joml.Matrix4f
import systems.render.Renderer
import utils.Maths

class SpriteSystem(renderer: Renderer) extends System {
  private var orthoMatrix = ortho(2544, 1494, 2f)
  override def init(): Unit = {
    renderer.setup()
  }

  override def update(timeDelta: Float): Unit = {
    val camera = for {
      cameraId <- getSingleton[CameraActive].activeCamera
      camera <- getComponent[Camera] { _.name == cameraId }
      transform <- camera.getSibling[Transform]
    } yield transform

    for { cTransform <- camera } {
      val cMatrix = orthoMatrix.translate(cTransform.position, new Matrix4f())
      processComponent[Sprite, Unit] { sprite =>
        for { sTransform <- sprite.getSibling[Transform] } {
          val spriteMatrix = Maths.createTransformationMatrix(sTransform)
          renderer.renderSprite(cMatrix, spriteMatrix, sprite)
        }
      }
    }
  }

  /**
    * @param width
    * @param height
    * @param scaling 2 would mean double size for retina display
    * @return
    */
  private def ortho(
      width: Int,
      height: Int,
      scaling: Float = 1f,
      camera: Matrix4f = new Matrix4f().identity()
  ) =
    camera
      .setOrtho2D(
        0,
        width.toFloat / scaling,
        0,
        height.toFloat / scaling
      )
  private def perspective(w: Int, h: Int) =
    new Matrix4f().setPerspective(
      Math.toRadians(70).toFloat,
      w.toFloat / h.toFloat,
      0.01f,
      1000f
    )
}
