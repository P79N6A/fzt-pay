var mapobjIsReady = false;
var maplet = null;
var cenpoi = null;
var mappois = new Array();
var showmap_poisMapbar = new Array();
var showmap_poisYesway = new Array();

var mapobjImages = ["images/1.png","images/2.png","images/3.png","images/4.png","images/5.png","images/6.png","images/7.png","images/8.png","images/9.png","images/10.png"];

//初始化地图
function initMap(){
	maplet = new TMap({div:'mapDiv',center:new TLatLon(39.90805,116.38672), zoom:5,mapService:MAP_SERVICE, tile_cached:true, url_map_server:window.URL_MAP_SERVER});

	maplet.enableScrollZoom(true);
	var zoombar = new TControl("T_CONTROL_LARGEZOOM", new TControlPosition(TControlPosition.T_ANCHOR_TOP_LEFT, new TSize(10,10)));
	maplet.addControlEx(zoombar);
	var birdview = new TControl("T_CONTROL_OVERVIEW", new TControlPosition(TControlPosition.T_ANCHOR_BOTTOM_RIGHT, new TSize(0,0), 100));
	maplet.addControlEx(birdview);
	maplet.addControl(TMAP_SCALE,new TControlPosition(T_ANCHOR_BOTTOM_LEFT,new TSize(5,5)));

	maplet.showAll();
    mapobjIsReady = true;
}
//隐藏右侧结果
function hideResult() {
    var isshow = arguments[0];
    if(isshow == 0) {
        $('#right').show();
		$('#hideResultImg').attr("src","images/s_hideResult2.gif");
        mapobjResize(document.documentElement.offsetWidth-462, document.documentElement.offsetHeight-30);
        return;
    }
	if($('#right').css("display") == "block" || isshow == 1){
        $('#right').hide();
		$('#hideResultImg').attr("src","images/s_hideResult.gif");
        mapobjResize(document.documentElement.offsetWidth-25, document.documentElement.offsetHeight-30);
    }else{
		$('#right').show();
		$('#hideResultImg').attr("src","images/s_hideResult2.gif");
        mapobjResize(document.documentElement.offsetWidth-462, document.documentElement.offsetHeight-30);
	}
}
//改变地图大小
function mapobjResize(width, height) {
    $('#mapDiv').width(width);
    $('#mapDiv').height(height);
    if (maplet) maplet.updateSize();
}
//在地图上添加一个点
function mapobjAddPoi(poi, img) {
  
    pv = "{province:'" + poi.province + "',city:'" + poi.city + "',pid:'" + poi.pid + "',strlonlat:'" + poi.strlonlat + "',name:'" + poi.name + "',address:'" + poi.address + "',phone:'" + poi.phone + "'}";

    var tipUrl = "mapAction!tip.do?pv=" + escape(pv);
    var tipContent = "<iframe width=\"260px\" height=\"180px\" frameborder=\"0\" scrolling=\"no\" src=\"" + tipUrl + "\"></iframe>";
	var typeInfo = {title:poi.name, imgUrl: img, markerAnchor: new TPoint(14, 42)};
    if(img == "images/center.gif") {
		typeInfo.markerSize = new TSize(38, 38);
		typeInfo.markerAnchor = new TPoint(14, 14);
    } if(img == "images/car.gif") {
		typeInfo.markerSize = new TSize(35, 15);
		typeInfo.markerAnchor = new TPoint(14, 14);
	} else {
		typeInfo.markerSize = new TSize(27, 44);
    }
	var arr = poi.strlonlat.split(",");
	var marker = new TMarker(new TLatLon(arr[1],arr[0]),typeInfo);
	maplet.addMarker(marker);

    poi.marker = marker;
    poi.tipContent = tipContent;
	marker.addListener('click', showBubbleCb(marker,tipContent,arr[1],arr[0]));
}

function showBubbleCb(marker, tipContent, lat, lon) {
    var callback = function(){
        showBubble.call(this, marker, tipContent, lat, lon);
    }
    return callback;
}

function showBubble(marker, tipContent, lat, lon){
    maplet.setCenter(new TLatLon(lat,lon), 3);
	if (window.currentInfoWindow) {
		window.currentInfoWindow.close();
	}
	var infoWindow =new TInfoWindow();
	window.currentInfoWindow = infoWindow;
	infoWindow.setVisibleAfterOpen(true);
	infoWindow.attachMarker(marker, new TPoint(20, 19), new TPoint(-35,-20));    
	maplet.addOverlay(infoWindow);
	infoWindow.open();
	infoWindow.setContent(tipContent);
}

