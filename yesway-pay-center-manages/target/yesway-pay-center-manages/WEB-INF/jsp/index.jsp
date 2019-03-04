<%@ page language="java" contentType="text/html; charset=UTF-8" deferredSyntaxAllowedAsLiteral="true" 
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sitemesh"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="default" />

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="A preview of the jQuery UI Bootstrap theme">
<meta name="author" content="Addy Osmani">
<link rel="shortcut icon" href="<%=request.getContextPath()%>/static/assets/images/favicon.png">
<link type="text/css" href="<%=request.getContextPath()%>/static/assets/jquery-ui-1.10.0.custom.css" rel="stylesheet" />
<style type="text/css">
	.ui-state-active{background-color:#ecf0f5 !important;border-bottom:1px solid #ecf0f5 !important}
	.ui-state-active{background-color:#ecf0f5 !important;border-bottom:1px solid #ecf0f5 !important}
	.ui-state-active a{background-color:#ecf0f5 !important;}
	.ui-tabs-nav{border:none !important;}
</style>
<title></title>
</head>
<body data-spy="scroll" data-target=".bs-docs-sidebar" data-twttr-rendered="true">
	<div id="tabs2" class="content-wrapper">
		<ul>
			<li>&nbsp;</li>
		</ul>
		<div class="tab-content">
		</div>
	</div>
<script src="<%=request.getContextPath()%>/static/assets/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>
<script type="text/javascript">
	
	$('#tabs2').tabs();
	var openList = [];
	var tabs = $( "#tabs2" ).tabs();
	var codeList = ""; 
	var tabTitle = $( "#tab_title" ),
	tabContent = $( "#tab_content" );
	
	var openTab = function(tabCode, tabName, url){
		if(codeList.indexOf(tabCode) < 0){
			var li = "<li id=\"li_" + tabCode + "\"><a onclick=\"showTab('" + tabCode + "')\">" + tabName + "</a>"
				+ "<span class='ui-icon ui-icon-close' style=\"background-position:80px 128px\" tabcode=\"" + tabCode + "\"></span></li>";
			tabs.find( ".ui-tabs-nav" ).append(li);
			//codeList += tabCode + ",";
			//console.log(codeList);
			var tabContent = "<div class=\"tab-pane active\" id=\"tab_" + tabCode + "\"><iframe id=\"tabs-" + tabCode + "-iframe\" class=\"content-wrapper\" name=\"main\"" +
				"id=\"main\" style=\"padding: 0px; margin: 0px !important; width: 100%; border: none\" src=\"<%=request.getContextPath()%>/html/loading.jsp\"></iframe></div>";
			tabs.find("div.tab-content").append(tabContent);
		} else {
			$("#tabs-" + tabCode + "-iframe").attr("src","<%=request.getContextPath()%>/html/loading.jsp");
		}
		/* setTimeout(function(){
			$("#tabs-" + tabCode + "-iframe").attr("src",url);
		},200); */
		$("#tabs2 ul li").unbind("mousemove").bind("mousemove", function(){
			$(this).find("span.ui-icon-close")
				.css("background-position", "-80px -128px")
				.unbind("click").bind("click", function(){
					closeTab($(this).attr("tabcode"));
				});
		}).unbind("mouseout").bind("mouseout", function(){
			$(this).find("span.ui-icon-close")
				.css("background-position", "80px 128px")
				.unbind("click");
		});
		showTab(tabCode);
		setTimeout(function(){
			$("#tabs-" + tabCode + "-iframe").attr("src",url);
		},100);
	};
	
	var showTab = function(tabCode){
		for(var i = 0 ; i < openList.length; i++){
			if(openList[i] == tabCode){
				openList.splice(i, 1);// 从i位置删除1个元素
			}
		}
		openList.unshift(tabCode);// 添加到Array开头
		codeList = openList.join();// 转换为字符串，默认逗号分隔
		console.log(codeList);
		var liId = "#li_"+tabCode;
		tabs.find("div.tab-pane").hide();
		$("#tab_" + tabCode).show();
		$("#tabs2 ul li").removeClass("ui-tabs-active");
		$("#tabs2 ul li").removeClass("ui-state-active");
		$("#tabs2 ul li").removeAttr("aria-labelledby");
		$("#tabs2 ul li").removeAttr("aria-selected");
		
		$(liId).attr("aria-labelledby:'ui-id-3', aria-selected:'true'");
		$(liId).addClass("ui-tabs-active ui-state-active");
		tabs.tabs( "refresh" );
		if($("#tabs2 ul li").length > 2){
			$("#tabs2 ul").show();
		}
		$(window).resize();
	};
	
	var closeTab = function(tabCode, delay,flag){
		var _delay = parseInt(delay);// NaN
		delay = _delay != _delay ? 0 : _delay;
		
		setTimeout(function(){
			$("#li_"+tabCode).remove();
			$("#tab_" + tabCode).remove();
			
			//暂时关闭先显示第一个标签
			//codeList = codeList.replace(tabCode + ",", "");
			
			if($("#tabs2 ul li").length > 0){
				//var showTabCode = codeList.substring(0, codeList.indexOf(","));
				//关闭不刷新，只有提交才刷新
				if(tabCode.indexOf("manner")!=-1&&flag=="submit"){
					openTab('payManner','支付方式管理','<%=request.getContextPath()%>/payManner/findPageList');
				}else if(tabCode.indexOf("businessSystem")!=-1&&flag=="submit"){
					openTab('payBusinessSystem','业务系统管理','<%=request.getContextPath()%>/payBusinessSystem/findPageList');
				}else{
					//showTab(showTabCode); 
				}
			}
			if($("#tabs2 ul li").length < 3){
				$("#tabs2 ul").hide();
			}
			
			for(var i = 0 ; i < openList.length; i++){
				if(openList[i] == tabCode){
					openList.splice(i, 1);// 从i位置删除1个元素
					i--;// splice后，length缩短，影响for的判断
				}
			}
			codeList = openList.join();// 转换为字符串，默认逗号分隔
			if(openList.length > 0){
				showTab(openList[0]); 
			}

			console.log(codeList);
		}, delay);
	};
	
</script>
</body>
</html>
