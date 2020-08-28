package loaders

import org.lwjgl.opengl.GL20._

trait ShaderLoader extends FileLoader {
  def draw(): Unit

  protected def load(shaderFile: String): Either[String, Int] = {
    val shaderPath = "shaders/" + shaderFile + ".glsl"
    for {
      shaderCode <- super.load(shaderPath)
      shaderPair <- splitShader(shaderCode)
      vertexShader <- compileShader(shaderPair.vertex, GL_VERTEX_SHADER)
      fragmentShader <- compileShader(shaderPair.fragment, GL_FRAGMENT_SHADER)
      shaderProgram <- linkShaders(List(vertexShader, fragmentShader))
    } yield shaderProgram
  }

  private def compileShader(shaderCode: String, shaderType: Int): Either[String, Int] = {
    val shaderId = glCreateShader(shaderType)
    glShaderSource(shaderId, shaderCode)
    glCompileShader(shaderId)

    glGetShaderi(shaderId, GL_COMPILE_STATUS) match {
      case 0 => Left(s"Could not compile shader! ${glGetShaderInfoLog(shaderId, 512)}")
      case _ => Right(shaderId)
    }
  }

  private def linkShaders(shaderIds: List[Int]): Either[String, Int] = {
    val shaderProgram = glCreateProgram()
    shaderIds.foreach(glAttachShader(shaderProgram, _))
    glLinkProgram(shaderProgram)
    shaderIds.foreach(glDeleteShader)

    glGetProgrami(shaderProgram, GL_LINK_STATUS) match {
      case 0 => Left(s"Could not link shader!\n${glGetProgramInfoLog(shaderProgram, 512)}")
      case _ => Right(shaderProgram)
    }
  }

  private def splitShader(shaderCode: String): Either[String, ShaderPair] = {
    val shaders = shaderCode.split("#---")
    shaders.length match {
      case 2 => Right(ShaderPair(vertex = shaders(0), fragment = shaders(1)))
      case _ => Left(
        s"""shader did not appear to contain a vertex and fragment section.
           |Does the file contain a '#---' line to indicate the separation""".stripMargin
      )
    }
  }

  case class ShaderPair(vertex: String, fragment: String)

}