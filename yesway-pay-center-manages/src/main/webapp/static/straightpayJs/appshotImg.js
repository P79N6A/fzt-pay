var appImgShotOpt = {
	browseFileId : "imgshot",
	browseFileBtn : " ",
	dragAndDropArea : "imgshot",
	dragAndDropTips : "<span></span>",
	filesQueueId : "imgBar",
	filesQueueHeight : 80,
	messagerId : "msg",
	multipleFiles : true,
	swfURL : path + "/static/plugins/uploadStream/swf/FlashUploader.swf",
	tokenURL : path + "/getToken?parentPath=appImgShot",
	frmUploadURL : path + "/flashUpload",
	uploadURL : path + "/html5Upload",
	extFilters : [ ".jpg", ".png" ],
	onSelect : function(list) {
		var selectLen = list.length;
		var uploadImgLen = $(".imgSrc").length || 0;
		if((selectLen+uploadImgLen)>5){
			$("#msg").text("最多上传5张截图");
			isScreenUploadImg = false;
			isScreenEditUploadImg=false;
			return true;
		}
		$("#imgBar").show();
		return false;/* true表示取消选择,false表示通过选择 */
	},
	onMaxSizeExceed : function(size, limited, name) {
	},
	onFileCountExceed : function(selected, limit) {
	},
	onExtNameMismatch : function(name, filters) {
		$("#msg").text("上传格式不对");
		$("#adUploadImg").html("");
		isScreenUploadImg = false;
		isScreenEditUploadImg=false;
	},
	onCancel : function(file) {
	},
	onComplete : function(file) {
		var src= serverPath+eval("(" + file.msg + ")").message;
		srcArr.push(src);
	},
	onQueueComplete : function() {
		$("#imgBar").hide();
		document.getElementById("msg").innerHTML = "上传成功!";
		var imgHtml = "";
		for(var i=0;i<srcArr.length;i++){
			imgHtml+="<div class='imgSrc'><img src='"+srcArr[i]+"'/><span class='delImg'></span></div>";
		}
		srcArr=[];
		/*$("#imgshot").empty();
		$("#imgshot").append(imgHtml);*/
		$("#showImg").append(imgHtml);
		if($(".imgSrc").length>=5) $("#imgshot").hide();
		isScreenUploadImg=true;
		isScreenEditUploadImg=true;
	},
	onUploadError : function(status, msg) {
		$("#msg").text("上传失败");
		$("#adUploadImg").html("");
		isScreenUploadImg = false;
		isScreenEditUploadImg=false;
	},
	onDestroy : function() {
		
	}
};
var appImg = new Stream(appImgShotOpt);
var srcArr = [];
var isScreenUploadImg = false;
var isScreenEditUploadImg = true;

$("#imgTd").on("click",".delImg",function(e){
	e.stopPropagation();
	var imgDiv = this.parentNode;
	var tdDiv = imgDiv.parentNode;
	tdDiv.removeChild(imgDiv);
	$("#imgshot").show();
});