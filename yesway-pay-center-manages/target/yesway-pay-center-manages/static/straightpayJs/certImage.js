//上传程序包
var certImgConfig = {
	browseFileId : "certImgButton",
	browseFileBtn : " ",
	dragAndDropArea : "certImgButton",
	dragAndDropTips : "<span></span>",
	filesQueueId : "certImgContainer",
	filesQueueHeight : 80,
	messagerId : "certImgMessage",
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
//				$("#certImgMessage").text("上传尺寸不对");
//				$("#certImgButton").html("");
//				uploadAddInfo.isUploadImg = false;
//				uploadUpdateInfo.isUploadImg = false;
//			} else {
				$("#certImgContainer").show();
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
		$("#certImgMessage").text("上传格式不对");
		$("#certImgButton").html("");
		uploadAddInfo.isUploadImg = false;
		uploadUpdateInfo.isUploadImg = false;
	},
	onCancel : function(file) {
	},
	onComplete : function(file) {
		$("#certImgContainer").hide();
		document.getElementById("certImgMessage").innerHTML = "上传成功!";
		document.getElementById("certImgButton").innerHTML = "<img id='imgSrc' src='"
				+ serverPath + eval("(" + file.msg + ")").message + "'>";
		$("#cert_image_url").val(serverPath + eval("(" + file.msg + ")").message);
		$("#cert_image").val(eval("(" + file.msg + ")").imageId);
		uploadAddInfo.isUploadImg = true;
		uploadUpdateInfo.isUploadImg = true;
		console.log(file.msg);
	},
	onQueueComplete : function() {
	},
	onUploadError : function(status, msg) {
		$("#certImgMessage").text("上传失败");
        $("#certImgButton").html("");
		uploadAddInfo.isUploadImg = false;
		uploadUpdateInfo.isUploadImg = false;
	},
	onDestroy : function() {
	}
};

var img = new Stream(certImgConfig);

// 上传是否成功的相关信息
var uploadAddInfo = {
	isUploadImg : false,
	isUploadApp : false
};
var uploadUpdateInfo = {
	isUploadImg : true,
	isUploadApp : true
};
