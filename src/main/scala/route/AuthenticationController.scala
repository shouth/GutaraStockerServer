package route

import akka.http.scaladsl.model.StatusCodes
import com.softwaremill.session.SessionDirectives._
import com.softwaremill.session.SessionOptions._
import com.softwaremill.session._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import dao.UserDAO
import com.github.t3hnar.bcrypt._

trait AuthenticationController {
  this: UserDAO =>

  object AuthenticationRoute {
    val sessionConfig: SessionConfig = SessionConfig.default(SessionUtil.randomServerSecret())
    implicit val sessionManager: SessionManager[Int] = new SessionManager[Int](sessionConfig)

    val route: Route =
      path("authentication") {
        post {
          formFields(Symbol("user_id").as[Int], Symbol("password")) { case (userId, password) =>
            if(userRepository.getUser(userId).map(u => password.isBcrypted(u.password)).exists(identity)) {
              setSession(oneOff, usingCookies, userId) {
                complete(StatusCodes.OK)
              }
            } else {
              complete(StatusCodes.Unauthorized)
            }
          }
        }
      }
  }
}
