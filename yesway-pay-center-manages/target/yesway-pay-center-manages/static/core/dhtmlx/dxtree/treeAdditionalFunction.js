
/**
 * The Author: ziming.xu
 */
function Trees() {
	this.selectedArray=new Array();
	this.iniStaticBasicTreeXML = function (context,div,width,heigth) {
		var tree = new dhtmlXTreeObject(div, width, heigth, 0);
		tree.setSkin("dhx_skyblue");
		tree.setImagePath(context + "/core/dhtmlx/imgs/");
		tree.enableDragAndDrop(false);
		tree.enableMercyDrag(false);
		tree.enableDivCheck(false);
		tree.enableDivCheckDel(false);
		tree.enableMenuBoolean(false);
		tree.openAllItems("1_root");
		return tree;
		}
	this.iniTreeXML = function (context,div,width,heigth) {
		var tree = new dhtmlXTreeObject(div, width, heigth, 0);
		tree.setSkin("dhx_skyblue");
		tree.setImagePath(context + "/core/dhtmlx/imgs/");
		tree.enableDragAndDrop(false);
		tree.enableMercyDrag(false);
		tree.enableDivCheck(false);
		tree.enableDivCheckDel(false);
		tree.enableMenuBoolean(false);
		tree.openAllItems("1_root");
		return tree;
		}
	this.newAddRootTree = function (context,id,name) {
		var tree = new dhtmlXTreeObject("treeDiv", "100%", "100%", 0);
		tree.setSkin("dhx_skyblue");
		tree.setImagePath(context + "/core/dhtmlx/imgs/");
		tree.enableDragAndDrop(true);
		tree.enableMercyDrag(false);
		tree.enableSefDarg("tree");
		tree.enableDivCheck(false);
		tree.enableDivCheckDel(false);
		tree.enableMenuBoolean(true);
		
		tree.setOnClickHandler(function(data){
			tree.asXML(data);
		});
		tree.insertNewItem(0,id,name);
		return tree;
		}
	this.iniCatalogTree = function (context) {
		var tree = new dhtmlXTreeObject("treeDiv", "100%", "100%", 0);
		tree.setSkin("dhx_skyblue");
		tree.setImagePath(context + "/core/dhtmlx/imgs/");
		tree.enableDragAndDrop(true);
		tree.enableMercyDrag(false);
		tree.enableSefDarg("treeDiv");
		tree.enableDivCheck(true);
		tree.enableDivCheckDel(false);
		tree.enableMenuBoolean(false);
		return tree;
		}
	this.iniStaticLeftTreeXML = function (context,dataXML) {
		var tree = new dhtmlXTreeObject("divData", "100%", "100%", 0);
		tree.setSkin("dhx_skyblue");
		tree.setImagePath(context + "/core/dhtmlx/imgs/");
		tree.enableDragAndDrop(true);
		tree.enableMercyDrag(true);
		tree.enableSefDarg("sourceXml");
		tree.enableDivCheck(true);
		tree.enableDivCheckDel(true);
		tree.enableMenuBoolean(false);
		tree.loadXMLString(dataXML);
		tree.setOnClickHandler(function(data){
			tree.asXML(data);
		});
		return tree;
		}
		/*
		Tree.tree.setOnRightClickHandler(function(){
			alert("右键测试");
		});*/
		this.iniStaticRightTreeXML = function (context,dataXML) {
			var tree = new dhtmlXTreeObject("treeDiv", "100%", "100%", 0);
			tree.setSkin("dhx_skyblue");
			tree.setImagePath(context + "/core/dhtmlx/imgs/");
			tree.enableDragAndDrop(true);
			tree.enableMercyDrag(false);
			tree.enableSefDarg("tree");
			tree.enableDivCheck(false);
			tree.enableDivCheckDel(false);
			tree.enableMenuBoolean(true);
			tree.loadXMLString(dataXML);
			tree.setOnClickHandler(function (data) {
				tree.asXML(data);
			});
			return tree;
		}
		
		this.iniCheckBasicTreeXML = function (context,div,width,heigth) {
			var tree = new dhtmlXTreeObject(div, width, heigth, 0);
			tree.setSkin("dhx_skyblue");
			tree.setImagePath(context + "/core/dhtmlx/imgs/");
			tree.enableCheckBoxes(true);
			tree.enableThreeStateCheckboxes(true);
			tree.openAllItems("1_root");
			return tree;
		}
		this.iniStaticTreeXML = function (context,dataXML) {
			var tree = new dhtmlXTreeObject("divData", "100%", "100%", 0);
			tree.setSkin("dhx_skyblue");
			tree.setImagePath(context + "/core/dhtmlx/imgs/");
			tree.enableDragAndDrop(false);
			tree.enableMercyDrag(false);
			tree.enableSefDarg("tree");
			tree.enableDivCheck(false);
			tree.enableDivCheckDel(false);
			tree.enableMenuBoolean(true);
			tree.loadXMLString(dataXML);
			return tree;
		}
}

