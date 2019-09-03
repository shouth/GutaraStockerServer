import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import scalikejdbc.config._
import scalikejdbc._

import scala.util.Properties

object Server extends App with ComponentRegistry {
  implicit val system = ActorSystem("GutaraStocker")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher
  val port = Properties.envOrElse("PORT", "8080").toInt
  println(s"Using port number $port")

  DBs.setupAll()
  Http().bindAndHandle(route, "0.0.0.0", port)

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
}
