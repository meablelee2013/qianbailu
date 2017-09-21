//if(self!=top){top.location=self.location;}
var url ='.';
var action = new Array();

action['root'] = url+'/action/root.php';
action['pagerank'] = url+'/action/pagerank.php';
action['engine_record'] = url+'/action/engine_record.php';
action['whois'] = url+'/action/whois.php';
action['alexa'] = url+'/action/alexa.php';
action['ip'] = url+'/action/ip.php';
action['engine_keyword'] = url+'/action/engine_keyword.php';
action['engine_link'] = url+'/action/engine_link.php';
action['density'] = url+'/action/density.php';
action['special_htmlchar'] = url+'/action/special_htmlchar.php';
action['source'] = url+'/action/source.php';
action['rate'] = url+'/action/rate.php';
action['express_cost'] = url+'/action/express_cost.php';
action['links'] = url+'/action/links.php';
action['mobile'] = url+'/action/mobile.php';
action['postalcode'] = url+'/action/postalcode.php';
action['wubi'] = url+'/action/wubi.php';
action['alexa_contrast'] = url+'/action/alexa_contrast.php';

//ajax请求时间限制
var timeout = 10000;

String.prototype.is_domain = function(){

	//出除两边空格
	var _this = this.replace(/(^\s*)|(\s*$)/g,'').toLowerCase();

	//是否输入“http://”?
	var regular = /[a-zA-z]+:\/\/(.*)/;

	if(!regular.test(_this)){ _this = 'http://'+_this; }

	regular = /\b((https?|ftp):\/\/[-a-z0-9]+(\.[-a-z0-9]+)*\.(?:com|edu|gov|int|mil|net|org|biz|info|name|museum|coop|aero|[a-z][a-z]|((25[0-5])|(2[0-4]\d)|(1\d\d)|([1-9]\d)|\d))\b(\/[-a-z0-9_:\@&?=+,.!/~%\$]*)?)/;

	if (regular.test(_this)){

		return _this;

	}else{

		alert('请输入正确的域名！');

		return false;

	}

}

String.prototype.trim = function(){
	return this.replace(/(^\s*)|(\s*$)/g,'');
}


function suburl(url){

	var reg =   /<>\"\'/g;

	url =   url.trim();

	url =   url.replace(reg,"");

	var regex = /.*\:\/\/([^\/]*).*/;

	var match = url.match(regex);

	if(typeof match != "undefined" && null != match){

		return match[1];

	}
}



function is_ip(){

	var _ip = document.getElementById('domain').value;

	var exp=/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
	var reg = _ip.match(exp);
	if(reg==null){
		return false;
	}
	else
	{
		return _ip;
	}
}



