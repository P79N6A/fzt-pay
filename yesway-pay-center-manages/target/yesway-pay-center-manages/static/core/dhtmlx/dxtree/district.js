
function chtmlShowWin(htmlObject,arryPro, type) {
	this.parentObject;
	this.fnName=null;
	if (typeof (htmlObject) != "object") {
		this.parentObject = document.getElementById(htmlObject);
	} else {
		this.parentObject = htmlObject;
	}
	this.id = this.parentObject.id;
	if (arryPro == null) {
	}
	this.HTMLstr = "";
	this.HTMLstr += "<!-- RightButton PopMenu -->\n";
	this.HTMLstr += "\n";
	this.HTMLstr += "<!-- PopMenu Starts -->\n";
	this.showDiv=new chtmlNewDivDisplayNone(this,arryPro);
	
	this.parentObject.onmouseover=function(e){
		var e = (e || event);
		if (!e.srcElement) {
			var z = e.target;
		} else {
			z = e.srcElement;
		}
		this.rootNode.getShowDiv(e,z);
	}
	this.parentObject.onmouseout=function(e){
		var e = (e || event);
		if (!e.srcElement) {
			var z = e.target;
		} else {
			z = e.srcElement;
		}
		this.rootNode.getDivNone(e,z);
	}
	this.parentObject.node="yes";
	this.parentObject.rootNode=this;
	
	return this;
}
function chtmlNewDivDisplayNone(parent,arryPro){
	this.divTag=document.createElement("div");
	this.isDisplay=false;
	this.divTag.id=parent.id+"div_root";
	//样式
	this.divTag.className="div_ini";
	this.divTag.node="yes";
	/*
	this.divTag.style.display="none";
	this.divTag.style.position="absolute";
	this.divTag.style.width= "150px";
	this.divTag.style.height= "100px";
	this.divTag.style.background="red";
	this.divTag.style.padding="4px";
	this.divTag.style.zIndex=10000;
	this.divTag.style.border="2px groove pink";
	this.divTag.node="";
	
	var HTMLstr ="";
	HTMLstr += "<table onmouseover='cancelBubble()' onmouseout='cancelBubble()'  width='100%' height='100%' style=' BORDER-BOTTOM: #a6a6a6 1px solid; BORDER-LEFT: #a6a6a6 1px solid;BORDER-TOP: #a6a6a6 1px solid; BORDER-RIGHT: #a6a6a6 1px solid'>";
	HTMLstr += "<tr><td>\u5317\u4eac</td><td>\u4e0a\u6d77</td><td>\u5317\u4eac</td><td>\u4e0a\u6d77</td></tr>";
	HTMLstr += "\t\t<tr><td>\u5317\u4eac</td><td>\u4e0a\u6d77</td><td>\u5317\u4eac</td><td>\u4e0a\u6d77</td></tr>";
	HTMLstr += "\t\t<tr><td>\u5317\u4eac</td><td>\u4e0a\u6d77</td><td>\u5317\u4eac</td><td>\u4e0a\u6d77</td></tr>";
	HTMLstr += "\t\t<tr><td>\u5317\u4eac</td><td>\u4e0a\u6d77</td><td>\u5317\u4eac</td><td>\u4e0a\u6d77</td></tr>";
	HTMLstr += "\t\t<tr><td>\u5317\u4eac</td><td>\u4e0a\u6d77</td><td>\u5317\u4eac</td><td>\u4e0a\u6d77</td></tr>";
	HTMLstr += "\t\t<tr><td>\u5317\u4eac</td><td>\u4e0a\u6d77</td><td>\u5317\u4eac</td><td>\u4e0a\u6d77</td></tr>";
	HTMLstr += "\t\t<tr><td>\u5317\u4eac</td><td>\u4e0a\u6d77</td><td>\u5317\u4eac</td><td>\u4e0a\u6d77</td></tr>";
	HTMLstr += "\t</table>";
	*/
	var divCont=document.createElement("div");
	divCont.calssName="divTag_ini";
	
	//组装--这段代码可以重新写。这样就能支持更多的类似的;以后需要支持xml的数据
	//和json数据
	//循环组装.
	if(arryPro!=null){
		var len=arryPro.length;
		if(len%3==0){
			var col=len/3;
		}else{
			col=len/3+1;
		}
		var index=0;
		for(var i=0;i<col;i++){
			var ul=document.createElement("ul");
			divCont.appendChild(ul);
			var max=3;
			if(i==(col-1)){
				max=len%3;
			}
			for(var n=0;n<max;n++){
				var li=document.createElement("li");
				var span=document.createElement("span");
				var aTag=document.createElement("a");
				ul.appendChild(li);
				li.appendChild(span);
				span.appendChild(aTag);
				aTag.innerHTML=arryPro[index++];
			}
			
		}
	}
//	this.divTag.appendChild(divCont);
	this.parentObject=parent;
	this.divTag.onmouseover=getmove;
	this.divTag.onmouseout=function(e){
			var e=(e || event);
			var obj=e.srcElement;
			if(!obj){
				obj=e.target;
			}
			getNoneDiv(obj);
			
	}
		
	//这个步骤就是加载在body中
	
	document.body.appendChild(this.divTag);
	this.divTag.divNode=this;
	return this;
}
function getmove(e){
		var e=(e || event);
		var obj=e.srcElement;
		if(!obj){
			obj=e.target;
		}
		document.body.onmousemove=getmove;
		getNewDiv(obj);
	}
function getNewDiv(z) {
	if(z.node){
		var fn=z.divNode.parentObject.fnName;
		window.clearTimeout(fn);
		z.divNode.parentObject.fnName=window.setTimeout("displayNone("+z.id+")",0);
	}else{
		getNewDiv(z.parent);
	}
};
function getNoneDiv(z) {
	if(z.node){
		displayNone(z.divNode.parentObject);
	}
};

function nomouse(o) {
	o.onmouseover=cancelBubble;
	o.onmouseout=cancelBubble;	
}
function cancelBubble(e) {
	if ( e && e.stopPropagation ){
	  e.stopPropagation(); 
	}
	else{
	window.event.cancelBubble = true;
	}
}
chtmlShowWin.prototype.getMenute = function () {
	return this.HTMLstr;
};

function displayNone(object) {
	var divWin=document.getElementById(object.id+"div_root");
	if(divWin){
		
		var node=divWin.divNode;
		if(node.isDisplay){
			node.isDisplay=false;
			divWin.style.display="none";
		}
	}
};
chtmlShowWin.prototype.getDivNone = function(e,z){
	if(z.node){
		window.clearTimeout(z.rootNode.fnName);
		z.rootNode.fnName=window.setTimeout("displayNone("+this.id+")",50);
	}else{
		var parent=z.parentElement;
		this.getDivNone(e,parent);
	}
};

chtmlShowWin.prototype.getShowDiv = function (e,z) {
		if(z.node){
			var id=this.id;
			var divWin=document.getElementById(id+"div_root");
			if(divWin){
				var node=divWin.divNode;
				if(!node.isDisplay){
				var fn=node.parentObject.fnName;
				window.clearTimeout(fn);
				node.isDisplay=true;
				var x=e.clientX;
				var y=e.clientY;
				var ox=e.offsetX;
				var oy=e.offsetY;
				var oWidth=z.offsetWidth;
				var oHeight=z.offsetHeight;
				divWin.style.display="block";
				divWin.style.left =x-ox+"px";
				divWin.style.top = y+oHeight-oy + "px";
				}
			}
		}else{
			var parent=z.parentElement;
			this.getShowDiv(e,parent);
		}
};
