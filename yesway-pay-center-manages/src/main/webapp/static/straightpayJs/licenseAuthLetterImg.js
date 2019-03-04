//上传程序包
var legalCertBackImgConfig = {
	browseFileId : "licenseAuthLetterImgButton",
	browseFileBtn : " ",
	dragAndDropArea : "licenseAuthLetterImgButton",
	dragAndDropTips : "<span></span>",
	filesQueueId : "licenseAuthLetterImgContainer",
	filesQueueHeight : 80,
	messagerId : "licenseAuthLetterImgMessage",
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
//				$("#licenseAuthLetterImgMessage").text("上传尺寸不对");
//				$("#licenseAuthLetterImgButton").html("");
//				uploadAddInfo.isUploadImg = false;
//				uploadUpdateInfo.isUploadImg = false;
//			} else {
				$("#licenseAuthLetterImgContainer").show();
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
		$("#licenseAuthLetterImgMessage").text("上传格式不对");
		$("#licenseAuthLetterImgButton").html("");
		uploadAddInfo.isUploadImg = false;
		uploadUpdateInfo.isUploadImg = false;
	},
	onCancel : function(file) {
	},
	onComplete : function(file) {
		$("#licenseAuthLetterImgContainer").hide();
		document.getElementById("licenseAuthLetterImgMessage").innerHTML = "上传成功!";
		document.getElementById("licenseAuthLetterImgButton").innerHTML = "<img id='imgSrc' src='"
				+ serverPath + eval("(" + file.msg + ")").message + "'>";
		$("#license_auth_letter_image_url").val(serverPath + eval("(" + file.msg + ")").message);
		$("#license_auth_letter_image").val(eval("(" + file.msg + ")").imageId);
		uploadAddInfo.isUploadImg = true;
		uploadUpdateInfo.isUploadImg = true;
		console.log(file.msg);
	},
	onQueueComplete : function() {
	},
	onUploadError : function(status, msg) {
		$("#licenseAuthLetterImgMessage").text("上传失败");
        $("#licenseAuthLetterImgButton").html("");
		uploadAddInfo.isUploadImg = false;
		uploadUpdateInfo.isUploadImg = false;
	},
	onDestroy : function() {
	}
};

var img = new Stream(legalCertBackImgConfig);

// 上传是否成功的相关信息
var uploadAddInfo = {
	isUploadImg : false,
	isUploadApp : false
};
var uploadUpdateInfo = {
	isUploadImg : true,
	isUploadApp : true
};
