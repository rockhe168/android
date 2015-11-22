using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace H5Site.Controllers
{
    public class UserController : Controller
    {
        //
        // GET: /User/

        public ActionResult Index()
        {
            return View();
        }


        public ActionResult Login()
        {
            //Response.ContentType = "application/json";
            //// Post方式下，取得client端传过来的数据  
            //if ("post".Equals(Request.HttpMethod.ToLower()))
            //{
            //    StreamReader reader = new StreamReader(Request.InputStream);
            //    string json = HttpUtility.UrlDecode(reader.ReadToEnd());
            //    Response.Write(json);
            //}
            //// Get方式下，取得client端传过来的数据  
            //else
            //{
            //    Response.Write("[{\"title\":\"Java高级编程\"},{\"title\":\"C#高级编程\"},{\"title\":\"JavaScript高级编程\"}]");
            //}

            //var d = Request.Params.GetValues("username");

            string username = "姓名:"+Request.Params["username"];
            string age ="年龄:"+ Request.Params["age"];

            var result = new JsonResult { Data = new { username, age }};
            result.JsonRequestBehavior = JsonRequestBehavior.AllowGet;

            return result;
        }

        //public JsonResult Hello()
        //{
        //    //return new JsonResult(JsonRequestBehavior.AllowGet);
        //}

    }
}
