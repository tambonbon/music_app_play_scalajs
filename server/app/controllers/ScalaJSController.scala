package controllers

import dao.AlbumDAOImpl
import play.api.Logging
import play.api.mvc.{AbstractController, ControllerComponents}

import javax.inject.{Inject, Singleton}

@Singleton
class ScalaJSController @Inject() (cc: ControllerComponents,
                                   albumDAO: AlbumDAOImpl)
  extends AbstractController(cc) with Logging with play.api.i18n.I18nSupport {

  def index = Action { implicit request =>
    Ok(views.html.scalajsMain())
  }
}
