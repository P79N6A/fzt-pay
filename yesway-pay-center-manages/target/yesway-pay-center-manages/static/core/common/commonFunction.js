// JavaScript Document

function $(elementId){
	return document.getElementById(elementId);
}

function openModelWindow(url, width, height) {
	var returnValue, strStyle;
	strStyle = "help:no;minimize:no;maximize:yes;border:thick;statusbar:no;"
		+ "dialogWidth:" + width + "px;dialogHeight:" + height + "px;center:yes;status:no;edge: raised";
	returnValue = window.showModalDialog(url, window,  strStyle);
	return returnValue;
}

function openModellessWin(url, width, height) {
	var returnValue, strStyle;
	strStyle = "help:no;minimize:no;maximize:yes;border:thick;statusbar:no;"
		+ "dialogWidth:" + width + "px;dialogHeight:" + height + "px;center:yes;status:no;edge: raised";
	returnValue = window.showModelessDialog(url, window,  strStyle);
	return returnValue;
}
 
function OpenURLWH(url,width,Height)
{
    var strStyle="width="+width+", height="+Height+", top="+(screen.availHeight-Height)/2+",left="+(screen.availWidth-width)/2+",toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes";
    var w1=window.open(url,"",strStyle);
}

/**
*     str = str.trim();
*/
String.prototype.trim = function()
{
    return this.replace(/(^[\s]*)|([\s]*$)/g, "");
}
String.prototype.lTrim = function()
{
    return this.replace(/(^[\s]*)/g, "");
}
String.prototype.rTrim = function()
{
    return this.replace(/([\s]*$)/g, "");
}



function isNull(str)
{
  if(str==null)
  {
    return true;
  }
  else if(str.trim()==""||str.trim().length==0)
  {
    return true;
  }
  else
  {
    return false;
  }
}

/**
 * 判断是否为数字
 */
function isNumber(str1)
{
    return (/^[-]?[\d]*[.]?[\d]*$/.test(str1));
}

/**
 * 判断开始时间是否大于截止时间
 * @param beginId:开始时间ID
 * @param endId:截止时间ID
 * @return boolean
 */
function isAfterEnd(beginId,endId)
{
	 //开始时间
    var beginTime=document.getElementById(beginId);
    //结束时间
    var endTime=document.getElementById(endId);
    
    if(!isNull(beginTime.value)&&!isNull(endTime.value))
    {
      //比较开始时间和结束时间
      if(beginTime.value>endTime.value)
      {
        alert("开始时间不能大于结束时间，请重新填写!");
        beginTime.value="";
        endTime.value="";
        beginTime.focus();
        return false;
      }
    }
    
    return true;
}

/**
 * 限制只能输入数字，包括'.'
 */
function inputOnlyNumber() 
{ 
  if((event.keyCode>=48&&event.keyCode<=57)||event.keyCode==8||(event.keyCode>=96&&event.keyCode<=105) 
      ||event.keyCode==46||event.keyCode==37||event.keyCode==39||event.keyCode==190||event.keyCode==110) 
  { 
    event.returnValue=true; 
  } 
  else 
  { 
    event.returnValue=false; 
  } 
}
