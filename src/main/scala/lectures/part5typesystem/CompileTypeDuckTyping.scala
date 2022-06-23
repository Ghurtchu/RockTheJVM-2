package lectures.part5typesystem

import reflect.Selectable._

object CompileTypeDuckTyping extends scala.App {

  // structural types

  type JavaCloseable = java.io.Closeable // file, socket or any other resource

  class HipsterCloseable:
    def close(): Unit = println("Yeah.. yeah.. I'm being closed :)")

  // structural type
  type UnifiedCloseable = {
    def close(): Unit
  }

  def closeQuietly(closeable: UnifiedCloseable): Unit = {
    closeable.close()
  }

  closeQuietly(new JavaCloseable {
    override def close(): Unit = println("closing")
  })

  closeQuietly(new HipsterCloseable)

}
