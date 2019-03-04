var tpageSize = 10;//每页的条数
var glo_dataNumber = 10;//当前页的总数据量
var tapageCount = 0;//总页数
var pagerCallBack = null;
var pagerCallBackParam = null;
var pagerEleId = null;

var PageClick = function(pageclickednumber) {
	$("#"+pagerEleId).hide();
	pagerCallBack(pageclickednumber, pagerCallBackParam);
	//initPager(pagerEleId, pageclickednumber, tapageCount, pagerCallBack, pagerCallBackParam);
};

/**
 * @param elementId      页面元素id
 * @param tpageIndex	 当前页
 * @param tapageCountTemp	 总页数
 * @param pagerCallBackTemp  点击页码响应回调方法
 * @param param			 回调方法传递参数
 * @return
 */
function initPager(elementId, tpageIndex, tapageCountTemp, pagerCallBackTemp, param) { //分页
	pagerCallBack = pagerCallBackTemp;
	pagerCallBackParam = param;
	pagerEleId = elementId;
	tapageCount = tapageCountTemp;
	if(!tapageCountTemp){
		$("#"+pagerEleId).hide();
		return false;
	}else{
		$("#"+pagerEleId).show();
	}
	$("#"+pagerEleId).pager({
        pagenumber: tpageIndex,
        pagecount: tapageCount,
        datanumber: glo_dataNumber,
        buttonClickCallback: function(pageclickednumber){
    		PageClick(pageclickednumber, pagerEleId, tapageCount, pagerCallBack, pagerCallBackParam);
    	}
    });
}