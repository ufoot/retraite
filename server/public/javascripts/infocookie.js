/* /js/jquery/jquery.plugin.infocookie.js */
/*!
 * jQuery InfoCookie Plugin v1.0
 *
 * Copyright 2015 sreg
 * Released under the MIT license
 * Options:
 *  - class: set class for the cookie notice bar (default "cookie-notice")
 *  - delay: set delay before to appear (default 1000ms)
 *  - cookieDuration: define the max duration (in month) before to appear a new bar if it was first accepted (default 12 months)
 *  - cookieName: the cookie name to remember the acceptation (default "cookie-stop")
 *  - position: "top" or "bottom" -> defines the position of the bar at the top of the screen or at the bottom (default "top")
 *  - validateFollow: true/false -> is equal to a click on "ok" on first document click (default true). A false value specifies a click to button "ok" as mandatory
 * Example:
 *  - $.infoCookie('Les cookies assurent le bon fonctionnement de nos services. En utilisant ces derniers, vous acceptez l\'utilisation des cookies. <a href="" target="_blank">En savoir plus</a>.',{class:"bandeau-cookie"});
 */
(function($) {
	
	$.infoCookie=function(text,options)	{
		var className="cookie-notice",delay=1000,cookieName="cookie-stop",cookieDuration=12,position="top";
		if(typeof options!=="undefined") {
			if(typeof options.class!=="undefined") className=options.class;
			if(typeof options.delay!=="undefined") delay=options.delay;
			if(typeof options.cookieDuration!=="undefined") cookieDuration=options.cookieDuration;
			if(typeof options.cookieName!=="undefined") cookieName=options.cookieName;
			if(typeof options.position!=="undefined") position=options.position;
		}

		var cookies=[];
		document.cookie.split(/; +/g).forEach(function(a) {
			a.replace(/^(.*?)=(.*)$/,function(s,a,b) {
				cookies[a]=b;
				return s;
			});
		});

		var setCookie=function(cookieName,cookieDuration) {
			var date=new Date();
			date.setMonth(date.getMonth()+cookieDuration);
			document.cookie=cookieName+'=true; expires='+date.toUTCString()+'; path=/';
		};

		var display=function(text,className,position) {
			var $div=$(document.createElement("div"));
			$div.css({"display":"none","width":"100%","position":"fixed","zIndex":"255","left":"0"})
			   .addClass(className);
			if(position=="top") $div.css("top","0"); else $div.css("bottom","0");
			$div.html(text);
			var $okButton = $('<input type="button" value="ok"/>');
			$okButton.appendTo($div).click(function() {
				setCookie(cookieName,cookieDuration);
				$div.slideUp();
			});
			$okButton.click(function() {
					setCookie(cookieName,cookieDuration);
				});
			$div.appendTo("body");
			return $div;
		};

		if(typeof cookies[cookieName]==="undefined") {
			var div=display(text,className,position);
			if(delay) setTimeout(function(){div.slideDown("fast");},delay);
			else div.show();
			return true;
		}
		return false;
	};
})(jQuery);


$(function() {
	$.infoCookie('Les cookies assurent le bon fonctionnement du service « Mes démarches retraites, pas à pas ». En poursuivant, vous acceptez l\'utilisation des cookies.',{"class":"bandeau-cookie","position":"top"});
});
