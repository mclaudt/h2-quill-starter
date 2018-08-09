import com.typesafe.config.ConfigFactory
import io.getquill.H2JdbcContext
import io.getquill._
object Main extends App {


  import java.sql.DriverManager

  import org.h2.tools.Server


  Server.createTcpServer("-tcpAllowOthers").start
  Class.forName("org.h2.Driver")
      //Это подключение - особое - оно поднимает саму базу. Потом будет подключение сюда же, но уже как обычный пользователь.
  DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test;MODE=PostgreSQL;AUTO_SERVER=TRUE", "sa", "")

  Server.createWebServer().start()
//  Поднимется веб интерфейс по адресу:
//  http://localhost:8082

  //Подключаемся уже как бы извне и создаем таблицу
  implicit lazy val ctx: H2JdbcContext[SnakeCase] = new H2JdbcContext(
    SnakeCase,
    ConfigFactory.parseString(
      s"""{"driverClassName":"org.h2.Driver","jdbcUrl":"jdbc:h2:file:~/test;USER=sa;PASSWORD="}"""
    )
  )



  ctx.executeAction(
    """CREATE TABLE todo (
      |  checked BOOLEAN
      |);
      |""".stripMargin
  )

  case class Todo(checked: Boolean)

  import ctx._
  run(query[Todo].insert(_.checked -> false))


    //Два вопроса - mem невозмиожен в данном виде?

  //Можно ли подрубиться через постгресовский драйвер - по идее да


}
