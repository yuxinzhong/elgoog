$(function () {
    window.loadheight = $('#hook').height();
    window.hidden = $("#hook").animate("marginTop", "-" + loadheight + "px");
    window.visible = $("#hook").animate("marginTop", "0px");
    $("#hook").css("marginTop", "-" + loadheight + "px")
});
var doSomeThing=function(){
  var data={ query: $(".query").attr("value"), responseUUID: $(".responseUUID").attr("value")};
  var englishNumber=['zero','one','two','three','four','five','six','seven','eight','nine','ten'];
  var switches=$(".switch");
  var anySwitchOn=false;
  for (var i = 0; i < switches.length; i++) {
    if($(switches[i]).bootstrapSwitch('status')){
      data[englishNumber[i+1]]='true';
      anySwitchOn=true;
    }
  };  
  if(anySwitchOn){
      $.ajax({
        type: "GET",
        url: window.location.origin+"/recompute",
        data: data
      }).done(function( msg ) {
        window.location.href=(window.location.origin+"/search?query="+msg);
        $(html).load(window.location.href);
      });
  }
  else{
    window.location.reload();
  }

};

$(function hook() {
    var loadheight = $('#hook').height();
    $(window).scroll(function (event) {
        var st = $(window).scrollTop();
        if (st <= 0) {
            $("#hook").animate({
                "marginTop": "0px"
            }, 200);
            $("#hook").delay(500).slideUp(220, function () {
                doSomeThing();
            });
        }
        if ($.browser.webkit) {
            if (st == 0) {
                $('body').css('overflow', 'hidden')
            }
        }
    })
});


//Browser detection, unsupported in jQuery latest.
(function () {
    var matched, browser;
    jQuery.uaMatch = function (ua) {
        ua = ua.toLowerCase();
        var match = /(chrome)[ \/]([\w.]+)/.exec(ua) || /(webkit)[ \/]([\w.]+)/.exec(ua) || /(opera)(?:.*version|)[ \/]([\w.]+)/.exec(ua) || /(msie) ([\w.]+)/.exec(ua) || ua.indexOf("compatible") < 0 && /(mozilla)(?:.*? rv:([\w.]+)|)/.exec(ua) || [];
        return {
            browser: match[1] || "",
            version: match[2] || "0"
        }
    };
    matched = jQuery.uaMatch(navigator.userAgent);
    browser = {};
    if (matched.browser) {
        browser[matched.browser] = true;
        browser.version = matched.version
    }
    if (browser.chrome) {
        browser.webkit = true
    } else if (browser.webkit) {
        browser.safari = true
    }
    jQuery.browser = browser;
    jQuery.sub = function () {
        function jQuerySub(selector, context) {
            return new jQuerySub.fn.init(selector, context)
        }
        jQuery.extend(true, jQuerySub, this);
        jQuerySub.superclass = this;
        jQuerySub.fn = jQuerySub.prototype = this();
        jQuerySub.fn.constructor = jQuerySub;
        jQuerySub.sub = this.sub;
        jQuerySub.fn.init = function init(selector, context) {
            if (context && context instanceof jQuery && !(context instanceof jQuerySub)) {
                context = jQuerySub(context)
            }
            return jQuery.fn.init.call(this, selector, context, rootjQuerySub)
        };
        jQuerySub.fn.init.prototype = jQuerySub.fn;
        var rootjQuerySub = jQuerySub(document);
        return jQuerySub
    }
})();