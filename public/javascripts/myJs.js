(function() {
	var clicked=false;
	var browsingtimes=[0,0,0,0,0,0,0,0,0,0,0];
	var lastClickedLink=0;
	var lastTime=new Date().getTime();
	$(function () {
		$(".search-query").css("height","40px").css("width","300px").css("margin-top","15px");
		$(".relevantForm").hide();
		$("a").click(function() {
			  clicked=true;
			  lastClickedLink=parseInt($(this).attr('anchorid'));
			  window.open($(this).attr("href"));
			  return false;
		});

		$('.switch').on('switch-change', function () {
			var isOn=$(this).bootstrapSwitch('status');
			var switchid=$(this).attr("switchid");
			if (isOn){
				$($(".checkbox")[switchid-1]).attr("checked","checked");
			} else{
				$($(".checkbox")[switchid-1]).removeAttr("checked");
			}
		});
	});

	var isVisible = false;
	function onVisible() {
		isVisible = true;
		alert("efefw");
	}
	var startTimer=function() {
		lastTime=new Date().getTime();
	};

	var stopTimer=function() {
		browsingtimes[lastClickedLink]=new Date().getTime()-lastTime;
		if(browsingtimes[lastClickedLink]>2000){
			$($(".checkbox")[lastClickedLink-1]).attr("checked","checked");
			$($(".switch")[lastClickedLink-1]).bootstrapSwitch('setState', true);;
		}		
	};

	//browser compatible page visibility example. http://www.w3.org/TR/2011/WD-page-visibility-20110602/
	(function() {
	    var hidden = "hidden";
	 
	    // Standards:
	    if (hidden in document){
	        document.addEventListener("visibilitychange", onchange);
	    }
	    else if ((hidden = "mozHidden") in document){
	        document.addEventListener("mozvisibilitychange", onchange);
	    }
	    else if ((hidden = "webkitHidden") in document){
	        document.addEventListener("webkitvisibilitychange", onchange);
	    }
	    else if ((hidden = "msHidden") in document){
	        document.addEventListener("msvisibilitychange", onchange);
	    }
	    // IE 9 and lower:
	    else if ('onfocusin' in document){
	        document.onfocusin = document.onfocusout = onchange;
	    }
	    // All others:
	    else{
	        window.onpageshow = window.onpagehide 
	            = window.onfocus = window.onblur = onchange;
	    }
	 
	    function onchange (evt) {
	        var result,v = 'visible', h = 'hidden',
	            evtMap = { 
	                focus:v, focusin:v, pageshow:v, blur:h, focusout:h, pagehide:h 
	            };
	 
	        evt = evt || window.event;
	        if (evt.type in evtMap){
	            result = evtMap[evt.type];
	        }
	        else{        
	            result = this[hidden] ? "hidden" : "visible";
	        }
	        
	        document.title = result;

	        if (result=="hidden") startTimer();
	        if (result=="visible") stopTimer();
	    }
	})();
})();