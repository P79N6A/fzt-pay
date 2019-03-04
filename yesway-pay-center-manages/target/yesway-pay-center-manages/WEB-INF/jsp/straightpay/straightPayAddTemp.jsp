<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<script type="text/javascript" src="../zTree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="../zTree/js/jquery.ztree.excheck-3.5.min.js"></script>
</head>
<body>
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			二级商户申请管理 <small>二级商户审核信息提交</small>
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
        function addButton(){
            //经营地址
			var addressInfo = {
				'city_code':$("#city_code").val(),
				'district_code':$("#district_code").val(),
				'address':$("#address").val(),
				'province_code':$("#province_code").val(),
				'longitude':$("#longitude").val(),
				'latitude':$("#latitude").val(),
				'type':$("#type").val()
			};
			//商户联系人信息
            var contactInfo = {
                'name':$("#name").val(),
                'phone':$("#phone").val(),
                'mobile':$("#mobile").val(),
                'email':$("#email").val(),
                'tag':$("#tag").val(),
                'type':$("#type").val(),
                'id_card_no':$("#id_card_no").val()
			};
			//商户行业资质
			var industryQualificationInfo = {
				'industry_qualification_type':$("#industry_qualification_type").val(),
				'industry_qualification_image':$("#industry_qualification_image").val()
			};
			//开票资料信息
			var merchantInvoiceInfo = {
                'auto_invoice':$("#auto_invoice").val(),
                'accept_electronic':$("#accept_electronic").val(),
                'tax_payer_qualification':$("#tax_payer_qualification").val(),
                'title':$("#title").val(),
                'tax_no':$("#tax_no").val(),
                'tax_payer_valid':$("#tax_payer_valid").val(),
                'address':$("#address").val(),
                'telephone':$("#telephone").val(),
                'bank_account':$("#bank_account").val(),
                'mail_name':$("#mail_name").val(),
                'mail_telephone':$("#mail_telephone").val(),
                'bank_name':$("#bank_name").val(),
                'mail_address':addressInfo
			}
			//商户结算卡信息
			var settleCardInfo = {
                'account_holder_name':$("#account_holder_name").val(),
                'account_no':$("#account_no").val(),
                'account_inst_province':$("#account_inst_province").val(),
                'account_inst_city':$("#account_inst_city").val(),
                'account_branch_name':$("#account_branch_name").val(),
                'usage_type':$("#usage_type").val(),
                'account_type':$("#account_type").val(),
                'account_inst_name':$("#account_inst_name").val(),
                'account_inst_id':$("#account_inst_id").val(),
                'bank_code':$("#bank_code").val()
			};
			//商户站点信息
			var siteInfo = {
                'site_type':$("#site_type").val(),
                'site_url':$("#site_url").val(),
                'site_name':$("#site_name").val(),
                'account':$("#account").val(),
                'password':$("#password").val()
			};
			var sites =  new Array();
            var qualifications = new Array();
            var biz_cards = new Array();
            var contact_infos = new Array();
            var service = new Array();
            var out_door_images = new Array();
            var bizContent = {
				'external_id':$("#external_id").val(),
				'out_biz_no':$("#out_biz_no").val(),
				'name':$("#name").val(),
				'alias_name':$("#alias_name").val(),
				'merchant_type':$("#merchant_type").val(),
				'mcc':$("#mcc").val(),
				'cert_no':$("#cert_no").val(),
				'cert_type':$("#cert_type").val(),
				'cert_image':$("#cert_image").val(),
				'legal_name':$("#legal_name").val(),
				'legal_cert_no':$("#legal_cert_no").val(),
				'legal_cert_front_image':$("#legal_cert_front_image").val(),
				'legal_cert_back_image':$("#legal_cert_back_image").val(),
				'service_phone':$("#service_phone").val(),
				'out_door_images':out_door_images,
				'license_auth_letter_image':$("#license_auth_letter_image").val(),
				'service':service,
				'sign_time_with_isv':$("#sign_time_with_isv").val(),
				'alipay_logon_id':$("#alipay_logon_id").val(),
				'cert_image_back':$("#cert_image_back").val(),
				'business_address': addressInfo,
				'contact_infos':contact_infos,
				'biz_cards':biz_cards,
				'qualifications':qualifications,
				'sites': sites,
				'invoice_info': merchantInvoiceInfo
            };
            $.ajax({
                url: path+"/aliStraightPay/save",
                type:'post',
                data: bizContent,
                datetype : "json",
                success:function(data){
                    alert(data);
                    window.location.href = "http://hotels.ctrip.com/";
                }
            });
        }
	</script>
</body>

</html>