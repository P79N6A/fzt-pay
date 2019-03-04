//上传程序包
var imgConfig = {
	browseFileId : "imgButton",
	browseFileBtn : " ",
	dragAndDropArea : "imgButton",
	dragAndDropTips : "<span></span>",
	filesQueueId : "imgContainer",
	filesQueueHeight : 80,
	messagerId : "imgMessage",
	multipleFiles : false,
	swfURL : path + "/static/plugins/uploadStream/swf/FlashUploader.swf",
	tokenURL : path + "/getToken?parentPath=image",
	frmUploadURL : path + "/flashUpload",
	uploadURL : path + "/html5Upload",
	extFilters : [ ".jpg", ".png" ],
	onSelect : function(list) {
		$("#prompt").text("请填写完所有带“*”的信息，谢谢。");
		return false;/* true表示取消选择,false表示通过选择 */
	},
	onImgGet : function(imgs, callback, uploader, self) {
		var image = new Image();
		image.onload = function() {
			debugger
//			if (image.height !== 180 || image.width !== 180) {
//				$("#imgMessage").text("上传尺寸不对");
//				$("#imgButton").html("");
//				uploadAddInfo.isUploadImg = false;
//				uploadUpdateInfo.isUploadImg = false;
//			} else {
				$("#imgContainer").show();
				callback.call(self, uploader);
//			}
			image.onload = null;
		};
		
		if (window.webkitURL) {
			var imgsrc = window.webkitURL.createObjectURL(imgs[0]);
			image.src = imgsrc;
		} else if (window.URL) {
			var imgsrc = window.URL.createObjectURL(imgs[0]);
			image.src = imgsrc;
		} else {
			var reader = new FileReader();
			reader.onload = function(e) {
				image.src = e.target.result;
			};
			reader.readAsDataURL(imgs[0]);
		}

	},
	onMaxSizeExceed : function(size, limited, name) {
	},
	onFileCountExceed : function(selected, limit) {
	},
	onExtNameMismatch : function(name, filters) {
		$("#imgMessage").text("上传格式不对");
		$("#imgButton").html("");
		uploadAddInfo.isUploadImg = false;
		uploadUpdateInfo.isUploadImg = false;
	},
	onCancel : function(file) {
	},
	onComplete : function(file) {
		$("#imgContainer").hide();
		document.getElementById("imgMessage").innerHTML = "上传成功!";
		document.getElementById("imgButton").innerHTML = "<img id='imgSrc' src='"
				+ serverPath + eval("(" + file.msg + ")").message + "'>";
		$("#imageUrl").val(serverPath + eval("(" + file.msg + ")").message);
		uploadAddInfo.isUploadImg = true;
		uploadUpdateInfo.isUploadImg = true;
		console.log(file.msg);
	},
	onQueueComplete : function() {
	},
	onUploadError : function(status, msg) {
		$("#imgMessage").text("上传失败");
		$("#uploadImg").html("");
		uploadAddInfo.isUploadImg = false;
		uploadUpdateInfo.isUploadImg = false;
	},
	onDestroy : function() {
	}
};

var img = new Stream(imgConfig);

// 上传是否成功的相关信息
var uploadAddInfo = {
	isUploadImg : false,
	isUploadApp : false
};
var uploadUpdateInfo = {
	isUploadImg : true,
	isUploadApp : true
};
