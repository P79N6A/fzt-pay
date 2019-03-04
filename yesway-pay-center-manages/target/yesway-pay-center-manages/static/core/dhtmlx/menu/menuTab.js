
function RightMenu() {
	
	this.AddExtendMenu = AddExtendMenu;
	this.AddItem = AddItem;
	this.AddItem1 = AddItem1;
	this.GetMenu = GetMenu;
	this.HideAll = HideAll;
	this.I_OnMouseOver = I_OnMouseOver;
	this.I_OnMouseOut = I_OnMouseOut;
	this.I_OnMouseUp = I_OnMouseUp;
	this.P_OnMouseOver = P_OnMouseOver;
	this.P_OnMouseOut = P_OnMouseOut;
	A_rbpm = new Array();
	
	HTMLstr = "";
	HTMLstr += "<!-- RightButton PopMenu -->\n";
	HTMLstr += "\n";
	HTMLstr += "<!-- PopMenu Starts -->\n";
	HTMLstr += "<div id='E_rbpm' class='menu_div_rm'>\n";
                        // rbpm = right button pop menu
	HTMLstr += "<table width='100%' border='0' cellspacing='0'>\n";
	HTMLstr += "<tr>";
	HTMLstr += "<td height='10' width='100%' style='padding: 0px 0px;margin:0px;' valign='bottom'>\n";
	HTMLstr += "<table width='100%' border='0' cellspacing='0'>\n";
	HTMLstr += "<!-- Insert A Extend Menu or Item On Here For E_rbpm -->\n";
	HTMLstr += "</table></td></tr></table>\n";
	HTMLstr += "</div>\n";
	HTMLstr += "<!-- Insert A Extend_Menu Area on Here For E_rbpm -->";
	HTMLstr += "\n";
	HTMLstr += "<!-- PopMenu Ends -->\n";
}
function AddExtendMenu(id, img, wh, name, parent) {
	var TempStr = "";
	eval("A_" + parent + ".length++");
	eval("A_" + parent + "[A_" + parent + ".length-1] = id");  // 将此项注册到父菜单项的ID数组中去
	TempStr += "<div id='E_" + id + "' class='menu_div_rm'>\n";
	TempStr += "<table class='menu_table' width='100%' border='0' cellspacing='0'>\n";
	TempStr += "<!-- Insert A Extend Menu or Item On Here For E_" + id + " -->";
	TempStr += "</table>\n";
	TempStr += "</div>\n";
	TempStr += "<!-- Insert A Extend_Menu Area on Here For E_" + id + " -->";
	TempStr += "<!-- Insert A Extend_Menu Area on Here For E_" + parent + " -->";
	HTMLstr = HTMLstr.replace("<!-- Insert A Extend_Menu Area on Here For E_" + parent + " -->", TempStr);
	eval("A_" + id + " = new Array()");
	TempStr = "";
	TempStr += "<!-- Extend Item : P_" + id + " -->\n";
	TempStr += "<tr id='P_" + id + "' class='menu_tr_out'";
	TempStr += " onmouseover='P_OnMouseOver(\"" + id + "\",\"" + parent + "\")'";
	TempStr += " onmouseout='P_OnMouseOut(\"" + id + "\",\"" + parent + "\")'";
	TempStr += " onmousedown=cancelBubble(event)";
	TempStr += " onclick=cancelBubble(event)";
	TempStr += "><td class='menu_tr' nowrap>";
	TempStr += "<font face='Wingdings' style='font-size:18px'>0</font> " + name + "  </td><td style='font-family: webdings; text-align: ;'>4";
	TempStr += "</td></tr>\n";
	TempStr += "<!-- Insert A Extend Menu or Item On Here For E_" + parent + " -->";
	HTMLstr = HTMLstr.replace("<!-- Insert A Extend Menu or Item On Here For E_" + parent + " -->", TempStr);
}
function AddItem(id, img, wh, name, parent,location,fn,target) {
	var TempStr = "";
	var ItemStr = "<!-- ITEM : I_" + id + " -->";
	if (id == "sperator") {
		TempStr += ItemStr + "\n";
		TempStr += "<tr class='menu_tr_out' onclick='cancelBubble(event)' onmousedown='cancelBubble(event)'><td colspan='2' height='0'><hr class='menu_hr_sperator'/></td></tr>";
		TempStr += "<!-- Insert A Extend Menu or Item On Here For E_" + parent + " -->";
		HTMLstr = HTMLstr.replace("<!-- Insert A Extend Menu or Item On Here For E_" + parent + " -->", TempStr);
		return;
	}
	if (HTMLstr.indexOf(ItemStr) != -1) {
		alert("I_" + id + "already exist!");
		return;
	}
	TempStr += ItemStr + "\n";
	TempStr += "<tr id='I_" + id + "' class='menu_tr_out'";
	TempStr += " onmouseover='I_OnMouseOver(\"" + id + "\",\"" + parent + "\")'";
	TempStr += " onmouseout='I_OnMouseOut(\"" + id + "\")'";
	TempStr += " onclick='cancelBubble(event)'";
	TempStr += "><td class='menu_tr' nowrap><a style='text-decoration:none' href='"+location+"'";
	if (target != null && target!='') {
		TempStr += " target='"+target+"'";
	} 
	if (location == null) {
		TempStr += " onclick='I_OnMouseUp(event,\"" + id + "\",\"" + parent + "\",null,"+fn+")'";
	} else {
		TempStr += " onclick='I_OnMouseUp(event,\"" + id + "\",\"" + parent + "\",\"" + location + "\","+fn+")'";
	}
	TempStr += "><font face='Wingdings' style='font-size:18px'>" + wh + "</font> " + name + " ";//以Wingdings字体做为图片，要改成图片，请在这里更改
	TempStr += "</a></td><td></td></tr>\n";
	TempStr += "<!-- Insert A Extend Menu or Item On Here For E_" + parent + " -->";
	HTMLstr = HTMLstr.replace("<!-- Insert A Extend Menu or Item On Here For E_" + parent + " -->", TempStr);
}
function AddItem1(id, img, wh, name, parent, location,nodeType) {
	var TempStr = "";
	var ItemStr = "<!-- ITEM : I_" + id + " -->";
	if (id == "sperator") {
		TempStr += ItemStr + "\n";
		TempStr += "<tr class='menu_tr_out' onclick='cancelBubble(event)' onmousedown='cancelBubble(event)'><td colspan='2' height='1'><hr class='menu_hr_sperator'></td></tr>";
		TempStr += "<!-- Insert A Extend Menu or Item On Here For E_" + parent + " -->";
		HTMLstr = HTMLstr.replace("<!-- Insert A Extend Menu or Item On Here For E_" + parent + " -->", TempStr);
		return;
	}
	//判断是是否存在相同的id name
	if (HTMLstr.indexOf(ItemStr) != -1) {
		alert("I_" + id + "already exist!");
		return;
	}
	TempStr += ItemStr + "\n";
	TempStr += "<tr id='I_" + id + "' class='menu_tr_out'";
	TempStr += " onmouseover='I_OnMouseOver(\"" + id + "\",\"" + parent + "\")'";
	TempStr += " onmouseout='I_OnMouseOut(\"" + id + "\")'";
	TempStr += " onclick='cancelBubble(event)'";
	/*
	if (location == null) {
		TempStr += " onmousedown='I_OnMouseUp(event,\"" + id + "\",\"" + parent + "\",null)'";
	} else {
		TempStr += " onmousedown='I_OnMouseUp(event,\"" + id + "\",\"" + parent + "\",\"" + location + "\")'";
	}
	TempStr += "><td class='menu_tr' nowrap>";
	*/
	//需要添加的内容
	var tableDiv=document.createElement("DIV");
	var TempTableStr="";
	TempTableStr += "<TABLE   border=0 cellSpacing=0 cellPadding=0><TBODY>";
	TempTableStr += "<TR  id=uName><TH noWrap onclick='cancelBubble(event)'>node\u540d\u79f0</TH><TD onclick='cancelBubble(event)' width=100>";
	TempTableStr += "<INPUT onclick='cancelBubble(event)' style='WIDTH: 90px'  tabIndex=1 maxLength=50 >";
	TempTableStr += "</TD></TR><TR><TH onclick='cancelBubble(event)'>&nbsp;</TH><TD onclick='cancelBubble(event)' colSpan=3><button tabIndex=9 onclick='addItemNode(event,"+nodeType+",this)'>\u6dfb \u52a0</button></TD></TR>";
	TempTableStr += "</TBODY></TABLE><DIV id=divError class=Error></DIV>";
	tableDiv.innerHTML=TempTableStr;
	//TempStr += "<font face='Wingdings' style='font-size:18px'>" + wh + "</font> " + name + " ";//以Wingdings字体做为图片，要改成图片，请在这里更改
	TempStr +=tableDiv.innerHTML;
	TempStr += "</td><td></td></tr>\n";
	TempStr += "<!-- Insert A Extend Menu or Item On Here For E_" + parent + " -->";
	HTMLstr = HTMLstr.replace("<!-- Insert A Extend Menu or Item On Here For E_" + parent + " -->", TempStr);
}
function GetMenu() {
	return HTMLstr;
}
function I_OnMouseOver(id, parent) {
	var Item;
	if (parent != "rbpm") {
		var ParentItem;
		ParentItem = $("P_" + parent);
		ParentItem.className = "menu_tr_over";
	}
	Item = $("I_" + id);
	Item.className = "menu_tr_over";
	HideAll(parent, 1);
}
function I_OnMouseOut(id) {
	var Item;
	Item = $("I_" + id);
	Item.className = "menu_tr_out";
}
function I_OnMouseUp(e, id, parent, location,fn) {
	var ParentMenu;
	(e || event).cancelBubble = true;
	OnClick();
	ParentMenu = $("E_" + parent);
	ParentMenu.display = "none";
	//
	var PopMenuName = $("E_rbpm");
	var node=PopMenuName.nodeObject;
	
	if (!e.srcElement) {
		var z = e.target;
	} else {
		z = e.srcElement;
	}
	z.nodeObject=node;
	if(fn!=null){
		fn(node);
		return false;
	}
	if (location == null) {
		eval("Do_" + id + "()");
		return false;
	} else {
		z.href=location+"?&id="+node.id+"&name="+node.label;
		return true;
	}
}
function addItemNode(e,nodeType){
	var PopMenuName = $("E_rbpm");
	var node=PopMenuName.nodeObject;
	//var obj=$("txtUserName");
	var obj=e.target;
	if(!obj){
		obj=e.srcElement;
	}

	var tr=obj.parentElement.parentElement.parentElement;
	var input=tr.getElementsByTagName("INPUT")[0];
	var newIdNum="new_"+(new Date()).valueOf()+"_"+(parseInt(Math.random()*9999)).toString(); 
	node.treeNod.addNewItem(node.id,newIdNum++,input.value,nodeType);
	obj.value="";
	input.value="";
	HideAll("rbpm", 0);
}

