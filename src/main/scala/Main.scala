import java.util.Calendar
import com.typesafe.config.ConfigFactory
import io.getquill.H2JdbcContext
import io.getquill._
import java.sql.DriverManager
import org.h2.tools.Server


object Main extends App {

  // ---------------------- Server code --------------------------

  Server.createTcpServer("-tcpAllowOthers").start

  Class.forName("org.h2.Driver")

  // Connection to create the database
  DriverManager.getConnection("jdbc:h2:tcp://localhost/~/h2test;MODE=PostgreSQL;AUTO_SERVER=TRUE", "sa", "")

  Server.createWebServer().start()
  //  WEB interface is up on address:
  //  http://localhost:8082



  // ---------------------- Client code --------------------------

  implicit lazy val ctx: H2JdbcContext[SnakeCase] = new H2JdbcContext(
    SnakeCase,
    ConfigFactory.parseString(
      s"""{"driverClassName":"org.h2.Driver","jdbcUrl":"jdbc:h2:file:~/h2test;USER=sa;PASSWORD="}"""
    )
  )

  // file is persisted, that's why "if not exists"
  ctx.executeAction(
    """CREATE TABLE IF NOT EXISTS todo (
      |  checked BOOLEAN,
      |  date VARCHAR
      |);
      |""".stripMargin
  )

  case class Todo(checked: Boolean,date:String)

  import ctx._

  run(query[Todo].insert(_.checked -> true, _.date-> lift(Calendar.getInstance().getTime.toString)))

  println(run(query[Todo].filter(_.checked==true)))

  scala.io.StdIn.readInt()

}