//在地图上显示一组点和中心店
function mapobjShowPois(pois, cenPoi) {
	maplet.closeInfoWindow();
	mapobjPois = new Array();
	maplet.clearMarkers();
    if (typeof(cenPoi)=="object") {
        cenpoi = cenPoi;
    }
    if(cenpoi != null) {
        cenpoi.pguid = 0;
        mapobjAddPoi(cenpoi, cenpoi.image);
        var arr = cenpoi.strlonlat.split(",");
        mapobjPois.push(new TLatLon(arr[1],arr[0]));
    }
    for (var i=0; i<pois.length; ++i){
        mapobjAddPoi(pois[i], mapobjImages[i]);
        var arr = pois[i].strlonlat.split(",");
        mapobjPois.push(new TLatLon(arr[1],arr[0]));
    }
	//将所有的点都在地图上显示出来
    if(mapobjPois.length > 0) {
    	maplet.refreshViewByZoom(mapobjPois);
    }

}
//在地图上显示缓存的点
function mapReady() {
    if (typeof(mapobjIsReady)== "boolean" && mapobjIsReady == true)
        mapobjShowPois(mappois);
    else
        window.setTimeout("mapReady();", 100);
}
//显示某个点的弹出内容
function mapobjCenterPoi(pid) {
    maplet.closeInfoWindow();
    if (mappois == null) return;
    for (var i=0; i<mappois.length; ++i) {
        if (mappois[i].pid == pid) {
            var arr = mappois[i].strlonlat.split(",");
            showBubble(mappois[i].marker, mappois[i].tipContent,arr[1],arr[0]);
            break;
        }
    }
}
//根据选择城市自动改变地图显示
function changemap() {
    var cityCode = $("#city").find("option:selected").val();
    var c_str = cs1[cityCode] + "";
    if (typeof(c_str) != "string" || c_str == "undefined") {
        return;
    } else {
        var c_arr = c_str.split(",");
        maplet.setCenter(new TLatLon(c_arr[2],c_arr[1]), 5);
    }    
}

// 地图窗口大小自适应
window.onresize = function(e) {
	if(maplet) {
        if($('#right').css("display") == "none") {
		    mapobjResize(document.documentElement.clientWidth-18, document.documentElement.clientHeight-30);
        } else {
            mapobjResize(document.documentElement.clientWidth-460, document.documentElement.clientHeight-30);
        }
    }
	if($("#divResultPOI")) {
		$("#divResultPOI").height(document.documentElement.clientHeight-118);
	}
	if($("#divResultYP")) {
		$("#divResultYP").height(document.documentElement.clientHeight-118);
	}
}

//查询结果显示提示
var tipLoading = "<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"2\" height=\"100%\"><tr><td bgcolor=\"F2F3F8\"><div align=\"center\"><image src=\"images/loading.gif\"><font color=\"#0000FF\">查询中...</font></div></td></tr></table>";
var tipFail = "<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"2\" height=\"100%\"><tr><td bgcolor=\"F2F3F8\"><div align=\"center\"><font color=\"#0000FF\">查询出错!</font></div></td></tr></table>";
var tipNoResult = "<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"2\" height=\"100%\"><tr><td bgcolor=\"F2F3F8\"><div align=\"center\"><font color=\"#0000FF\">无搜索结果!</font></div></td></tr></table>";