function cancelBubble(e) {
	if ( e && e.stopPropagation ){
		e.stopPropagation(); 
	}
	else{
	  window.event.cancelBubble = true;
	}
}
function P_OnMouseOver(id, parent) {
	var Item;
	var Extend;
	var Parent;
	if (parent != "rbpm") {
		var ParentItem;
		ParentItem = $("P_" + parent);
		ParentItem.className = "menu_tr_over";
	}
	HideAll(parent, 1);
	Item = $("P_" + id);
	Extend = $("E_" + id);
	Parent = $("E_" + parent);
	Item.className = "menu_tr_over";
	Extend.style.display = "block";
	scrollLeft = document.documentElement.scrollLeft || document.body.scrollLeft;
	clientWidth = document.documentElement.clientWidth || document.body.clientWidth;
	clientHeight = document.documentElement.clientHeight || document.body.clientHeight;
	scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
	Extend.style.left = scrollLeft + Parent.offsetLeft + Parent.offsetWidth - 4 + "px";
	if (Extend.offsetLeft + Extend.offsetWidth > scrollLeft + clientWidth) {
		Extend.style.left = Extend.offsetLeft - Parent.offsetWidth - Extend.offsetWidth + 8 + "px";
	}
	if (Extend.offsetLeft < 0) {
		Extend.style.left = scrollLeft + Parent.offsetLeft + Parent.offsetWidth + "px";
	}
	Extend.style.top = Parent.offsetTop + Item.offsetTop + 1 + "px";
	if (Extend.offsetTop + Extend.offsetHeight > scrollTop + clientHeight) {
		Extend.style.top = scrollTop + clientHeight - Extend.offsetHeight + "px";
	}
	if (Extend.offsetTop < 0) {
		Extend.style.top = 0 + "px";
	}
}
function P_OnMouseOut(id, parent) {
}
function HideAll(id, flag) {
	var Area;
	var Temp;
	var i;
	if (!flag) {
		Temp = $("E_" + id);
		Temp.style.display = "none";
	}
	Area = eval("A_" + id);
	if (Area.length) {
		for (i = 0; i < Area.length; i++) {
			HideAll(Area[i], 0);
			Temp = $("E_" + Area[i]);
			Temp.style.display = "none";
			Temp = $("P_" + Area[i]);
			Temp.className = "menu_tr_out";
		}
	}
}
//错误提示信息
window.onerror = reportError;
function reportError(msg, url, line) {
	var str = "You have found an error as below: \n\n";
	str += "Err: " + msg + " on line: " + line;
	alert(str);
	return true;
}

