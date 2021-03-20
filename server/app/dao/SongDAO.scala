package dao

import com.github.tminglei.slickpg.{ExPostgresProfile, PgDate2Support}
import com.google.inject.ImplementedBy
import javax.inject.{Inject, Singleton}
import models.Songs
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

trait NewPostgresProfile extends ExPostgresProfile with PgDate2Support {
  override val api = MyAPI
  object MyAPI extends API with DateTimeImplicits
}
object NewPostgresProfile extends NewPostgresProfile

trait SongComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import NewPostgresProfile.api._

//  implicit val durationMapper = MappedColumnType.base[Duration, String](
//    duration => duration.toString,
//    s => new Duration(s)
//  )
  class SongTable(tag: Tag) extends Table[Songs](tag, "songs"){

    def songId = column[Int]("songId", O.AutoInc)
    def title = column[String]("title")
    def duration = column[String]("duration")
    def pk = primaryKey("pk" , (title, duration))

    def * = (songId, title, duration) <> ((Songs.apply _).tupled, Songs.unapply)
  }
}

@ImplementedBy(classOf[SongDAOImpl])
trait SongDAO {
  def allSongs(): Future[Seq[Songs]]
  def addSong( title: String, duration: String): Future[Songs]
  def getIdMostRecentSong: Future[Int]
  def getMostRecentSong(): Future[Songs]
}

@Singleton
class SongDAOImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] with SongComponent with SongDAO  {
  import NewPostgresProfile.api._

  private val songs  = TableQuery[SongTable]

  def allSongs(): Future[Seq[Songs]] = dbConfig.db.run{
    songs.result
  }
  def addSong( title: String, duration: String): Future[Songs] = {
    val query = dbConfig.db.run{
      (songs.map(sng => (sng.title, sng.duration))
        returning songs.map(_.songId)
        into ((theRest, id) => Songs(id, theRest._1, theRest._2))
        ) += (title, duration) //TODO: Add multiple songs
      // ---> As soon as you convert DBIO to Future
      // you lose capability of bundling actions into single transaction <---
    }
    query
  }
  def getIdMostRecentSong: Future[Int] = dbConfig.db.run {
    songs.sortBy(_.songId.desc).take(1).map(_.songId).result.head.transactionally
  }
 def getMostRecentSong(): Future[Songs] = dbConfig.db.run {
    songs.sortBy(_.songId.desc).take(1).result.head.transactionally
  }

  def getSongIdFromSong(song: String): Future[Int] = dbConfig.db.run {
    songs.filter(_.title === song).map(_.songId).result.head
  }
}
