var tabID = null;

$(document).ready(function() {
	demandAllServer(serviceName);
});
function refresh(){
	parent.openTab(serviceName , serviceName, basePath + "/deploy/server/serviceInfo?serviceName="+serviceName);
}
function goToServerPage(ip) {
	parent.openTab(("deploy_" + ip).replace(new RegExp(/\./g), "_"), ip, (basePath + "/deploy/server/status.html?ip=" + ip));
}
function openOrClose(ip, cmdType, serviceName) {
	var url = basePath + "/deploy/server/openOrClose?ip=" + ip + "&cmdType=" + cmdType + "&serviceName=" + serviceName;
	parent.openTab(tabId, "${server.ip}", url);
}
/**
 * 发送多个ajax查询每个服务器的某服务状况
 */
function demandAllServer(serviceName) {
	$("#allServers tr").each(function() {
		var $serverMainTr = $(this);
		var ip = $serverMainTr.attr("ip");
//		var firstTdInTr = $(serverMainTr).find("td")[0];
		$.ajax({
			url :basePath+"/deploy/server/singleSrviceInfo",
			type : "GET",
			data : {
				ip : ip,
				serviceName : serviceName
			},
			success : function(dataMap) {
				/* 这服务器上没有这个服务，移除此服务器相关信息 */
				if (!dataMap || !dataMap.versions) {
					$serverMainTr.remove();
					return;
				}

				// 循环服务器上的各版本，组织页面
				for(var i = 0 ; i < dataMap.versions.length ; i++){
					var infoArr = dataMap.versions[i].split(",");
					var serviceName =  infoArr[0];
					var servicePid =  infoArr[1];
					var $currentTr = $serverMainTr;
					//若非第一条，增加该服务器下的第二行数据
					if(i != 0){
						$currentTr.after("<tr><td name=\"serviceName\"></td><td name=\"servicePid\"></td><td name=\"operations\"></td></tr>");
						$currentTr = $currentTr.next();
					}
					// 填表
					$currentTr.find("td[name='serviceName']").html(serviceName);
					$currentTr.find("td[name='servicePid']").html(servicePid);
					$currentTr.find("td[name='operations']").append("--"==servicePid?
							"<button type=\"button\" class=\"btn btn-sm btn-success\" id=\"serviceOpenOrClose\" onclick=\"javascript:openOrClose('"+ip+"',1,'"+serviceName+"');\">启动</button>":
								"<button type=\"button\" class=\"btn btn-sm btn-danger\" id=\"serviceOpenOrClose\" onclick=\"javascript:openOrClose('"+ip+"',0,'"+serviceName+"');\">停止</button>");
				}
				// 合并单元格
				$serverMainTr.find("td:first-child").attr("rowspan", dataMap.versions.length);
				// 显示
				$serverMainTr.show();
				// 若无隐藏的tr，则说明最后一条ajax执行完毕，刷新按钮停止转动
				if($("#allServers tr:hidden").length < 1){
					// fa-spin转动，fa-refresh不转动
					$("#serviceStatus_refresh i").removeClass("fa-spin").addClass("fa-refresh");
				}
			}
		});
	});
}


function openOrClose(ip,cmdType,wholeServiceName){
	var tabId = "deploy_${server.ip}".replace(new RegExp(/\./g), "_")
	
	serviceName= wholeServiceName.split("-0.")[0];
	var cmdUrl = basePath+"/deploy/server/openOrCloseInServicePage?ip="+ip+"&cmdType="+cmdType+"&serviceName="+wholeServiceName;

	var url = basePath+"/deploy/server/serviceInfo?serviceName="+serviceName;
	parent.openTab(serviceName , serviceName, cmdUrl);
}
