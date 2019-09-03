import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import scalikejdbc.config._
import scalikejdbc._

object TestServer extends App with TestEnvironment {
    implicit val system = ActorSystem("GutaraStocker")
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    DBs.setupAll()
    Http().bindAndHandle(route, "localhost", 8080)

    DB autoCommit { implicit session =>
      sql"""
      CREATE TABLE IF NOT EXISTS user_group (
        group_id INT PRIMARY KEY auto_increment,
        group_name VARCHAR (30) NOT NULL
      )
      """.execute().apply()

      sql"""
      CREATE TABLE IF NOT EXISTS item (
        item_id INT PRIMARY KEY auto_increment,
        item_name VARCHAR (30) NOT NULL,
        location_id INT NOT NULL,
        belonging_group_id INT NOT NULL
      )
      """.execute().apply()

      sql"""
      CREATE TABLE IF NOT EXISTS location (
        location_id INT PRIMARY KEY auto_increment,
        location_name VARCHAR (30) NOT NULL,
        belonging_group_id INT NOT NULL
      );
      """.execute().apply()

      sql"""
      CREATE TABLE IF NOT EXISTS stock_history (
        history_id INT PRIMARY KEY auto_increment,
        item_id INT NOT NULL,
        location_id INT NOT NULL,
        belonging_group_id INT NOT NULL,
        remain INT NOT NULL,
        created_at TIMESTAMP NOT NULL
      );
      """.execute().apply()

      sql"""
      CREATE TABLE IF NOT EXISTS service_user (
        user_id INT PRIMARY KEY auto_increment,
        password VARCHAR (255) NOT NULL
      );
      """.execute().apply()

      sql"""
      CREATE TABLE IF NOT EXISTS user_and_group (
        relation_id INT PRIMARY KEY auto_increment,
        user_id INT NOT NULL,
        group_id INT NOT NULL
      );
      """.execute().apply()
    }

    groupRepository.add("admin-group")
    userRepository.addUser("admin")
    groupRepository.addUserToGroup(1, 1)
    itemRepository.add("aaa", 1)
    itemRepository.add("bbb", 1)
    itemRepository.add("ccc", 1)
    itemRepository.add("ddd", 1)
    locationRepository.add("xxx", 1)
    locationRepository.add("yyy", 1)
    locationRepository.add("zzz", 1)
    stockRepository.addUsageHistory(1, 3, 1, 100)
    stockRepository.addUsageHistory(2, 3, 1, 100)
    stockRepository.addUsageHistory(1, 3, 1, 100)
    stockRepository.addUsageHistory(3, 3, 1, 100)
    stockRepository.addUsageHistory(1, 3, 1, 100)
    stockRepository.addUsageHistory(2, 3, 1, 100)
    stockRepository.addUsageHistory(1, 3, 1, 100)
    stockRepository.addUsageHistory(3, 3, 1, 100)
    stockRepository.addUsageHistory(3, 3, 1, 100)
    stockRepository.addUsageHistory(2, 3, 1, 100)
    stockRepository.addUsageHistory(1, 3, 1, 100)

}
