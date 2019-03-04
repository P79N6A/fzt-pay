(function($) {
//	var pagerJS_src = document.getElementById("pagerJS").src;
//	var param = pagerJS_src.substring(pagerJS_src.indexOf('?') + 1,
//			pagerJS_src.length);
	var number = 10;
//	if (param == 'number=null') {
//		number = 9;
//	} else {
//		number = parseInt(param.split('=')[1]);
//	}
	$.fn.pager = function(options) {
		var opts = $.extend({}, $.fn.pager.defaults, options);
		return this.each(function() {
			// empty out the destination element and then render out the pager
			// with the supplied options
			$(this).empty().append(
					renderpager(parseInt(options.pagenumber),
							parseInt(options.pagecount),
							options.buttonClickCallback));
			// specify correct cursor activity
			$('.pages li').mouseover(function() {
				document.body.style.cursor = "pointer";
			}).mouseout(function() {
				document.body.style.cursor = "auto";
			});
		});
	};

	// render and return the pager with the supplied options
	function renderpager(pagenumber, pagecount, buttonClickCallback) {
		// setup $pager to hold render
		var $pager = $('<ul class="pagination"></ul>');
		// add in the previous and next buttons
		// $pager.append(renderButton('first', pagenumber, pagecount,
		// buttonClickCallback)).append(renderButton('prev', pagenumber,
		// pagecount, buttonClickCallback));
		//if (pagenumber != 1) {
			$pager.append(renderButton('上一页', pagenumber, pagecount,
					buttonClickCallback));
		//}
		// pager currently only handles 10 viewable pages ( could be easily
		// parameterized, maybe in next version ) so handle edge cases
		var startPoint = 1;
		var endPoint = number;
		if (number % 2 != 0) {
			if (pagenumber > (number - 1) / 2) {

				var temp2 = (number - 1) / 2;
				startPoint = pagenumber - temp2;
				endPoint = pagenumber + temp2;

			}
		} else {
			if (pagenumber > (number / 2 - 1)) {
				var temp1 = number / 2 - 1;
				startPoint = pagenumber - temp1;
				endPoint = pagenumber + temp1 + 1;
			}
		}
		if (endPoint > pagecount) {
			// if(number%2!=0){
			startPoint = pagecount - (number - 1);
			// }else
			// {
			// startPoint = pagecount - number;
			// }
			endPoint = pagecount;
		}
		if (startPoint < 1) {
			startPoint = 1;
		}
		// loop thru visible pages and render buttons
		for ( var page = startPoint; page <= endPoint; page++) {
			var currentButton = $('<li pageNumber="' + page + '"><a href="javascript:void(0)">' + (page) + '</a></li>');
			page == pagenumber ? currentButton.addClass('active')
					: currentButton.click(function() {
						buttonClickCallback($(this).attr("pageNumber"));
					});
			currentButton.appendTo($pager);
		}
		// render in the next and last buttons before returning the whole
		// rendered control back.
		// $pager.append(renderButton('next', pagenumber, pagecount,
		// buttonClickCallback)).append(renderButton('last', pagenumber,
		// pagecount, buttonClickCallback));
		//if (pagenumber != pagecount) {
			$pager.append(renderButton('下一页', pagenumber, pagecount,
					buttonClickCallback));
		//}

		return $pager;
	}

	// renders and returns a 'specialized' button, ie 'next', 'previous' etc.
	// rather than a page number button
	function renderButton(buttonLabel, pagenumber, pagecount,
			buttonClickCallback) {
		var $Button = $('<li><a href="javascript:void(0)">' + buttonLabel + '</a></li>');
		var destPage = 1;
		// work out destination page for required button type
		switch (buttonLabel) {
		// case "first":
		// destPage = 1;
		// break;
		case "上一页":
			$Button.find("a").attr("aria-label", "Previous");
			destPage = pagenumber - 1;
			break;
		case "下一页":
			$Button.find("a").attr("aria-label", "Next");
			destPage = pagenumber + 1;
			break;
		// case "last":
		// destPage = pagecount;
		// break;
		}
		// disable and 'grey' out buttons if not needed.
		if (buttonLabel == "上一页") {
			pagenumber <= 1 ? $Button.addClass('disabled') : $Button
					.click(function() {
						buttonClickCallback(destPage);
					});
		} else {
			pagenumber >= pagecount ? $Button.addClass('disabled') : $Button
					.click(function() {
						buttonClickCallback(destPage);
					});
		}
		return $Button;
	}
	// pager defaults. hardly worth bothering with in this case but used as
	// placeholder for expansion in the next version
	$.fn.pager.defaults = {
		pagenumber : 1,
		pagecount : 1
	};
})(jQuery);
