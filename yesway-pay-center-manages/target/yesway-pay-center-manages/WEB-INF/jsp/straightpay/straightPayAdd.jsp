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
		.instructions {
			color:#CCCCCC;
		}
</style>
	<link rel="stylesheet" href="${request.contextPath}/static/plugins/datepicker/skin/WdatePicker.css">
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
					<label for="app_id" class="col-sm-2 control-label"><font color="red">*</font>应用ID</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="app_id" name="app_id" placeholder="请输入商户全称" value="${appId}">
					</div>
				</div>

				<div class="box-header">商户资质信息</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label"><font color="red">*</font>商户全称</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="name" name="name" placeholder="请输入商户全称" value="">
						<span class="instructions">主体名称需严格按照证件返回，在注册确认后不可更改。避免付款后因主体原因导致认证失败</span>
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
					<input type="hidden" name="cert_image_url" id="cert_image_url" value=""/>
					<label class="col-sm-2 control-label"><font color="red"></font>商户证件照片</label>
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
						<span class="instructions">请参考 https://mif-pub.alipayobjects.com/AlipayMCC.xlsx，输入商户类别码</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red"></font>门头照</label>
					<input type="hidden" name="out_door_images" id="out_door_images" value=""/>
					<input type="hidden" name="out_door_images_url" id="out_door_images_url" value=""/>
					<div id="imgTd" class="input-group col-sm-5">
						<div id="outDoorImageShot" class="uploadImages"></div>
						<div id="showImg" style="height:80px;"></div>
						<span id="msg" class="uploadTip">（请依据车机屏幕大小上传合适尺寸的图片，图片格式：支持JPG、PNG）</span>
						<%--<div id="imgBar" style="margin-right:0;overflow-y: scroll;"></div>--%>
						<div id="imgBar"></div>
					</div>
				</div>
				<div class="form-group">
					<label for="sign_time_with_isv" class="col-sm-2 control-label"><font color="red">*</font>二级商户与服务商的签约时间</label>
					<div class="input-group col-sm-5">
							<input type="text" class="form-control input-icon" aria-describedby="basic-addon2" name="sign_time_with_isv" id="sign_time_with_isv" value="" placeholder="请输入二级商户与服务商的签约时间"
								   onclick="WdatePicker({el:this,dateFmt:'yyyy-MM-dd'})"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>商户使用服务</label>
					<div class="input-group col-sm-5">
						<input type="checkbox" name="service" value="app支付" onclick="checkService()"/>app支付
						<input type="checkbox" name="service" value="当面付" onclick="checkService()"/>当面付
						<input type="checkbox" name="service" value="wap支付" onclick="checkService()"/>wap支付
						<input type="checkbox" name="service" value="电脑支付" onclick="checkService()"/>电脑支付
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
					<input type="hidden" name="legal_cert_front_image_url" id="legal_cert_front_image_url" value=""/>
					<label class="col-sm-2 control-label"><font color="red">*</font>法人身份证照</label>
					<div class="input-group col-sm-5">
						<div id="legalCertFrontImgButton" class="uploadImages"></div>
						<i>*</i><span id="legalCertFrontImgMessage">(上传图片尺寸：180px*180px，图片格式：支持jpg、png)</span>
						<div id="legalCertFrontImgContainer"></div>
					</div>
					<input type="hidden" name="legal_cert_back_image" id="legal_cert_back_image" value=""/>
					<input type="hidden" name="legal_cert_back_image_url" id="legal_cert_back_image_url" value=""/>
					<label class="col-sm-2 control-label"></label>
					<div class="input-group col-sm-5">
						<div id="legalCertBackImgButton" class="uploadImages"></div>
						<i>*</i><span id="legalCertBackImgMessage">(上传图片尺寸：180px*180px，图片格式：支持jpg、png)</span>
						<div id="legalCertBackImgContainer"></div>
					</div>
				</div>

				<div class="box-header">经营场所信息</div>
				<div class="form-group">
					<label for="address" class="col-sm-2 control-label"><font color="red">*</font>经营地址</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="address" name="address" placeholder="请输入经营地址" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="province_code" class="col-sm-2 control-label"><font color="red">*</font>企业所在省份</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="province_code" name="province_code" placeholder="请输入企业所在省份areaCode" value="">
						<span class="instructions">请下载国标省市区号表，对照后填写编号下载地址：http://aopsdkdownload.cn-hangzhou.alipay-pub.aliyun-inc.com/doc/2016.xls?spm=a219a.7629140.0.0.qRW4KQ&file=2016.xls</span>
					</div>
				</div>
				<div class="form-group">
					<label for="city_code" class="col-sm-2 control-label"><font color="red">*</font>企业所在城市</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="city_code" name="city_code" placeholder="请输入企业所在城市areaCode" value="">
						<span class="instructions">请下载国标省市区号表，对照后填写编号下载地址及内容同省编码</span>
					</div>
				</div>
				<div class="form-group">
					<label for="district_code" class="col-sm-2 control-label"><font color="red">*</font>企业所在区县</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="district_code" name="district_code" placeholder="请输入企业所在区县areaCode" value="">
						<span class="instructions">请下载国标省市区号表，对照后填写编号下载地址及内容同省编码</span>
					</div>
				</div>
				<div class="form-group">
					<label for="service_phone" class="col-sm-2 control-label"><font color="red">*</font>客服电话</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="service_phone" name="service_phone" placeholder="请输入客服电话" value="">
					</div>
				</div>

				<div class="box-header">特殊资质信息（非必填项，商户所属行业为特殊行业时为必填项）</div>
				<div class="form-group">
					<label for="industry_qualification_type" class="col-sm-2 control-label"><font color="red">*</font>行业资质类型</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="industry_qualification_type" name="industry_qualification_type" placeholder="请输入行业资质类型" value="">
						<span class="instructions">具体选值参见https://mif-pub.alipayobjects.com/QualificationType.xlsx</span>
					</div>
				</div>
				<div class="form-group">
					<input type="hidden" name="industry_qualification_image" id="industry_qualification_image" value=""/>
					<input type="hidden" name="industry_qualification_image_url" id="industry_qualification_image_url" value=""/>
					<label class="col-sm-2 control-label">行业资质照</label>
					<div class="input-group col-sm-5">
						<div id="industryQualificationImgButton" class="uploadImages"></div>
						<i>*</i><span id="industryQualificationImgMessage">(上传图片尺寸：180px*180px，图片格式：支持jpg、png)</span>
						<div id="industryQualificationImgContainer"></div>
					</div>
				</div>
				<div class="form-group">
					<input type="hidden" name="license_auth_letter_image" id="license_auth_letter_image" value=""/>
					<input type="hidden" name="license_auth_letter_image_url" id="license_auth_letter_image_url" value=""/>
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
				<div class="box-header">商户结算卡信息（本业务当前只允许传入一张结算卡。与支付宝账号字段二选一必填）</div>
				<div class="form-group">
					<label for="account_holder_name" class="col-sm-2 control-label"><font color="red">*</font>卡户名</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="account_holder_name" name="account_holder_name" placeholder="请输入卡户名" value="">
						<span class="instructions">例如：招商银行杭州高新支行</span>
					</div>
				</div>
				<div class="form-group">
					<label for="account_branch_name" class="col-sm-2 control-label"><font color="red">*</font>开户支行</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="account_branch_name" name="account_branch_name" placeholder="请输入开户支行" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="account_no" class="col-sm-2 control-label"><font color="red">*</font>账号</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="account_no" name="account_no" placeholder="请输入账号" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="account_inst_name" class="col-sm-2 control-label"><font color="red">*</font>开户总行</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="account_inst_name" name="account_inst_name" placeholder="请输入开户总行" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="account_inst_id" class="col-sm-2 control-label"><font color="red">*</font>开户行缩写</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="account_inst_id" name="account_inst_id" placeholder="请输入开户行缩写" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="account_type" class="col-sm-2 control-label"><font color="red">*</font>银行卡类型</label>
					<div class="input-group col-sm-5">
						<select class="form-control selectpicker show-tick" name="account_type"  id="account_type">
							<option value="DC" >借记卡</option>
							<option value="CC" >信用卡</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="usage_type" class="col-sm-2 control-label"><font color="red">*</font>使用类型</label>
					<div class="input-group col-sm-5">
						<select class="form-control selectpicker show-tick" name="usage_type"  id="usage_type">
							<option value="01" >对公</option>
							<option value="02" >对私</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="account_inst_province" class="col-sm-2 control-label"><font color="red">*</font>开户所在省份</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="account_inst_province" name="account_inst_province" placeholder="请输入开户所在省份" value="">
						<span class="instructions">例如：浙江省</span>
					</div>
				</div>
				<div class="form-group">
					<label for="account_inst_city" class="col-sm-2 control-label"><font color="red">*</font>开户所在城市</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="account_inst_city" name="account_inst_city" placeholder="请输入开户所在城市" value="">
						<span class="instructions">例如：杭州市</span>
					</div>
				</div>

				<div class="box-header">支付宝账号信息（用作结算账号。与商户结算卡信息二选一必填）</div>
				<div class="form-group">
					<label for="alipay_logon_id" class="col-sm-2 control-label"><font color="red">*</font>支付宝账号</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="alipay_logon_id" name="alipay_logon_id" placeholder="请输入支付宝账号" value="">
					</div>
				</div>

				<div class="box-header">开票信息</div>
				<div class="form-group">
					<label for="auto_invoice" class="col-sm-2 control-label"><font color="red">*</font>是否自动开票</label>
					<div class="input-group col-sm-5">
						<select class="form-control selectpicker show-tick" name="auto_invoice"  id="auto_invoice">
							<option value="true" >是</option>
							<option value="false" >否</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="accept_electronic" class="col-sm-2 control-label"><font color="red">*</font>接受电子发票</label>
					<div class="input-group col-sm-5">
						<select class="form-control selectpicker show-tick" name="accept_electronic"  id="accept_electronic">
							<option value="true" >接受</option>
							<option value="false" >不接受</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="tax_payer_qualification" class="col-sm-2 control-label"><font color="red">*</font>纳税人资格种类</label>
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
						<input type="text" class="form-control" id="title" name="title" placeholder="请输入发票抬头" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="tax_no" class="col-sm-2 control-label"><font color="red">*</font>纳税人识别号</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="tax_no" name="tax_no" placeholder="请输入纳税人识别号" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="tax_payer_valid_format" class="col-sm-2 control-label"><font color="red">*</font>纳税人资格认定时间</label>
					<div class="input-group col-sm-5">
							<input type="text" class="form-control input-icon" aria-describedby="basic-addon2" name="tax_payer_valid_format" id="tax_payer_valid_format" value=""
								   placeholder="请输入纳税人资格认定时间"
								   onclick="WdatePicker({el:this,dateFmt:'yyyy-MM-dd'})"/>
						<input type="hidden" id="tax_payer_valid" name="tax_payer_valid" value=""/>
					</div>
				</div>
				<div class="form-group">
					<label for="bank_account" class="col-sm-2 control-label"><font color="red">*</font>银行账号</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="bank_account" name="bank_account" placeholder="请输入银行账号" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="bank_name" class="col-sm-2 control-label"><font color="red">*</font>开户行名称</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="bank_name" name="bank_name" placeholder="请输入开户行名称" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="merchantInvoiceInfoAddress" class="col-sm-2 control-label"><font color="red">*</font>开票地址</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="merchantInvoiceInfoAddress" name="merchantInvoiceInfoAddress" placeholder="请输入开票地址" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="telephone" class="col-sm-2 control-label"><font color="red">*</font>开票电话</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="telephone" name="telephone" placeholder="请输入开票电话" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="mail_name" class="col-sm-2 control-label"><font color="red">*</font>收件人名称</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="mail_name" name="mail_name" placeholder="请输入收件人名称" value="">
					</div>
				</div>
				<%--<div class="form-group">--%>
					<%--<label for="address" class="col-sm-2 control-label"><font color="red">*</font>收件人地址</label>--%>
					<%--<div class="input-group col-sm-5">--%>
						<%--<input type="text" class="form-control" id="address" name="address" placeholder="" value="">--%>
					<%--</div>--%>
				<%--</div>--%>
				<div class="form-group">
					<label for="mail_telephone" class="col-sm-2 control-label"><font color="red">*</font>收件人电话</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="mail_telephone" name="mail_telephone" placeholder="请输入收件人电话" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="merchantInvoiceInfo.province_code" class="col-sm-2 control-label"><font color="red">*</font>企业所在省份</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="merchantInvoiceInfo.province_code" name="merchantInvoiceInfo.province_code" placeholder="请输入企业所在省份areaCode" value="">
						<span class="instructions">请下载国标省市区号表，对照后填写编号下载地址：http://aopsdkdownload.cn-hangzhou.alipay-pub.aliyun-inc.com/doc/2016.xls?spm=a219a.7629140.0.0.qRW4KQ&file=2016.xls</span>
					</div>
				</div>
				<div class="form-group">
					<label for="merchantInvoiceInfoCityCode" class="col-sm-2 control-label"><font color="red">*</font>企业所在城市</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="merchantInvoiceInfoCityCode" name="merchantInvoiceInfoCityCode" placeholder="请输入企业所在城市areaCode" value="">
						<span class="instructions">请下载国标省市区号表，对照后填写编号下载地址及内容同省编码</span>
					</div>
				</div>
				<div class="form-group">
					<label for="merchantInvoiceInfoDistrictCode" class="col-sm-2 control-label"><font color="red">*</font>企业所在区县</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="merchantInvoiceInfoDistrictCode" name="merchantInvoiceInfoDistrictCode" placeholder="请输入企业所在区县areaCode" value="">
						<span class="instructions">请下载国标省市区号表，对照后填写编号下载地址及内容同省编码</span>
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
					<label for="type" class="col-sm-2 control-label"><font color="red">*</font>联系人类型</label>
					<div class="input-group col-sm-5">
						<select class="form-control selectpicker show-tick" name="type"  id="type">
							<option value="LEGAL_PERSON" >法人</option>
							<option value="CONTROLLER" >实际控制人</option>
							<option value="AGENT" >代理人</option>
							<option value="OTHER" >其他</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="tag" class="col-sm-2 control-label"><font color="red">*</font>联系人的职责</label>
					<div class="input-group col-sm-5">
						<select class="form-control selectpicker show-tick" name="tag"  id="tag">
							<option value="02" >异议处理接口人</option>
							<option value="06" >商户关键联系人</option>
							<option value="11" >数据反馈接口人</option>
							<option value="08" >服务联动接口人</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="contactInfo.name" class="col-sm-2 control-label"><font color="red">*</font>联系人姓名</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="contactInfo.name" name="contactInfo.name" placeholder="请输入联系人姓名" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="mobile" class="col-sm-2 control-label"><font color="red">*</font>联系人手机</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="mobile" name="mobile" placeholder="请输入联系人手机" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="phone" class="col-sm-2 control-label"><font color="red">*</font>联系人公司座机</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="phone" name="phone" placeholder="请输入联系人公司座机" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="email" class="col-sm-2 control-label"><font color="red">*</font>联系人邮箱</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="email" name="email" placeholder="请输入联系人邮箱" value="">
					</div>
				</div>
				<div class="form-group">
					<label for="id_card_no" class="col-sm-2 control-label"><font color="red">*</font>联系人身份证号</label>
					<div class="input-group col-sm-5">
						<input type="text" class="form-control" id="id_card_no" name="id_card_no" placeholder="请输入联系人身份证号" value="">
					</div>
				</div>
				<div class="col-sm-offset-2 col-sm-10">
					<button type="button" onclick="previousStep('threeTag')"  class="btn btn-primary">返回</button>
					<button type="button" onclick="previousStep('threeTag')"  class="btn btn-primary">上一步</button>
					<%--<button type="button" onclick="addButton()"  class="btn btn-primary">保存</button>--%>
					<button type="button" onclick="addButton()"  class="btn btn-primary">提交审核</button>
				</div>
			</div>

		</div>
	</section>
	<script>
        var path = "<%=request.getContextPath()%>";
        var serverPath ="<%=cn.yesway.bmw.manage.utils.PropUtils.get("file_url")%>";
        var services;
        //组装商户使用的服务
        function checkService() {
            services = new Array();
            var obj = document.getElementsByName("service");
            for(k in obj){
                if(obj[k].checked)
                    services.push(obj[k].value);
            }
        }
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
            //地址
			var addressInfo_bizContent = {
				'city_code':$("#city_code").val(),
				'district_code':$("#district_code").val(),
				'address':$("#address").val(),
				'province_code':$("#province_code").val()
				// 'longitude':"",
				// 'latitude':"",
				// 'type':""
			};
			//地址
			var addressInfo_Merchant = {
				'city_code':$("#merchantInvoiceInfoCityCode").val(),
				'district_code':$("#merchantInvoiceInfoDistrictCode").val(),
				'address':$("#merchantInvoiceInfoAddress").val(),
				'province_code':$("#merchantInvoiceInfoProvinceCode").val()
				// 'longitude':"",
				// 'latitude':"",
				// 'type':""
			};
			//商户联系人信息
            var contactInfo = {
                'name':$("#contactInfo.name").val(),
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
                // 'tax_payer_valid':$("#tax_payer_valid").val(),
                'address':$("#address").val(),
                'telephone':$("#telephone").val(),
                'bank_account':$("#bank_account").val(),
                'mail_name':$("#mail_name").val(),
                'mail_telephone':$("#mail_telephone").val(),
                'bank_name':$("#bank_name").val(),
                'mail_address':addressInfo_Merchant
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
			// var siteInfo = {
             //    'site_type':"",
             //    'site_url':"",
             //    'site_name':"",
             //    'account':"",
             //    'password':""
			// };
			// var sites =  new Array();
			// 	sites[0] = siteInfo;
            var qualifications = new Array();
            	qualifications[0] = industryQualificationInfo;
            var biz_cards = new Array();
                biz_cards[0] = settleCardInfo;
            var contact_infos = new Array();
                contact_infos[0] = contactInfo;
            var out_door_images = new Array();
            out_door_images = $("#out_door_images").val().split(",");
            var out_door_images_url = new Array();
            out_door_images_url = $("#out_door_images_url").val().split(",");
            console.log(out_door_images);
            var bizContentStrengthen = {
                'app_id':$("#app_id").val(),
				'external_id':"",
				// 'out_biz_no':"",
				'name':$("#name").val(),
				'alias_name':$("#alias_name").val(),
				'merchant_type':$("#merchant_type").val(),
				'mcc':$("#mcc").val(),
				'cert_no':$("#cert_no").val(),
				'cert_type':$("#cert_type").val(),
				'cert_image':$("#cert_image").val(),
				'cert_image_url':$("#cert_image_url").val(),
				'legal_name':$("#legal_name").val(),
				'legal_cert_no':$("#legal_cert_no").val(),
				'legal_cert_front_image':$("#legal_cert_front_image").val(),
				'legal_cert_front_image_url':$("#legal_cert_front_image_url").val(),
				'legal_cert_back_image':$("#legal_cert_back_image").val(),
				'legal_cert_back_image_url':$("#legal_cert_back_image_url").val(),
				'service_phone':$("#service_phone").val(),
				'out_door_images':out_door_images,
				'out_door_images_url':out_door_images_url,
				'license_auth_letter_image':$("#license_auth_letter_image").val(),
				'license_auth_letter_image_url':$("#license_auth_letter_image_url").val(),
				'service':services,
				'sign_time_with_isv':$("#sign_time_with_isv").val(),
				'alipay_logon_id':$("#alipay_logon_id").val(),
				// 'cert_image_back':$("#cert_image_back").val(),
				'business_address': addressInfo_bizContent,
				'contact_infos':contact_infos,
				'biz_cards':biz_cards,
				'qualifications':qualifications,
				// 'sites': sites,
				'invoice_info': merchantInvoiceInfo,
                'tax_payer_valid_format':$("#tax_payer_valid_format").val(),
            };
            $.ajax({
                url: path+"/aliStraightPay/save",
                type:'POST',
                datetype : "json",
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(bizContentStrengthen),
                success:function(data){
                    if (data == 'success') {
                        alert("提交成功！");
					}  else {
                        alert("提交失败！");
					}
					window.location.href = path+"/aliStraightPay/straightPayList.html";
                }
            });
        }
	</script>
	<script src="${request.contextPath}/static/plugins/jquery/jquery-2.1.4.min.js"></script>
	<script src="<%=request.getContextPath()%>/static/core/My97DatePicker/WdatePicker.js"></script>
	<script src="${request.contextPath}/static/plugins/uploadStream/js/stream-v1.js"></script>
	<script src="${request.contextPath}/static/straightpayJs/outDoorImage.js"></script>
	<script src="${request.contextPath}/static/straightpayJs/upload.js"></script>
	<script src="${request.contextPath}/static/straightpayJs/certImage.js"></script>
	<script src="${request.contextPath}/static/straightpayJs/legalCertFrontImg.js"></script>
	<script src="${request.contextPath}/static/straightpayJs/legalCertBackImg.js"></script>
	<script src="${request.contextPath}/static/straightpayJs/industryQualificationImg.js"></script>
	<script src="${request.contextPath}/static/straightpayJs/licenseAuthLetterImg.js"></script>
</body>

</html>