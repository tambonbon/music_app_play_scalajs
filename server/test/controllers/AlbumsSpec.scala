package controllers

import dao.{AlbumDAO, AlbumDAOImpl}
import models.Albums
import org.mockito.Mockito.when
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.json.{Reads, __}
import play.api.test.Helpers.{GET, defaultAwaitTimeout}
import play.api.test._

import scala.concurrent.Future
import scala.language.postfixOps

class AlbumsSpec  extends UnitTest ("Albums") {
  "Albums page" must {
    implicit val albumReader: Reads[Albums] = (
      (__ \ "id").read[Int] and
        (__ \ "artist").read[String] and
        (__ \ "song").read[String] and
        (__ \ "genre").read[String]
      ) (Albums.apply _)
    "show registered albums" in  {
      val controller = inject[HomeController]
      val all_albums = controller.index().apply(FakeRequest(GET, "/all-albums"))
      val album = mock[AlbumDAOImpl]

      Helpers.contentAsJson(all_albums) mustBe()
    }
  }
  "A song added" must {
    "be indeed added" in {
      //      import play.api.db.Databases
      //      val database = Databases.inMemory() // create a h2-in-memory
      // ^ can't use it because when we import jdbc % test ---> fault
      implicit lazy val app = new GuiceApplicationBuilder().configure(
        "slick.dbs.default.profile" -> "slick.jdbc.H2Profile$",
        "slick.dbs.default.db.profile" -> "org.h2.Driver",
        "slick.dbs.default.db.url" -> "jdbc:h2:mem:play;DB_CLOSE_DELAY=-1",
      ).build()

      val albumDAO = mock[AlbumDAO]
      val albumToBeAdded: Future[Albums] = Future.successful(Albums(1,"MJ","Thriller", "Pop"))
      when(albumDAO.addAlbum("MJ","Thriller", "Pop")).thenReturn(albumToBeAdded)

      val actual = app.injector.instanceOf[AlbumDAOImpl]
      actual.addAlbum("MJ"," Thriller", "Pop")
      actual.getMostRecentAlbum() mustBe albumToBeAdded
    }
  }

}
