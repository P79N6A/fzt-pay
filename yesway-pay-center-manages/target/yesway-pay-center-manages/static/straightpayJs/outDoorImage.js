var outDoorImageOpt = {
	browseFileId : "outDoorImageShot",
	browseFileBtn : " ",
	dragAndDropArea : "outDoorImageShot",
	dragAndDropTips : "<span></span>",
	filesQueueId : "imgBar",
	filesQueueHeight : 80,
	messagerId : "msg",
	multipleFiles : true,
	swfURL : path + "/static/plugins/uploadStream/swf/FlashUploader.swf",
	tokenURL : path + "/getToken?parentPath=image",
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
		// $("#imgBar").show();
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
		debugger
		var src= serverPath+eval("(" + file.msg + ")").message;
		var imageId= eval("(" + file.msg + ")").imageId;
		srcArr.push(src);
        imageIdArr.push(imageId);
	},
	onQueueComplete : function() {
		$("#imgBar").hide();
		document.getElementById("msg").innerHTML = "上传成功!";
		var imgHtml = "";
		for(var i=0;i<srcArr.length;i++){
			imgHtml+="<div class='imgSrc'><img src='"+srcArr[i]+"' myvalue='"+imageIdArr[i]+"' style='width: 80px;height: 80px'/><span class='delImg'></span></div>";
		}
		srcArr=[];
		/*$("#imgshot").empty();
		$("#imgshot").append(imgHtml);*/
		$("#showImg").append(imgHtml);
		if($(".imgSrc").length>=5) $("#outDoorImageShot").hide();
		isScreenUploadImg=true;
		isScreenEditUploadImg=true;
        setImgPaths()
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
var appImg = new Stream(outDoorImageOpt);
var srcArr = [];
var imageIdArr = [];
var isScreenUploadImg = false;
var isScreenEditUploadImg = true;

$("#imgTd").on("click",".delImg",function(e){
	e.stopPropagation();
	var imgDiv = this.parentNode;
	var tdDiv = imgDiv.parentNode;
	tdDiv.removeChild(imgDiv);
	$("#imgshot").show();
    setImgPaths();
});
function setImgPaths() {
    var imgs = "";
    var imageIds = "";
    var obj = document.getElementById("showImg").getElementsByTagName("img");
    debugger
    for (var i = 0; i <obj.length ; i++) {
        imgs += (obj[i].src+",");
        imageIds += (obj[i].getAttribute("myvalue")+",");
    }
    $("#out_door_images_url").val(imgs.substring(0,imgs.length-1));
    $("#out_door_images").val(imageIds.substring(0,imageIds.length-1));
}