// pagerank 查询
function get_pagerank(){
	var domain = $('#domain').val().is_domain();
	if(domain){
		$("#pagerank").ajaxStart(function(){$(this).html('<div class="loading">正在加载...</div>')})
		$.ajax({
			type: "post",
			cache:false,
			url: action['pagerank'],
			data: "action=pagerank&domain="+domain,
			timeout:timeout,
			success: function(data){
				$('#copyvalue').val($('#thisurl').val()+'?domain='+domain.replace(/^http:\/\//,''));
				$('#copyblock').show();
				$('#pagerank').html(data);
			},
			error: function(){
				$('#pagerank').html('服务器繁忙，请稍后再试！');
			}
		});
	}
}








// 全选/反选
function select_all(object) {

	var e = object.form.elements;

	for (var i = 0; i < object.form.elements.length; i++) {

		if (e[i].name != object.name && e[i].type == 'checkbox' ){

			e[i].checked = object.checked;

		}

	}

}

// whois
function get_whois(){

	var domain = $('#domain').val().is_domain();

	if(!domain){

		return false;

	}

	$("#whois").ajaxStart(function(){$(this).html('<div class="loading">正在加载...</div>')});

	$.ajax({
		type: "post",
		cache:false,
		url: action['whois'],
		data: "action=whois&domain="+domain,
		timeout:timeout,
		success: function(data){
			$('#copyvalue').val($('#thisurl').val()+'?domain='+domain.replace(/^http:\/\//,''));
			$('#copyblock').show();
			$('#whois').html(data);
		},
		error: function(){
			$('#whois').html('服务器繁忙，请稍后再试！');
		}
	});

}


//aleax
function get_alexa(_domain){

	if(!_domain){

		_domain = $('#domain').val().is_domain();

	}

	if(_domain){

		$("#alexa").ajaxStart(function(){$(this).html('<div class="loading">正在加载...</div>')})

		$.ajax({
			type: "post",
			cache:false,
			url: action['alexa'],
			data: "action=alexa&domain="+_domain,
			timeout:timeout,
			success: function(data){
				$('#copyvalue').val($('#thisurl').val()+'?domain='+_domain.replace(/^http:\/\//,''));
				$('#copyblock').show();
				$('#alexa').html(data);
			},
			error: function(){
				$('#alexa').html('服务器繁忙，请稍后再试！');
			}
		});

	}

	return false;
}


function get_alexa_contrast(_domain){



	if(!_domain){

		_domain = $('#domain').val();

	}


	if(_domain){

		$("#alexa").ajaxStart(function(){$(this).html('<div class="loading">正在加载...</div>')})

		$.ajax({
			type: "post",
			cache:false,
			url: action['alexa_contrast'],
			data: "action=alexa_contrast&domain="+_domain,
			timeout:timeout,
			success: function(data){
				$('#copyvalue').val($('#thisurl').val()+'?domain='+_domain.replace(/^http:\/\//,''));
				$('#copyblock').show();
				$('#alexa').html(data);
			},
			error: function(){
				$('#alexa').html('服务器繁忙，请稍后再试！');
			}
		});

	}

	return false;


}



//aleax
function get_ip(){

	var domain = $('#domain').val().is_domain();

	if(!domain){

		return false;

	}

	domain = suburl(domain);



	$("#ip").ajaxStart(function(){$(this).html('<div class="loading">正在加载...</div>')})

	$.ajax({
		type: "post",
		cache:false,
		url: action['ip'],
		data: "action=ip&domain="+domain,
		timeout:timeout,
		success: function(data){
			$('#copyvalue').val($('#thisurl').val()+'?domain='+domain.replace(/^http:\/\//,''));
			$('#copyblock').show();
			$('#ip').html(data);
		},
		error: function(){
			$('#ip').html('服务器繁忙，请稍后再试！');
		}
	});

}


// get_source 获取原代码
function get_source(){
	var domain = $('#url').val().is_domain();
	if(domain){
		$("#html_source1").ajaxStart(function(){$(this).html('<div class="loading">正在加载...</div>')})
		$.ajax({
			type: "post",
			cache:false,
			url: action['source'],
			data: "action=get_source&url="+domain,
			timeout:timeout,
			success: function(data){

				$('#copybutton').show();
				$('#html_source1').val(data);
			},
			error: function(){
				$('#html_source1').val('服务器繁忙，请稍后再试！');
			}
		});
	}
}


//special_htmlchar
function get_special_htmlchar(div_id, key_word){

	$('#'+div_id).ajaxStart(function(){$(this).html('<div class="loading">正在加载...</div>')});

	$.ajax({
		type: "post",
		cache:false,
		url: action['special_htmlchar'],
		data: "action=special_htmlchar&key_word="+key_word,
		timeout:timeout,
		success: function(data){
			$('#'+div_id).html(data);
		},
		error: function(){
			$('#'+div_id).html('服务器繁忙，请稍后再试！');
		}
	});

}

//robot
function get_robot(){

	var domain = $('#domain').val().is_domain();

	if(!domain){

		return false;

	}

	$("#robot").ajaxStart(function(){$(this).html('<div class="loading">正在加载...</div>')})

	$.ajax({
		type: "post",
		cache:false,
		url: action['root'],
		data: "action=robot&domain="+domain,
		timeout:timeout,
		success: function(data){
			$('#copybutton').show();
			$('#robot').html(data);
		},
		error: function(){
			$('#robot').html('服务器繁忙，请稍后再试！');
		}
	});

}

//density
function get_density(){

	var domain = $('#domain').val().is_domain();

	if(!domain){

		return false;

	}

	$("#density").ajaxStart(function(){$(this).html('<div class="loading">正在加载...</div>')})

	$.ajax({
		type: "post",
		cache:false,
		url: action['density'],
		data: "action=density&domain="+domain,
		timeout:timeout,
		success: function(data){
			$('#density').html(data);
		},
		error: function(){
			$('#density').html('服务器繁忙，请稍后再试！');
		}
	});


}

// keyword
function get_keyword(){

	var domain = $('#domain').val().is_domain();

	if(!domain){

		return false;

	}

	var keyword = $('#keyword').val();

	if(!keyword.trim()){

		alert('请输入要查询的关键字！');

		return false;

	}

	var page = $('#page').val();

	var engine = $('#engine').val();

	$("#engine_keyword").ajaxStart(function(){$(this).html('<div class="loading">正在加载...</div>')})

	$.ajax({
		type: "post",
		cache:false,
		url: action['engine_keyword'],
		data: "action=keyword&domain="+domain+'&engine='+engine+'&keyword='+keyword+'&page='+page,
		timeout:100000,
		success: function(data){
			$('#engine_keyword').html(data);
		},
		error: function(){
			$('#engine_keyword').html('服务器繁忙，请稍后再试！');
		}
	});

}

//rate
function get_rate(){

	$("#rate").ajaxStart(function(){$(this).html('<div class="loading">正在加载...</div>')})

	var a = document.getElementById('a').value;

	var b = document.getElementById('b').value;

	var number = document.getElementById('number').value;

	//是否为数字
	var regular=/^\d+\.?\d*$/;

	if(!regular.test(number)){

		alert('请您输入有效的数字！');

		return false;

	}

	$.ajax({
		type: "post",
		cache:false,
		url: action['rate'],
		data: "action=rate&a="+a+'&b='+b+'&number='+number,
		timeout:100000,
		success: function(data){
			$('#rate').html(data);
		},
		error: function(){
			$('#rate').html('服务器繁忙，请稍后再试！');
		}
	});

}

function set_rate_img(val,object){

	document.getElementById('rate_img').src = val;

	var a = document.getElementsByTagName('a');

	for(var i=0; i < a.length; i++){
		if(a[i].className = 'active'){
			a[i].className = '';
		}
	}

	object.className = 'active';

}

//导航
function change_navigation(object,number){

	$("#toolmenu li").removeClass();

	object.parentNode.className = 'focu';

	for(var i=0;i<10;i++){
		if(document.getElementById('pack'+i) != null){
			document.getElementById('pack'+i).style.display ='none';
		}
	}

	document.getElementById('pack'+number).style.display ='block';

}

function copy(txt)
{
	if (window.clipboardData)
	{
		window.clipboardData.setData("Text",txt);
	}
	else
	{
		txt = encodeURIComponent(txt);

		var flashcopier = 'flashcopier';
		if(!document.getElementById(flashcopier))
		{
			var divholder = document.createElement('div');
			divholder.id = flashcopier;
			document.body.appendChild(divholder);
		}
		document.getElementById(flashcopier).innerHTML = '';
		var divinfo = '<embed src="js/clipboard.swf" flashvars="clipboard='+txt+'" type="application/x-shockwave-flash" width="0" height="0"></embed>';
		document.getElementById(flashcopier).innerHTML = divinfo;
	}

	alert("复制成功");
}


/*加入本机书签*/
function add_bookmark() {
	var title = document.title;
	var url = window.location;
	if (window.sidebar) {
		window.sidebar.addPanel(title, url,"");
	} else if( document.all ) {
		window.external.AddFavorite( url, title);
	} else if( window.opera && window.print ) {
		return true;
	}
}

function isIE() {
	if(document.all) return true;
	return false;
}

/*设置为首页*/
function set_homepage(url){
	if (document.all)
	{
		document.body.style.behavior='url(#default#homepage)';
		document.body.setHomePage(url);
	}else{
		if (window.sidebar)
		{
			try {
				netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
			}
			catch (e)
			{
				alert("此操作被浏览器拒绝！\n请在浏览器地址栏输入“about:config”并回车\n然后将[signed.applets.codebase_principal_support]设置为'true'");
				return false;
			}
			var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefBranch);
			prefs.setCharPref('browser.startup.homepage',url);

		}
	}
}

function selectTag(showContent,selfObj){
	var tag = document.getElementById("searchmenu").getElementsByTagName("li");
	var taglength = tag.length;
	for(i=0; i<taglength; i++){
		tag[i].className = "";
	}
	selfObj.parentNode.className = "focu";
	for(i=0; j=document.getElementById("baidu"+i); i++){
		j.style.display = "none";
	}
	document.getElementById(showContent).style.display = "block";
}
function clicklogo(){
	//if( !isIE() || checkhomepage()){
	window.location=('http://tool.114la.com/index.html');
	//}else{
	//  document.getElementById('logourl').style.behavior='url(#default#homepage)';
	//  document.getElementById('logourl').setHomePage('http://tool.114la.com/index.html');
	//}
}
function checkhomepage(){
	if (document.getElementById('logo').isHomePage("http://tool.114la.com/index.html") ||
	document.getElementById('logo').isHomePage("http://tool.114la.com")||
	document.getElementById('logo').isHomePage("http://tool.114la.com/"))
	{
		return true;
	}else{
		return false;
	}
}

function get_alexa_map(url){

	var time = document.getElementById('time').value;

	var type_group = document.getElementsByName('type');
	var type_value = null;
	for (var i = 0; i < type_group.length; i++) {
		if (type_group[i].checked) {
			type_value = type_group[i].value;
		}
	}

	var map_api = 'http://traffic.alexa.com/graph?w=700&h=260&r='+time+'&y='+type_value+url;



	document.getElementById('map').src = map_api;

}



function getQueryString(name) {
	if(location.href.indexOf("?")==-1 || location.href.indexOf(name+'=')==-1)
	{
		return '';
	}

	var queryString = location.href.substring(location.href.indexOf("?")+1);
	var parameters = queryString.split("&");
	var pos, paraName, paraValue;
	for(var i=0; i<parameters.length; i++)     {
		pos = parameters[i].indexOf('=');
		if(pos == -1) { continue; }
		paraName = parameters[i].substring(0, pos);
		paraValue = parameters[i].substring(pos + 1);
		if(paraName == name){
			//alert(paraValue.replace(/\+/g, " "));
			//return unescape(paraValue.replace(/\+/g, " "));
			//return paraValue.replace(/\+/g, " ");
			return encodeURIComponent(paraValue.replace(/\+/g, " "));
		}
	}

	return '';
};



//全一快递货件
function _apex100(){
	var borcode = document.getElementById('borcode').value;
	borcode = '%27'+borcode+'%27,%27%27';
	window.open('http://www.apex100.com/trace.aspx?barcode='+borcode);
	return false;
}

//快递费用查询
function get_express_cost(start,end,weight,page){

	$("#express_cost").ajaxStart(function(){$(this).html('<div class="loading">正在加载...</div>')})

	$.ajax({
		type: "post",
		cache:false,
		url: action['express_cost'],
		data: "action=express_cost&start="+start+"&end="+end+"&weight="+weight+"&page="+page,
		timeout:timeout,
		success: function(data){
			$('#express_cost').html(data);
		},
		error: function(){
			$('#express_cost').html('服务器繁忙，请稍后再试！');
		}
	});

	return false;

}



//快递费用查询
function get_links(){

	var domain = $('#domain').val().is_domain();

	if(!domain){

		return false;

	}

	$("#links").ajaxStart(function(){$(this).html('<div class="loading">正在加载...</div>')})

	$.ajax({
		type: "post",
		cache:false,
		url: action['links'],
		data: "action=links&domain="+domain,
		timeout:timeout,
		success: function(data){
			$('#links').html(data);
		},
		error: function(){
			$('#links').html('服务器繁忙，请稍后再试！');
		}
	});

	return false;

}

function links_baidu(id,domain){

	$.ajax({
		type: "post",
		cache:false,
		url: action['links'],
		data: "action=links_baidu&domain="+domain,
		timeout:300000,
		success: function(data){
			$('#b'+id).html(data);
		},
		error: function(){
			$('#b'+id).html('服务器繁忙，请稍后再试！');
		}
	});


}

function links_pr(id,domain){

	$.ajax({
		type: "post",
		cache:false,
		url: action['links'],
		data: "action=links_pr&domain="+domain,
		timeout:300000,
		success: function(data){
			$('#p'+id).html(data);
		},
		error: function(){
			$('#p'+id).html('服务器繁忙，请稍后再试！');
		}
	});

}

function links_info(id,domain,url){

	$.ajax({
		type: "post",
		cache:false,
		url: action['links'],
		data: "action=links_info&domain="+domain+"&url="+url,
		timeout:300000,
		success: function(data){
			$('#i'+id).html(data);
		},
		error: function(){
			$('#i'+id).html('服务器繁忙，请稍后再试！');
		}
	});

}

function get_mobile(){

	$("#info").ajaxStart(function(){$(this).html('<div class="loading">正在加载...</div>')})



	var number = document.getElementById('number').value;

	if(/^(13[0-9]|15[0-9]|18[8|9])\d{8}$/.test(number)){

	$.ajax({
		type: "post",
		cache:false,
		url: action['mobile'],
		data: "action=mobile&number="+number,
		timeout:300000,
		success: function(data){
			$('#copyvalue').val($('#thisurl').val()+'?mobile='+number);
			$('#copyblock').show();
			$('#info').html(data);
		},
		error: function(){
			$('#info').html('服务器繁忙，请稍后再试！');
		}
	});

}else{

	alert('请您输入正确的手机号码!');

}

return false;
}


function get_postalcode(val,type,url){

	$("#postalcode").ajaxStart(function(){$(this).html('<div class="loading">正在加载...</div>')})

	$.ajax({
		type: "post",
		cache:false,
		url: url+'/action/postalcode.php',
		data: "action=postalcode&val="+val+"&type="+type,
		timeout:300000,
		success: function(data){
			$('#postalcode').html(data);
		},
		error: function(){
			$('#postalcode').html('服务器繁忙，请稍后再试！');
		}
	});
}

function get_wubi(){
	var chinese = $("#chinese").val();

	if(chinese.trim() == ''){
		alert("请您输入您要查询的汉字！");
		return false;
	}

	$("#wubi").ajaxStart(function(){$(this).html('<div class="loading">正在加载...</div>')})

	$.ajax({
		type: "post",
		cache:false,
		url: url+'/action/wubi.php',
		data: "action=wubi&chinese="+chinese,
		timeout:300000,
		success: function(data){
			$('#wubi').html(data);
		},
		error: function(){
			$('#wubi').html('服务器繁忙，请稍后再试！');
		}
	});
	return false;
}