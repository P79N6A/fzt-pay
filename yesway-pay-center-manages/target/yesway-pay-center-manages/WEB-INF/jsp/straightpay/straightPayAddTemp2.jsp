<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title></title>
<meta name="decorator" content="mainframe" />
<!--[if lt IE 9]>
		<script src="js/html5.js"></script>	
	<![endif]-->
	<style type="text/css">
	.uploadImages{position:relative;width: 80px;height: 80px;background: url("<%=request.getContextPath()%>/static/images/uploadImages.png") no-repeat center;/* vertical-align: middle; */border:1px #eee solid;cursor: pointer;}
	.uploadImages img{width: 100%;height:100%;}
	.uploadImages input{position:absolute;display: block;top:0;left:0;width: 100%;height:100%; opacity:0;filter:alpha(opacity=0)}
	.showImg .imgSrc img {
		width: 80px;
		height: 80px;
	}
	.imgSrc{display: inline-block;position: relative;}
	.imgSrc span{display: block;position:absolute;top:0;right:0;width: 12px;height: 12px;background: url("<%=request.getContextPath()%>/static/images/close.png") no-repeat center;cursor: pointer;}
	.uploadImages{position:relative;width: 80px;height: 80px;background: url("<%=request.getContextPath()%>/static/images/uploadImages.png") no-repeat center;/* vertical-align: middle; */border:1px #eee solid;cursor: pointer;}
	.uploadImages img{width: 100%;height:100%;}
	.uploadImages input{position:absolute;display: block;top:0;left:0;width: 100%;height:100%; opacity:0;filter:alpha(opacity=0)}
</style>
</head>
<body>
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			二级商户申请管理
		</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i>二级商户列表</a></li>
			<li><a href="#"><i class="fa"></i>二级商户申请</a></li>
			<!-- <li class="active">Here</li> -->
		</ol>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="box box-solid box-default">
			<div class="box-header">二级商户申请</div>
			<div class="box-body">
				<div class="form-group">
					<input type="hidden" name="imageUrl" id="imageUrl" value=""/>
					<label class="col-sm-2 control-label">图标上传</label>
					<div class="input-group col-sm-5">
						<div id="imgButton" class="uploadImages"></div>
						<i>*</i><span id="imgMessage">(上传图片尺寸：180px*180px，图片格式：支持jpg、png)</span>
						<div id="imgContainer"></div>
					</div>
				</div>
				<div class="form-group">
					<%--<input type="hidden" name="imageUrl" id="imageUrl" value="${oem.imageUrl}"/>--%>
						<div id="imgTd">
							<div id="imgshot" class="uploadImages"></div>
							<div id="showImg" style="height:80px;"></div>
							<span id="msg" class="uploadTip">（请依据车机屏幕大小上传合适尺寸的图片，图片格式：支持JPG、PNG）</span>
							<div id="imgBar" style="margin-right:0;overflow-y: scroll;"></div>
						</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">商户编号</label>
					<div class="btn-group">
						<input type="text" id="external_id" name="external_id"  value=""  class="form-control" placeholder="" style="width: 150px; display: inline;">
					</div>
					<span style="display:inline-block;width:222px;text-align:right;">城市编码</span>
					<div class="btn-group">
						<input type="text" id="business_address.city_code" name="business_address.city_code"  class="form-control" value=""  placeholder="" style="width: 150px; display: inline;">
					</div>
					<span style="display:inline-block;width:222px;text-align:right;">联系人名字</span>
					<div class="btn-group">
						<input type="text" id="contact_infos.name" name="contact_infos.name"  class="form-control" value=""  placeholder="" style="width: 150px; display: inline;">
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="button" onclick="addButton()"  class="btn btn-primary">保 存</button>
						<button type="button" class="btn btn-default" onclick="javascript:history.back(-1);">返 回</button>
					</div>
				</div>
			</div>
		</div>
	</section>
	<script>
        var path = "<%=request.getContextPath()%>";
        var serverPath ="<%=cn.yesway.bmw.manage.utils.PropUtils.get("file_url")%>";
        function addButton(){
            //经营地址
			var addressInfo = {
				'city_code':"1",
				'district_code':"1",
				'address':"1",
				'province_code':"1",
				'longitude':"1",
				'latitude':"1",
				'type':"1"
			};
			//商户联系人信息
            var contactInfo = {
                'name':"1",
                'phone':"1",
                'mobile':"1",
                'email':"1",
                'tag':"1",
                'type':"1",
                'id_card_no':"1"
			};
			//商户行业资质
			var industryQualificationInfo = {
				'industry_qualification_type':"1",
				'industry_qualification_image':"1"
			};
			//开票资料信息
			var merchantInvoiceInfo = {
                'auto_invoice':true,
                'accept_electronic':true,
                'tax_payer_qualification':"1",
                'title':"1",
                'tax_no':"1",
                'tax_payer_valid':"1",
                'address':"1",
                'telephone':"1",
                'bank_account':"1",
                'mail_name':"1",
                'mail_telephone':"1",
                'bank_name':"1",
                'mail_address':addressInfo
			}
			//商户结算卡信息
			var settleCardInfo = {
                'account_holder_name':"1",
                'account_no':"1",
                'account_inst_province':"1",
                'account_inst_city':"1",
                'account_branch_name':"1",
                'usage_type':"1",
                'account_type':"1",
                'account_inst_name':"1",
                'account_inst_id':"1",
                'bank_code':"1"
			};
			//商户站点信息
			var siteInfo = {
                'site_type':"1",
                'site_url':"1",
                'site_name':"1",
                'account':"1",
                'password':"1"
			};
			var sites =  new Array();
            for (var i = 0; i <2 ; i++) {
                sites[i] = siteInfo;
            }
            var qualifications = new Array();
            for (var i = 0; i <2 ; i++) {
                qualifications[i] = industryQualificationInfo;
            }
            var biz_cards = new Array();
            for (var i = 0; i <2 ; i++) {
                biz_cards[i] = settleCardInfo;
            }
            var contact_infos = new Array();
            for (var i = 0; i <2 ; i++) {
                contact_infos[i] = contactInfo;
            }
            var service = new Array();
            for (var i = 0; i <2 ; i++) {
                service[i]= i;
            }
            var out_door_images = new Array();
            for (var i = 0; i <2 ; i++) {
                out_door_images[i]= i;
            }
            var bizContent = {
				'external_id':"1",
				'out_biz_no':"1",
				'name':"1",
				'alias_name':"1",
				'merchant_type':"1",
				'mcc':"1",
				'cert_no':"1",
				'cert_type':"1",
				'cert_image':"1",
				'legal_name':"1",
				'legal_cert_no':"1",
				'legal_cert_front_image':"1",
				'legal_cert_back_image':"1",
				'service_phone':"1",
				'out_door_images':out_door_images,
				'license_auth_letter_image':"1",
				'service':service,
				'sign_time_with_isv':"1",
				'alipay_logon_id':"1",
				'cert_image_back':"1",
				'business_address': addressInfo,
				'contact_infos':contact_infos,
				'biz_cards':biz_cards,
				'qualifications':qualifications,
				'sites': sites,
				'invoice_info': merchantInvoiceInfo
            };
            $.ajax({
                url: path+"/aliStraightPay/save",
                type:'POST',
                datetype : "json",
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(bizContent),
                success:function(data){
                    alert(data);
                }
            });
        }
	</script>
	<script src="${request.contextPath}/static/plugins/jquery/jquery-2.1.4.min.js"></script>
	<script src="${request.contextPath}/static/plugins/uploadStream/js/stream-v1.js"></script>
	<script src="${request.contextPath}/static/upload/appshotImg.js"></script>
	<script src="${request.contextPath}/static/upload/upload.js"></script>
</body>

</html>