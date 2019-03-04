//每次只显示5个页码
$(function($) {
    //设定页码方法，初始化
    $.fn.setPager = function(options) {
        var opts = $.extend({}, pagerDefaults, options);
        return this.each(function() {
            $(this).empty().append(setPagerHtml(opts));
        });
    };
    //设定页数及html
    function setPagerHtml(opts) {
		var RecordCount=opts.RecordCount;
		var PageIndex=parseInt(opts.PageIndex);
		var pagerClick=opts.buttonClick;
        var $content = $("<div class=\"pagingCon\"></div>");
        var startPageIndex = 1;
        //若页码超出
        if (RecordCount <= 0) RecordCount = opts.PageSize;
        //末页
        var endPageIndex = parseInt(RecordCount % parseInt(opts.PageSize)) > 0 ? parseInt(RecordCount / parseInt(opts.PageSize)) + 1 : RecordCount / parseInt(opts.PageSize)

        if (PageIndex > endPageIndex) PageIndex = endPageIndex;
        if (PageIndex <= 0) PageIndex = startPageIndex;
        var nextPageIndex = PageIndex + 1;
        var prevPageIndex = PageIndex - 1;
        if (PageIndex == startPageIndex) {
            $content.append($("<span class=\"begin\">首页</span>"));
            $content.append($("<span class=\"prev\">上一页</span>"));
        } else {
            $content.append(renderButton(RecordCount, 1, pagerClick, "begin", "首页"));
            $content.append(renderButton(RecordCount, prevPageIndex, pagerClick, "prev", "上一页"));
        }
        //这里判断是否显示页码
        if (opts.ShowPageNumber) {
            // var html = "";
            //页码部分隐藏 只显示中间区域
            if (endPageIndex <= 5 && PageIndex <= 5) {
                for (var i = 1; i <= endPageIndex; i++) {
                    if (i == PageIndex) {
                        $content.append($("<span class=\"current\">" + i + "</span>"));
                    } else {
                        $content.append(renderButton(RecordCount, i, pagerClick, "", i));
                    }
                }
            } else if (endPageIndex > 5 && endPageIndex - PageIndex <= 2) {
                $content.append($("<span class=\"more\">...</span>"));
                for (var i = endPageIndex - 4; i <= endPageIndex; i++) {
                    if (i == PageIndex) {
                        $content.append($("<span class=\"current\">" + i + "</span>"));
                    } else {
                        $content.append(renderButton(RecordCount, i, pagerClick, "", i));
                    }
                }
            } else if (endPageIndex > 5 && PageIndex > 3) {
                $content.append($("<span class=\"more\">...</span>"));
                for (var i = PageIndex - 2; i <= PageIndex + 2; i++) {
                    if (i == PageIndex) {
                        $content.append($("<span class=\"current\">" + i + "</span>"));
                    } else {
                        $content.append(renderButton(RecordCount, i, pagerClick, "", i));
                    }
                }
                $content.append($("<span class=\"more\">...</span>"));

            } else if (endPageIndex > 5 && PageIndex <= 3) {
                for (var i = 1; i <= 5; i++) {
                    if (i == PageIndex) {
                        $content.append($("<span class=\"current\">" + i + "</span>"));
                    } else {
                        $content.append(renderButton(RecordCount, i, pagerClick, "", i));
                    }
                }
                $content.append($("<span class=\"more\">...</span>"));
            }
        }
        if (PageIndex == endPageIndex) {
            $content.append($("<span class=\"next\">下一页</span>"));
            $content.append($("<span class=\"end\">末页</span>"));
        } else {
            $content.append(renderButton(RecordCount, nextPageIndex, pagerClick, "next", "下一页"));
            $content.append(renderButton(RecordCount, endPageIndex, pagerClick, "end", "末页"));
        }
		if(opts.ShowSkip){
			var $skipTxt=$('<span class="txt pl10">跳转至</span>');
			var $pageTxt=$('<span class="txt">页</span>');
			var $pageVal=$('<input type="text" class="text" value="'+PageIndex+'" />');
			var $determineBtn=$('<a href="javascript:;" class="btn">确定</a>');
			$content.append($skipTxt);
			$content.append($pageVal);
			$content.append($pageTxt);
			$content.append($determineBtn);
			$pageVal.keyup(function(){
				var num = $(this).val();
				// 如果不为数字时强制转换
				if(num == ''|| /\D+/.test(num)){
					num = 1;
					$(this).val(num);
					return false;
				}
			});
			$determineBtn.click(function(){
				var pageVal=parseInt($pageVal.val());
				if(isNaN(pageVal)){
					pageVal = 1;
				}
				if(pageVal<1){
					pageVal=1;
				}
				if(pageVal>endPageIndex){
					pageVal=endPageIndex;
				}
				pagerClick(RecordCount,pageVal);
			});
		}
        return $content;
    }
    function renderButton(recordCount, goPageIndex, EventHander, pagerClass, text) {
		if(pagerClass == ""){
			var $goto = $("<a title=\"第" + goPageIndex + "页\" href=\"###\">" + text + "</a>");
		}else{
			var $goto = $("<a title=\"第" + goPageIndex + "页\" href=\"###\" class=\"" + pagerClass + "\">" + text + "</a>");
		}
        $goto.click(function() {
            EventHander(recordCount, goPageIndex);
        });
        return $goto;
    }
    var pagerDefaults = {
        DefaultPageCount: 1,
        DefaultPageIndex: 1,
        PageSize: 10,
        ShowPageNumber: true, //是否显示页码
		ShowSkip: false 
    };
})