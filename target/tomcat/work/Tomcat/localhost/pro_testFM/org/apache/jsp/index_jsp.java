/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2016-04-27 08:59:53 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(3);
    _jspx_dependants.put("/static/jsp/head.html", Long.valueOf(1460706237829L));
    _jspx_dependants.put("/static/jsp/css-init.jspf", Long.valueOf(1460322037579L));
    _jspx_dependants.put("/static/jsp/js-init.jspf", Long.valueOf(1461689154146L));
  }

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html ng-app=\"FMApp\">\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("<title>土豆FM</title>\r\n");
      out.write("<link rel=\"icon\" href=\"img/icon.png\">\r\n");
      out.write("<!-- css -->\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/bootstrap.css\">\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/font-awesome.css\">\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/font-awesome-icon.css\">\r\n");
      out.write("\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/app.css\">");
      out.write("\r\n");
      out.write("<!-- js -->\r\n");
      out.write("<script type=\"text/javascript\" src=\"js/lib/angular.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"js/lib/angular-ui-router.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"js/lib/angular-translate.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"js/i18n/i18n-zh.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"js/i18n/i18n-czh.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"js/lib/jquery-2.1.1.js\"></script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" src=\"js/views/login/login-controller.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"js/views/search/search-controller.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"js/views/heart/heart-controller.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"js/views/personal/personal-controller.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"js/views/filtrate/filtrate-controller.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" src=\"js/directives/content-img/content-img-directive.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"js/directives/list-head/list-head-directive.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"js/directives/songs-list/songs-list-directive.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"js/directives/albums-list/albums-list-directive.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"js/directives/music-transmit/music-transmit-directive.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"js/directives/singer-name-list/singer-name-list-directive.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"js/directives/album-name-list/album-name-list-directive.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"js/directives/context-menu/context-menu-directive.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" src=\"js/app.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"js/app-config.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"js/app-controller.js\"></script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body ng-controller=\"appCtrl\">\r\n");
      out.write("\t<div class=\"container-fluid\">\r\n");
      out.write("\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t");
      out.write("<div class=\"row\"\r\n");
      out.write("\tstyle=\"background: #F9F8F2; margin-bottom: 2px; font-size: 20px; border-bottom: 1px solid #CDC9B4\">\r\n");
      out.write("\t<div class=\"col-md-offset-1 col-md-2\">\r\n");
      out.write("\t\t<a ng-href=\"#/\">\r\n");
      out.write("\t\t\t<table style=\"width: 100%; font-size: 15px\">\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td><img src=\"img/home.png\"></td>\r\n");
      out.write("\t\t\t\t\t<td><strong style=\"font-size: 25px; font-family: newF\">{{'HOME'\r\n");
      out.write("\t\t\t\t\t\t\t| translate}}<span style=\"color: #A8D64E\">FM</span>\r\n");
      out.write("\t\t\t\t\t</strong> </br>\r\n");
      out.write("\t\t\t\t\t<span style=\"font-style: italic; padding-left: 10px\">tudou.com</span>\r\n");
      out.write("\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t</table>\r\n");
      out.write("\t\t</a>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div class=\"col-md-1 header-clearfix\" style=\"margin-left: 50px\"\r\n");
      out.write("\t\tid=\"title-heart\" ng-mouseover=\"titleMouseOver('title-heart')\"\r\n");
      out.write("\t\tng-mouseleave=\"titleMouseLeave('title-heart')\">\r\n");
      out.write("\t\t<a ng-href=\"#/heart\">{{'HEART_LIST' | translate}}</a>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div class=\"col-md-1 header-clearfix\" id=\"title-personal\"\r\n");
      out.write("\t\tng-mouseover=\"titleMouseOver('title-personal')\"\r\n");
      out.write("\t\tng-mouseleave=\"titleMouseLeave('title-personal')\">\r\n");
      out.write("\t\t<a ng-href=\"#/personal\">{{'PERSONAL_LIST' | translate}}</a>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div class=\"col-md-1 header-clearfix\" id=\"title-filtrate\"\r\n");
      out.write("\t\tng-mouseover=\"titleMouseOver('title-filtrate')\"\r\n");
      out.write("\t\tng-mouseleave=\"titleMouseLeave('title-filtrate')\">\r\n");
      out.write("\t\t<a ng-href=\"#/filtrate\">{{'FILTRATE_LIST' | translate}}</a>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div class=\"col-md-1 header-clearfix\" id=\"title-search\"\r\n");
      out.write("\t\tng-mouseover=\"titleMouseOver('title-search')\"\r\n");
      out.write("\t\tng-mouseleave=\"titleMouseLeave('title-search')\">\r\n");
      out.write("\t\t<a ng-href=\"#/search\">{{'SEARCH' | translate}}</a>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div class=\"col-md-offset-3 col-md-1\">\r\n");
      out.write("\t\t<a ng-show=\"Authenticated == 1 ? false : true\" ng-href=\"#/login\"> <!-- <div class=\"icon-spinner icon-spin fa-2x\" style=\"line-height:238%;color:#00FF7C;\"></div> -->\r\n");
      out.write("\t\t\t<div class=\"header-clearfix\" style=\"\" id=\"title-login\"\r\n");
      out.write("\t\t\t\tng-mouseover=\"titleMouseOver('title-login')\"\r\n");
      out.write("\t\t\t\tng-mouseleave=\"titleMouseLeave('title-login')\">{{'LOGIN' |\r\n");
      out.write("\t\t\t\ttranslate}}</div>\r\n");
      out.write("\t\t</a>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>");
      out.write("\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div class=\"row\" style=\"background: #EFEEEB; margin-top: -2px;\">\r\n");
      out.write("\t\t\t<div class=\"container contain-clearfix\"\r\n");
      out.write("\t\t\t\tstyle=\"border-color: #FBFBFB; margin-top: 15px; margin-bottom: 15px\">\r\n");
      out.write("\t\t\t\t<div class=\"row contain-clearfix\"\r\n");
      out.write("\t\t\t\t\tstyle=\"background: #D8D8D8; border-color: #E8E8E8\">\r\n");
      out.write("\t\t\t\t\t<div class=\"contain-clearfix\" style=\"background: #F9F8F2; border-color: #BBB\">\r\n");
      out.write("\t\t\t\t\t\t<div id=\"MainPage\" main-page-width class=\"ui-view container\" style=\"min-height:350px\"></div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<div music-transmit song-list-model=\"songListModel\"></div>\r\n");
      out.write("\t</div>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}