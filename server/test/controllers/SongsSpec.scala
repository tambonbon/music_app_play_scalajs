package controllers

import java.time.Duration

import dao.{SongDAO, SongDAOImpl}
import models.Songs
import org.mockito.Mockito.when
import play.api.inject.guice.GuiceApplicationBuilder

import scala.concurrent.Future

class SongsSpec extends UnitTest ("Songs"){

  "A song with duration format" must {
    " in mm:ss" in {
      val song = Songs(1, "Test Song", "01:20")
      song.validationDuration(song.id, song.title, song.duration) mustBe Some(Songs(1, "Test Song", "01:20"))
    }
    "in hh:mm:ss" in {
      val song = new Songs(1, "Test Song", "10:01:20")
      song.validationDuration(song.id, song.title, song.duration) mustBe Some(Songs(1, "Test Song", "10:01:20"))
    }
    " in h:mm:ss" in {
      val song = new Songs(1, "Test Song", "1:01:20")
      song.validationDuration(song.id, song.title, song.duration) mustBe Some(Songs(1, "Test Song", "1:01:20"))
    }
     " in m:ss" in {
      val song = new Songs(1, "Test Song", "0:20")
      song.validationDuration(song.id, song.title, song.duration) mustBe Some(Songs(1, "Test Song", "0:20"))
    }
    "not in hhh:mm:ss" in {
      val song = new Songs(1, "Test Song", "100:50:20")
      song.validationDuration(song.id, song.title, song.duration) mustBe None
    }

  }
  "A song converted to Duration" must {
    "have type Duration" in {
      val song = Songs(1, "Test Song", "01:20")
      song.convertFromString mustBe Duration.parse("PT" + "1M20S")
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

      val songDAO = mock[SongDAO]
      val songToBeAdded: Future[Songs] = Future.successful(Songs(1,"Thriller", "03:20"))
      when(songDAO.addSong("Thriller", "03:20")).thenReturn(songToBeAdded)

      val actual = app.injector.instanceOf[SongDAOImpl]
      actual.addSong("Thriller", "03:20")
      actual.getMostRecentSong() mustBe songToBeAdded
    }
  }
}