//根据条件查询POI信息
function searchPOI1() {
	var province = "";
    //if(($("#city").val() - $("#city").val()) != 0) {
    //    return;
    //}
    var city = $("#city").find("option:selected").text().replace(/[^\u4E00-\u9FA5]/g,'');
	if(city == "全国") {
		city = "";
	} else if(($("#city").val() - $("#city").val()) != 0) {
		province = city;
		city = "";
	} else {
		var cityCode = $("#city").find("option:selected").val();
		//城市所属省份
        for (var p in ps) {
        	var pv = ps[p].split(",");
        	for(var i=0; i<pv.length; ++i) {
        		if(pv[i] == cityCode) {
        			province = p;
        		}
        	}
        }
	}	
    var key = $("#key").val().replace(/(^\s*)|(\s*$)/g, "");
    var type = "";
    if($("#type").val() != "") {
        type = $("#type").val();
    }
    if(key != "" || type != "") {		
        searchPOI(province,city,key,type,0,10);
    }
}
//POI查询及显示结果
function searchPOI(province,city,key,type,currentpage,countperpage) {
	key = key.replace(/’/g,"'");
    var url = 'mapAction!searchPOI.do';
	var pars = {province:province,city:city,key:key,type:type,currentpage:currentpage,countperpage:countperpage,counselor:counselor,counselorMac:counselorMac};
	$.ajax({
		url: url,
	    type: "post",
		data: pars,
		beforeSend: function(XMLHttpRequest){
			XMLHttpRequest.setRequestHeader("RequestType", "ajax");
			$("#resultPOI").html(tipLoading);
		},
		success: function(data, textStatus){
			try {
				eval(data);
                var divResult = "";
                if(!result.datas || result.datas.length == 0) {
                    $("#resultPOI").html(tipNoResult);
                } else {
					var height = document.documentElement.clientHeight-118;
                    divResult += "<div style=\"width:100%; height:" + height + "px; overflow-y:auto;overflow-x:hidden;\" id=\"divResultPOI\">";
                    divResult += "<table width=\"95%\"  border=\"0\" cellpadding=\"2\" cellspacing=\"0\">";
                    divResult += "<tr>";
                    divResult += "<td>" + result.total + "个结果</td>";
					if(result.totalpage > 1) {
						divResult += "<td align=\"right\">";
						if(result.total > 0 && currentpage > 0) {
							divResult += "<a href=\"javascript:searchPOI('" + province + "','" + city + "','" + key.replace(/'/g,"’") + "','" + type + "'," + (Number(currentpage)-1) + "," + countperpage + ")\">上</a> ";
						} else {
							divResult += "上 ";
						}
						var start = 0;
						var end = currentpage + 3;
						if(currentpage > 2) start = currentpage - 2;
						if(end < 5) end = 5;
						if(end > result.totalpage) end = result.totalpage;        
						for(i=start;i<end;i++) {
							if(i == currentpage) {
								divResult += (i+1) + " ";
							} else {
								//divResult += "<a href=\"javascript:searchPOI('" + province + "','" + city + "','" + key + "','" + type + "'," + i + "," + countperpage + ")\">" + (i+1) + "</a> "
								divResult += "<a href=\"javascript:searchPOI('" + province + "','" + city + "','" + key.replace(/'/g,"’") + "','" + type + "'," + i + "," + countperpage + ")\">" + (i+1) + "</a> "
							}
						}
						if(result.total > 0 && currentpage < (result.totalpage -1)) {
							divResult += "<a href=\"javascript:searchPOI('" + province + "','" + city + "','" + key.replace(/'/g,"’") + "','" + type + "',"+(Number(currentpage)+1)+"," + countperpage + ")\">下</a> ";
						} else {
							divResult += "下 ";
						}
						divResult += "</td>";
					}
                    divResult += "</tr>";
                    divResult += "</table>";

                    divResult += "<table width=\"95%\"  border=\"0\" cellpadding=\"2\" cellspacing=\"0\">";
                    for(i=0;i<result.datas.length;i++) {
                    	var pca = result.datas[i].city;
						if(result.datas[i].area != "") {
							pca += "-" + result.datas[i].area + ",";
						} else {
							pca += ",";
						}
                        var backgroundColor = "";
                        if(i%2 == 0) {
                            backgroundColor = "#F2F3F8";
                        } else {
                            backgroundColor = "#E6E8F1";
                        }
                        divResult += "<tr bgcolor=\"" + backgroundColor + "\" onmouseover=\"this.style.backgroundColor='#F0E6D9';\" onmouseleave=\"this.style.backgroundColor='" + backgroundColor + "';\" onclick=\"showTipMapbar('" + result.datas[i].pid + "')\">";        
                        divResult += "<td valign=\"top\"> <font color=\"#FF0000\">" + (i+1) + "." +  result.datas[i].name + "</font><br/>";
                        divResult += "地址：" + pca + result.datas[i].address + "<br/>";
                        divResult += "电话：" +  result.datas[i].phone + "<br/>";
                        divResult += "</td></tr>";
                        divResult += "<tr><td align=\"right\" bgcolor=\"" + backgroundColor + "\"><a href=\"javascript:setDestination('" + result.datas[i].province + "','" + result.datas[i].city + "','" + result.datas[i].name + "','" + result.datas[i].strlonlat + "','" + result.datas[i].address + "','" + result.datas[i].phone + "')\">设为目的地</a></td></tr>";
                    }
                    divResult += "</table>";
                    divResult += "<table width=\"95%\"  border=\"0\" cellpadding=\"2\" cellspacing=\"0\">";
                    divResult += "<tr>";
                    divResult += "<td>" + result.total + "个结果</td>";
					if(result.totalpage > 1) {
						divResult += "<td align=\"right\">";
						if(result.total > 0 && currentpage > 0) {
							divResult += "<a href=\"javascript:searchPOI('" + province + "','" + city + "','" + key.replace(/'/g,"’") + "','" + type + "'," + (Number(currentpage)-1) + "," + countperpage + ")\">上</a> ";
						} else {
							divResult += "上 ";
						}
						var start = 0;
						var end = currentpage + 3;
						if(currentpage > 2) start = currentpage - 2;
						if(end < 5) end = 5;
						if(end > result.totalpage) end = result.totalpage;        
						for(i=start;i<end;i++) {
							if(i == currentpage) {
								divResult += (i+1) + " ";
							} else {
								divResult += "<a href=\"javascript:searchPOI('" + province + "','" + city + "','" + key.replace(/'/g,"’") + "','" + type + "'," + i + "," + countperpage + ")\">" + (i+1) + "</a> "
							}
						}
						if(result.total > 0 && currentpage < (result.totalpage -1)) {
							divResult += "<a href=\"javascript:searchPOI('" + province + "','" + city + "','" + key.replace(/'/g,"’") + "','" + type + "',"+(Number(currentpage)+1)+"," + countperpage + ")\">下</a> ";
						} else {
							divResult += "下 ";
						}
						divResult += "</td>";
					}
                    divResult += "</tr>";
                    divResult += "</table>";

                    divResult += "</div>";

                    $("#resultPOI").html(divResult);
                    $("#tabMapbar").css("background","url(images/new_center_bg01.gif)");
                	$("#tabYesway").css("background","url(images/new_center_bg02.gif)");
                    mappois = result.datas;
                    showmap_poisMapbar = result.datas;
                    mapReady();
                }
            } catch(e) {
				$("#resultPOI").html(tipFail);
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			$("#resultPOI").html(tipFail);
		},
		complete: function(XMLHttpRequest, textStatus){
			
		}
	});
}


//POI周边查询及显示结果
function searchNear(city,strlonlat,akey,atype,range,currentpage,countperpage) {   
	akey = akey.replace(/’/g,"'");
    var url = 'mapAction!searchNear.do';
	var pars = {city:city,strlonlat:strlonlat,akey:akey,atype:atype,range:range,currentpage:currentpage,countperpage:countperpage};
	$.ajax({
		url: url,
	    type: "post",
		data: pars,
		beforeSend: function(XMLHttpRequest){
			XMLHttpRequest.setRequestHeader("RequestType", "ajax");
			$("#resultPOI").html(tipLoading);
		},
		success: function(data, textStatus){
			try {
				eval(data);
                var divResult = "";
                if(!result.datas || result.datas.length == 0) {
                    $("#resultPOI").html(tipNoResult);
                } else {
					var height = document.documentElement.clientHeight-118;
                    divResult += "<div style=\"width:100%; height:" + height + "; overflow-y:auto;overflow-x:hidden;\" id=\"divResultPOI\">";
                    divResult += "<table width=\"95%\"  border=\"0\" cellpadding=\"2\" cellspacing=\"0\">";
                    divResult += "<tr>";
                    divResult += "<td>" + result.total + "个结果</td>";
					if(result.totalpage > 1) {
						divResult += "<td align=\"right\">";
						if(result.total > 0 && currentpage > 0) {
							divResult += "<a href=\"javascript:searchNear('" + city + "','" + strlonlat + "','" + akey.replace(/'/g,"’") + "','" + atype + "'," + range + "," + (Number(currentpage)-1) + "," + countperpage + ")\">上</a> ";
						} else {
							divResult += "上 ";
						}
						var start = 0;
						var end = currentpage + 3;
						if(currentpage > 2) start = currentpage - 2;
						if(end < 5) end = 5;
						if(end > result.totalpage) end = result.totalpage;        
						for(i=start;i<end;i++) {
							if(i == currentpage) {
								divResult += (i+1) + " ";
							} else {
								divResult += "<a href=\"javascript:searchNear('" + city + "','" + strlonlat + "','" + akey.replace(/'/g,"’") + "','" + atype + "'," + range + "," + i + "," + countperpage + ")\">" + (i+1) + "</a> "
							}
						}
						if(result.total > 0 && currentpage < (result.totalpage -1)) {
							divResult += "<a href=\"javascript:searchNear('" + city + "','" + strlonlat + "','" + akey.replace(/'/g,"’") + "','" + atype + "'," + range + "," +(Number(currentpage)+1)+"," + countperpage + ")\">下</a> ";
						} else {
							divResult += "下 ";
						}
						divResult += "</td>";
					}
                    divResult += "</tr>";
                    divResult += "</table>";

                    divResult += "<table width=\"95%\"  border=\"0\" cellpadding=\"2\" cellspacing=\"0\">";
                    for(i=0;i<result.datas.length;i++) {
                    	var pca = result.datas[i].city;
						if(result.datas[i].area != "") {
							pca += "-" + result.datas[i].area + ",";
						} else {
							pca += ",";
						}
                        var backgroundColor = "";
                        if(i%2 == 0) {
                            backgroundColor = "#F2F3F8";
                        } else {
                            backgroundColor = "#E6E8F1";
                        }
                        divResult += "<tr bgcolor=\"" + backgroundColor + "\" onmouseover=\"this.style.backgroundColor='#F0E6D9';\" onmouseleave=\"this.style.backgroundColor='" + backgroundColor + "';\" onclick=\"showTipMapbar('" + result.datas[i].pid + "')\">";        
                        divResult += "<td valign=\"top\"> <font color=\"#FF0000\">" + (i+1) + "." +  result.datas[i].name + "</font><br/>";
                        divResult += "地址：" + pca + result.datas[i].address + "<br/>";
                        divResult += "电话：" +  result.datas[i].phone + "<br/>";
						divResult += "驾车距离：";
						if(result.datas[i].distance >= 1000) {
							divResult += result.datas[i].distance/1000 + "公里<br/>";
						} else {
							divResult += result.datas[i].distance + "米<br/>";
						}
                        divResult += "</td></tr>";
                        divResult += "<tr><td align=\"right\" bgcolor=\"" + backgroundColor + "\"><a href=\"javascript:setDestination('" + result.datas[i].province + "','" + result.datas[i].city + "','" + result.datas[i].name + "','" + result.datas[i].strlonlat + "','" + result.datas[i].address + "','" + result.datas[i].phone + "')\">设为目的地</a></td></tr>";
                    }
                    divResult += "</table>";

                    divResult += "<table width=\"95%\"  border=\"0\" cellpadding=\"2\" cellspacing=\"0\">";
                    divResult += "<tr>";
                    divResult += "<td>" + result.total + "个结果</td>";
					if(result.totalpage > 1) {
						divResult += "<td align=\"right\">";
						if(result.total > 0 && currentpage > 0) {
							divResult += "<a href=\"javascript:searchNear('" + city + "','" + strlonlat + "','" + akey.replace(/'/g,"’") + "','" + atype + "'," + range + "," + (Number(currentpage)-1) + "," + countperpage + ")\">上</a> ";
						} else {
							divResult += "上 ";
						}
						var start = 0;
						var end = currentpage + 3;
						if(currentpage > 2) start = currentpage - 2;
						if(end < 5) end = 5;
						if(end > result.totalpage) end = result.totalpage;        
						for(i=start;i<end;i++) {
							if(i == currentpage) {
								divResult += (i+1) + " ";
							} else {
								divResult += "<a href=\"javascript:searchNear('" + city + "','" + strlonlat + "','" + akey.replace(/'/g,"’") + "','" + atype + "'," + range + "," + i + "," + countperpage + ")\">" + (i+1) + "</a> "
							}
						}
						if(result.total > 0 && currentpage < (result.totalpage -1)) {
							divResult += "<a href=\"javascript:searchNear('" + city + "','" + strlonlat + "','" + akey.replace(/'/g,"’") + "','" + atype + "'," + range + "," +(Number(currentpage)+1)+"," + countperpage + ")\">下</a> ";
						} else {
							divResult += "下 ";
						}
						divResult += "</td>";
					}
                    divResult += "</tr>";
                    divResult += "</table>";

                    divResult += "</div>";

                    $("#resultPOI").html(divResult);
                    $("#tabMapbar").css("background","url(images/new_center_bg01.gif)");
                	$("#tabYesway").css("background","url(images/new_center_bg02.gif)");
                    mappois = result.datas;
                    showmap_poisMapbar = result.datas;
                    mapReady();
                }
            } catch(e) {
				$("#resultPOI").html(tipFail);
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			$("#resultPOI").html(tipFail);
		},
		complete: function(XMLHttpRequest, textStatus){
			
		}
	});
}

//根据条件查询黄页信息
function searchYP1() {
    if(($("#city").val() - $("#city").val()) != 0) {
        return;
    }
    var city = $("#city").find("option:selected").text().replace(/[^\u4E00-\u9FA5]/g,'');
    var key = $("#key").val().replace(/(^\s*)|(\s*$)/g, "");
    if(key != "" && city != "") {
        searchYP(city,key,0,10);
    }
}
//黄页查询及显示结果
function searchYP(city,key,currentpage,countperpage) {
    var url = 'yelloPageAction!search.do';
	var pars = {city:city,key:key,currentpage:currentpage,countperpage:countperpage};
	$.ajax({
		url: url,
	    type: "post",
		data: pars,
		beforeSend: function(XMLHttpRequest){
			XMLHttpRequest.setRequestHeader("RequestType", "ajax");
			$("#resultYP").html(tipLoading);
		},
		success: function(data, textStatus){
			try {
				eval(data);
                var divResult = "";
                if(!result.datas || result.datas.length == 0) {
                    $("#resultYP").html(tipNoResult);
                } else {
					var height = document.documentElement.clientHeight-118;
                    divResult += "<div style=\"width:100%; height:" + height + "; overflow-y:auto;overflow-x:hidden;\" id=\"divResultYP\">";
                    divResult += "<table width=\"95%\"  border=\"0\" cellpadding=\"2\" cellspacing=\"0\">";
                    divResult += "<tr>";
                    divResult += "<td>" + result.total + "个结果</td>";
                    divResult += "<td align=\"right\">";
                    if(result.total > 0 && currentpage > 0) {
                        divResult += "<a href=\"javascript:searchYP('" + city + "','" + key + "'," + (Number(currentpage)-1) + "," + countperpage + ")\">上</a> ";
                    } else {
                        divResult += "上 ";
                    }
                    var start = 0;
                    var end = currentpage + 3;
                    if(currentpage > 2) start = currentpage - 2;
                    if(end < 5) end = 5;
                    if(end > result.totalpage) end = result.totalpage;        
                    for(i=start;i<end;i++) {
                        if(i == currentpage) {
                            divResult += (i+1) + " ";
                        } else {
                            divResult += "<a href=\"javascript:searchYP('" + city + "','" + key + "'," + i + "," + countperpage + ")\">" + (i+1) + "</a> "
                        }
                    }
                    if(result.total > 0 && currentpage < (result.totalpage -1)) {
                        divResult += "<a href=\"javascript:searchYP('" + city + "','" + key + "',"+(Number(currentpage)+1)+"," + countperpage + ")\">下</a> ";
                    } else {
                        divResult += "下 ";
                    }
                    divResult += "</td>";
                    divResult += "</tr>";
                    divResult += "</table>";

                    divResult += "<table width=\"95%\"  border=\"0\" cellpadding=\"2\" cellspacing=\"0\">";
                    for(i=0;i<result.datas.length;i++) {
                    	var pca = result.datas[i].city;
						if(result.datas[i].area != "") {
							pca += "-" + result.datas[i].area + ",";
						} else {
							pca += ",";
						}
                        var backgroundColor = "";
                        if(i%2 == 0) {
                            backgroundColor = "#F2F3F8";
                        } else {
                            backgroundColor = "#E6E8F1";
                        }
                        divResult += "<tr bgcolor=\"" + backgroundColor + "\" onmouseover=\"this.style.backgroundColor='#F0E6D9';\" onmouseleave=\"this.style.backgroundColor='" + backgroundColor + "';\">";        
                        divResult += "<td valign=\"top\"> <font color=\"#FF0000\">" + (i+1) + "." +  result.datas[i].ename + "</font><br/>";
                        divResult += "地址：" + pca + result.datas[i].address + "<br/>";
                        divResult += "电话：" +  result.datas[i].telephone + "<br/>";
                        divResult += "</td></tr>";
                    }
                    divResult += "</table>";
                    divResult += "</div>";

                    $("#resultYP").html(divResult);
                }
            } catch(e) {
				$("#resultYP").html(tipFail);
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			$("#resultYP").html(tipFail);
		},
		complete: function(XMLHttpRequest, textStatus){
			
		}
	});
}

//发送poi信息
function setDestination(province,city,poiName,strlonlat,address,phone) {
	if(!counselorMac || counselorMac == "") {
		alert("无法获取网卡ID，无法发送POI");
		return;
	}
	var msg = "将要发送的目的地信息：";
	if(typeof(province) == "undefined" || province == "undefined") {
		province = "";
	}
	if(typeof(city) == "undefined" || city == "undefined") {
		city = "";
	}
	if(province != city) {
		msg += province + "-";
	}
	msg += city + "-" + poiName;
	if(!confirm(msg)) {
		return;
	}
	if(phone.length > 0){
		if(phone.indexOf(",") > 0 ){
			phone = phone.substring(0,phone.indexOf(","));
		}
//		if(phone.length > 14 ||phone.length < 10){
//			phone = "";
//		}	
	}
	var u4DvK = strlonlat.split(",");
    var lon = u4DvK[0];
    var lat = u4DvK[1];

    var url = 'poiAction!sendPoiInfo.do?poiName=' + encodeURIComponent(encodeURIComponent(poiName)) + "&latitude=" + lat + "&longitude=" + lon + "&address=" + encodeURIComponent(encodeURIComponent(province+city+address)) + "&phone=" + phone + "&counselor=" + counselor + "&counselorMac=" + counselorMac;
	//var pars = {poiName:encodeURIComponent(encodeURIComponent(poiName)),latitude:lat,longitude:lon,address:encodeURIComponent(encodeURIComponent(address)),phone:phone,counselor:counselor,counselorMac:counselorMac};
	$.ajax({
		url: url,
	    type: "post",
		data: {},
		beforeSend: function(XMLHttpRequest){
			XMLHttpRequest.setRequestHeader("RequestType", "ajax");
		},
		success: function(data, textStatus){
			try {
				eval(data);
                if(error != "") {
                    alert(error);
                } else {
					try	{
						var desc = "将" + province + city + poiName + "设置为目的地，发送成功。";
						parent.topFrame.serviceRecord.serviceRecordForm.serviceDesc.value += desc;
						parent.topFrame.serviceRecord.serviceRecordForm.remark.value += desc;
					} catch(e) {
						
					}
                    alert("目的地设置指令发送成功，请等待结果返回");
                }
            } catch(e) {
				alert("发送POI信息时发生js错误");
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			alert("发送POI信息时发生错误!");
		},
		complete: function(XMLHttpRequest, textStatus){
			
		}
	});
}

//初始化POI分类列表
function initPoiclass(poiclassid) {
    var poiclass = document.getElementById(poiclassid);
    for(i=0;i<cs.length;i++) {
        opt = document.createElement("option");
        opt.text = cs[i].name;
        opt.value = cs[i].value;
        opt.name = cs[i].spell;
        poiclass.add(opt);
    }
}

//通过简拼查询poi分类
function searchPoiclass(poiclassSpellid,poiclassid) {
    var poiclass = document.getElementById(poiclassid);
    var spell = document.getElementById(poiclassSpellid).value;
    if(spell != "") {
        for(var i=0;i<poiclass.options.length; i++) {
            if(poiclass.options[i].name.toUpperCase().indexOf(spell.toUpperCase()) == 0) {
                poiclass.selectedIndex = i;
                break;
            }
        }
    } else {
		poiclass.selectedIndex = 0;
	}
}

//初始化城市分类列表
function initCity(cityCode) {
    var city = document.getElementById("city");
    opt = document.createElement("option");
    opt.text = "全国";
    opt.value = "全国";
    city.add(opt);    
    for (var p in ps) {
        var pv = ps[p].split(",");
        if (p!="") {
            opt = document.createElement("option");
            opt.text = p;
            opt.value = p;
            city.add(opt);    
        }
        for(var i=0; i<pv.length; ++i) {
            var cc = pv[i];
            var cv = cs1[cc];
            var c_arr = cv.split(",");
            var c_name = c_arr[0];
            if (p!="") {
                opt = document.createElement("option");
                opt.text = " ├ "+c_name;
                opt.value = cc;
                city.add(opt);
            } else {
                opt = document.createElement("option");
                opt.text = "　　"+c_name;
                opt.value = cc;
                city.add(opt);
            }
			if (cc == cityCode) {
                city.selectedIndex=city.options.length-1;
            }
        }
    }
}

//通过简拼查询城市分类
function searchCityBySpell() {
    var city = document.getElementById("city");
    var spell = document.getElementById("citySpell").value;
    if(spell != "") {
        spell = spell.toUpperCase();
        city.innerHTML = "";
        for (var p in ps) {
            var pv = ps[p].split(",");
            if (p != "") {
                var cc = p;     // city code
                var cv = cs1[cc];    // city value
                var c_arr = cv.split(",");
                var c_name = c_arr[0];
                var c_spell = c_arr[3];
                if (c_spell.match("^"+spell)) {
                    opt = document.createElement("option");
                    opt.text = "　　"+c_name;
                    opt.value = cc;
                    city.add(opt);
                }
            }
            for(var i=0; i<pv.length; ++i) {
                var cc = pv[i];     // city code
                var cv = cs1[cc];    // city value
                var c_arr = cv.split(",");
                var c_name = c_arr[0];
                var c_spell = c_arr[3];
                if (c_spell.match("^"+spell)) {
                    opt = document.createElement("option");
                    opt.text = "　　"+c_name;
                    opt.value = cc;
                    city.add(opt);
                }
            }
        }
    } else {
        city.innerHTML = "";
        initCity(searchCity);
    }
}

function resizeMainFrame() {
	if(maplet) {
        if($('#right').css("display") == "none") {
		    mapobjResize(document.documentElement.clientWidth-18, document.documentElement.clientHeight-30);
        } else {
            mapobjResize(document.documentElement.clientWidth-460, document.documentElement.clientHeight-30);
        }
    }
	if($("#divResultPOI")) {
		$("#divResultPOI").height(document.documentElement.clientHeight-118);
	}
	if($("#divResultYP")) {
		$("#divResultYP").height(document.documentElement.clientHeight-118);
	}
}

//点击四维导航库标签
function showTabMapbar() {
	mappois = showmap_poisMapbar;
	mapReady();        
	$("#tabMapbar").css("background","url(images/new_center_bg01.gif)");
	$("#tabYesway").css("background","url(images/new_center_bg02.gif)");
}
//点击四维导航库某个POI点
function showTipMapbar(pid) {
	mappois = showmap_poisMapbar;
    mapReady();        
    $("#tabMapbar").css("background","url(images/new_center_bg01.gif)");
	$("#tabYesway").css("background","url(images/new_center_bg02.gif)");
    mapobjCenterPoi(pid);
}

//点击九五智驾导航库标签
function showTabYesway() {
	mappois = showmap_poisYesway;
    mapReady();        
    $("#tabMapbar").css("background","url(images/new_center_bg02.gif)");
	$("#tabYesway").css("background","url(images/new_center_bg01.gif)");
}
//点击九五智驾导航库某个POI点
function showTipYesway(pid) {
	mappois = showmap_poisYesway;
    mapReady();        
    $("#tabMapbar").css("background","url(images/new_center_bg02.gif)");
	$("#tabYesway").css("background","url(images/new_center_bg01.gif)");
    mapobjCenterPoi(pid);
}

//点击搜索按钮搜索九五智驾POI
function search95Poi() {
	var city = $("#city").val();
	if(city == "全国") { // 选择全国
		alert("无法基于全国查询POI信息");
		return false;
	} else if(($("#city").val() - $("#city").val()) != 0) { // 选择省份
		alert("无法基于省份查询POI信息");
		return false;
	}
	
	var key = $("#key").val().replace(/(^\s*)|(\s*$)/g, "");
	if(key == ""){
		alert("请输入关键字");
		$("#key").focus();
		return false;
	}
	search95PoiList(city,key,0,10);
}

//搜索95190 POI库
function search95PoiList(city, key, currentpage, countperpage){
	if(key != "") {
    	key = key.replace(/'/g, '＇');
    	key = key.replace(/"/g, '＂');
    	key = key.replace(/&/g, '＆');
    }
	$("#poiTitle").html("九五智驾导航库");
	var url = 'yeswayPoiAction!getPoiList.do';
	var pars = {
		city : city,
		key : key,
		currentpage : currentpage,
		countperpage : countperpage
	};
	$.ajax({
		url : url,
		type : "post",
		data : pars,
		beforeSend : function(XMLHttpRequest) {
			XMLHttpRequest.setRequestHeader("RequestType", "ajax");
			$("#resultYP").html(tipLoading);
		},
		success : function(data, textStatus) {
			eval(data);
			try {
				var divResult = "";
				if(!result.datas || result.datas.length == 0) {
                    $("#resultYP").html(tipNoResult);
                } else {
					var height = document.documentElement.clientHeight-118;
                    divResult += "<div style=\"width:100%; height:" + height + "px; overflow-y:auto;overflow-x:hidden;\" id=\"divResultYP\">";
                    divResult += "<table width=\"95%\"  border=\"0\" cellpadding=\"2\" cellspacing=\"0\">";
                    divResult += "<tr>";
                    divResult += "<td>" + result.total + "个结果</td>";
					if(result.totalpage > 1) {
						divResult += "<td align=\"right\">";
						if(result.total > 0 && currentpage > 0) {
							divResult += "<a href=\"javascript:search95PoiList('" + city + "','" + key.replace(/'/g,"’") + "'," + (Number(currentpage)-1) + "," + countperpage + ")\">上</a> ";
						} else {
							divResult += "上 ";
						}
						var start = 0;
						var end = currentpage + 3;
						if(currentpage > 2) start = currentpage - 2;
						if(end < 5) end = 5;
						if(end > result.totalpage) end = result.totalpage;        
						for(var i = start; i < end; i++) {
							if(i == currentpage) {
								divResult += (i + 1) + " ";
							} else {
								divResult += "<a href=\"javascript:search95PoiList('" + city + "','" + key.replace(/'/g,"’") + "'," + i + "," + countperpage + ")\">" + (i+1) + "</a> ";
							}
						}
						if(result.total > 0 && currentpage < (result.totalpage -1)) {
							divResult += "<a href=\"javascript:search95PoiList('" + city + "','" + key.replace(/'/g,"’") + "'," +(Number(currentpage)+1)+"," + countperpage + ")\">下</a> ";
						} else {
							divResult += "下 ";
						}
						divResult += "</td>";
					}
                    divResult += "</tr>";
                    divResult += "</table>";

                    divResult += "<table width=\"95%\"  border=\"0\" cellpadding=\"2\" cellspacing=\"0\">";
                    for(var i = 0; i <result.datas.length; i++) {
                    	var pca = result.datas[i].city;
						if(result.datas[i].area != "") {
							if(result.datas[i].address != "") {
								pca += "-" + result.datas[i].area + "," + result.datas[i].address;
							} else {
								pca += "-" + result.datas[i].area;
							}
						} else {
							if(result.datas[i].address != "") {
								pca += "," + result.datas[i].address;
							}
						}
                        var backgroundColor = "";
                        if(i % 2 == 0) {
                            backgroundColor = "#F2F3F8";
                        } else {
                            backgroundColor = "#E6E8F1";
                        }
                        divResult += "<tr bgcolor=\"" + backgroundColor + "\" onmouseover=\"this.style.backgroundColor='#F0E6D9';\" onmouseleave=\"this.style.backgroundColor='" + backgroundColor + "';\" onclick=\"showTipYesway('" + result.datas[i].pid + "')\">";        
                        divResult += "<td valign=\"top\"> <font color=\"#FF0000\">" + (i+1) + "." +  result.datas[i].name + "</font><br/>";
                        divResult += "地址：" + pca + "<br/>";
                        divResult += "电话：" +  result.datas[i].phone + "<br/>";
                        divResult += "</td></tr>";
                        divResult += "<tr><td align=\"right\" bgcolor=\"" + backgroundColor + "\"><a href=\"javascript:setDestination('" + result.datas[i].province + "','" + result.datas[i].city + "','" + result.datas[i].name + "','" + result.datas[i].strlonlat + "','" + result.datas[i].address + "','" + result.datas[i].phone + "')\">设为目的地</a></td></tr>";
                    }
                    divResult += "</table>";
                    divResult += "<table width=\"95%\"  border=\"0\" cellpadding=\"2\" cellspacing=\"0\">";
                    divResult += "<tr>";
                    divResult += "<td>" + result.total + "个结果</td>";
					if(result.totalpage > 1) {
						divResult += "<td align=\"right\">";
						if(result.total > 0 && currentpage > 0) {
							divResult += "<a href=\"javascript:search95PoiList('" + city + "','" + key.replace(/'/g,"’") + "'," + (Number(currentpage)-1) + "," + countperpage + ")\">上</a> ";
						} else {
							divResult += "上 ";
						}
						var start = 0;
						var end = currentpage + 3;
						if(currentpage > 2) start = currentpage - 2;
						if(end < 5) end = 5;
						if(end > result.totalpage) end = result.totalpage;        
						for(var i = start; i < end; i++) {
							if(i == currentpage) {
								divResult += (i+1) + " ";
							} else {
								divResult += "<a href=\"javascript:search95PoiList('" + city + "','" + key.replace(/'/g,"’") + "'," + i + "," + countperpage + ")\">" + (i+1) + "</a> ";
							}
						}
						if(result.total > 0 && currentpage < (result.totalpage -1)) {
							divResult += "<a href=\"javascript:search95PoiList('" + city + "','" + key.replace(/'/g,"’") + "',"+(Number(currentpage)+1)+"," + countperpage + ")\">下</a> ";
						} else {
							divResult += "下 ";
						}
						divResult += "</td>";
					}
                    divResult += "</tr>";
                    divResult += "</table>";

                    divResult += "</div>";

                    $("#resultYP").html(divResult);
                    $("#tabMapbar").css("background","url(images/new_center_bg02.gif)");
                	$("#tabYesway").css("background","url(images/new_center_bg01.gif)");
                    mappois = result.datas;
                    showmap_poisYesway = result.datas;
                    mapReady();
                }
			} catch (e) {
				$("#resultYP").html(tipFail);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$("#resultYP").html(tipFail);
		},
		complete : function(XMLHttpRequest, textStatus) {

		}
	});
}

//缺失poi登记
function hiatusPoi(){
	var city = $("#city").val();
	var provinceName = "";
	var cityName = "";
	if(city == "全国") { // 选择全国
		alert("无法基于全国登记POI信息");
		return false;
	} else if(($("#city").val() - $("#city").val()) != 0) { // 选择省份
		alert("无法基于省份登记POI信息");
		return false;
	} else {
		cityName = $("#city").find("option:selected").html();
		cityName = cityName.replace(" ├ ", "");
		cityName = cityName.replace("　　", "");
		//城市所属省份
        for (var p in ps) {
        	var pv = ps[p].split(",");
        	for(var i=0; i<pv.length; ++i) {
        		if(pv[i] == city) {
        			provinceName = p;
        		}
        	}
        }
        if(provinceName == "") {
        	provinceName = cityName;
    	}
	}
	var key = $("#key").val();
	if(key == ""){
		alert("请输入关键字");
		$("#key").focus();
		return false;
	}
	var url = 'yeswayPoiAction!hiatusPoi.do';
	var pars = {
		sTspcode : city,
		sPOIName : key,
		sCustomerName : "现代",
		sJobNumber : counselor	
	};
	$.ajax({
		url : url,
		type : "post",
		data : pars,
		beforeSend : function(XMLHttpRequest) {
			XMLHttpRequest.setRequestHeader("RequestType", "ajax");
		},
		success : function(data, textStatus) {
			eval(data);
			try {
				if(result == true){
					//alert("缺失POI登记成功！");
					var desc = cityName;
					if(provinceName != cityName) {
						desc = provinceName + desc;
					}
					var serviceDesc = parent.topFrame.serviceRecord.serviceRecordForm.serviceDesc.value;
					if(serviceDesc.indexOf("缺失点") >= 0) {
						desc = "\n" + desc + key;				
					} else if(serviceDesc == ""){
						desc = "缺失点:\n" + desc + key;
					} else {
						desc = "\n缺失点:\n" + desc + key;
					}
					parent.topFrame.serviceRecord.serviceRecordForm.serviceDesc.value += desc;
				} else {
					alert("缺失POI登记错误，请联系系统管理员");
				}
			} catch (e) {
				alert("缺失POI登记异常");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("缺失POI登记异常！");
		},
		complete : function(XMLHttpRequest, textStatus) {

		}
	});
}

$(document).ready(function() {
	initCity(searchCity);
    initPoiclass("type");
    initPoiclass("atype");

    if(searchCity != "") {
        $("#city").attr("value",searchCity);
    }
    if(searchType != "") {
        $("#type").attr("value",searchType);
    }
    if(searchRange != "" && searchRange != "0") {
        $("#range").attr("value",searchRange);
    }
    $("#key").val(searchKey);
    if(searchAtype != "") {
        $("#atype").attr("value",searchAtype);
    }
    $("#akey").val(searchAkey);

	parent.midFrame.hideFrame();
    parent.mid3Frame.hideFrame();    

    $("#mapDiv").width(document.documentElement.clientWidth-462);
    $("#mapDiv").height(document.documentElement.clientHeight-30);
    TApiInit(initMap);    

    searchPOI1();
});