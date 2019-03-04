if (!Catt)
	var Catt = {};
Catt.Layout = function(elements) {
	this.elements = elements;
	this.doLayouts();
	var the = this;
	if(window.attachEvent){
		window.attachEvent("onresize", function() {
				the.doLayouts();
			});
	}else if(window.addEventListener){
//		window.addEventListener("resize", function() {
//				the.doLayouts();
//			});
	}
	
}
Catt.Layout.prototype.doLayouts=function(){
	for (var i = 0; i < this.elements.length; i++) {
		var element = this.elements[i];
		this.doLayout(element);
	}
}
Catt.Layout.prototype.doLayout = function(element, type) {
	var config = this.parseAttribute(element.getAttribute('layout'));
	var type=config.type || type;
	switch (config.type) {
		case 'viewport' :
			element.className = "layout_viewport";
			//element.style.width = document.documentElement.clientWidth;
			//element.style.height = document.documentElement.clientHeight;
			// 得到包含他的li的高度
//			alert( document.documentElement.clientHeight);
			element.style.width = document.getElementById("content_table").clientWidth -(document.getElementById("content_table").clientWidth * 0.05) ;
			document.getElementById("content_table").style.height = document.documentElement.clientHeight-60 - 60;
			element.style.height = document.documentElement.clientHeight-60 - 60;
			break;
		case 'viewport_m' :
			element.className = "layout_viewport";
			element.style.width = document.getElementById("content_table").clientWidth -(document.getElementById("content_table").clientWidth * 0.002) ;
			document.getElementById("content_table").style.height = document.documentElement.clientHeight-60 - 60;
			element.style.height = document.documentElement.clientHeight-60 - 60;
			break;
		default :
			element.style.position="absolute";
			break;
	}
	//do cols and rows config
	var totalWidth=element.style.width||element.offsetWidth;
	totalWidth=parseInt(totalWidth);
	var totalWidthPrecent = 100;
	var totalHeight=element.style.height||element.offsetHeight;
	totalHeight=parseInt(totalHeight);
	var totalHeightPrecent = 100;
	var cols,rows;
	var childs=element.children;
	if(config.cols){
		cols=config.cols.split(",");
		for (var i = 0; i < cols.length; i++) {
			var a = cols[i];
			if (a.indexOf("%") != -1) {
				totalWidthPrecent -= parseInt(a);
			} else if (a.indexOf("*") != -1) {
	
			} else {
				totalWidth -= parseInt(a);
			}
		}
		var colsOffset=0;
		for (var i = 0; i < cols.length; i++) {
			if(!childs[i])break;
			childs[i].style.position="absolute";
			childs[i].style.left=colsOffset;
			childs[i].style.top=0;
			var a = cols[i];
			var w =0;
			if (a.indexOf("%") != -1) {
				w=totalWidth * parseInt(a) / 100 ;
			} else if (a.indexOf("*") != -1) {
				var b_width = totalWidth * totalWidthPrecent / 100 ;
				if(b_width>=0) w=b_width;
				else{
					//alert(cols);
				}
			} else {
				w = parseInt(a);
			}
			childs[i].style.width =w ;
			colsOffset+=w;
			//edit by zjh 2010-06-02
			if(childs[i].id != "main_table"){
				childs[i].style.height=element.style.height;
			}
		}
	}else if(config.rows){
		rows=config.rows.split(",");
		for (var i = 0; i < rows.length; i++) {
			var a = rows[i];
			if (a.indexOf("%") != -1) {
				totalHeightPrecent -= parseInt(a);
			} else if (a.indexOf("*") != -1) {
	
			} else {
				totalHeight -= parseInt(a);
			}
		}
		var rowsOffset=0;
		for (var i = 0; i < rows.length; i++) {
			if(!childs[i])break;
			childs[i].style.position="absolute";
			childs[i].style.left=0;
			childs[i].style.top=rowsOffset;
			var a = rows[i];
			var h=0;
			if (a.indexOf("%") != -1) {
				h = totalHeight * parseInt(a) / 100;
			} else if (a.indexOf("*") != -1) {
				var b_height = totalHeight * totalHeightPrecent / 100 ;
				if(b_height>=0) h=b_height;
				else{
					//alert(rows);
				}
			} else {
				h = parseInt(a) ;
			}
			childs[i].style.height=h;
			rowsOffset+=h;
			childs[i].style.width=element.style.width;
		}
	}
	//do childNodes
	//element.childNodes.length;
	var len=childs.length;
	for(var i=0;i<len;i++){
		if(childs[i].layout){
			this.doLayout(childs[i],type);
		}
	}

}

Catt.Layout.prototype.doLayoutByCols = function(element,cols) {

	var totalWidth = element.style.width || element.offsetWidth;
	totalWidth = parseInt(totalWidth);
	if (element == document.body) {
		totalWidth = document.documentElement.clientWidth;
	}
	//alert(totalWidth);
	var totalWidthPrecent = 100;
	var rr = cols.split(",");
	for (var i = 0; i < rr.length; i++) {
		var a = rr[i];
		if (a.indexOf("%") != -1) {
			totalWidthPrecent -= parseInt(a);
		} else if (a.indexOf("*") != -1) {

		} else {
			totalWidth -= parseInt(a);
		}
	}
	for (var i = 0; i < rr.length&&i<element.childNodes.length; i++) {
		var a = rr[i];
		var b = element.childNodes[i].style;
		if (a.indexOf("%") != -1) {
			b.width = totalWidth * parseInt(a) / 100 + "px";
		} else if (a.indexOf("*") != -1) {
			var b_width = totalWidth * totalWidthPrecent / 100 + "px";
			if(b_width>=0)b.width=b_width;
			else{
				//alert(cols);
			}
		} else {
			b.width = parseInt(a) + "px";
		}
		//alert(b.width);
		var c = element.childNodes[i].parentNode;
		b.height=c.style.height||c.offsetHeight;
	}

}
Catt.Layout.prototype.doLayoutByRows = function(element, rows) {
	var totalHeiht = element.style.height || element.offsetHeight;
	totalHeiht = parseInt(totalHeiht);
	if (element == document.body) {
		totalHeiht = document.documentElement.clientHeight;
	}
	var totalHeightPrecent = 100;
	var rr = rows.split(",");
	for (var i = 0; i < rr.length; i++) {
		var a = rr[i];
		if (a.indexOf("%") != -1) {
			totalHeightPrecent -= parseInt(a);
		} else if (a.indexOf("*") != -1) {

		} else {
			totalHeiht -= parseInt(a);
		}
	}
	for (var i = 0; i < rr.length; i++) {
		var a = rr[i];
		var b = element.childNodes[i].style;
		if (a.indexOf("%") != -1) {
			b.height = totalHeiht * parseInt(a) / 100 + "px";
		} else if (a.indexOf("*") != -1) {
			b.height = totalHeiht * totalHeightPrecent / 100 + "px";
		} else {
			b.height = parseInt(a) + "px";
		}
		b.width=element.style.width || element.offsetWidth;
	}
}
Catt.Layout.prototype.parseAttribute = function(attribute) {
	var attributeObject = {};
	var attributes = attribute.split(";");
	for (var i = 0; i < attributes.length; i++) {
		var a = attributes[i].split(":");
		var name = a[0];
		var value = a[1];
		attributeObject[name] = value;
	}
	return attributeObject;
}
if($==null){
	var $=document.getElementById;
}
window.attachEvent("onload",function(){
	var viewport=document.getElementById("viewport");
	if(viewport!=null)
		new Catt.Layout([viewport]);

});
