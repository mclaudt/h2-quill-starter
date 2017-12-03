

object Main extends App {


  import java.sql.DriverManager

  import org.h2.tools.Server

  Server.createTcpServer("-tcpAllowOthers").start
  Class.forName("org.h2.Driver")
  DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test;MODE=PostgreSQL;AUTO_SERVER=TRUE", "sa", "")

  Server.createWebServer().start()
}
