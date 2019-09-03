package route

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import dao.UserDAO

trait UserController {
  this: UserDAO =>

  object UserRoute extends JsonSupport {
    val route: Route =
      pathPrefix("user") {
        concat (
          path("add") {
            post {
              formFields(Symbol("password")) { case (password) =>
                userRepository.addUser(password)
                complete(StatusCodes.OK)
              }
            }
          },
          path("belongs" / IntNumber) { id =>
            get {
              complete(userRepository.getGroupsUserBelongs(id))
            }
          }
        )
      }
  }
}