//屏蔽自身的邮件菜单
function showRightMenu(e) {
	(e || event).returnValue = false;
	document.oncontextmenu = null;
}
//document.onmousedown = OnMouseUp;
document.onclick = OnClick;
function OnMouseUp(e,nodeObject) {
	if ((e || event) && (e || event).button != 2) {
		return;
	}
	document.oncontextmenu = showRightMenu;
	var PopMenu;
	PopMenu = $("E_rbpm");
	PopMenu.nodeObject=nodeObject;
	HideAll("rbpm", 0);
	PopMenu.style.display = "block";
	PopMenu.style.left = document.body.scrollLeft + (e || event).clientX + "px";
	PopMenu.style.top = document.body.scrollTop + (e || event).clientY + "px";
	scrollLeft = document.documentElement.scrollLeft || document.body.scrollLeft;
	clientWidth = document.documentElement.clientWidth || document.body.clientWidth;
	clientHeight = document.documentElement.clientHeight || document.body.clientHeight;
	scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
	if (PopMenu.offsetLeft + PopMenu.offsetWidth > scrollLeft + clientWidth) {
		PopMenu.style.left = scrollLeft + clientWidth - PopMenu.offsetWidth + "px";
	}
	if (PopMenu.offsetLeft < 0) {
		PopMenu.style.left = 0 + "px";
	}
	if (PopMenu.offsetTop + PopMenu.offsetHeight > scrollTop + clientHeight) {
		PopMenu.style.top = scrollTop + clientHeight - PopMenu.offsetHeight + "px";
	}
	if (PopMenu.offsetTop < 0) {
		PopMenu.style.top = 0 + "px";
	}
}
function OnClick() {
	HideAll("rbpm", 0);
}
  // Add Your Function on following
function Do_viewcode() {
	window.location = "view-source:" + window.location.href;
}
function Do_help() {
	window.showHelp(window.location);
}
function Do_exit() {
	window.close();
}
function Do_refresh() {
	window.location.reload();
}
function Do_back() {
	history.back();
}
function Do_forward() {
	history.forward();
}
function Do_help() {
	alert("\u5e2e\u52a9");
}
function $(idName) {
	return document.getElementById(idName);
}

