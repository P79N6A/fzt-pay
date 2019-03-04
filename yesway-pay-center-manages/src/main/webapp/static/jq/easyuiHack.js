/**
 * PART II EasyUI Bug修复
 */
;
(function($) {
//	$.fn.datagrid.defaults = {
//  title: null,
//  iconCls: null,
//  border: true,
//  width: 'auto',
//  height: 'auto',
//  frozenColumns: null,
//  columns: null,
//  striped: false,
//  method: 'post',
//  nowrap: true,
//  idField: null,
//  url: null,
//	loadMsg:'数据加载中，请稍后...',/////
//  pagination: false,
//  rownumbers: false,
//  singleSelect: false,
//  fit: false,
//  pageNumber: 1,/////
//  pageSize: 10,//////
//  pageList: [10],
//  queryParams: {},
//  sortName: null,
//  sortOrder: 'asc',
//  onLoadSuccess: function(){},
//  onLoadError: function(){},
//  onClickRow: function(rowIndex, rowData){},
//  onDblClickRow: function(rowIndex, rowData){},
//  onSortColumn: function(sort, order){},
//  onSelect: function(rowIndex, rowData){},
//  onUnselect: function(rowIndex, rowData){}
//};
//$.fn.pagination.defaults = {
//        total: 1,
//        pageSize: 10,
//        pageNumber: 1,
//        pageList: [10,20,30,50],
//        loading: false,
//        buttons: null,
//        showPageList: true,
//        showRefresh: true,
//        
//        onSelectPage: function(pageNumber, pageSize){},
//        onBeforeRefresh: function(pageNumber, pageSize){},
//        onRefresh: function(pageNumber, pageSize){},
//        onChangePageSize: function(pageSize){},
//        
//        beforePageText: 'Page',
//        afterPageText: 'of {pages}',
//        displayMsg: 'Displaying {from} to {to} of {total} items'
//};
	//Hack1  调整EasyUI的默认设置
	//datagrid默认设置
	$.fn.datagrid.defaults.loadMsg='数据加载中，请稍后...';
	$.fn.datagrid.defaults.pageSize=10;
	//分页组件设置
	$.fn.pagination.defaults.beforePageText= '第';   
	$.fn.pagination.defaults.showPageList=false;
	$.fn.pagination.defaults.afterPageText='页 共 {pages} 页';   
	$.fn.pagination.defaults.displayMsg='当前显示 {from} - {to} 条记录   共 {total} 条记录';
	//下拉组件
	$.fn.combo.defaults.editable=false;

	//Hack2 修改Datagrid的样式问题
	$.extend($.fn.datagrid.defaults, {
	    onLoadSuccess: function () {
	    	if($(this).datagrid("getRows").length == 0){
				$($(this).siblings().filter(".datagrid-view2").children()[1]).html("<div class='ml10 mt10 tc'>暂无数据记录!</div>");
			}
			//$(".datagrid-header-check :checkbox").attr("checked", false); 
	    }, 
	    onUnselect: function (rowIndex, rowData) { 
	        $(".datagrid-header-check :checkbox").attr("checked", false); 
	    }, 
	    onSelect: function () { 
	        var tThis = $(this); 
	        if (tThis.datagrid("getRows").length == tThis.datagrid("getSelections").length) { 
	            $(".datagrid-header-check :checkbox").attr("checked", true); 
	        } 
	        return true; 
	    }, 
	    loadFilter: function (data) { 
	        for (var i = 0; i < data.rows.length; i++) { 
	               for (var att in data.rows[i]) { 
	                   if (typeof (data.rows[i][att]) == "string") { 
	                       data.rows[i][att] = data.rows[i][att].replace(/</g, "&lt;").replace(/>/g, "&gt;"); 
	                   } 
	               } 
	        } 
	        return data; 
	    } 

	}); 
	
})(jQuery);


