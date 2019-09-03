import route.{AuthenticationController, GroupController, ItemController, LocationController, StockHistoryController, UserController}
import akka.http.scaladsl.server.Directives._
import dao.{GroupDAO, ItemDAO, LocationDAO, StockHistoryDAO, UserDAO}

trait TestEnvironment extends
  GroupDAO with
  ItemDAO with
  LocationDAO with
  StockHistoryDAO with
  UserDAO with
  GroupController with
  ItemController with
  LocationController with
  StockHistoryController with
  UserController with
  AuthenticationController {

  val route =
    GroupRoute.route ~
      ItemRoute.route ~
      LocationRoute.route ~
      StockHistoryRoute.route ~
      UserRoute.route ~
      AuthenticationRoute.route

  override val groupRepository: GroupRepository = new GroupRepository
  override val itemRepository: ItemRepository = new ItemRepository
  override val userRepository: UserRepository = new UserRepository
  override val locationRepository: LocationRepository = new LocationRepository
  override val stockRepository: StockRepository = new StockRepository
}
