package controllers;

import poly.edu.wse.Relevant;
import play.*;
import play.api.cache.Cache;
import play.mvc.*;

import views.html.*;

import java.io.*;
import java.net.Socket;

public class Application extends Controller {
  
    public static Result index() {
        return ok(index.render());
    }

    public static Result search(String query){
        response().setContentType("text/html");
        String result=null;// (String) play.cache.Cache.get(query);
        if (result!=null) return ok(result);
        String content=null;
        StringBuffer sb=null;
        try {
            Socket socket=new Socket("localhost",9998);
            new ObjectOutputStream(socket.getOutputStream()).writeObject(query);
            content= (String) new ObjectInputStream(socket.getInputStream()).readObject();
            String uuid=content.split("~~~>")[0];
            String[] results=content.split("!>>>>!");
            if(results.length==1) throw new Exception("no results");

            socket.close();
            sb=new StringBuffer();
            sb.append("<html><head><link rel=\"stylesheet\" media=\"screen\" href=\"/assets/stylesheets/bootstrap.css\" />\n")
            .append("<link rel=\"stylesheet\" media=\"screen\" href=\"/assets/stylesheets/bootstrapSwitch.css\" />\n")
            .append("<script type=\"text/javascript\" src=\"/assets/javascripts/jquery-1.9.0.min.js\"></script>\n")
            .append("<script type=\"text/javascript\" src=\"/assets/javascripts/myJs.js\"></script>\n")
            .append("<script type=\"text/javascript\" src=\"/assets/javascripts/bootstrapSwitch.js\"></script>\n")
            .append("<script type=\"text/javascript\" src=\"/assets/javascripts/jquery.highlight.js\"></script>\n")
            .append("<link rel=\"stylesheet\" href=\"/assets/stylesheets/hook.css\" type=\"text/css\" />")
            .append("<script src=\"/assets/javascripts/hook.js\" type=\"text/javascript\"></script>")
            .append("<script src=\"http://yui.yahooapis.com/3.10.0/build/yui/yui-min.js\"></script>\n</head><body>");
            sb.append("<div id=\"hook\"><div id=\"loader\"><div class=\"spinner\"></div></div><span id=\"hook-text\">Reformating...</span></div>");
            sb.append("<div class=\"navbar\">\n" +
                    "  <div class=\"navbar-inner\">\n" +
                    "<form class=\"yui3-skin-sam\" class=\"navbar-search pull-left\" action=\"search\" method=\"get\">\n" +
                    "  <input name=\"query\"  id=\"ac-input\"\" type=\"text\" class=\"search-query\" placeholder=\"Search\"").append("value=\""+query+"\"").append(">\n" +
                    "</form>" +
                    "  </div>\n" +
                    "</div>");
            sb.append("    <div style=\"margin-left:10px;margin-top:5px\">\n" +
                    "        <img src=\"/assets/images/madeByYuxinZhong.png\"/>\n" +
                    "    </div>");
            sb.append("    <div style=\"margin-top:10px;margin-left:auto;margin-right:auto;width:600px\">\n" +
                    "        <img src=\"/assets/images/home.png\"/>\n" +
                    "    </div>");
            //sb.append("<div style=\"width:50%;margin:auto;padding:2%\">");
            //sb.append("<form class=\"hidden\" class=\"yui3-skin-sam\" name=\"input\" action=\"search\" method=\"get\"><input id=\"ac-input\"").append("value=\""+query+"\"").append(" type=\"text\" name=\"query\" style=\"width:95%;height:40px\"></form>").append("</div>");
            sb.append("    <script>\n" +
                    "        YUI().use('autocomplete', 'autocomplete-highlighters', function (Y) {\n" +
                    "        Y.one('#ac-input').plug(Y.Plugin.AutoComplete, {\n" +
                    "        resultHighlighter: 'phraseMatch',\n" +
                    "        source: 'select * from search.suggest where query=\"{query}\"',\n" +
                    "        yqlEnv: 'http://pieisgood.org/yql/tables.env'\n" +
                    "        });\n" +
                    "        });\n" +
                    "    </script>");
            sb.append("<form class=\"relevantForm\" name=\"relevantForm\" action=\"/recompute\" method=\"GET\">\n" +
                    "\n" +
                    "query: <input class=\"query\" name=\"query\" value=\""+query+"\"  />\n" +
                    "\n" +
                    "responseUUID: <input class=\"responseUUID\" name=\"responseUUID\" value=\""+uuid+"\"  />\n" +
                    "\n" +
                    "1: <input class=\"checkbox\" type=\"checkbox\" name=\"one\" value=\"true\"  />\n" +
                    "\n" +
                    "2: <input class=\"checkbox\" type=\"checkbox\" name=\"two\" value=\"true\"  />\n" +
                    "\n" +
                    "3: <input class=\"checkbox\" type=\"checkbox\" name=\"three\" value=\"true\"  />\n" +
                    "\n" +
                    "4: <input class=\"checkbox\" type=\"checkbox\" name=\"four\" value=\"true\"  />\n" +
                    "\n" +
                    "5: <input class=\"checkbox\" type=\"checkbox\" name=\"five\" value=\"true\"  />\n" +
                    "\n" +
                    "6: <input class=\"checkbox\" type=\"checkbox\" name=\"six\" value=\"true\"  />\n" +
                    "\n" +
                    "7: <input class=\"checkbox\" type=\"checkbox\" name=\"seven\" value=\"true\"  />\n" +
                    "\n" +
                    "8: <input class=\"checkbox\" type=\"checkbox\" name=\"eight\" value=\"true\"  />\n" +
                    "\n" +
                    "9: <input class=\"checkbox\" type=\"checkbox\" name=\"nine\" value=\"true\"  />\n" +
                    "\n" +
                    "10: <input class=\"checkbox\" type=\"checkbox\" name=\"ten\" value=\"true\"  />\n" +
                    "\n" +
                    "\n" +
                    "<input type=\"submit\" value=\"Submit\" />\n" +
                    "\n" +
                    "</form>");
            sb.append("<div class=\"container\">");
            for(int i=1;i<results.length;i++){
                String[] tmps=results[i].split("<.,.13>");
                sb.append("<div style=\"width:60%;margin:auto;padding:2%\">");//border-style:solid;
                sb.append("<a anchorid=").append(i).append(" href=\""+tmps[4]).append("\">").append(tmps[1]).append("</a>");
                sb.append("<div style=\"height:25px;margin-left:15px\" class=\"switch switch-small\"").append(" switchid="+i);
                sb.append("><input type=\"checkbox\" /></div>");
                sb.append("<br/>");
                sb.append("<div style=\"color:GREEN\">").append(tmps[3]).append("</div>");
                sb.append("<span>").append(tmps[2]).append("</span>");
                sb.append("</div><br/>");
            }
            sb.append("</div>");
            String[] words=query.split(" ");

            sb.append("<script type=\"text/javascript\">$(document).ready(function(){");
            for(String word:words){
                sb.append("$(\"span\").highlight(\"").append(word).append("\");");
            }
            sb.append("$(\".highlight\").css({ backgroundColor: \"#FFFF88\" });});</script></body></html>");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (Exception e){
            e.printStackTrace();
            //return ok("sorry!no result for this query.");
            return ok(noresult.render());
        }
        result=sb.toString();
        //play.cache.Cache.set(query,result);
        return ok(result);
    }
    public static Result recompute(String query,String responseUUID,boolean one,boolean two,boolean three,boolean four,boolean five,boolean six,boolean seven,boolean eight,boolean nine,boolean ten){
			boolean[] relevant={one,two,three,four,five,six,seven,eight,nine,ten};
            Relevant r=new Relevant();
            r.query=query;
            r.responseUUID=responseUUID;
            r.relevant=relevant;
        try {
            Socket socket=new Socket("localhost",9997);
            new ObjectOutputStream(socket.getOutputStream()).writeObject(r);
            String content= (String) new ObjectInputStream(socket.getInputStream()).readObject();
            return ok(content);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (Exception e){
            e.printStackTrace();
        }

        return ok(noresult.render());
    }

    public static Result document(int id){
        String content=null;
        try {
            Socket socket=new Socket("localhost",9999);
            new DataOutputStream(socket.getOutputStream()).writeInt(id);
            content= (String) new ObjectInputStream(socket.getInputStream()).readObject();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        response().setContentType("text/html");
        return ok(content);
    }

}
