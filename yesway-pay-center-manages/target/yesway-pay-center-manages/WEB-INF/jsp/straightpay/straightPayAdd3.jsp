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
		.box-header {
			color: aquamarine;
		}
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
			<%--企业信息--%>
			<div class="box-body" id="oneTag">
				<div class="box-header">系统信息</div>
				<div class="form-group">
					<label for="external_id" class="col-sm-2 control-label"><font color="red"></font>商户全称</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="external_id" name="external_id" placeholder="请输入商户全称" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="out_biz_no" class="col-sm-2 control-label"><font color="red"></font>外部业务号</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="out_biz_no" name="out_biz_no" placeholder="请输入商户全称" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="app_id" class="col-sm-2 control-label"><font color="red">*</font>应用ID</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="app_id" name="app_id" placeholder="请输入商户全称" value="">
					</div>
				</div>

				<div class="box-header">商户资质信息</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label"><font color="red">*</font>商户全称</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="name" name="name" placeholder="请输入商户全称" value="">
						<span>主体名称需严格按照证件返回，在注册确认后不可更改。避免付款后因主体原因导致认证失败</span>
					</div>
				</div>
				<div class="form-group">
					<label for="alias_name" class="col-sm-2 control-label"><font color="red">*</font>商户简称</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="alias_name" name="alias_name" placeholder="请输入商户简称" value="">
					</div>
				</div>

				<div class="form-group">
					<label for="merchant_type" class="col-sm-2 control-label"><font color="red">*</font>商家类型</label>
					<div class="input-group col-sm-5">
						<select class="form-control selectpicker show-tick" name="merchant_type"  id="merchant_type">
							<option value="01" >企业</option>
							<option value="02" >事业单位</option>
							<option value="07" >个体工商户</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="cert_type" class="col-sm-2 control-label"><font color="red">*</font>商户证件类型</label>
					<div class="input-group col-sm-5">
						<select class="form-control selectpicker show-tick" name="cert_type"  id="cert_type">
							<option value="201" >营业执照</option>
							<option value="2011" >营业执照(统一社会信用代码)</option>
							<option value="218" >事业单位法人证书</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<input type="hidden" name="cert_image" id="cert_image" value=""/>
					<label class="col-sm-2 control-label">商户证件照片</label>
					<div class="input-group col-sm-5">
						<div id="certImgButton" class="uploadImages"></div>
						<i>*</i><span id="certImgMessage">(上传图片尺寸：180px*180px，图片格式：支持jpg、png)</span>
						<div id="certImgContainer"></div>
					</div>
				</div>
				<div class="form-group">
					<label for="cert_no" class="col-sm-2 control-label"><font color="red">*</font>商户证件编号</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="cert_no" name="cert_no" placeholder="请输入商户证件编号" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="mcc" class="col-sm-2 control-label"><font color="red">*</font>商户类别码</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="mcc" name="mcc" placeholder="请输入商户类别码" value="">
						<span>请参考 https://mif-pub.alipayobjects.com/AlipayMCC.xlsx，输入商户类别码</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red"></font>门头照</label>
					<div id="imgTd" class="input-group col-sm-5">
						<div id="imgshot" class="uploadImages"></div>
						<div id="showImg" style="height:80px;"></div>
						<span id="msg" class="uploadTip">（请依据车机屏幕大小上传合适尺寸的图片，图片格式：支持JPG、PNG）</span>
						<div id="imgBar" style="margin-right:0;overflow-y: scroll;"></div>
					</div>
				</div>
				<div class="form-group">
					<label for="sign_time_with_isv" class="col-sm-2 control-label"><font color="red">*</font>二级商户与服务商的签约时间</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="sign_time_with_isv" name="sign_time_with_isv" placeholder="" value="">
					</div>
				</div>

				<div class="box-header">企业法人信息</div>
				<div class="form-group">
					<label for="legal_name" class="col-sm-2 control-label"><font color="red">*</font>法人名称</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="legal_name" name="legal_name" placeholder="请输入法人名称" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="legal_cert_no" class="col-sm-2 control-label"><font color="red">*</font>法人身份证号</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="legal_cert_no" name="legal_cert_no" placeholder="请输入法人身份证号" value="">
					</div>
				</div>
				<div class="form-group">
					<input type="hidden" name="legal_cert_front_image" id="legal_cert_front_image" value=""/>
					<label class="col-sm-2 control-label">法人身份证照</label>
					<div class="input-group col-sm-5">
						<div id="legalCertFrontImgButton" class="uploadImages"></div>
						<i>*</i><span id="legalCertFrontImgMessage">(上传图片尺寸：180px*180px，图片格式：支持jpg、png)</span>
						<div id="legalCertFrontImgContainer"></div>
					</div>
					<input type="hidden" name="legal_cert_back_image" id="legal_cert_back_image" value=""/>
					<label class="col-sm-2 control-label"></label>
					<div class="input-group col-sm-5">
						<div id="legalCertBackImgButton" class="uploadImages"></div>
						<i>*</i><span id="legalCertBackImgMessage">(上传图片尺寸：180px*180px，图片格式：支持jpg、png)</span>
						<div id="legalCertBackImgContainer"></div>
					</div>
				</div>

				<div class="box-header">经营场所信息</div>
				<div class="form-group">
					<label for="addressInfo.address" class="col-sm-2 control-label"><font color="red">*</font>经营地址</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="addressInfo.address" name="addressInfo.address" placeholder="请输入经营地址" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="province_code" class="col-sm-2 control-label"><font color="red"></font>企业所在省份</label>
					<div class="input-group col-sm-5">
						<select class="form-control selectpicker show-tick" name="province_code"  id="province_code">
							<option value="01" >企业</option>
							<option value="02" >事业单位</option>
							<option value="07" >个体工商户</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="city_code" class="col-sm-2 control-label"><font color="red"></font>企业所在城市</label>
					<div class="input-group col-sm-5">
						<select class="form-control selectpicker show-tick" name="city_code"  id="city_code">
							<option value="01" >企业</option>
							<option value="02" >事业单位</option>
							<option value="07" >个体工商户</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="district_code" class="col-sm-2 control-label"><font color="red"></font>企业所在区县</label>
					<div class="input-group col-sm-5">
						<select class="form-control selectpicker show-tick" name="district_code"  id="district_code">
							<option value="01" >企业</option>
							<option value="02" >事业单位</option>
							<option value="07" >个体工商户</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="service_phone" class="col-sm-2 control-label"><font color="red">*</font>客服电话</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="service_phone" name="service_phone" placeholder="" value="">
					</div>
				</div>

				<div class="box-header">特殊资质信息（非必填项，商户所属行业为特殊行业时为必填项）</div>
				<div class="form-group">
					<input type="hidden" name="industry_qualification_image" id="industry_qualification_image" value=""/>
					<label class="col-sm-2 control-label">行业资质照</label>
					<div class="input-group col-sm-5">
						<div id="industryQualificationImgButton" class="uploadImages"></div>
						<i>*</i><span id="industryQualificationImgMessage">(上传图片尺寸：180px*180px，图片格式：支持jpg、png)</span>
						<div id="industryQualificationImgContainer"></div>
					</div>
				</div>
				<div class="form-group">
					<input type="hidden" name="license_auth_letter_image" id="license_auth_letter_image" value=""/>
					<label class="col-sm-2 control-label">营业执照授权函</label>
					<div class="input-group col-sm-5">
						<div id="licenseAuthLetterImgButton" class="uploadImages"></div>
						<i>*</i><span id="licenseAuthLetterImgMessage">(上传图片尺寸：180px*180px，图片格式：支持jpg、png)</span>
						<div id="licenseAuthLetterImgContainer"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="button" class="btn btn-default" onclick="javascript:history.back(-1);">返 回</button>
						<button type="button" onclick="nextStep('oneTag')"  class="btn btn-primary">下一步</button>
					</div>
				</div>
			</div>
			<%--账户信息--%>
			<div class="box-body" id="twoTage">
				<div class="box-header">开户行信息</div>
				<div class="form-group">
					<label for="account_holder_name" class="col-sm-2 control-label"><font color="red">*</font>卡户名</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="account_holder_name" name="account_holder_name" placeholder="" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="account_branch_name" class="col-sm-2 control-label"><font color="red">*</font>开户支行</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="account_branch_name" name="account_branch_name" placeholder="" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="account_no" class="col-sm-2 control-label"><font color="red">*</font>账号</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="account_no" name="account_no" placeholder="" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="account_inst_name" class="col-sm-2 control-label"><font color="red">*</font>开户总行</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="account_inst_name" name="account_inst_name" placeholder="" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="account_inst_id" class="col-sm-2 control-label"><font color="red">*</font>开户行缩写</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="account_inst_id" name="account_inst_id" placeholder="" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="account_type" class="col-sm-2 control-label"><font color="red"></font>银行卡类型</label>
					<div class="input-group col-sm-5">
						<select class="form-control selectpicker show-tick" name="account_type"  id="account_type">
							<option value="DC" >借记卡</option>
							<option value="CC" >信用卡</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="usage_type" class="col-sm-2 control-label"><font color="red"></font>使用类型</label>
					<div class="input-group col-sm-5">
						<select class="form-control selectpicker show-tick" name="usage_type"  id="usage_type">
							<option value="01" >对公</option>
							<option value="02" >对私</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="account_inst_province" class="col-sm-2 control-label"><font color="red"></font>开户所在省份</label>
					<div class="input-group col-sm-5">
						<select class="form-control selectpicker show-tick" name="account_inst_province"  id="account_inst_province">
							<option value="01" >企业</option>
							<option value="02" >事业单位</option>
							<option value="07" >个体工商户</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="account_inst_city" class="col-sm-2 control-label"><font color="red"></font>开户所在城市</label>
					<div class="input-group col-sm-5">
						<select class="form-control selectpicker show-tick" name="account_inst_city"  id="account_inst_city">
							<option value="01" >企业</option>
							<option value="02" >事业单位</option>
							<option value="07" >个体工商户</option>
						</select>
					</div>
				</div>

				<div class="box-header">支付宝账号信息</div>
				<div class="form-group">
					<label for="alipay_logon_id" class="col-sm-2 control-label"><font color="red">*</font>支付宝账号</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="alipay_logon_id" name="alipay_logon_id" placeholder="" value="">
					</div>
				</div>

				<div class="box-header">开票信息</div>
				<div class="form-group">
					<label for="accept_electronic" class="col-sm-2 control-label"><font color="red"></font>接受电子发票</label>
					<div class="input-group col-sm-5">
						<select class="form-control selectpicker show-tick" name="accept_electronic"  id="accept_electronic">
							<option value="true" >接受</option>
							<option value="false" >不接受</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="tax_payer_qualification" class="col-sm-2 control-label"><font color="red"></font>纳税人资格种类</label>
					<div class="input-group col-sm-5">
						<select class="form-control selectpicker show-tick" name="tax_payer_qualification"  id="tax_payer_qualification">
							<option value="01" >一般纳税人</option>
							<option value="02" >小规模纳税人</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="title" class="col-sm-2 control-label"><font color="red">*</font>发票抬头</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="title" name="title" placeholder="" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="tax_no" class="col-sm-2 control-label"><font color="red">*</font>纳税人识别号</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="tax_no" name="tax_no" placeholder="" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="tax_payer_valid" class="col-sm-2 control-label"><font color="red">*</font>纳税人资格认定</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="tax_payer_valid" name="tax_payer_valid" placeholder="" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="merchantInvoiceInfo.address" class="col-sm-2 control-label"><font color="red">*</font>开票地址</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="merchantInvoiceInfo.address" name="merchantInvoiceInfo.address" placeholder="" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="telephone" class="col-sm-2 control-label"><font color="red">*</font>开票电话</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="telephone" name="telephone" placeholder="" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="mail_name" class="col-sm-2 control-label"><font color="red">*</font>收件人名称</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="mail_name" name="mail_name" placeholder="" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="address" class="col-sm-2 control-label"><font color="red">*</font>收件人地址</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="address" name="address" placeholder="" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="mail_telephone" class="col-sm-2 control-label"><font color="red">*</font>收件人电话</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="mail_telephone" name="mail_telephone" placeholder="" value="">
					</div>
				</div>
				<div class="col-sm-offset-2 col-sm-10">
					<button type="button" onclick="previousStep('twoTag')"  class="btn btn-primary">上一步</button>
					<button type="button" onclick="nextStep('twoTag')"  class="btn btn-primary">下一步</button>
				</div>
			</div>
			<%--联系人信息--%>
			<div class="box-body" id="threeTag">
				<div class="form-group">
					<label for="subMenu" class="col-sm-2 control-label"><font color="red"></font>联系人类型</label>
					<div class="input-group col-sm-5">
						<select class="form-control selectpicker show-tick" name="subMenu"  id="subMenu">
							<option value="LEGAL_PERSON" >法人</option>
							<option value="CONTROLLER" >实际控制人</option>
							<option value="AGENT" >代理人</option>
							<option value="OTHER" >其他</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="contactInfo.name" class="col-sm-2 control-label"><font color="red">*</font>联系人姓名</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="contactInfo.name" name="contactInfo.name" placeholder="" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="mobile" class="col-sm-2 control-label"><font color="red">*</font>联系人手机</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="mobile" name="mobile" placeholder="" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="phone" class="col-sm-2 control-label"><font color="red">*</font>联系人公司座机</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="phone" name="phone" placeholder="" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="email" class="col-sm-2 control-label"><font color="red">*</font>联系人邮箱</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="email" name="email" placeholder="" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="id_card_no" class="col-sm-2 control-label"><font color="red">*</font>联系人身份证号</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="id_card_no" name="id_card_no" placeholder="" value="">
					</div>
				</div>
				<div class="col-sm-offset-2 col-sm-10">
					<button type="button" onclick="previousStep('threeTag')"  class="btn btn-primary">返回</button>
					<button type="button" onclick="previousStep('threeTag')"  class="btn btn-primary">上一步</button>
					<button type="button" onclick="addButton()"  class="btn btn-primary">保存</button>
					<button type="button" onclick="addButton()"  class="btn btn-primary">提交审核</button>
				</div>
			</div>

		</div>
	</section>
	<script>
        var path = "<%=request.getContextPath()%>";
        var serverPath ="<%=cn.yesway.bmw.manage.utils.PropUtils.get("file_url")%>";
        //上一步
		function previousStep(obj) {
			if (obj == 'twoTag') {
				$("#oneTag").show();
				$("#twoTage").hide();
				$("#threeTag").hide();
			} else if (obj == 'threeTag') {
                $("#oneTag").hide();
                $("#twoTage").show();
                $("#threeTag").hide();
			}
        }
        //下一步
        function nextStep(obj) {
			if (obj == 'oneTag') {
                $("#oneTag").hide();
                $("#twoTage").show();
                $("#threeTag").hide();
			} else if (obj == 'twoTag') {
                $("#oneTag").hide();
                $("#twoTage").hide();
                $("#threeTag").show();
			}
        }
        //初始化页面
        function hiddenTag() {
            $("#twoTage").hide();
            $("#threeTag").hide();
        }
        hiddenTag();
        //保存
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
	<script src="${request.contextPath}/static/straightpayJs/appshotImg.js"></script>
	<script src="${request.contextPath}/static/straightpayJs/upload.js"></script>
	<script src="${request.contextPath}/static/straightpayJs/certImage.js"></script>
	<script src="${request.contextPath}/static/straightpayJs/legalCertFrontImg.js"></script>
	<script src="${request.contextPath}/static/straightpayJs/legalCertBackImg.js"></script>
	<script src="${request.contextPath}/static/straightpayJs/industryQualificationImg.js"></script>
	<script src="${request.contextPath}/static/straightpayJs/licenseAuthLetterImg.js"></script>
</body>

</html>